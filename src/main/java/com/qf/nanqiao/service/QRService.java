package com.qf.nanqiao.service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public interface QRService {
    // 生成到本地文件
    void generateFile(String content, File file);

    // 输出到流
    void generateStream(String content, HttpServletResponse response) throws IOException;

    // 生成base64的验证码图片
    String generateBase64(String content);

}
