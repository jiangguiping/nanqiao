package com.qf.nanqiao.utils;

import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author zed
 * @date 2022/12/05
 */
public class StringUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
