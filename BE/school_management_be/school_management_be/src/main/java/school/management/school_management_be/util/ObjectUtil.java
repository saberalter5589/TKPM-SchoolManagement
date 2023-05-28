package school.management.school_management_be.util;

public class ObjectUtil {
    public static String getValueOfString(Object obj){
        if(obj instanceof String){
            return String.valueOf(obj);
        }
        return "";
    }

    public static Long getValueOfLong(Object obj){
        Long value = null;
        if (obj != null) {
            value = Long.parseLong(obj.toString());
        }
        return value;
    }

    public static Double getValueOfDouble(Object obj){
        Double value = null;
        if (obj != null) {
            value = Double.parseDouble(obj.toString());
        }
        return value;
    }

    public static Boolean getValueOfBoolean(Object obj){
        if(obj == null){
            return false;
        }
        return (Boolean) obj;
    }
}
