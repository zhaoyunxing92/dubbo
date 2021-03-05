package org.apache.dubbo.demo.spi.dubbo;

/**
 * @author zhaoyunxing
 * @date 2021/3/5 14:10
 */
public class DubboPrintServiceImpl implements DubboPrintService {

    @Override
    public void printInfo() {
        System.out.println("hello dubbo spi");
    }
}
