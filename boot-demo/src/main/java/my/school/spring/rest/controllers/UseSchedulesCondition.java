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
package my.school.spring.rest.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *
 * @author skrymets
 */
class UseSchedulesCondition implements Condition {

    private static final Logger LOG = LoggerFactory.getLogger(UseSchedulesCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        
        final Map<String, Object> annotationAttributes = Optional
                .ofNullable(metadata.getAnnotationAttributes(UseSchedules.class.getName()))
                .orElse(new HashMap<>());
        Boolean isScheduledInstance = (Boolean) annotationAttributes.getOrDefault("value", false);

        Boolean allowScheduled = Optional
                .ofNullable(context.getEnvironment().getProperty("setup.versionProvider.scheduled", Boolean.class))
                .orElse(false);
        final boolean scheduled = Objects.equals(allowScheduled, isScheduledInstance);
        LOG.info("Application will reuse {} implementation of the VersionProvider", (scheduled) ? "SCHEDULED" : "NOT SCHEDULED");
        return scheduled;
    }

}
