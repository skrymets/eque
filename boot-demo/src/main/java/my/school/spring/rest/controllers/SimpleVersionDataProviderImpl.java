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

import javax.annotation.PostConstruct;
import my.school.spring.beans.CustomDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@UseSchedules(false)
public class SimpleVersionDataProviderImpl implements VersionDataProvider {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleVersionDataProviderImpl.class);
    
    @CustomDataProvider(data = "1.0.0")
    private String versionInfo;
    
    @PostConstruct
    public void sayHello() {
        LOG.info("Simple Version Provider");
    }
    
    @Override
    public String getVersionInfo() {
        return versionInfo;
    }
}
