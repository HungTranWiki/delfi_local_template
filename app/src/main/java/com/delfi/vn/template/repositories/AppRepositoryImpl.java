package com.delfi.vn.template.repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import com.delfi.core.common.SharedManager;
import com.delfi.vn.template.models.enums.MenuCode;
import com.delfi.vn.template.utils.Constants;

import javax.inject.Inject;

import static com.delfi.core.utils.Constant.EXTRA_ACTIVATION_SUCCESS;
import static com.delfi.vn.template.services.api.Constants.TOKEN_ACCESS_AX;
import static com.delfi.vn.template.utils.Constants.NO_TEXT_DATA;

public class AppRepositoryImpl implements AppRepository {

    private Context context;

    @Inject
    public AppRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean getActivation() {
        return SharedManager.getInstance(context).getBoolean(EXTRA_ACTIVATION_SUCCESS, false);
    }

    @Override
    public void saveToken(String token) {
        SharedManager.getInstance(context).putString(TOKEN_ACCESS, token);
    }

    @Override
    public String getToken() {
        return SharedManager.getInstance(context).getString(TOKEN_ACCESS);
    }


    @Override
    public void savePrinterIP(String ip, int port, String dpi) {
        SharedManager.getInstance(context).putString(Constants.PRINTER_IP, ip);
        SharedManager.getInstance(context).putInt(Constants.PRINTER_PORT, port);
        SharedManager.getInstance(context).putString(Constants.PRINTER_DPI, dpi);
    }

    @Override
    public void saveUserName(String username) {
        SharedManager.getInstance(context).putString(USER_NAME, username);
    }

    @Override
    public String getUsername() {
        return SharedManager.getInstance(context).getString(USER_NAME, NO_TEXT_DATA);
    }

    @Override
    public void savePassword(String password) {
        SharedManager.getInstance(context).putString(PASSWORD, password);
    }

    @Override
    public String getPassword() {
        return SharedManager.getInstance(context).getString(PASSWORD, NO_TEXT_DATA);
    }

    @Override
    public void setAdmin(boolean isAdmin) {
        SharedManager.getInstance(context).putBoolean(IS_ADMIN, isAdmin);
    }

    @Override
    public boolean isAdmin() {
        return SharedManager.getInstance(context).getBoolean(IS_ADMIN, false);
    }

    @Override
    public void saveExpiration(String expiration) {
        SharedManager.getInstance(context).putString(EXPIRATION, expiration);
    }

    @Override
    public String getExpiration() {
        return SharedManager.getInstance(context).getString(EXPIRATION, NO_TEXT_DATA);
    }

    @SuppressLint("MissingPermission")
    @Override
    public String getSerial() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Build.getSerial();
        } else {
            return Build.SERIAL;
        }
    }

    @Override
    public void saveAppID(String appId) {
        SharedManager.getInstance(context).putString(APP_ID, appId);
    }

    @Override
    public String getAppId() {
        return SharedManager.getInstance(context).getString(APP_ID);
    }

    @Override
    public void savePrinterAddress(String address) {
        SharedManager.getInstance(context).putString(Constants.PRINTER_ADDRESS, address);
    }

    @Override
    public String getPrinterAddress() {
        return SharedManager.getInstance(context).getString(Constants.PRINTER_ADDRESS);
    }

    @Override
    public void saveBluetoothPrinterAddress(String address) {
        SharedManager.getInstance(context).putString(Constants.BT_PRINTER_ADDRESS, address);
    }

    @Override
    public String getBluetoothPrinterAddress() {
        return SharedManager.getInstance(context).getString(Constants.BT_PRINTER_ADDRESS);
    }


    @Override
    public void saveDefaultPrinter(int menu, String name) {
        if (menu == MenuCode.MENU_11.ordinal())
            SharedManager.getInstance(context).putString(Constants.MENU_11, name);
        else  if (menu == MenuCode.MENU_11.ordinal())
            SharedManager.getInstance(context).putString(Constants.MENU_5, name);
        else
            SharedManager.getInstance(context).putString(Constants.MENU_OTHER, name);
    }

    @Override
    public String getDefaultPrinter(int menu) {
        if (menu == MenuCode.MENU_11.ordinal())
            return SharedManager.getInstance(context).getString(Constants.MENU_11);
        else if (menu == MenuCode.MENU_11.ordinal())
            return SharedManager.getInstance(context).getString(Constants.MENU_5);
        else
            return SharedManager.getInstance(context).getString(Constants.MENU_OTHER);
    }

    @Override
    public void saveWorkingPrinter(String name) {
        SharedManager.getInstance(context).putString(Constants.WORKING_PRINTER, name);
    }

    @Override
    public String getWorkingPrinter() {
        return SharedManager.getInstance(context).getString(Constants.WORKING_PRINTER);
    }

    @Override
    public void saveNMVN(String nmvn) {
        SharedManager.getInstance(context).putString(Constants.LAST_NMVN, nmvn);
    }

    @Override
    public String getNMVN() {
        return SharedManager.getInstance(context).getString(Constants.LAST_NMVN);
    }

    @Override
    public void saveURL(String url) {
        SharedManager.getInstance(context).putString(Constants.URLAX, url);
    }

    @Override
    public String getURL() {
        return SharedManager.getInstance(context).getString(Constants.URLAX, "");
    }

    @Override
    public void saveURLOdoo(String url) {
        SharedManager.getInstance(context).putString(Constants.URL, url);
    }

    @Override
    public String getURLOdoo() {
        return SharedManager.getInstance(context).getString(Constants.URL, "");
    }

    @Override
    public void saveURLPrinter(String url) {
        SharedManager.getInstance(context).putString(Constants.URL_PRINTER, url);
    }

    @Override
    public String getURLPrinter() {
        return SharedManager.getInstance(context).getString(Constants.URL_PRINTER, "");
    }

    private static String TOKEN_ACCESS = "KEY_TOKEN";
    private static final String IS_ADMIN = "FULL_NAME";
    private static final String EXPIRATION = "EXPIRATION";
    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";
    public static final String APP_ID = "APP_ID";

}
