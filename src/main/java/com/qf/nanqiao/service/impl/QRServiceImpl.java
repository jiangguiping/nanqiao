package com.qf.nanqiao.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.qf.nanqiao.service.QRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class QRServiceImpl implements QRService {
    private final QrConfig qrConfig;

    // 生成到本地文件
    @Override
    public void generateFile(String content, File file) {
        QrCodeUtil.generate(content, qrConfig, file);
    }

    // 输出到流
    @Override
    public void generateStream(String content, HttpServletResponse response) throws IOException {
        QrCodeUtil.generate(content, qrConfig, "png", response.getOutputStream());
    }

    @Override
    public String generateBase64(String content) {
        return QrCodeUtil.generateAsBase64(content,qrConfig,"png");
    }
}
