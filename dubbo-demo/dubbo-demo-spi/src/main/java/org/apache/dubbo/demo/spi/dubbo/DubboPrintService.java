package org.apache.dubbo.demo.spi.dubbo;


import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author zhaoyunxing
 * @date 2021/3/5 14:09
 */
@SPI("printService")
public interface DubboPrintService {

    void printInfo();
}
