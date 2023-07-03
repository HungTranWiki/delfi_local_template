package com.delfi.vn.template.utils;

import android.os.Environment;
import android.util.Log;

import com.delfi.core.log.LogEventArgs;
import com.delfi.core.log.LogLevel;
import com.delfi.core.log.Logger;
import com.delfi.vn.template.BuildConfig;

public class Constants {
    public static final String BASE_URL = "https://odoo-uat.viettinhanh.com.vn";
    public static final String URL_AX = "https://integ-uat.viettinhanh.com.vn";
    public static final String DOWNLOAD_FILE = "DownloadFile1/";
    public static final String IMPORT_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/import/Master Data.txt";
    public static final String EXPORT_SCAN_DATA_FILE = Environment.getExternalStorageDirectory().getPath() + "/export/ExportMenu11.txt";

    public static final String DB_NAME = "mykingdom.db";
    public static final int DB_VERSION = BuildConfig.VERSION_CODE;
    public static final String DEFAULT_DEVICE_ID = "MYKINGDOM";

    public static final int HTTP_STATUS_SUCCESS = 200;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_UNAUTHORIZED = 454;
    public static final int HTTP_STATUS_FORBIDDEN_ACCESS = 403;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
    public static final int HTTP_SERVICE_UNAVAILABLE = 503;
    public static final int HTTP_GATEWAY_TIMEOUT = 504;

    public static final float DISABLED_ALPHA = 0.3f;
    public static final float ENABLED_ALPHA = 1f;

    public static final int REQUEST_CODE_ACTIVITY_INTERNAL_PRODUCT = 100;

    public static final int MAX_QUANTITY_LENGTH = 10;
    public static final String INTENT_SUB_MENU_TYPE = "INTENT_SUB_MENU_TYPE";


    public static String Language = "LANGUAGE";

    public static final String URL = "URL";
    public static final String URLAX = "URLAX";
    public static final String URL_PRINTER = "URL_PRINTER";

    public static final String DEFAULT_ORDER_DATE = "2019-01-01";

    public static final String PRINTER_IP = "PRINTER_IP";
    public static final String PRINTER_PORT = "PRINTER_PORT";
    public static final String PRINTER_DPI = "PRINTER_DPI";
    public static final String NO_TEXT_DATA = "";

    public static final String BXL_XT5 = "XT5";
    public static final String BXL_XT2 = "XT2";
    public static final String BXL_SPP_L310 = "L310";
    public static final String BXL_SPP_R310 = "R310";
    public static final int BXL_BLUETOOTH_PORT = 2;
    public static final int BXL_WIFI_PORT = 1;
    public static final String PRINTER_ADDRESS = "PRINTER_ADDRESS";
    public static final String DEFAULT_PRINTER = "DEFAULT_MENU_CHECK_PRINTER";
    public static final String MENU_5 = "Menu5";
    public static final String MENU_OTHER = "MenuOther";
    public static final String MENU_11 = "Menu_11";
    public static final String WORKING_PRINTER = "DEFAULT_PRINTER";
    public static final String BT_PRINTER_ADDRESS = "BT_PRINTER_ADDRESS";
    public static final int LABEL_OLD_SIZE = 15;
    public static final int LABEL_XT_SIZE = 25;
    public static final int LABEL_NEW_SIZE = 75;
    public static final String LAST_NMVN = "LAST_NMVN";

    public static final int SYNC_SUCCESS = 1000;
    public static final int RESULT_EDITED = 8888;

    public static void showErrorLog(String componentName, Throwable t) {
        Log.e(componentName, t.getMessage());
        t.printStackTrace();
        Logger.getInstance().logMessage(new LogEventArgs(LogLevel.ERROR, t.getMessage(), t));
    }
}