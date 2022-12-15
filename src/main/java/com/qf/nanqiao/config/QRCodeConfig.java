package com.qf.nanqiao.config;

import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * 二维码的配置类
 *
 * @author zed
 * @date 2022/11/27
 */
@Configuration
public class QRCodeConfig {
    @Bean
    public QrConfig qrConfig() {
        QrConfig qrConfig = new QrConfig();
        // 二维码的背景色
        qrConfig.setBackColor(Color.white);
        // 二维码的前景色
        qrConfig.setForeColor(Color.black);
        return qrConfig;
    }
}
