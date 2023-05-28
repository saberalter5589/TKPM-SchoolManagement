package school.management.school_management_be.util;

import java.util.HashMap;
import java.util.List;

public class CommonUtil {
    public static boolean isNullOr(Object obj){
        return obj == null;
    }

    public static <T> boolean  isNullOrEmpty(List<T> collection){
        return collection == null || collection.size() == 0;
    }

    public static <K,V> boolean isNullOrEmpty(HashMap<K,V> map){
        return map == null || map.isEmpty();
    }

    public static boolean isNullOrWhitespace(String str){
        return str == null || str.isBlank();
    }
}
