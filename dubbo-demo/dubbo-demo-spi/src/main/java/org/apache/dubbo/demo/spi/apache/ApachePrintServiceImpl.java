package org.apache.dubbo.demo.spi.apache;

/**
 * @author zhaoyunxing
 * @date 2021/3/6 16:56
 */
public class ApachePrintServiceImpl implements ApachePrintService {

    @Override
    public void printInfo() {
        System.out.println("hello apache dubbo spi");
    }
}
