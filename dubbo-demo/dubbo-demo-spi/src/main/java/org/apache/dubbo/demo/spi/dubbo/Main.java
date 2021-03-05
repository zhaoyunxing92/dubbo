package org.apache.dubbo.demo.spi.dubbo;


import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @author zhaoyunxing
 * @date 2021/3/5 14:14
 */
public class Main {

    public static void main(String[] args) {
        /*ExtensionFactory factory = new SpiExtensionFactory();
        DubboPrintService printService = factory.getExtension(DubboPrintService.class, "printService");*/
        DubboPrintService printService = ExtensionLoader.getExtensionLoader(DubboPrintService.class)
                .getDefaultExtension();

        printService.printInfo();
    }
}
