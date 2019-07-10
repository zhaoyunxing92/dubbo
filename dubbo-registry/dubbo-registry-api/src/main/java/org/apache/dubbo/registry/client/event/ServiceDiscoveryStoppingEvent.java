/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.registry.client.event;

import org.apache.dubbo.event.Event;
import org.apache.dubbo.registry.client.ServiceDiscovery;

/**
 * An event raised when the {@link ServiceDiscovery Service Discovery} is stopping.
 *
 * @see ServiceDiscovery#stop()
 * @since 2.7.4
 */
public class ServiceDiscoveryStoppingEvent extends Event {

    /**
     * Constructs a prototypical Event.
     *
     * @param serviceDiscovery The instance of {@link ServiceDiscovery} as source
     * @throws IllegalArgumentException if source is null.
     */
    public ServiceDiscoveryStoppingEvent(ServiceDiscovery serviceDiscovery) {
        super(serviceDiscovery);
    }

    /**
     * Get the instance of {@link ServiceDiscovery} as source
     *
     * @return the instance of {@link ServiceDiscovery} as source
     */
    public ServiceDiscovery getServiceDiscovery() {
        return (ServiceDiscovery) getSource();
    }
}
