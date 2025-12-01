package mothes.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Converter {

    public static int valuesToSeconds(int hours, int minutes, int seconds){
        int hoursInSec = hours * 3600;
        int minutesInSec = minutes * 60;
        return hoursInSec + minutesInSec + seconds;
    }

    public static String IntTimeToStrTime(int intTime){
        int hours = intTime / 3600;
        int minutes = (intTime % 3600) / 60;
        int seconds = intTime % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String getHoursFromStrTime(int intTime){
        int hours = intTime / 3600;
        return String.valueOf(hours);
    }

    public static String getMinutesFromStrTime(int intTime){
        int minutes = (intTime % 3600) / 60;
        return String.valueOf(minutes);
    }

    public static String getSecondsFromStrTime(int intTime){
        int seconds = intTime % 60;
        return String.valueOf(seconds);
    }


}
