package com.delfi.vn.template.ui.settings.printbluetooth;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.delfi.core.common.SharedManager;
import com.delfi.core.common.SoundManager;
import com.delfi.core.utils.ShowHideKeyboardEvent;
import com.delfi.vn.template.R;
import com.delfi.vn.template.ui.base.BaseActivity;
import com.delfi.vn.template.utils.Constants;

import javax.inject.Inject;


public class PrinterSettingActivity extends BaseActivity<PrinterSettingPresenter>
        implements PrinterSettingContract.View, View.OnClickListener {

    private EditText edtAddress;
    private Button saveButton, connectButton;
    private ImageView icBack;

    @Inject
    PrinterSettingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_printer_setting;
    }

    @Override
    protected void initViews() {
        initToolbar();
        initContent();
    }

    @Override
    public PrinterSettingPresenter getPresenter() {
        return presenter;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d("dispatchKeyEvent", event.toString());
        if (getCurrentFocus() == icBack)
            icBack.clearFocus();

        if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R1 || event.keyCodeToString(event.getKeyCode()).contains("KEYCODE_SCANNER_")) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                hideSoftKeyboard(edtAddress);
            }
        }

        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (getCurrentFocus() == edtAddress) {
                saveButton.performClick();
            }
        }
        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) {
            onBackPressed();
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            String address = edtAddress.getText().toString();
            if (address.isEmpty()) {
                SoundManager.getInstance().PlayError(this);
                return;
            }
            presenter.save(address);
            SoundManager.getInstance().PlayOK(PrinterSettingActivity.this);
        } else {
            String address = edtAddress.getText().toString();
            if (address.isEmpty()) {
                SoundManager.getInstance().PlayError(this);
                return;
            }
            presenter.connect(address);
        }
    }

    private void initToolbar() {
        TextView tvMainTitle = findViewById(R.id.tvMainTitle);
        tvMainTitle.setText(R.string.printer_setting_title);

        icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(view -> finish());
    }

    private void initContent() {
        edtAddress = findViewById(R.id.edtPrinterIP);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(this);
        String address = SharedManager.getInstance(this).getString(Constants.PRINTER_ADDRESS);
        if (address != null && address.length() > 0) {
            edtAddress.setText(address);
            setEditTextEnable(edtAddress, true);
        }

        ShowHideKeyboardEvent.assistActivity(this).setShowHideKeyboardEvent((b, i) -> isKeyboardShow = b);

        edtAddress.setTag(1);
        addEditableView(edtAddress);
    }

    @Override
    public void onPrinterConnected(boolean status) {
        if (status == false) {
            SoundManager.getInstance().PlayError(this);
            Toast.makeText(getApplicationContext(), "Failed to connect printer", Toast.LENGTH_SHORT).show();
        } else {
            SoundManager.getInstance().PlayOK(PrinterSettingActivity.this);
            Toast.makeText(getApplicationContext(), "Printer connected", Toast.LENGTH_SHORT).show();
        }
    }
}
