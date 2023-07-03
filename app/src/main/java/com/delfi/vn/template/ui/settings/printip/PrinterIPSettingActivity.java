package com.delfi.vn.template.ui.settings.printip;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.delfi.core.common.SharedManager;
import com.delfi.core.common.SoundManager;
import com.delfi.core.utils.ShowHideKeyboardEvent;
import com.delfi.vn.template.R;
import com.delfi.vn.template.ui.base.BaseActivity;
import com.delfi.vn.template.utils.Constants;

import javax.inject.Inject;

public class PrinterIPSettingActivity extends BaseActivity<PrinterIPSettingPresenter>
        implements PrinterIPSettingContract.View, View.OnClickListener {

    @Inject
    PrinterIPSettingPresenter presenter;
    private RadioButton rd300dpi, rd600dpi;
    private EditText edtIPAddress;
    private EditText edtPort;
    private Button saveButton;
    private ImageView icBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_printer_ip_setting;
    }

    @Override
    protected void initViews() {
        initToolbar();
        edtIPAddress = findViewById(R.id.edtPrintIP);
        edtPort = findViewById(R.id.edtPort);
        rd300dpi = findViewById(R.id.rd300Dpi);
        rd600dpi = findViewById(R.id.rd600Dpi);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        String URL = SharedManager.getInstance(this).getString(Constants.PRINTER_IP);
        if (URL != null && URL.length() > 0) {
            edtIPAddress.setText(URL);
            setEditTextEnable(edtIPAddress, true);
        }
        int PORT = SharedManager.getInstance(this).getInt(Constants.PRINTER_PORT);
        if (URL != null && URL.length() > 0) {
            edtPort.setText(String.valueOf(PORT));
        }
        String dpi = SharedManager.getInstance(this).getString(Constants.PRINTER_DPI, "300");
        if (dpi.equals("300"))
            rd300dpi.setChecked(true);
        else
            rd600dpi.setChecked(true);
        ShowHideKeyboardEvent.assistActivity(this).setShowHideKeyboardEvent((b, i) -> isKeyboardShow = b);

        edtIPAddress.setTag(1);
        edtPort.setTag(2);
        addEditableView(edtIPAddress);
        addEditableView(edtPort);
    }

    @Override
    public PrinterIPSettingPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //Log.d("dispatchKeyEvent", event.toString());
        if (getCurrentFocus() == icBack)
            icBack.clearFocus();

        if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R1 || KeyEvent.keyCodeToString(event.getKeyCode()).contains("KEYCODE_SCANNER_")) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                hideSoftKeyboard(edtIPAddress);
            }
        }

        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (getCurrentFocus() == edtIPAddress) {
                saveButton.performClick();
            }
        }
        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) {
            onBackPressed();
        }

        return super.dispatchKeyEvent(event);
    }

    private void initToolbar() {
        TextView tvMainTitle = findViewById(R.id.tvMainTitle);
        tvMainTitle.setText(R.string.printer_ip);

        icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            if (edtIPAddress.getText().length() == 0) {
                validateError();
                edtIPAddress.requestFocus();
            }
            if (edtPort.getText().length() == 0) {
                validateError();
                edtPort.requestFocus();
            }
            presenter.save(edtIPAddress.getText().toString(), edtPort.getText().toString(), (rd300dpi.isChecked() ? "300" : "600"));
        }
    }

    @Override
    public void addEditableView(EditText editText) {
        super.addEditableView(editText);
    }

    @Override
    public void validateError() {
        edtIPAddress.requestFocus();
        edtIPAddress.setError(getString(R.string.error_invalid_ip));
        SoundManager.getInstance().PlayError(this);
    }

    @Override
    public void saveChanged() {
        finish();
    }

}
