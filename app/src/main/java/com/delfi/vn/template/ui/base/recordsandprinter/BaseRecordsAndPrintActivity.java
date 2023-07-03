package com.delfi.vn.template.ui.base.recordsandprinter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.delfi.core.common.SoundManager;
import com.delfi.core.controls.DialogCreator;
import com.delfi.core.controls.editableview.interfaces.IItem;
import com.delfi.core.controls.editableview.ui.ViewListRecordActivity;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.printmodes.ESCPrintItem;
import com.delfi.vn.template.models.printmodes.PrintItem;
import com.delfi.vn.template.models.enums.MenuCode;
import com.delfi.vn.template.services.api.NetworkStatusReceiver;
import com.delfi.vn.template.ui.customview.printer.SelectPrinterPopup;
import com.delfi.vn.template.ui.main.MainActivity;
import com.delfi.vn.template.ui.settings.printbluetooth.PrinterSettingActivity;
import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.BluetoothConnection;
import com.delfi.vn.template.utils.CommonUtil;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.printer.PrintBlueToothHelper;
import com.delfi.vn.template.utils.printer.BXLPrinter;
import com.delfi.vn.template.utils.printer.model.BxlLabel;
import com.delfi.vn.template.utils.printer.model.BxlPrintOptions;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;
import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseRecordsAndPrintActivity<P extends BaseRecordsAndPrintPresenter>
        extends ViewListRecordActivity
        implements
        BaseRecordsAndPrintContract.Presenter,
        NetworkStatusReceiver.ConnectivityReceiverListener, BaseRecordsAndPrintContract.View {
    @Inject
    SchedulerListener schedulerListener;
    private NetworkStatusReceiver mNetworkReceiver = new NetworkStatusReceiver();
    private P mPresenter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressDialog progressDialog;
    private AppDialog appDialog;

    public abstract P getPresenter();

    public abstract MenuCode getCurrentMenu();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        registerNetworkCheckReceiver();
        this.mPresenter = getPresenter();
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppCompatButton ivDone = this.findViewById(R.id.doneImageView);
        ivDone.setBackgroundResource(R.drawable.ic_print_setting);
        ivDone.setVisibility(View.VISIBLE);
        ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectPrinter();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clearDispose();
        clearDispose();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void finish() {
        CommonUtil.hideKeyboard(getCurrentFocus(), this);
        Completable.complete().delay(100, TimeUnit.MILLISECONDS)
                .observeOn(schedulerListener.ui())
                .subscribeOn(schedulerListener.io())
                .doOnComplete(super::finish)
                .subscribe();
    }

    public void clearDispose() {
        compositeDisposable.dispose();
    }

    private void registerNetworkCheckReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetworkReceiver, intentFilter);
    }


    public void showLoadingDialog() {
        hideSoftKeyboard(getCurrentFocus());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMessage(String message) {
        new AppDialog(this).showErrorDialog(message);
    }

    @Override
    public void showMessage(int resId) {
        new AppDialog(this).showErrorDialog(resId);
    }

    @Override
    public void showError(int errCode) {
        AppDialog appDialog = createAppDialog();
        if (Constants.HTTP_STATUS_UNAUTHORIZED == errCode) {
            appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                    R.string.token_expired_title, R.string.token_expired_message, () -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finishAffinity();
                    });
        } else if (Constants.HTTP_STATUS_INTERNAL_SERVER_ERROR == errCode) {
            appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                    R.string.internal_error_title, R.string.internal_error_message, this::finish);
        }
    }

    protected void onSelectPrinter() {
        SelectPrinterPopup dialog = new SelectPrinterPopup();
        Bundle bundle = new Bundle();
        bundle.putInt("MENU", getCurrentMenu().ordinal());
        dialog.setArguments(bundle);
        dialog.setListener(name -> {
            Log.d("SWITCH_PRINTER", "Switch to " + name);
            if (name.equalsIgnoreCase(Constants.BXL_XT2) || name.equalsIgnoreCase(Constants.BXL_XT5)) {
                if (TextUtils.isEmpty(getPresenter().getPrinterAddress())) {
                    new AppDialog(BaseRecordsAndPrintActivity.this).showConfirmDialog(R.drawable.ic_checked_white_circle_blue, R.string.confirm,
                            R.string.confirm_set_printer_ip_setting, R.string.cancel, R.string.btn_complete, new DialogCreator.IYesNoListener() {
                                @Override
                                public void onYes() {
                                    getPresenter().setDefaultPrinter(getCurrentMenu().ordinal(), name);
                                    startActivity(new Intent(BaseRecordsAndPrintActivity.this, PrinterSettingActivity.class));
                                }

                                @Override
                                public void onCancel() {
                                }
                            });
                } else {
                    getPresenter().setDefaultPrinter(getCurrentMenu().ordinal(), name);
                }
                BXLPrinter.getInstance().reCreate();
            } else {
                BluetoothConnection.getInstance().prepare(BaseRecordsAndPrintActivity.this, new BluetoothConnection.IConnectionCallback() {
                    @Override
                    public void onDeviceSelected(BluetoothDevice device) {
                        getPresenter().setDefaultPrinter(getCurrentMenu().ordinal(), name);
                        getPresenter().saveBluetoothAddress(device);
                        BXLPrinter.getInstance().reCreate();
                    }

                    @Override
                    public void onNoPairedDevice(String message) {
                        showMessage(message);
                    }

                    @Override
                    public void error(String message) {
                        showMessage(message);
                    }
                });
            }
        });
        dialog.show(getSupportFragmentManager(), "pop_printer");
    }

    @SuppressLint("StaticFieldLeak")
    protected void onPrintAsync(List items) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoadingDialog();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                BxlPrintOptions options = getPresenter().getLabelOptions();

                for (Object item : items) {
                    BxlLabel label = ((PrintItem) item).getLabel(options);
                    BXLPrinter.getInstance().print(label);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                hideLoadingDialog();
                getPresenter().updatePrintItems(items);
                onPrintSuccess();
            }
        }.execute();
    }

    //
//    @SuppressLint("StaticFieldLeak")
//    protected void onPrintAsync(SavedMenu1_1PH phItem,  IItem item) {
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                showLoadingDialog();
//            }
//            @Override
//            protected Void doInBackground(Void... voids) {
//                BxlPrintOptions options = getPresenter().getLabelOptions();
//
//
//                BXLPrinter.getInstance().print(((IPrintItem) item).getLabel(options));
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                hideLoadingDialog();
//                getPresenter().updatePrintItem(item);
//                onPrintSuccess();
//            }
//        }.execute();
//    }

    protected void onESCPrint(IItem item) {
        List<Byte> printBuffer = new ArrayList<>();
        printBuffer.addAll(Bytes.asList(((ESCPrintItem) item).getESCData()));
        PrintBlueToothHelper.getInstance().print(this, Bytes.toArray(printBuffer), 1, new PrintBlueToothHelper.IPrinterCallback() {
            @Override
            public void success() {
                getPresenter().updatePrintItem(item);
                runOnUiThread(() -> onPrintSuccess());
            }

            @Override
            public void error(String message) {
                runOnUiThread(() -> showMessage(message));
            }
        });
    }

    protected void onESCPrint(List items) {
        List<Byte> printBuffer = new ArrayList<>();
        for (Object item : items) {
            printBuffer.addAll(Bytes.asList(((ESCPrintItem) item).getESCData()));
        }
        PrintBlueToothHelper.getInstance().print(this, Bytes.toArray(printBuffer), 1, new PrintBlueToothHelper.IPrinterCallback() {
            @Override
            public void success() {
                getPresenter().updatePrintItems(items);
                runOnUiThread(() -> onPrintSuccess());
            }

            @Override
            public void error(String message) {
                runOnUiThread(() -> showMessage(message));
            }
        });
    }

    public void onPrintSuccess() {
        new AppDialog(this).showInfoDialog(R.string.print_success_title, R.string.message_print_success, R.string.ok, null);
        SoundManager.getInstance().PlayOK(this);
    }

    @Override
    public void updateAfterPrint(IItem item) {
        updateItem(item);
    }

    protected AppDialog createAppDialog() {
        if (appDialog != null && appDialog.isShowing())
            appDialog.dismiss();
        appDialog = new AppDialog(this);
        return appDialog;
    }

    protected void clearAppDialog() {
        if (appDialog != null && appDialog.isShowing())
            appDialog.dismiss();
    }

}
