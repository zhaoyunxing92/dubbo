package org.apache.dubbo.demo.spi.apache;


import org.apache.dubbo.common.extension.SPI;

/**
 * @author zhaoyunxing
 * @date 2021/3/6 16:55
 */
@SPI("apachePrintService")
public interface ApachePrintService {

    void printInfo();
}
