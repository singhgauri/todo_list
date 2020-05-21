package com.example.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.TypeConverter;




public class DateConverter {

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {

           // SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy");
           // return dateTimeFormat.format(date);
            return date.getTime();
        }
    }
}


