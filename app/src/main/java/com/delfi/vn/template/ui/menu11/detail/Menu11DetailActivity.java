package com.delfi.vn.template.ui.menu11.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Filter;
import android.widget.TextView;

import com.delfi.core.common.SoundManager;
import com.delfi.core.scanner.DelfiScannerHandler;
import com.delfi.core.scanner.LaserEventArgs;
import com.delfi.core.scanner.OnScanListener;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.enums.ActivityRequestCode;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.ui.base.screen.BaseFeatureActivity;
import com.delfi.vn.template.ui.customeadapter.search.MyAdapter;
import com.delfi.vn.template.ui.customview.SimpleEditedText;
import com.delfi.vn.template.ui.menu11.review.Menu11ReviewActivity;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.CommonUtil;

import java.util.List;

import javax.inject.Inject;

public class Menu11DetailActivity extends BaseFeatureActivity<Menu11DetailPresenter>
        implements Menu11DetailContract.View {
    private boolean isScanBarcode = false;
    private EditQuantityPopup detailProductPopup = null;
    private SimpleEditedText edtReceiptCode;
    private ProductListAdapter productListAdapter;
    private Warehouse warehouse;

    public static Intent initActivity(Context context, Warehouse warehouse, Receipt11 receiptPO1) {
        Intent intent = new Intent(context, Menu11DetailActivity.class);
        intent.putExtra("Warehouse", warehouse);
        intent.putExtra("ReceiptId", receiptPO1);
        return intent;
    }

    @Override
    protected void receiveDataFromPreviousScreen() {
        Warehouse warehouse = (Warehouse) getIntent().getSerializableExtra("Warehouse");
        Receipt11 receipt11 = (Receipt11) getIntent().getSerializableExtra("ReceiptId");
        presenter.receiptData(warehouse, receipt11);
    }

    @Inject
    Menu11DetailPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu11_detail;
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
        finish();
    }

    @Override
    public Menu11DetailPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode.REVIEW_ITEMS.ordinal()) {
            presenter.refresh();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initContentView() {
        ((TextView) findViewById(R.id.tvMainTitle)).setText(R.string.manager_menu11_title);
        edtReceiptCode = findViewById(R.id.edtReceiptCode);
        productListAdapter = new ProductListAdapter(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object item, int position) {
                presenter.setSelectedProduct(position, (ProductMenu11) item);
            }

            @Override
            public void onDataListChanged(List items) {
            }
        });
        RecyclerView recycleListView = findViewById(R.id.recycleReceiptList);
        recycleListView.setLayoutManager(new LinearLayoutManager(this));
        recycleListView.setAdapter(productListAdapter);
        canNotSyncedData();

        clearEditableView();
        edtTag = 1;
        addEditView(edtReceiptCode);
        edtReceiptCode.focus();

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
    }

    @Override
    public void showProductList(String soCT, List<ProductMenu11> inventoryList) {
        ((TextView) findViewById(R.id.tvReceiptCodeDetail)).setText(soCT);
        productListAdapter.setDataList(inventoryList);
        showTotalOrderCode(inventoryList.size());
        CommonUtil.hideKeyboard(edtReceiptCode, this);
    }

    @Override
    public void showEditQuantity(int position, ProductMenu11 product) {
        if (presenter.getSelectedProduct() == null) {
            return;
        }
        if (detailProductPopup != null && detailProductPopup.isVisible()) {
            detailProductPopup.dismiss();
            detailProductPopup = null;
        }
        detailProductPopup = new EditQuantityPopup();

        detailProductPopup.show(getSupportFragmentManager(), "Menu12EditNotePopup",
                product, new EditQuantityPopup.Listener() {

                    @Override
                    public void complete(int quantity, ProductMenu11 productMenu11, String barcode) {
                        if (detailProductPopup != null) {
                            detailProductPopup.dismiss();
                            detailProductPopup = null;
                        }
                        presenter.saveQuantity(position, quantity, productMenu11, barcode);
                    }

                    @Override
                    public void cancel() {
                        if (detailProductPopup != null) {
                            detailProductPopup.dismiss();
                            detailProductPopup = null;
                        }
                        //edtProductCode.selectAll();
                    }

                    @Override
                    public void stop() {
                        registerScanBarcodeListener();
                    }
                });
    }

    @Override
    public void savedDataSuccess(int position, String barcode, ProductMenu11 menu11) {
        SoundManager.getInstance().PlayOK(this);
        //presenter.refresh();
        presenter.onCheckLocalDataForSync();
        productListAdapter.setValue(position, menu11);
        edtReceiptCode.getText().clear();

        if (!barcode.isEmpty()) {
            handleInput(true, barcode);
        }
    }

    @Override
    protected void moveToReviewScreen() {
        Intent intent = Menu11ReviewActivity.initActivity(Menu11DetailActivity.this);
        startActivityForResult(intent, ActivityRequestCode.REVIEW_ITEMS.ordinal());
    }

    @Override
    protected void handleInput(boolean isLaserScan, String barcode) {
        if (getCurrentFocus() == null) {
            return;
        }
        isScanBarcode = isLaserScan;
        switch (getCurrentFocus().getId()) {
            case R.id.edtReceiptCode:
                if (isLaserScan) {
                    edtReceiptCode.setText(barcode);
                }
                isScanBarcode = true;
                handler.removeCallbacks(runnableFilter);
                handler.postDelayed(runnableFilter, 0);
                break;
        }

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

    private void showTotalOrderCode(int total) {
        TextView tvTotalLocations = findViewById(R.id.tvTotalReceipt);
        String totalLocations = getResources().getString(R.string.product_list, total);
        tvTotalLocations.setText(totalLocations);
    }

    final Handler handler = new Handler();
    final Runnable runnableFilter = new Runnable() {
        public void run() {
            if (productListAdapter != null) {
                productListAdapter.getFilter().filter(edtReceiptCode.getText(), new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        showTotalOrderCode(productListAdapter.getItemCount());
                        if (isScanBarcode) {
                            if (productListAdapter.getItemCount() == 0) {
                                showErrorMessage(new AppException(ErrorCode.BARCODE_NOT_FOUND));
                            } else if (productListAdapter.getItemCount() == 1) {
                                clearAppDialog();
                                presenter.setSelectedProduct(0, productListAdapter.getValue(0));
                            }
                            isScanBarcode = false;
                        }
                    }
                });
            }
        }
    };
}
