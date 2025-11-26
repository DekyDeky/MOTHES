package mothes.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validation {

    public static boolean checkEmptyTime(String h, String m, String s){
        return  (h == null || h.isEmpty()) &&
                (m == null || m.isEmpty()) &&
                (s == null || s.isEmpty());
    }

    public static boolean isNumeric(String str){
        if (str == null || str.isEmpty()) return true; // agora "" é considerado numérico
        String regex = "[-+]?\\d+(\\.\\d+)?";
        return str.matches(regex);
    }

    public static boolean isInt(String str){
        try {
            int parsedInt = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}
