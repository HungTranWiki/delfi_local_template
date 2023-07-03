package com.delfi.vn.template.services.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

public class NetworkStatusReceiver extends BroadcastReceiver {

    public ConnectivityReceiverListener connectivityReceiverListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        isConnectedOrConnecting(context);
    }

    private void isConnectedOrConnecting(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (context instanceof ConnectivityReceiverListener && connMgr != null) {
            connectivityReceiverListener = (ConnectivityReceiverListener) context;
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                connectivityReceiverListener.onNetworkConnectionChanged(true);
            } else {
                connectivityReceiverListener.onNetworkConnectionChanged(false);
            }
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isDataNetworkAvailable);
    }
}
