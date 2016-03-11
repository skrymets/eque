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

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author skrymets
 */
@Component
public class ProfilingBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ProfilingBeanPostProcessor.class);

    private final Map<String, Class> profilingClasses = new HashMap<>();

    private final ProfilingController profilingController = new ProfilingController();

    public ProfilingBeanPostProcessor() {
        try {
            ManagementFactory
                    .getPlatformMBeanServer()
                    .registerMBean(profilingController, ObjectName.getInstance("my.school", "profiling", "profiling"));
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException ex) {
            LOG.error(ex.getMessage());
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(Profiling.class)) {
            profilingClasses.put(beanName, bean.getClass());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClazz = profilingClasses.get(beanName);
        if (beanClazz != null) {
            Object proxyInstance = Proxy.newProxyInstance(
                    beanClazz.getClassLoader(),
                    beanClazz.getInterfaces(),
                    (Object proxy, Method method, Object[] args) -> {
                        LOG.info("Call for {}::{}", proxy.getClass().getName(), method.getName());
                        Object ret;
                        if (profilingController.isEnabled()) {
                            long startTime = System.nanoTime();
                            ret = method.invoke(bean, args);
                            long endTime = System.nanoTime();
                            LOG.info("PROF:8150F577C514: {}", endTime - startTime);
                        } else {
                            ret = method.invoke(bean, args);
                        }
                        ret.getClass().getName();
                        return ret;
                    });
            LOG.info("Creating a PROXY for {}: {} over target of class {} (Defined as: {})", 
                    beanName, 
                    proxyInstance.getClass().getName(), 
                    bean.getClass().getName(), 
                    beanClazz.getName()
            );
            return proxyInstance;
        }
        return bean;
    }

}
