package com.valerehealth.brightree.util;

import java.util.List;

public class CommonUtil {

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() <= 0;
    }

    public static boolean hasValue(String str) {
        return str != null && str.length() > 0 && str.trim().length() > 0;
    }

    public static String capitalFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String joinStrings(List<String> strs) {
        if (CommonUtil.isEmpty(strs)) {
            return null;
        }

        return String.join(" ", strs);
    }

}
