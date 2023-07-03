package com.delfi.vn.template.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.delfi.core.common.SoundManager;
import com.delfi.core.configuration.ConfigurationObject;
import com.delfi.core.configuration.HandleConfigurationIO;
import com.delfi.core.settings.IValidationPasswordCallback;
import com.delfi.core.settings.activities.DelfiPassActivity;
import com.delfi.core.utils.ShowHideKeyboardEvent;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.enums.ActivityRequestCode;
import com.delfi.vn.template.ui.base.BaseActivity;
import com.delfi.vn.template.ui.main.MainActivity;
import com.delfi.vn.template.ui.settings.ExMainSettingActivity;
import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.AppException;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<LoginPresenter>
        implements LoginContract.View, View.OnClickListener, IValidationPasswordCallback {

    @Inject
    LoginPresenter presenter;

    EditText edtUserName, edtPassword;
    Button btnLogin, btnSettings;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        edtUserName.requestFocus();

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected void initViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtUserName.setText(presenter.getLastedUsername());
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword.setText(presenter.getLastedPassword());

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);

        ShowHideKeyboardEvent.assistActivity(this)
                .setShowHideKeyboardEvent((isShow, heightKeyboard) -> isKeyboardShow = isShow);
        edtUserName.setTag(1);
        edtPassword.setTag(2);
        addEditableView(edtUserName);
        addEditableView(edtPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                presenter.onLogin("", edtUserName.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.btnSettings:
                goToSettings();
                break;
        }
    }

    @Override
    public LoginPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = MainActivity.initActivity(getBaseContext(), true);
        startActivityForResult(intent, ActivityRequestCode.MAIN_SCREEN.ordinal());
        finish();
    }

    private void showToastMessage(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show();
        SoundManager.getInstance().PlayError(LoginActivity.this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R1
                || event.keyCodeToString(event.getKeyCode()).contains("KEYCODE_SCANNER_")) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                hideSoftKeyboard(edtUserName);
                hideSoftKeyboard(edtPassword);
            }
        }

        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                view.postDelayed(() -> {
                    view.requestFocus();
                    handleInput(event.getScanCode() == 0);
                }, 100);
            }
        }
        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) {
            onBackPressed();
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        if(getCurrentFocus() == edtUserName && edtUserName.getText().toString().isEmpty()){
            finish();
            return;
        }
        super.onBackPressed();


    }

    public void handleInput(boolean isScanBarcode) {
        if (getCurrentFocus() == edtUserName) {
            edtPassword.requestFocus();
        } else if (getCurrentFocus() == edtPassword) {
            btnLogin.performClick();
        }

    }

    private void goToSettings() {
        ConfigurationObject config = HandleConfigurationIO.readConfig(this);
        if (config == null)
            config = new ConfigurationObject();
        config.appName = getResources().getString(R.string.arito_app_name);
        HandleConfigurationIO.writeConfig(this, config);
        Intent dpIntent = new Intent(this, DelfiPassActivity.class);
        //Customer your Password here
        //dpIntent.putExtra(Constant.EXTRA_DELFI_PASSWORD, "1337");
        DelfiPassActivity.setValidationCallback(this);
        startActivity(dpIntent);
    }

    @Override
    public void onPasswordValidated() {
        Intent intent = new Intent(getBaseContext(), ExMainSettingActivity.class);
        startActivity(intent);
    }


    @Override
    public void onLoginFail(AppException exception) {
        switch (exception.getErrorCode()) {
            case NO_DEVICE_ID_INPUT:
                showToastMessage(R.string.settings_device_empty);
                break;
            case NO_USERNAME_INPUT:
                showToastMessage(R.string.settings_username_empty);
                break;
            case NO_PASSWORD_INPUT:
                showToastMessage(R.string.settings_password_empty);
                break;
            case NO_LOGIN_CONFIGURATION:
                createAppDialog().showInfoDialog(
                        R.string.configuration_title, R.string.configuration_message, R.string.start,
                        this::goToSettings);
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (v != null) {
                    v.vibrate(500);
                }
                break;
            case LOGIN_FAIL:
            default:
                createAppDialog().showErrorDialog(
                        R.drawable.ic_warning_orange_circle_grey, R.string.login_error_title,
                        R.string.login_error_content, null);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED)
            finish();
        if (requestCode == ActivityRequestCode.SERVER_IP_SETTING.ordinal() && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
            System.exit(0);
        }
    }

    public static Intent initActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

}

