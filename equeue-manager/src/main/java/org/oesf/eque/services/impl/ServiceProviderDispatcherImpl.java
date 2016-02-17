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
package org.oesf.eque.services.impl;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.oesf.eque.services.ServiceProviderDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServiceProviderDispatcherImpl implements ServiceProviderDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceProviderDispatcherImpl.class);

    private final Queue<Integer> awaitedQueue = new LinkedBlockingQueue<>();

}
