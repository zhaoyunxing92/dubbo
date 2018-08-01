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
package org.apache.dubbo.config.dynamic;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 *
 */
@SPI("zookeeper")
public interface DynamicConfiguration {
    String TYPE_KEY = "dubbo.config.type";
    String ENV_KEY = "dubbo.config.env";
    String ADDRESS_KEY = "dubbo.config.address";
    String GROUP_KEY = "dubbo.config.group";
    String NAMESPACE_KEY = "dubbo.config.namespace";
    String CLUSTER_KEY = "dubbo.config.cluster";
    String APP_KEY = "dubbo.config.app";

    public void init();

    URL getUrl();

    void setUrl(URL url);

    void addListener(String key, ConfigurationListener listener);

    String getConfig(String key, String group);

    String getConfig(String key, String group, long timeout);

    String getConfig(String key, String group, ConfigurationListener listener);

}
