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
package org.apache.dubbo.governance.support.archaius;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.governance.DynamicConfiguration;
import org.apache.dubbo.governance.DynamicConfigurationFactory;

/**
 *
 */
public class ArchaiusDynamicConfigurationFactory implements DynamicConfigurationFactory {

    private volatile DynamicConfiguration configuration;

    @Override
    public synchronized DynamicConfiguration getDynamicConfiguration(URL url) {
        if (configuration == null) {
            configuration = new ArchaiusDynamicConfiguration();
            configuration.setUrl(url);
            configuration.init();
        }
        return configuration;
    }

    @Override
    public DynamicConfiguration getExistedDynamicConfiguration() {
        return configuration;
    }
}
