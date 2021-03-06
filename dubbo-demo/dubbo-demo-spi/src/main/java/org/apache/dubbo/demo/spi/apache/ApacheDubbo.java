package org.apache.dubbo.demo.spi.apache;


import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @author zhaoyunxing
 * @date 2021/3/6 15:34
 */
public class ApacheDubbo {

    public static void main(String[] args) {
        // 使用dubbo 2.6.7可以工作
        ApachePrintService apache = ExtensionLoader.getExtensionLoader(ApachePrintService.class)
                .getDefaultExtension();

        apache.printInfo();
    }
}
