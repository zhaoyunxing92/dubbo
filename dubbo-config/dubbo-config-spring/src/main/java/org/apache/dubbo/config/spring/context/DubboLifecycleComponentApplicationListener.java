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
package org.apache.dubbo.config.spring.context;


import org.apache.dubbo.common.context.Lifecycle;
import org.apache.dubbo.config.DubboShutdownHook;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.Collections.emptyList;
import static java.util.ServiceLoader.load;
import static org.springframework.beans.factory.BeanFactoryUtils.beansOfTypeIncludingAncestors;

/**
 * A {@link ApplicationListener listener} for the {@link Lifecycle Dubbo Lifecycle} components
 *
 * @see {@link Lifecycle Dubbo Lifecycle}
 * @see SmartApplicationListener
 * @since 2.7.4
 */
public class DubboLifecycleComponentApplicationListener implements ApplicationListener {

    private List<Lifecycle> lifecycleComponents = emptyList();

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (!supportsEvent(event)) {
            return;
        }

        if (event instanceof ContextRefreshedEvent) {
            onContextRefreshedEvent((ContextRefreshedEvent) event);
        } else if (event instanceof ContextClosedEvent) {
            onContextClosedEvent((ContextClosedEvent) event);
        }
    }

    protected void onContextRefreshedEvent(ContextRefreshedEvent event) {
        initLifecycleComponents(event);
        startLifecycleComponents();
    }

    protected void onContextClosedEvent(ContextClosedEvent event) {
        DubboShutdownHook.getDubboShutdownHook().doDestroy();
    }

    private void initLifecycleComponents(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        ClassLoader classLoader = context.getClassLoader();
        lifecycleComponents = new LinkedList<>();
        // load the instances of Lifecycle from ServiceLoader
        loadLifecycleComponents(lifecycleComponents, classLoader);
        // load the Beans of Lifecycle from ApplicationContext
        loadLifecycleComponents(lifecycleComponents, context);
    }

    private void loadLifecycleComponents(List<Lifecycle> lifecycleComponents, ClassLoader classLoader) {
        ServiceLoader<Lifecycle> serviceLoader = load(Lifecycle.class, classLoader);
        serviceLoader.forEach(lifecycleComponents::add);
    }

    private void loadLifecycleComponents(List<Lifecycle> lifecycleComponents, ApplicationContext context) {
        lifecycleComponents.addAll(beansOfTypeIncludingAncestors(context, Lifecycle.class).values());
    }

    private void startLifecycleComponents() {
        lifecycleComponents.forEach(Lifecycle::start);
    }

//    private void destroyLifecycleComponents() {
//        lifecycleComponents.forEach(Lifecycle::destroy);
//    }

    /**
     * the specified {@link ApplicationEvent event} must be {@link ApplicationContextEvent} and
     * its correlative {@link ApplicationContext} must be root
     *
     * @param event
     * @return
     */
    private boolean supportsEvent(ApplicationEvent event) {
        return event instanceof ApplicationContextEvent &&
                isRootApplicationContext((ApplicationContextEvent) event);
    }


    private boolean isRootApplicationContext(ApplicationContextEvent event) {
        return event.getApplicationContext().getParent() == null;
    }
}
