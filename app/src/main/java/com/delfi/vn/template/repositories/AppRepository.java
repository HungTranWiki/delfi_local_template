package com.delfi.vn.template.repositories;

public interface AppRepository {
    boolean getActivation();

    void saveToken(String token);

    String getToken();

    void saveUserName(String username);

    String getUsername();

    void savePassword(String password);

    String getPassword();

    void setAdmin(boolean isAdmin);

    boolean isAdmin();

    void saveExpiration(String expiration);

    String getExpiration();

    void savePrinterIP(String ip, int port, String dpi);

    String getSerial();

    void saveURLOdoo(String url);

    String getURLOdoo();

    void saveURLPrinter(String url);

    String getURLPrinter();

    void saveAppID(String appId);

    String getAppId();

    void savePrinterAddress(String address);

    String getPrinterAddress();

    void saveBluetoothPrinterAddress(String address);

    String getBluetoothPrinterAddress();

    void saveDefaultPrinter(int menu, String name);

    String getDefaultPrinter(int menu);

    void saveWorkingPrinter(String name);

    String getWorkingPrinter();

    void saveNMVN(String nmvn);

    String getNMVN();

    void saveURL(String url);

    String getURL();

}
