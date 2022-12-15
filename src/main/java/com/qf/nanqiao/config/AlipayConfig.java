package com.qf.nanqiao.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 支付宝配置类
 * @Version: V1.0
 */
@Configuration
public class AlipayConfig {

    @Bean
    public Config config(AliPayProperties payProperties) {
        Config config = new Config();
        config.protocol = payProperties.getProtocol();
        config.gatewayHost = payProperties.getGatewayHost();
        config.signType = payProperties.getSignType();

        config.appId = payProperties.getAppId();

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = payProperties.getMerchantPrivateKey();

        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//        config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
//        config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
//        config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = payProperties.getAlipayPublicKey();

        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = "";

        //可设置AES密钥，调用AES加解密相关接口时需要（可选） <-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->
        // 接口内容加密方式的加密密钥
        config.encryptKey = "4p+P9qj0GAQbk7rUjR2/wQ==";

        // 设置参数（全局只需设置一次）
        Factory.setOptions(config);
        return config;
    }


}
