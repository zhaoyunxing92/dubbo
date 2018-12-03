package org.apache.dubbo.metadata.integration;

import com.google.gson.Gson;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.store.test.JTestMetadataReport4Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 2018/9/14
 */
public class MetadataReportServiceTest {
    URL url = URL.valueOf("JTest://" + NetUtils.getLocalAddress().getHostName() + ":4444/org.apache.dubbo.TestService?version=1.0.0&application=vic");
    MetadataReportService metadataReportService1;

    @Before
    public void before() {

        metadataReportService1 = MetadataReportService.instance(new Supplier<URL>() {
            @Override
            public URL get() {
                return url;
            }
        });
    }

    @Test
    public void testInstance() {

        MetadataReportService metadataReportService2 = MetadataReportService.instance(new Supplier<URL>() {
            @Override
            public URL get() {
                return url;
            }
        });
        Assert.assertSame(metadataReportService1, metadataReportService2);
        Assert.assertEquals(metadataReportService1.metadataReportUrl, url);
    }

    @Test
    public void testPublishProviderNoInterfaceName() {


        URL publishUrl = URL.valueOf("dubbo://" + NetUtils.getLocalAddress().getHostName() + ":4444/org.apache.dubbo.TestService?version=1.0.0&application=vicpubprovder");
        metadataReportService1.publishProvider(publishUrl);

        Assert.assertTrue(metadataReportService1.metadataReport instanceof JTestMetadataReport4Test);

        JTestMetadataReport4Test jTestMetadataReport4Test = (JTestMetadataReport4Test) metadataReportService1.metadataReport;
        Assert.assertTrue(!jTestMetadataReport4Test.store.containsKey(JTestMetadataReport4Test.getProviderKey(publishUrl)));

    }

    @Test
    public void testPublishProviderWrongInterface() {

        URL publishUrl = URL.valueOf("dubbo://" + NetUtils.getLocalAddress().getHostName() + ":4444/org.apache.dubbo.TestService?version=1.0.0&application=vicpu&interface=ccc");
        metadataReportService1.publishProvider(publishUrl);

        Assert.assertTrue(metadataReportService1.metadataReport instanceof JTestMetadataReport4Test);

        JTestMetadataReport4Test jTestMetadataReport4Test = (JTestMetadataReport4Test) metadataReportService1.metadataReport;
        Assert.assertTrue(!jTestMetadataReport4Test.store.containsKey(JTestMetadataReport4Test.getProviderKey(publishUrl)));

    }

    @Test
    public void testPublishProviderContainInterface() {

        URL publishUrl = URL.valueOf("dubbo://" + NetUtils.getLocalAddress().getHostName() + ":4444/org.apache.dubbo.TestService?version=1.0.3&application=vicpubp&interface=org.apache.dubbo.metadata.integration.InterfaceNameTestService");
        metadataReportService1.publishProvider(publishUrl);

        Assert.assertTrue(metadataReportService1.metadataReport instanceof JTestMetadataReport4Test);

        JTestMetadataReport4Test jTestMetadataReport4Test = (JTestMetadataReport4Test) metadataReportService1.metadataReport;
        Assert.assertTrue(jTestMetadataReport4Test.store.containsKey(JTestMetadataReport4Test.getProviderKey(publishUrl)));

        String value = jTestMetadataReport4Test.store.get(JTestMetadataReport4Test.getProviderKey(publishUrl));
        FullServiceDefinition fullServiceDefinition = toServiceDefinition(value);
        Map<String,String> map = fullServiceDefinition.getParameters();
        Assert.assertEquals(map.get("application"), "vicpubp");
        Assert.assertEquals(map.get("version"), "1.0.3");
        Assert.assertEquals(map.get("interface"), "org.apache.dubbo.metadata.integration.InterfaceNameTestService");
    }

    @Test
    public void testPublishConsumer() {

        URL publishUrl = URL.valueOf("dubbo://" + NetUtils.getLocalAddress().getHostName() + ":4444/org.apache.dubbo.TestService?version=1.0.x&application=vicpubconsumer&side=consumer");
        metadataReportService1.publishConsumer(publishUrl);

        Assert.assertTrue(metadataReportService1.metadataReport instanceof JTestMetadataReport4Test);

        JTestMetadataReport4Test jTestMetadataReport4Test = (JTestMetadataReport4Test) metadataReportService1.metadataReport;
        Assert.assertTrue(jTestMetadataReport4Test.store.containsKey(JTestMetadataReport4Test.getConsumerKey(publishUrl)));

        String value = jTestMetadataReport4Test.store.get(JTestMetadataReport4Test.getConsumerKey(publishUrl));
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(value, Map.class);
        Assert.assertEquals(map.get("application"), "vicpubconsumer");
        Assert.assertEquals(map.get("version"), "1.0.x");

    }

    private FullServiceDefinition toServiceDefinition(String urlQuery) {
        Gson gson = new Gson();
        return gson.fromJson(urlQuery, FullServiceDefinition.class);
    }

}
