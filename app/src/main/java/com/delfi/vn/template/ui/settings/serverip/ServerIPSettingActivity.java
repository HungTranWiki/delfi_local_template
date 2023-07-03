package com.delfi.vn.template.ui.settings.serverip;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.core.common.SharedManager;
import com.delfi.core.common.SoundManager;
import com.delfi.core.utils.ShowHideKeyboardEvent;
import com.delfi.vn.template.R;
import com.delfi.vn.template.ui.base.BaseActivity;
import com.delfi.vn.template.utils.Constants;

import javax.inject.Inject;

public class ServerIPSettingActivity extends BaseActivity<ServerIPSettingPresenter>
        implements ServerIPSettingContract.View, View.OnClickListener {

    @Inject
    ServerIPSettingPresenter presenter;

    private EditText edtAppID;
    private Button saveButton;
    private ImageView icBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_server_ip_setting;
    }

    @Override
    protected void initViews() {
        initToolbar();
        edtAppID = findViewById(R.id.edtServiceUrl);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        String URL = SharedManager.getInstance(this).getString(Constants.URL);
        if (URL != null && URL.length() > 0) {
            edtAppID.setText(URL);
            setEditTextEnable(edtAppID, true);
        }

        ShowHideKeyboardEvent.assistActivity(this).setShowHideKeyboardEvent((b, i) -> isKeyboardShow = b);

        edtAppID.setTag(1);
        addEditableView(edtAppID);
    }

    @Override
    public ServerIPSettingPresenter getPresenter() {
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
                hideSoftKeyboard(edtAppID);
            }
        }

        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (getCurrentFocus() == edtAppID) {
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
        tvMainTitle.setText(R.string.server_ip);

        icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            presenter.validateServerId(edtAppID.getText().toString());
        }
    }

    @Override
    public void addEditableView(EditText editText) {
        super.addEditableView(editText);
    }

    @Override
    public void validateServerIdError() {
        SoundManager.getInstance().PlayError(this);
    }

    @Override
    public void serverIdIsChanged(boolean isChanged) {
        if (isChanged == true) {
            SoundManager.getInstance().PlayOK(this);
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
