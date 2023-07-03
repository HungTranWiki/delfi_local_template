package com.delfi.vn.template.ui.menu11.input;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.delfi.core.common.SoundManager;
import com.delfi.core.controls.styledinput.SimpleStyledAutoCompleteView;
import com.delfi.core.controls.styledinput.basicrules.BlankRule;
import com.delfi.core.controls.styledinput.basicrules.MaxLengthRule;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.base.screen.BaseFeatureActivity;
import com.delfi.vn.template.ui.customeadapter.search.MyAdapter;
import com.delfi.vn.template.ui.customview.SimpleEditedText;
import com.delfi.vn.template.ui.menu11.detail.Menu11DetailActivity;
import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.CommonUtil;

import java.util.List;

import javax.inject.Inject;

public class Menu11InputActivity extends BaseFeatureActivity<Menu11InputPresenter>
        implements Menu11InputContract.View {
    private boolean isScanBarcode = false;
    private SimpleStyledAutoCompleteView edtWarehouse;
    private SimpleEditedText edtReceiptCode;
    private TextView tvTotalReceipt;
    private ReceiptListAdapter receiptListAdapter;

    @Inject
    Menu11InputPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu11_input;
    }

    @Override
    protected void receiveDataFromPreviousScreen() {
    }

    @Override
    protected boolean enableReviewList() {
        return true;
    }

    @Override
    protected boolean enableSyncAction() {
        return true;
    }

    @Override
    protected void doOnSyncComplete() {
        presenter.onCheckLocalDataForSync();
    }

    @Override
    protected void onControlClick(View view) {
    }

    @Override
    public Menu11InputPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getCurrentFocus() == edtWarehouse && edtWarehouse.getText().toString().isEmpty()) {
            presenter.resetReceiptList();
            receiptListAdapter.clearData();
            tvTotalReceipt.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initContentView() {
        ((TextView) findViewById(R.id.tvMainTitle)).setText(R.string.manager_menu11_title);
        tvTotalReceipt = findViewById(R.id.tvTotalReceipt);
        tvTotalReceipt.setVisibility(View.GONE);
        edtWarehouse = findViewById(R.id.edtWarehouse);
        edtWarehouse.addRule(new BlankRule(getString(R.string.empty_field)));
        edtWarehouse.setOnItemClickListener(dropdownWarehouseItemClick);

        edtReceiptCode = findViewById(R.id.edtReceiptCode);
        edtReceiptCode.addRule(new BlankRule(getString(R.string.empty_field)));

        receiptListAdapter = new ReceiptListAdapter(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object item, int position) {
                presenter.setSelectedReceipt((Receipt11) item);
            }

            @Override
            public void onDataListChanged(List items) {
            }
        });
        RecyclerView recycleListView = findViewById(R.id.recycleReceiptList);
        recycleListView.setLayoutManager(new LinearLayoutManager(this));
        recycleListView.setAdapter(receiptListAdapter);

        edtReceiptCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.removeCallbacks(runnableFilter);
                handler.postDelayed(runnableFilter, 500);
            }
        });
        canNotSyncedData();
        clearEditableView();
        edtTag = 1;
        addEditView(edtWarehouse);
        addEditView(edtReceiptCode);
        edtWarehouse.focus();
    }

    @Override
    public void showReceiptList(List<Receipt11> list) {
        edtWarehouse.success();
        edtReceiptCode.focus();
        receiptListAdapter.setDataList(list);
        showTotalOrderCode(list.size());
        CommonUtil.hideKeyboard(edtReceiptCode, this);
    }

    @Override
    public void showProductsScreen(Warehouse warehouse, Receipt11 receipt11) {
        Intent intent = Menu11DetailActivity.initActivity(this, warehouse, receipt11);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showErrorMessage(AppException exception) {
        super.showErrorMessage(exception);
        if(appDialog != null)
            return;
        createAppDialog();
        switch (exception.getErrorCode()) {
            case GET_RECEIPT_11_ERROR:
                appDialog.showErrorDialog(R.drawable.ic_dialog_error,
                        getString(R.string.error),
                        String.format(getString(R.string.get_receipt_11_error_message), exception.getMessage()),
                        null);
                edtWarehouse.focus();
                break;
            default:
                clearAppDialog();
        }
    }

    @Override
    public void showWarehouseList(List<Warehouse> list) {
        edtWarehouse.setDataSource(list);
    }

    protected void handleInput(boolean isLaserScan, String barcode) {
        if (getCurrentFocus() == null) {
            return;
        }
        isScanBarcode = isLaserScan;
        switch (getCurrentFocus().getId()) {
            case R.id.edtWarehouse:
                if (isLaserScan) {
                    edtWarehouse.setText(barcode);
                }
                String validateWarehouse = edtWarehouse.validate();
                if (validateWarehouse != null && !validateWarehouse.isEmpty()) {
                    edtWarehouse.error(validateWarehouse);
                    break;
                }
                Warehouse warehouse = presenter.findWarehouseByKey(edtWarehouse.getText().toString());
                if (warehouse != null) {
                    edtWarehouse.setText(warehouse.getValueShowOnDropdown());
                    presenter.pullReceiptsFromServer();
                } else {
                    edtWarehouse.error(getString(R.string.input_again));
                }
                break;
            case R.id.edtReceiptCode:
                if (isLaserScan) {
                    edtReceiptCode.setText(barcode);
                }
                break;
        }
    }

    private AdapterView.OnItemClickListener dropdownWarehouseItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            hideSoftKeyboard(edtWarehouse);
            handleInput(false, "");
        }
    };

    private void showTotalOrderCode(int total) {
        tvTotalReceipt.setVisibility(View.VISIBLE);
        String totalLocations = getResources().getString(R.string.px_receipt_list, total);
        tvTotalReceipt.setText(totalLocations);
    }

    final Handler handler = new Handler();
    final Runnable runnableFilter = new Runnable() {
        public void run() {
            if (receiptListAdapter != null) {
                receiptListAdapter.getFilter().filter(edtReceiptCode.getText().toString(), count -> {

                    showTotalOrderCode(receiptListAdapter.getItemCount());
                    if (isScanBarcode) {
                        if (receiptListAdapter.getItemCount() == 0) {
                            SoundManager.getInstance().PlayError(Menu11InputActivity.this);
                        } else if (receiptListAdapter.getItemCount() == 1) {
                            presenter.setSelectedReceipt(receiptListAdapter.getValue(0));
                        }
                        isScanBarcode = false;
                    }
                });
            }
        }
    };

}
