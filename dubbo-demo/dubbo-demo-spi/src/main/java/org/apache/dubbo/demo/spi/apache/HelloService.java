package org.apache.dubbo.demo.spi.apache;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author zhaoyunxing
 * @date 2021/3/6 19:53
 */
@SPI("helloService")
public interface HelloService {

<<<<<<< HEAD

=======
    @Adaptive("abc")
>>>>>>> 7f46405b32519bacc79d11d227a3894f9a439dde
    void echo(String abc);
}
