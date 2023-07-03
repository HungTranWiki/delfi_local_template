package com.delfi.vn.template.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.delfi.core.log.LogEventArgs;
import com.delfi.core.log.LogLevel;
import com.delfi.core.log.Logger;
import com.delfi.vn.template.models.enums.ErrorCode;

public class AppException extends Exception {
    private ErrorCode errorCode;

    private int statusCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public AppException(@NonNull ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public AppException(@NonNull ErrorCode errorCode, Throwable cause) {
        super(errorCode.name() + " :: " + cause);
        this.errorCode = errorCode;
    }

    public AppException(@NonNull ErrorCode errorCode, int statusCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public AppException(ErrorCode errorCode, int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public void writeAppLog(String className) {
        Log.e(className, getMessage());
        Logger.getInstance().logMessage(new LogEventArgs(LogLevel.ERROR, getMessage(), this));
    }
}
