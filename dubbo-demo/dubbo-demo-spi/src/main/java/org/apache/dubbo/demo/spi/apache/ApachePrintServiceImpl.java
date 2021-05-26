package org.apache.dubbo.demo.spi.apache;

/**
 * @author zhaoyunxing
 * @date 2021/3/6 16:56
 */
public class ApachePrintServiceImpl implements ApachePrintService {

    /**
     * 通过setter 注入服务
     *
     * @param helloService hello服务
     */
    public void setHelloService(HelloService helloService) {
        helloService.echo("abc");
    }

    @Override
    public void printInfo() {
        System.out.println("hello apache dubbo spi");
    }
}
