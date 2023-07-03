package com.delfi.vn.template.ui.settings.appid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.core.common.SharedManager;
import com.delfi.core.common.SoundManager;
import com.delfi.core.controls.DialogCreator;
import com.delfi.core.utils.Constant;
import com.delfi.core.utils.ShowHideKeyboardEvent;
import com.delfi.vn.template.R;
import com.delfi.vn.template.ui.base.BaseActivity;
import com.delfi.vn.template.utils.AppDialog;

import javax.inject.Inject;


public class AppIDSettingActivity extends BaseActivity<AppIDSettingPresenter>
        implements AppIDSettingContract.View, View.OnClickListener {

    private EditText edtAppID;
    private Button saveButton;
    private ImageView icBack;
    
    @Inject
    public AppIDSettingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appid_setting;
    }

    @Override
    protected void initViews() {
        initToolbar();
        initContent();
    }

    @Override
    public AppIDSettingPresenter getPresenter() {
        return this.presenter;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d("dispatchKeyEvent", event.toString());
        if (getCurrentFocus() == icBack)
            icBack.clearFocus();

        if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R1 || event.keyCodeToString(event.getKeyCode()).contains("KEYCODE_SCANNER_")) {
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            String url = edtAppID.getText().toString();
            if (url.isEmpty()) {
                SoundManager.getInstance().PlayError(this);
                return;
            }
            createAppDialog().showConfirmDialog(R.drawable.ic_warning_blue_circle_grey,
                    R.string.configuration_title, R.string.confirm_app_id_change,
                    R.string.cancel, R.string.str_continue,
                    new DialogCreator.IYesNoListener() {
                        @Override
                        public void onYes() {
                            getPresenter().saveAppId(url);
                            SoundManager.getInstance().PlayOK(AppIDSettingActivity.this);

                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            if (i != null) {
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                            finish();
                        }

                        @Override
                        public void onCancel() {
                            selectAllEditText(edtAppID);
                        }
                    });
        }
    }

    private void initToolbar() {
        TextView tvMainTitle = findViewById(R.id.tvMainTitle);
        tvMainTitle.setText(R.string.server_ip);

        icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(view -> finish());
    }

    private void initContent() {
        edtAppID = findViewById(R.id.edtServiceUrl);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        String appId = SharedManager.getInstance(this).getString(Constant.APP_ID);
        if (appId != null && appId.length() > 0) {
            edtAppID.setText(appId);
            setEditTextEnable(edtAppID, true);
        }

        ShowHideKeyboardEvent.assistActivity(this).setShowHideKeyboardEvent((b, i) -> isKeyboardShow = b);

        edtAppID.setTag(1);
        addEditableView(edtAppID);

        detectInput(edtAppID, 36, false, null);
    }
}
