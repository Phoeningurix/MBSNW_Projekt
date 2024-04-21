package de.htw.mbsnw_projekt.database.type_converters;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter {


    @TypeConverter
    public static LocalDateTime localDateTimeFromString(String str) {
        if (str == null || str.equals("null")) {
            return null;
        } else {
            return LocalDateTime.parse(str);
        }
    }


    @Nullable
    @TypeConverter
    public static String localDateTimeToString(@Nullable LocalDateTime time) {
        if (time == null) {
            return null;
        } else {
            return time.toString();
        }
    }
}
