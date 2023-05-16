package com.jmg.checkagro.provider.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class DateTimeUtils {



    public static LocalDate now() {
        return LocalDate.now(ZoneId.of("America/Argentina/Buenos_Aires"));

    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));

    }


}
