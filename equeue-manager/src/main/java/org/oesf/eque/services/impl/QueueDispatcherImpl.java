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

import org.oesf.eque.services.domain.NumberStates;
import java.time.LocalDateTime;
import org.oesf.eque.services.exceptions.ChangeStateException;
import java.util.NoSuchElementException;
import org.oesf.eque.services.exceptions.NoFreePlaceAvailableException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import static org.oesf.eque.services.domain.NumberStates.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.oesf.eque.services.QueueDispatcher;

@Service
public class QueueDispatcherImpl implements QueueDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(QueueDispatcherImpl.class);

    private final BlockingQueue<Integer> freeQueue = new PriorityBlockingQueue<>();
    private final BlockingQueue<Integer> occupiedQueue = new LinkedBlockingQueue<>();

    private final Queue<Integer> servicedQueue = new LinkedBlockingQueue<>();
    private final Queue<Integer> cancelledQueue = new LinkedBlockingQueue<>();

    private final Lock lock = new ReentrantLock();
    private ExecutorService threadPool;

    public QueueDispatcherImpl() {
    }

    @Override
    public Integer allocateNumber() throws NoFreePlaceAvailableException {

        Lock atomic = this.lock;

        try {
            atomic.lock();

            occupiedQueue.add(freeQueue.element());
            Integer allocatedNumber = freeQueue.remove();
            LOG.debug("QDS-34C5F87A3F23: The on-demand allocated number is: \"{}\"", allocatedNumber);

            notifyStateChange(allocatedNumber, LocalDateTime.now(), FREE, OCCUPIED);
            return allocatedNumber;

        } catch (IllegalStateException ex) { // occupiedQueue.add()

            LOG.error("QDS-0EAE6367CEEE: Can not occupy a number.");
            throw new ChangeStateException();

        } catch (NoSuchElementException ex) { // freeQueue .element() | .remove()

            LOG.debug("QDS-3A2DFE3EE673: Allocating a new place number.");
            throw new NoFreePlaceAvailableException();

        } finally {
            atomic.unlock();
        }
    }

        
    @PostConstruct
    public void setup() {
        threadPool = Executors.newCachedThreadPool();
        LOG.debug("QDS-C53016E36B8B: Is up and running.");
    }

    @PreDestroy
    public void tearDown() {
        threadPool.shutdown();
    }

    private void notifyStateChange(Integer number, LocalDateTime when, NumberStates oldState, NumberStates newState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
