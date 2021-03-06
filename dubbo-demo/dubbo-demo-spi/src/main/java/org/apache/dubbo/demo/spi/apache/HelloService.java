package org.apache.dubbo.demo.spi.apache;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author zhaoyunxing
 * @date 2021/3/6 19:53
 */
@SPI("helloService")
public interface HelloService {

    @Adaptive("abc")
    void echo(String abc);
}
