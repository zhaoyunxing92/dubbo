package org.apache.dubbo.demo.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author zhaoyunxing
 * @date 2021/3/5 14:09
 */
@SPI("printService")
public interface DubboPrintService {

    void printInfo();
}
