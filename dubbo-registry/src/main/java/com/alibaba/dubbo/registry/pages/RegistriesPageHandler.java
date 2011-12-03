/*
 * Copyright 1999-2011 Alibaba Group.
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
package com.alibaba.dubbo.registry.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.dubbo.common.Extension;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.container.page.Menu;
import com.alibaba.dubbo.container.page.Page;
import com.alibaba.dubbo.container.page.PageHandler;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;

/**
 * RegistriesPageHandler
 * 
 * @author william.liangf
 */
@Menu(name = "Registries", desc = "Registries", order = 10000)
@Extension("registries")
public class RegistriesPageHandler implements PageHandler {

    public Page handle(URL url) {
        List<List<String>> rows = new ArrayList<List<String>>();
        Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
        if (registries != null && registries.size() > 0) {
            for (Registry registry : registries) {
                String server = registry.getUrl().getAddress();
                List<String> row = new ArrayList<String>();
                row.add(server);
                if (registry.isAvailable()) {
                    row.add("<font color=\"green\">Connected</font>");
                } else {
                    row.add("<font color=\"red\">Disconnected</font>");
                }
                row.add("<a href=\"registered.html?registry=" + server + "\">Registered</a>");
                rows.add(row);
            }
        }
        return new Page("Registries", "Registries (" + rows.size() + ")",
                new String[] { "Registry Address:", "Ststus:", "Registered" }, rows);
    }

}