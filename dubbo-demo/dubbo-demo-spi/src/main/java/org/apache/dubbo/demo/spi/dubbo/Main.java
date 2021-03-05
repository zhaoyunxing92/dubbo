package org.apache.dubbo.demo.spi.dubbo;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @author zhaoyunxing
 * @date 2021/3/5 14:14
 */
public class Main {

    public static void main(String[] args) {

        DubboPrintService dubboPrintService = ExtensionLoader.getExtensionLoader(DubboPrintService.class)
                .getDefaultExtension();

        dubboPrintService.printInfo();
    }
}
