/*
 * Copyright 2016 Pivotal Software, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package my.school.spring.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author skrymets
 */
@Component
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(PostProxyInvokerContextListener.class);

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();

        for (String beanName : context.getBeanDefinitionNames()) {
            final String beanClassName = beanFactory.getBeanDefinition(beanName).getBeanClassName();
            if (beanClassName == null) {
                continue;
            }
            try {
                Class<?> originalClass = Class.forName(beanClassName);
                for (Method originalMethod : originalClass.getMethods()) {
                    if (originalMethod.isAnnotationPresent(PostProxy.class)) {
                        try {
                            Object bean = context.getBean(beanName);
                            Method currentMethod = bean.getClass().getMethod(originalMethod.getName(), originalMethod.getParameterTypes());
                            currentMethod.invoke(bean);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                            LOG.error(ex.getLocalizedMessage());
                        }
                    }
                }
            } catch (NullPointerException | ClassNotFoundException ex) {
                LOG.warn("Failed to find bean's class: {} (bean name: {})", beanClassName, beanName);
            }
        }
    }

}
