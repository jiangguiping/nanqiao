package com.qf.nanqiao.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 线程本地工具类
 *
 * @author zed
 * @date 2022/12/06
 */
public class ThreadLocalUtils {

    private static ThreadLocal<Map<Object, Object>> RESOURCES = ThreadLocal.withInitial(() -> new HashMap<>());

    public static void removeResources() {
        if (Objects.nonNull(RESOURCES)) {
            RESOURCES.remove();
        }
    }

    public static Map<Object, Object> getResources() {
        return Objects.isNull(RESOURCES) ? null : RESOURCES.get();
    }

    public static void put(Object key, Object value) {
        Objects.requireNonNull(key, "key不能为空");
        Objects.requireNonNull(getResources(), "RESOURCES 还未初始化");
        getResources().put(key, value);
    }

    public static Object get(Object key) {
        return Objects.isNull(getResources()) ? null : getResources().get(key);
    }

    public static Object remove(Object key) {
        return Objects.isNull(getResources()) ? null : getResources().remove(key);
    }

}