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
package org.apache.dubbo.rpc.model;

import org.apache.dubbo.common.utils.Assert;
import org.apache.dubbo.rpc.Invoker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Consumer Model which is about subscribed services.
 */
public class ConsumerModel {
    private final String serviceKey;
    private final Class<?> serviceInterfaceClass;

    private final Map<Method, ConsumerMethodModel> methodModels = new IdentityHashMap<Method, ConsumerMethodModel>();

    private Object proxyObject;
    private Invoker<?> invoker;

    /**
     *  This constructor create an instance of ConsumerModel and passed objects should not be null.
     *  If service name, service instance, proxy object,methods should not be null. If these are null
     *  then this constructor will throw {@link IllegalArgumentException}
     * @param serviceKey Name of the service.
     * @param serviceInterfaceClass Service interface class.
     * @param proxyObject  Proxy object.
     * @param methods Methods of service class
     * @param attributes Attributes of methods.
     */
    public ConsumerModel(String serviceKey
            , Class<?> serviceInterfaceClass
            , Object proxyObject
            , Method[] methods
            , Map<String, Object> attributes) {

        Assert.notEmptyString(serviceKey, "Service name can't be null or blank");
        Assert.notNull(serviceInterfaceClass, "Service interface class can't null");
        Assert.notNull(methods, "Methods can't be null");

        this.serviceKey = serviceKey;
        this.serviceInterfaceClass = serviceInterfaceClass;
        this.proxyObject = proxyObject;
        for (Method method : methods) {
            methodModels.put(method, new ConsumerMethodModel(method, attributes));
        }
    }

    /**
     * Return the proxy object used by called while creating instance of ConsumerModel
     * @return
     */
    public Object getProxyObject() {
        return proxyObject;
    }

    /**
     * Return method model for the given method on consumer side
     *
     * @param method method object
     * @return method model
     */
    public ConsumerMethodModel getMethodModel(Method method) {
        return methodModels.get(method);
    }

    /**
     * Return method model for the given method on consumer side
     *
     * @param method method object
     * @return method model
     */
    public ConsumerMethodModel getMethodModel(String method) {
        Optional<Map.Entry<Method, ConsumerMethodModel>> consumerMethodModelEntry = methodModels.entrySet().stream().filter(entry -> entry.getKey().getName().equals(method)).findFirst();
        return consumerMethodModelEntry.map(Map.Entry::getValue).orElse(null);
    }

    /**
     * Return all method models for the current service
     *
     * @return method model list
     */
    public List<ConsumerMethodModel> getAllMethods() {
        return new ArrayList<ConsumerMethodModel>(methodModels.values());
    }

    public Class<?> getServiceInterfaceClass() {
        return serviceInterfaceClass;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setProxyObject(Object proxyObject) {
        this.proxyObject = proxyObject;
    }

    public Invoker<?> getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker<?> invoker) {
        this.invoker = invoker;
    }
}
