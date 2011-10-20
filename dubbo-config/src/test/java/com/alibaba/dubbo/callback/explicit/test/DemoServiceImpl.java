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
package com.alibaba.dubbo.callback.explicit.test;

/**
 * @author chao.liuc
 */
public class DemoServiceImpl  implements IDemoService {
	public String get(){
        return "ok" ;
	}

    public void xxx(final IDemoCallback callback ,String arg1) {
        callback.yyy("Sync callback msg .This is callback data. arg1:"+arg1);
        Thread t = new Thread(new Runnable() {
            public void run() {
                for(int i = 0 ;i< 10;i++){
                    String ret = callback.yyy("server invoke callback : arg:"+System.currentTimeMillis());
                    System.out.println("callback result is :"+ret);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
        System.out.println("xxx invoke complete");
    }

    public void unxxx(IDemoCallback callback, String arg1) {
        
    }

    public void xxx2(IDemoCallback callback, IDemoCallback callback2, String arg1) {

    }
}