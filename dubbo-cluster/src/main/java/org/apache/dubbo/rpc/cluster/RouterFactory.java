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
package org.apache.dubbo.rpc.cluster;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.config.dynamic.DynamicConfiguration;
<<<<<<< HEAD
import org.apache.dubbo.rpc.Invocation;
=======
>>>>>>> 75853a88698e2f83a1dae2ba028e4a2316eab326

/**
 * RouterFactory. (SPI, Singleton, ThreadSafe)
 * <p>
 * <a href="http://en.wikipedia.org/wiki/Routing">Routing</a>
 *
 * @see org.apache.dubbo.rpc.cluster.Cluster#join(Directory)
 * @see org.apache.dubbo.rpc.cluster.Directory#list(org.apache.dubbo.rpc.Invocation)
 */
@SPI
public interface RouterFactory {

    /**
     * Create router.
     *
     * @param url
     * @return router
     */
    @Adaptive("protocol")
    Router getRouter(URL url);

    /**
     * @param dynamicConfiguration
     * @param url                  reserved for future usage.
     * @return
     */
    default Router getRouter(DynamicConfiguration dynamicConfiguration, URL url) {
        return null;
    }
}