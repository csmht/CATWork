package com.example.controller;


import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeTran {


    public static String tran(String input){

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("H:m:s");
        LocalTime localTime = LocalTime.parse(input, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Time sqlTime = Time.valueOf(localTime);
        return sqlTime.toLocalTime().format(outputFormatter);
    }

    public static String tran2(String input){

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("u-M-d H:m:s");
        LocalDateTime localDateTime = LocalDateTime.parse(input, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return localDateTime.format(outputFormatter);
    }

}
