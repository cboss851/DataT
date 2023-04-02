package com.tao.commons.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bean的扩展复制
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <T> T copyBean(Object from, Class<T> clazz, String... ignores) {
        if (from != null) {
            T obj = instantiateClass(clazz);
            org.springframework.beans.BeanUtils.copyProperties(from, obj, ignores);
            return obj;
        }
        return null;
    }

    public static <T> List<T> copyBeanList(List<?> origin, Class<T> clazz, String... ignores) {
        if (origin != null) {
            List<T> newArrayList = new ArrayList(origin.size());
            for (Object f : origin) {
                newArrayList.add(copyBean(f, clazz, ignores));
            }
            return newArrayList;
        }
        return Collections.emptyList();
    }
}
