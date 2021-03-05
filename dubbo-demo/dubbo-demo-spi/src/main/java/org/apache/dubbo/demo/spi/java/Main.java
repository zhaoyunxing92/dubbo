package org.apache.dubbo.demo.spi.java;

import java.util.ServiceLoader;

/**
 * @author zhaoyunxing
 * @date 2021/3/5 10:14
 */
public class Main {

    public static void main(String[] args) {

        ServiceLoader<PrintService> serviceServiceLoader = ServiceLoader.load(PrintService.class);

        for (PrintService printservice : serviceServiceLoader) {
            printservice.printInfo();
        }
    }
}
