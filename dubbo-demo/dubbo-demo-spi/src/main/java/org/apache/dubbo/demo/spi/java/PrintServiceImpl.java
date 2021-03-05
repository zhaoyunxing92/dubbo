package org.apache.dubbo.demo.spi.java;

/**
 * @author zhaoyunxing
 * @date 2021/3/5 10:37
 */
public class PrintServiceImpl implements PrintService {
    @Override
    public void printInfo() {

        System.out.println("hello java spi");
    }
}
