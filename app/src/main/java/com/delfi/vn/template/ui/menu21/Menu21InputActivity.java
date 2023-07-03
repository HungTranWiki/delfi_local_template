package com.delfi.vn.template.ui.menu21;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.core.controls.DialogCreator;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.enums.MenuCode;
import com.delfi.vn.template.models.printmodes.PrintItem;
import com.delfi.vn.template.models.printmodes.Receipt11Label;
import com.delfi.vn.template.ui.base.screen.BaseFeatureActivity;
import com.delfi.vn.template.ui.customview.printer.SelectPrinterPopup;
import com.delfi.vn.template.ui.settings.printbluetooth.PrinterSettingActivity;
import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.BluetoothConnection;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.printer.BXLPrinter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Menu21InputActivity extends BaseFeatureActivity<Menu21InputPresenter>
        implements Menu21InputContract.View {

    private boolean isScanBarcode = false;
    private TextView tvTotalReceipt;
    private ReceiptSwipeListAdapter receiptSwipeListAdapter;

    //Important for Printer
    @Override
    public MenuCode getCurrentMenu() {
        return MenuCode.MENU_21;
    }

    @Inject
    Menu21InputPresenter presenter;

    @Override
    public Menu21InputPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu21_input;
    }

    @Override
    protected void doOnSyncComplete() {

    }

    @Override
    protected void receiveDataFromPreviousScreen() {
    }

    @Override
    protected boolean enableReviewList() {
        return false;
    }

    @Override
    protected boolean enableSyncAction() {
        return true;
    }

    @Override
    protected void onControlClick(View view) {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initContentView() {
        initPrinter(MenuCode.MENU_21);
        ((TextView) findViewById(R.id.tvMainTitle)).setText(R.string.manager_menu12_title);
        tvTotalReceipt = findViewById(R.id.tvTotalReceipt);
        tvTotalReceipt.setVisibility(View.GONE);
        receiptSwipeListAdapter = new ReceiptSwipeListAdapter(new ReceiptSwipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Receipt11 w, int position) {

            }

            @Override
            public void onViewOrderDetail(Receipt11 w, int position) {

            }

            @Override
            public void onPrintLabel(Receipt11 item, int position) {
                presenter.print(item);
            }
        });
        RecyclerView recycleListView = findViewById(R.id.recycleReceiptList);
        recycleListView.setLayoutManager(new LinearLayoutManager(this));
        recycleListView.setAdapter(receiptSwipeListAdapter);

        ImageView ivDone = this.findViewById(R.id.icCheckSuccess);
        ivDone.setImageResource(R.drawable.ic_print_setting);
        ivDone.setVisibility(View.VISIBLE);
        ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectPrinter();
            }
        });
    }

    @Override
    protected void handleInput(boolean isLaserScan, String barcode) {

    }

    @Override
    public void showReceiptList(List<Receipt11> list) {
        receiptSwipeListAdapter.setDataList(list);
        showTotalOrderCode(list.size());
    }

    @Override
    public void showErrorMessage(AppException exception) {
        super.showErrorMessage(exception);
        if (appDialog != null)
            return;
        createAppDialog();
        switch (exception.getErrorCode()) {
            case GET_RECEIPT_11_ERROR:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        getString(R.string.error),
                        String.format(getString(R.string.get_receipt_11_error_message), exception.getMessage()),
                        null);
                break;
            default:
                clearAppDialog();
        }
    }

    private void showTotalOrderCode(int total) {
        tvTotalReceipt.setVisibility(View.VISIBLE);
        String totalLocations = getResources().getString(R.string.px_receipt_list, total);
        tvTotalReceipt.setText(totalLocations);
    }


    @Override
    public void startPrint(Receipt11Label item) {
        List<Receipt11Label> list = new ArrayList<>();
        list.add(item);
        onPrintAsync(list);
    }

    @Override
    public void updateAfterPrint(PrintItem item){
        receiptSwipeListAdapter.notifyDataSetChanged();
    }

    protected void onSelectPrinter() {
        SelectPrinterPopup dialog = new SelectPrinterPopup();
        Bundle bundle = new Bundle();
        bundle.putInt("MENU", getCurrentMenu().ordinal());
        dialog.setArguments(bundle);
        dialog.setListener(new SelectPrinterPopup.OnPrinterSelected() {
            @Override
            public void onSelected(String name) {
                Log.d("SWITCH_PRINTER", "Switch to " + name);
                if (name.equalsIgnoreCase(Constants.BXL_XT2) || name.equalsIgnoreCase(Constants.BXL_XT5)) {
                    if (TextUtils.isEmpty(Menu21InputActivity.this.getPresenter().getPrinterAddress())) {
                        new AppDialog(Menu21InputActivity.this).showConfirmDialog(R.drawable.ic_checked_white_circle_blue, R.string.confirm,
                                R.string.confirm_set_printer_ip_setting, R.string.cancel, R.string.btn_complete, new DialogCreator.IYesNoListener() {
                                    @Override
                                    public void onYes() {
                                        getPresenter().setDefaultPrinter(getCurrentMenu().ordinal(), name);
                                        startActivity(new Intent(Menu21InputActivity.this, PrinterSettingActivity.class));
                                    }

                                    @Override
                                    public void onCancel() {
                                    }
                                });
                    } else {
                        Menu21InputActivity.this.getPresenter().setDefaultPrinter(Menu21InputActivity.this.getCurrentMenu().ordinal(), name);
                    }
                    BXLPrinter.getInstance().reCreate();
                } else {
                    BluetoothConnection.getInstance().prepare(Menu21InputActivity.this, new BluetoothConnection.IConnectionCallback() {
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
            }
        });
        dialog.show(getSupportFragmentManager(), "pop_printer");
    }
}
