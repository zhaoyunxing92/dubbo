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
package org.apache.dubbo.registry.client.event.listener;

import org.apache.dubbo.event.EventListener;
import org.apache.dubbo.registry.client.ServiceInstance;
import org.apache.dubbo.registry.client.ServiceInstanceCustomizer;
import org.apache.dubbo.registry.client.event.ServiceInstancePreRegisteredEvent;

import java.util.Iterator;
import java.util.ServiceLoader;

import static java.util.ServiceLoader.load;


/**
 * An {@link EventListener event listener} to customize {@link ServiceInstance the service instance} by the instances of
 * {@link ServiceInstanceCustomizer} {@link ServiceLoader SPI}.
 *
 * @see EventListener
 * @see ServiceInstancePreRegisteredEvent
 * @see ServiceInstanceCustomizer
 * @since 2.7.4
 */
public class CustomizableServiceInstanceListener implements EventListener<ServiceInstancePreRegisteredEvent> {

    @Override
    public void onEvent(ServiceInstancePreRegisteredEvent event) {

        ServiceLoader<ServiceInstanceCustomizer> customizers = load(ServiceInstanceCustomizer.class);

        Iterator<ServiceInstanceCustomizer> iterator = customizers.iterator();

        while (iterator.hasNext()) {
            ServiceInstanceCustomizer customizer = iterator.next();
            // customizes
            customizer.customize(event.getServiceInstance());
        }
    }
}
