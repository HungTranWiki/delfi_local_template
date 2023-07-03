package com.delfi.vn.template.ui.menu22;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.base.screen.BaseFeatureActivity;
import com.delfi.vn.template.ui.customeadapter.expandcollapse.ExpandCollapseAdapter;
import com.delfi.vn.template.utils.AppException;

import java.util.List;

import javax.inject.Inject;

public class Menu22InputActivity extends BaseFeatureActivity<Menu22InputPresenter>
        implements Menu22InputContract.View {

    private boolean isScanBarcode = false;
    private TextView tvTotalReceipt;
    private ReceiptExpandCollapseListAdapter receiptExpandCollapseListAdapter;

    @Inject
    Menu22InputPresenter presenter;

    @Override
    public Menu22InputPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu21_input;
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
        return false;
    }

    @Override
    protected void doOnSyncComplete() {
    }

    @Override
    protected void onControlClick(View view) {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initContentView() {
        ((TextView) findViewById(R.id.tvMainTitle)).setText(R.string.manager_menu13_title);
        tvTotalReceipt = findViewById(R.id.tvTotalReceipt);
        tvTotalReceipt.setVisibility(View.GONE);
        receiptExpandCollapseListAdapter = new ReceiptExpandCollapseListAdapter(new ExpandCollapseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object item, int position) {

            }

            @Override
            public void onDataListChanged(List items) {

            }
        });

        RecyclerView recycleListView = findViewById(R.id.recycleReceiptList);
        recycleListView.setLayoutManager(new LinearLayoutManager(this));
        recycleListView.setAdapter(receiptExpandCollapseListAdapter);
    }

    @Override
    protected void handleInput(boolean isLaserScan, String barcode) {

    }

    @Override
    public void showReceiptList(List<Receipt11> list) {
        receiptExpandCollapseListAdapter.setDataList(list);
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

    private void print(Receipt11 receipt11) {
    }

}
