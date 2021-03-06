package org.apache.dubbo.demo.spi.apache;

/**
 * @author zhaoyunxing
 * @date 2021/3/6 19:53
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public void echo(String abc) {
        System.out.println("echo hello");
    }
}
