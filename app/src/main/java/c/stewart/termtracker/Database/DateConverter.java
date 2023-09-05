package c.stewart.termtracker.Database;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final String DATE_FORMAT = "MM/dd/yy";

    @TypeConverter
    public static Date fromString(String value) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return sdf.format(date);
    }
}

