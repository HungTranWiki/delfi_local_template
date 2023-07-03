package com.delfi.vn.template.ui.base.screen;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.core.common.SoundManager;
import com.delfi.core.controls.DialogCreator;
import com.delfi.core.scanner.DelfiScannerHandler;
import com.delfi.core.scanner.LaserEventArgs;
import com.delfi.core.scanner.OnScanListener;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.printmodes.PrintItem;
import com.delfi.vn.template.models.enums.ActivityRequestCode;
import com.delfi.vn.template.models.enums.MenuCode;
import com.delfi.vn.template.ui.base.BaseActivity;
import com.delfi.vn.template.ui.customview.printer.SelectPrinterPopup;
import com.delfi.vn.template.ui.settings.printbluetooth.PrinterSettingActivity;
import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.BluetoothConnection;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.printer.BXLPrinter;
import com.delfi.vn.template.utils.printer.model.BxlLabel;
import com.delfi.vn.template.utils.printer.model.BxlPrintOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseFeatureActivity<P extends BaseFeaturePresenter>
        extends BaseActivity<P> implements BaseFeatureContract.View, View.OnClickListener {

    protected ImageView icList, icCheckSuccess;

    private boolean isEnableReviewList = true;
    private boolean isEnableSyncAction = true;
    private boolean isEnablePrintBeforeSync = false;

    protected abstract void doOnSyncComplete();

    protected abstract void initContentView();

    protected abstract void receiveDataFromPreviousScreen();

    protected abstract boolean enableReviewList();

    protected abstract boolean enableSyncAction();

    protected abstract void handleInput(boolean isLaserScan, String barcode);

    protected int edtTag = 1;

    protected void moveToReviewScreen() {
    }

    @Override
    protected void initViews() {
        receiveDataFromPreviousScreen();
        initToolbar();
        initContentView();
        getPresenter().getDataToStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerScanBarcodeListener();
        getPresenter().onCheckLocalDataForSync();
    }

    @Override
    public void onPause() {
        super.onPause();
        onStopScannerListener();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.icList) {
            moveToReviewScreen();
        } else if (v.getId() == R.id.icCheckSuccess) {
            if(isEnablePrintBeforeSync)
                showPrintConfirmDialog();
            else
                showCompleteConfirmDialog();
        }
        this.onControlClick(v);
    }

    protected void onControlClick(View view) {
    }

    @Override
    public boolean dispatchKeyEvent(@NotNull KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R1
                || KeyEvent.keyCodeToString(event.getKeyCode()).contains("KEYCODE_SCANNER_")) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
            }
        }

        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                view.postDelayed(() -> {
                    view.requestFocus();
                    handleInput(false, "");
                }, 100);
            }
        }
        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) {
            onBackPressed();
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onCheckLocalDataForSync(int result) {
        if (result > 0) {
            syncEnabled(true);
        } else {
            syncEnabled(false);
        }
    }

    @Override
    public void onGetDataToStartError(AppException throwable) {
        createAppDialog().showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                R.string.request_data_server_error_title, R.string.request_data_server_error_msg, null);
    }

    @Override
    public void canSyncedData() {
        icList.setAlpha(Constants.ENABLED_ALPHA);
        icList.setEnabled(true);
        icCheckSuccess.setAlpha(Constants.ENABLED_ALPHA);
        icCheckSuccess.setEnabled(true);
    }

    @Override
    public void canNotSyncedData() {
        icList.setAlpha(Constants.DISABLED_ALPHA);
        icList.setEnabled(false);
        icCheckSuccess.setAlpha(Constants.DISABLED_ALPHA);
        icCheckSuccess.setEnabled(false);
    }

    @Override
    public void viewList(int size) {
        if (size > 0) {
            icList.setAlpha(Constants.ENABLED_ALPHA);
            icList.setEnabled(true);
        } else {
            icList.setAlpha(Constants.DISABLED_ALPHA);
            icList.setEnabled(false);
        }
    }

    @Override
    public void onSyncDataSuccess() {
        canNotSyncedData();
        viewList(0);
        SoundManager.getInstance().PlayOK(this);
        AppDialog appDialog = createAppDialog();
        appDialog.showInfoDialog(R.drawable.ic_sync_success_cicrle_blue, R.string.sync_success_title,
                R.string.sync_success_message, R.string.btn_completed, new DialogCreator.IYesNoListener() {
                    @Override
                    public void onYes() {
                        doOnSyncComplete();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

    @Override
    public void onSyncDataError() {
        AppDialog appDialog = createAppDialog();
        appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                R.string.sync_error_title, R.string.sync_error_msg, null);
    }

    private void initToolbar() {
        isEnableReviewList = enableReviewList();
        isEnableSyncAction = enableSyncAction();
        ((TextView)findViewById(R.id.tvMainTitle)).setText(R.string.menu2_2_title);
        icList = findViewById(R.id.icList);
        icCheckSuccess = findViewById(R.id.icCheckSuccess);

        ImageView icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(view -> onBackPreviousScreen());
        if (isEnableReviewList) {
            icList.setVisibility(View.VISIBLE);
            icList.setOnClickListener(this);
        } else {
            icList.setVisibility(View.GONE);
        }
        if (isEnableSyncAction) {
            icCheckSuccess.setVisibility(View.VISIBLE);
            icCheckSuccess.setOnClickListener(this);
        } else {
            icCheckSuccess.setVisibility(View.GONE);
        }
        syncEnabled(false);
    }

    private void syncEnabled(boolean enable) {
        if (isEnableReviewList) {
            icList.setEnabled(enable);
            if (enable) {
                icList.setAlpha(Constants.ENABLED_ALPHA);
            } else {
                icList.setAlpha(Constants.DISABLED_ALPHA);
            }
        }
        if (isEnableSyncAction) {
            icCheckSuccess.setEnabled(enable);
            if (enable) {
                icCheckSuccess.setAlpha(Constants.ENABLED_ALPHA);
            } else {
                icCheckSuccess.setAlpha(Constants.DISABLED_ALPHA);
            }
        }
    }

    protected void showCompleteConfirmDialog() {
        AppDialog appDialog = createAppDialog();
        appDialog.showConfirmDialog(R.drawable.ic_checked_blue_circle_grey, R.string.confirm_inventory_title,
                R.string.confirm_inventory_message, R.string.cancel, R.string.btn_completed, new DialogCreator.IYesNoListener() {
                    @Override
                    public void onYes() {
                        getPresenter().onSyncData();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

    protected void showPrintConfirmDialog() {
        AppDialog appDialog = createAppDialog();
        appDialog.showConfirmDialog(R.drawable.ic_checked_blue_circle_grey, R.string.confirm_print_title,
                R.string.confirm_print_message, R.string.menu_sync, R.string.print_title, new DialogCreator.IYesNoListener() {
                    @Override
                    public void onYes() {
                        getPresenter().getDataToPrint();
                        //onPrintAsync(list);
                    }

                    @Override
                    public void onCancel()
                    {
                        showCompleteConfirmDialog();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode.REVIEW_ITEMS.ordinal()) {
            getPresenter().onCheckLocalDataForSync();
        }
    }

    protected void onBackPreviousScreen() {
        finish();
    }

    protected void addEditView(EditText edt) {
        edt.setTag(edtTag++);
        addEditableView(edt);
    }

    private void registerScanBarcodeListener() {
        DelfiScannerHandler.getInstance(this).enableScanner();
        DelfiScannerHandler.getInstance(this).setDecodeResultType(0); //user msg
        DelfiScannerHandler.getInstance(this).setOnScanListener(new OnScanListener() {
            @Override
            public void onRead(LaserEventArgs args) {
                if (args == null || args.Barcode == null)
                    return;
                args.Barcode = args.Barcode.replace("\n", "");
                if (getCurrentFocus() != null) {
                    handleInput(true, args.Barcode);

                }
            }
        });
    }

    private void onStopScannerListener() {
        DelfiScannerHandler.getInstance(this).setDecodeResultType(1); //key event listener
        DelfiScannerHandler.getInstance(this).removeOnScanListener();
    }



    @SuppressLint("StaticFieldLeak")
    @Override
    public void onPrintAsync(List items) {
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
                //onPrintSuccess();
            }
        }.execute();
    }


    @Override
    public void updateAfterPrint(PrintItem item) {
        //updateItem(item)
    }

    @Override
    public void showChoosePrinter() {
        SelectPrinterPopup dialog = new SelectPrinterPopup();
        Bundle bundle = new Bundle();
        bundle.putInt("MENU", getCurrentMenu().ordinal());
        dialog.setArguments(bundle);
        dialog.setListener(name -> {
            Log.d("SWITCH_PRINTER", "Switch to " + name);
            if (name.equalsIgnoreCase(Constants.BXL_XT2) || name.equalsIgnoreCase(Constants.BXL_XT5)) {
                if (TextUtils.isEmpty(getPresenter().getPrinterAddress())) {
                    createAppDialog().showConfirmDialog(R.drawable.ic_checked_white_circle_blue, R.string.confirm,
                            R.string.confirm_set_printer_ip_setting, R.string.cancel, R.string.btn_complete, new DialogCreator.IYesNoListener() {
                                @Override
                                public void onYes() {
                                    getPresenter().setDefaultPrinter(getCurrentMenu().ordinal(), name);
                                    startActivity(new Intent(BaseFeatureActivity.this, PrinterSettingActivity.class));
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
                BluetoothConnection.getInstance().prepare(BaseFeatureActivity.this, new BluetoothConnection.IConnectionCallback() {
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

    public void onPrintSuccess() {
        SoundManager.getInstance().PlayOK(this);
        createAppDialog().showConfirmDialog(R.drawable.ic_checked_blue_circle_grey, R.string.print_success_and_continue_title,
                R.string.print_success_and_continue_message, R.string.menu_sync, R.string.print_title, new DialogCreator.IYesNoListener() {
                    @Override
                    public void onYes() {
                        getPresenter().getDataToPrint();
                        //onPrintAsync(list);
                    }

                    @Override
                    public void onCancel()
                    {
                        showCompleteConfirmDialog();
                    }
                });

    }

    public void setEnablePrintBeforeSync(boolean printBeforeSync){
        this.isEnablePrintBeforeSync = printBeforeSync;
    }

    protected MenuCode getCurrentMenu(){
        return MenuCode.MENU_21;
    }

    protected void initPrinter(MenuCode menuCode){
        BXLPrinter.getInstance().with(this);
        getPresenter().setActiveMenu(menuCode.ordinal());
    }
}
