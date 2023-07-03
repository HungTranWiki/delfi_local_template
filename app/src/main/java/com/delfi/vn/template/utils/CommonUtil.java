package com.delfi.vn.template.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommonUtil {
    public static void hideKeyboard(View view, Context context) {
        if (view == null)
            return;
        view.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }, 100);
    }

    @NotNull
    public static Object[] createObj(String key, String defaultValue, Object value) {
        Object[] objects = new Object[3];
        objects[0] = key;
        objects[1] = defaultValue;
        objects[2] = value;
        return objects;
    }

    public static String formatNumber(float number) {
        return number % 1.0 != 0 ? String.format("%s", number) : String.format(Locale.getDefault(), "%.0f", number);
    }

    public static int convertInt(Float number) {
        return Math.round(number);
    }

    public static String getCurrentDateTime() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss", Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

    public static String format(float number) {
        DecimalFormat format = new DecimalFormat("#.0000");
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        return format.format(number);
    }

    public static String padLeft(float input, int length, String fill) {
        String pad = String.format("%" + length + "s", "").replace(" ", fill) + String.valueOf(input);
        return pad.substring(pad.length() - length);
    }

    public static String padLeft(String input, int length, String fill) {
        String pad = String.format("%" + length + "s", "").replace(" ", fill) + input.trim();
        return pad.substring(pad.length() - length);
    }

    public static boolean isIPV4(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    public static <T> void saveListToSharedPreferences(SharedPreferences sharedPreferences, String key, List<T> list) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.commit();
    }

    public static String convertToString(int number) {
        String palletString = "";
        if (number < 10) palletString = "00" + number;
        else if (number < 100) palletString = "0" + number;
        else palletString = "" + number;
        return palletString;
    }
}
