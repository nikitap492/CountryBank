package com.cbank.utils;

import com.google.common.collect.Maps;
import lombok.val;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Podshivalov N.A.
 * @since 22.11.2017.
 */
public final class MapUtils {

    @SafeVarargs
    public static <K, V> Map<K, V> from(Map.Entry<K, V>... entries){
        val map = new HashMap<K, V>(entries.length);
        for (Map.Entry<K, V> entry : entries) map.put(entry.getKey(), entry.getValue());
        return map;
    }

}
