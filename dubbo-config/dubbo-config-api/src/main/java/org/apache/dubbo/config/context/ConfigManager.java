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
package org.apache.dubbo.config.context;

import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.AbstractConfig;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ModuleConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.dubbo.common.Constants.DEFAULT_KEY;

/**
 * TODO
 * Experimental API, should only being used internally at present.
 * Maybe we can consider open to end user in the following version by providing a fluent style builder.
 *
 * <pre>{@code
 *  public void class DubboBuilder() {
 *
 *      public static DubboBuilder create() {
 *          return new DubboBuilder();
 *      }
 *
 *      public DubboBuilder application(ApplicationConfig application) {
 *          ConfigManager.getInstance().addApplication(application);
 *          return this;
 *      }
 *
 *      ...
 *
 *      public void build() {
 *          // export all ServiceConfigs
 *          // refer all ReferenceConfigs
 *      }
 *  }
 * </pre>
 *
 * TODO
 * The properties defined here are duplicate with that in ReferenceConfig/ServiceConfig,
 * the properties here are currently only used for duplication check but are still not being used in the export/refer process yet.
 * Maybe we can remove the property definition in ReferenceConfig/ServiceConfig and only keep the setXxxConfig() as an entrance.
 * All workflow internally can rely on ConfigManager.
 * }
 */
public class ConfigManager {
    private static final ConfigManager configManager = new ConfigManager();

    public static ConfigManager getInstance() {
        return configManager;
    }

    private ApplicationConfig application;
    private MonitorConfig monitor;
    private ModuleConfig module;
    private ConfigCenterConfig configCenter;

    private Map<String, ProtocolConfig> protocols = new ConcurrentHashMap<>();
    private Map<String, RegistryConfig> registries = new ConcurrentHashMap<>();
    private Map<String, ProviderConfig> providers = new ConcurrentHashMap<>();
    private Map<String, ConsumerConfig> consumers = new ConcurrentHashMap<>();

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        if (application != null) {
            checkDuplicate(this.application, application);
            this.application = application;
        }
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        if (monitor != null) {
            checkDuplicate(this.monitor, monitor);
            this.monitor = monitor;
        }
    }

    public ModuleConfig getModule() {
        return module;
    }

    public void setModule(ModuleConfig module) {
        if (module != null) {
            checkDuplicate(this.module, module);
            this.module = module;
        }
    }

    public ConfigCenterConfig getConfigCenter() {
        return configCenter;
    }

    public void setConfigCenter(ConfigCenterConfig configCenter) {
        if (configCenter != null) {
            checkDuplicate(this.configCenter, configCenter);
            this.configCenter = configCenter;
        }
    }

    public Optional<ProviderConfig> getProvider(String id) {
        return Optional.ofNullable(providers.get(id));
    }

    public Optional<ProviderConfig> getDefaultProvider() {
        return Optional.ofNullable(providers.get(DEFAULT_KEY));
    }

    public void addProviderConfig(ProviderConfig providerConfig) {
        if (providerConfig == null) {
            return;
        }

        String key = (providerConfig.isDefault() == null || providerConfig.isDefault()) ? DEFAULT_KEY : providerConfig.getId();

        if (StringUtils.isEmpty(key)) {
            throw new IllegalStateException("A ProviderConfig should either has an id or it's the default one, " + providerConfig);
        }

        if (providers.containsKey(key) && !providerConfig.equals(providers.get(key))) {
            throw new IllegalStateException("Duplicate ProviderConfig found, there already has one default ProviderConfig, " + providerConfig);
        } else {
            providers.put(key, providerConfig);
        }
    }

    public Optional<ConsumerConfig> getConsumer(String id) {
        return Optional.ofNullable(consumers.get(id));
    }

    public Optional<ConsumerConfig> getDefaultConsumer() {
        return Optional.ofNullable(consumers.get(DEFAULT_KEY));
    }

    public void addConsumer(ConsumerConfig consumerConfig) {
        if (consumerConfig == null) {
            return;
        }

        String key = (consumerConfig.isDefault() == null || consumerConfig.isDefault()) ? DEFAULT_KEY : consumerConfig.getId();

        if (StringUtils.isEmpty(key)) {
            throw new IllegalStateException("A ConsumerConfig should either has an id or it's the default one, " + consumerConfig);
        }

        if (consumers.containsKey(key) && !consumerConfig.equals(consumers.get(key))) {
            throw new IllegalStateException("Duplicate ConsumerConfig found, there already has one default ConsumerConfig, " + consumerConfig);
        } else {
            consumers.put(key, consumerConfig);
        }
    }

    public Optional<ProtocolConfig> getProtocol(String id) {
        return Optional.ofNullable(protocols.get(id));
    }

    public Optional<ProtocolConfig> getDefaultProtocol() {
        return Optional.ofNullable(protocols.get(DEFAULT_KEY));
    }

    public void addProtocols(List<ProtocolConfig> protocolConfigs) {
        if (protocolConfigs != null) {
            protocolConfigs.forEach(this::addProtocol);
        }
    }

    public void addProtocol(ProtocolConfig protocolConfig) {
        if (protocolConfig == null) {
            return;
        }

        String key = (protocolConfig.isDefault() == null || protocolConfig.isDefault()) ? DEFAULT_KEY : protocolConfig.getId();

        if (StringUtils.isEmpty(key)) {
            throw new IllegalStateException("A ProtocolConfig should either has an id or it's the default one, " + protocolConfig);
        }

        if (protocols.containsKey(key) && !protocolConfig.equals(protocols.get(key))) {
            throw new IllegalStateException("Duplicate ProtocolConfig found, there already has one default ProtocolConfig, " + protocolConfig);
        } else {
            protocols.put(key, protocolConfig);
        }
    }

    public Optional<RegistryConfig> getRegistry(String id) {
        return Optional.ofNullable(registries.get(id));
    }

    public Optional<RegistryConfig> getDefaultRegistry() {
        return Optional.ofNullable(registries.get(DEFAULT_KEY));
    }

    public void addRegistries(List<RegistryConfig> registryConfigs) {
        if (registryConfigs != null) {
            registryConfigs.forEach(this::addRegistry);
        }
    }

    public void addRegistry(RegistryConfig registryConfig) {
        if (registryConfig == null) {
            return;
        }

        String key = (registryConfig.isDefault() == null || registryConfig.isDefault()) ? DEFAULT_KEY : registryConfig.getId();

        if (StringUtils.isEmpty(key)) {
            throw new IllegalStateException("A RegistryConfig should either has an id or it's the default one, " + registryConfig);
        }

        if (registries.containsKey(key) && !registryConfig.equals(registries.get(key))) {
            throw new IllegalStateException("Duplicate RegistryConfig found, there already has one default RegistryConfig, " + registryConfig);
        } else {
            registries.put(key, registryConfig);
        }
    }

    public Map<String, ProtocolConfig> getProtocols() {
        return protocols;
    }

    public Map<String, RegistryConfig> getRegistries() {
        return registries;
    }

    public Map<String, ProviderConfig> getProviders() {
        return providers;
    }

    public Map<String, ConsumerConfig> getConsumers() {
        return consumers;
    }

    private void checkDuplicate(AbstractConfig oldOne, AbstractConfig newOne) {
        if (oldOne != null && oldOne.equals(newOne)) {
            throw new IllegalStateException("Duplicate Config found for " + oldOne.getClass().getSimpleName() + ", the old one is " + oldOne + ", the new one is " + newOne);
        }
    }

}
