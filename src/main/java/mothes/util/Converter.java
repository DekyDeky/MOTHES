package mothes.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Converter {

    public static Time StrToSQLTime(String hour, String minutes, String seconds) throws ParseException {
        String h = (hour == null || hour.isBlank()) ? "00" : String.format("%02d", Integer.parseInt(hour));
        String m = (minutes == null || minutes.isBlank()) ? "00" : String.format("%02d", Integer.parseInt(minutes));
        String s = (seconds == null || seconds.isBlank()) ? "00" : String.format("%02d", Integer.parseInt(seconds));

        return Time.valueOf(h + ":" + m + ":" + s);
    }

}
