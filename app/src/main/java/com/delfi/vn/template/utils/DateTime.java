package com.delfi.vn.template.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTime {
    public static String DAY_FORMAT_IN_SERVER = "yyyy-MM-dd";
    public static String DAY_FORMAT_IN_LOCAL = "dd/MM/yyyy";
    public static String DAY_FORMAT_TO_SYNC = "YYYddMM";
    public static String today(String format) {
        DateFormat df = new SimpleDateFormat(format);
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getCurrentTimeServer() {
        return today(DAY_FORMAT_IN_SERVER + " HH:mm:ss");
    }

    public static String getCurrentTimeLocal() {
        return today(DAY_FORMAT_IN_LOCAL + " HH:mm:ss");
    }

    public static String getCurrentDateLocal() {
        return today(DAY_FORMAT_IN_LOCAL);
    }

    public static String getCurrentDateToSync() {
        return today(DAY_FORMAT_TO_SYNC);
    }

    public static String getCurrentDate() {
        return today(DAY_FORMAT_IN_SERVER);
    }

    public static String getWithin60Days(){
        DateFormat df = new SimpleDateFormat(DAY_FORMAT_IN_SERVER);
        String inputDate = df.format(Calendar.getInstance().getTime());

        if (inputDate == null) return "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(DAY_FORMAT_IN_SERVER, Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(date);
            dateCalendar.add(Calendar.DAY_OF_MONTH, -60);
            inputFormat.format(dateCalendar.getTime());
            return inputFormat.format(dateCalendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDate;
    }

    public static String getWithin60Days(String inputDate){
        if (inputDate == null) return "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(DAY_FORMAT_IN_LOCAL, Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(date);
            dateCalendar.add(Calendar.DAY_OF_MONTH, -60);
            inputFormat.format(dateCalendar.getTime());
            SimpleDateFormat inputServerFormat = new SimpleDateFormat(DAY_FORMAT_IN_SERVER, Locale.getDefault());
            return inputServerFormat.format(dateCalendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDate;
    }

    public static String getDate() {
        return today("dd/MM/yyyy");
    }


    public static String getTime() {
        return today("HH:mm:ss");
    }

    public static String parseDate(String inputDate) {
        return parseDate(inputDate, "dd/MM/yyyy");
    }

    public static String parseDate(String inputDate, String format) {
        return parseDate(inputDate, DAY_FORMAT_IN_SERVER, format);
    }

    public static String parseDate(String inputDate, String inFormat, String outFormat) {
        if (inputDate == null) return "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inFormat, Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(date);
            if (dateCalendar.get(Calendar.YEAR) <= 1900) {
                return "";
            } else {
                inputFormat = new SimpleDateFormat(outFormat, Locale.getDefault());
            }
            return inputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDate;
    }

    /********
     DATEVALUE(NOW()) in excel
     Calendar rightNow = Calendar.getInstance();
     oaDate = (double) days + ((double) rightNow.get(Calendar.HOUR_OF_DAY) / 24)
     + ((double) rightNow.get(Calendar.MINUTE) / (60 * 24))
     + ((double) rightNow.get(Calendar.SECOND) / (60 * 24 * 60));
     ********/
    public static int getOADate(Date endDate) throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = myFormat.parse("30/12/1899");
        long diff = endDate.getTime() - startDate.getTime();
        int oaDate = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return oaDate;

    }

    public static int getOADate(String endDate) throws ParseException {
        if (endDate == null) return 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm");
        return getOADate(myFormat.parse(endDate));
    }

    public static boolean isCompare(String dateString1, String dateString2)  {
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(DAY_FORMAT_IN_SERVER);
            Date date1 = myFormat.parse(dateString1);
            Date date2 = myFormat.parse(dateString2);
            return date1.compareTo(date2) > 0;
        }catch (ParseException ex){
            return false;
        }
    }

    public static boolean isEqual(String dateString1, String dateString2)  {
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(DAY_FORMAT_IN_SERVER);
            Date date1 = myFormat.parse(dateString1);
            Date date2 = myFormat.parse(dateString2);
            return date1.compareTo(date2) == 0;
        }catch (ParseException ex){
            return false;
        }
    }

    public static boolean isDate(String date){
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(DAY_FORMAT_IN_LOCAL);
            Date date1 = myFormat.parse(date);
            return true;
        }catch (ParseException ex){
            return false;
        }
    }
}
