package com.delfi.vn.template.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.delfi.core.BuildConfig;
import com.delfi.core.common.SharedManager;
import com.delfi.core.communication.CommunicationHandler;
import com.delfi.core.communication.ICommunicationListener;
import com.delfi.core.configuration.ConfigurationObject;
import com.delfi.core.configuration.HandleConfigurationIO;
import com.delfi.core.controls.DialogCreator;
import com.delfi.core.settings.IValidationPasswordCallback;
import com.delfi.core.settings.activities.DelfiPassActivity;
import com.delfi.core.settings.activities.GeneralInfoActivity;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.MainMenu;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;
import com.delfi.vn.template.models.enums.ActivityRequestCode;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.ui.base.BaseActivity;
import com.delfi.vn.template.ui.customeadapter.mainmenu.MainMenuAdapter;
import com.delfi.vn.template.ui.customeadapter.mainmenu.OnItemClickListener;
import com.delfi.vn.template.ui.login.LoginActivity;
import com.delfi.vn.template.ui.main.submenu.SubMainMenuActivity;
import com.delfi.vn.template.ui.settings.ExMainSettingActivity;
import com.delfi.vn.template.ui.settings.printip.PrinterIPSettingActivity;
import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.Constants;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainPresenter>
        implements MainContract.View, View.OnClickListener, OnItemClickListener, IValidationPasswordCallback {

    @Inject
    MainPresenter presenter;
    MainMenuAdapter adapter;
    private final static String IS_MANGER = "IS_MANGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        findViewById(R.id.imgOptionMenus).setOnClickListener(this);

        //test
        adapter = new MainMenuAdapter(this, presenter.isActivation());
        adapter.setOnItemClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycleMainMenu);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.showMenuList(getIntent().getBooleanExtra(IS_MANGER, true));
    }

    @Override
    public void showErrorMessage(AppException exception) {
        super.showErrorMessage(exception);
        if (appDialog != null)
            return;
        createAppDialog();
        switch (exception.getErrorCode()) {
            case ERROR_SERVER_CONFIG:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.sync_title, R.string.ftp_not_found_message, new DialogCreator.IOKListener() {
                            @Override
                            public void onOk() {
                                goToSettings();
                            }
                        });
                break;
            case ERROR_GET_SAVED_NHAP_KHO:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.sync_title, R.string.get_receipt_11_to_sync_error_message, null);
                break;
            case NO_DATA_IMPORT:
                appDialog.showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                        R.string.sync_error_title, R.string.sync_error_msg, null);
                break;
            default:
                clearAppDialog();
        }
    }

    private void promptPrinterSettings() {
        String ip = SharedManager.getInstance(this).getString(Constants.PRINTER_IP, "");
        if (ip.length() == 0) {
            createAppDialog();
            appDialog.showConfirmDialog(R.drawable.ic_question,
                    R.string.printer_settings,
                    R.string.ask_for_printer_settings,
                    R.string.noButtonText, R.string.yesButtonText, new DialogCreator.IYesNoListener() {
                        @Override
                        public void onYes() {
                            Intent main = new Intent(getBaseContext(), PrinterIPSettingActivity.class);
                            startActivity(main);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
    }

    @Override
    public MainPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgOptionMenus:
                showOptionDialog();
                break;

        }
    }

    @Override
    public void onRefreshMenuList(List<MainMenu> mainMenuList) {
        adapter.refresh(mainMenuList);
    }

    @Override
    public void logoutSuccess() {
        boolean isManager = getIntent().getBooleanExtra(IS_MANGER, true);
        finish();
        if (!isManager) {
            Intent intent = LoginActivity.initActivity(MainActivity.this);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(MainMenu mainMenu) {
        if (mainMenu.getSubMenus().isEmpty()) {
            if (mainMenu.getcClass() != null)
                MainActivity.this.startActivity(new Intent(MainActivity.this, mainMenu.getcClass()));
        } else {
            Intent intent = new Intent(MainActivity.this, SubMainMenuActivity.class);
            intent.putExtra(SubMainMenuActivity.SUB_MENU_NAME, mainMenu.getNameResource());
            intent.putExtra(SubMainMenuActivity.SUB_MENU_LIST, (Serializable) mainMenu.getSubMenus());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode.SERVER_IP_SETTING.ordinal()) {
            presenter.checkURLConfigIsChanged();
        }
    }

    @Override
    public void onPasswordValidated() {
        Intent intent = new Intent(getBaseContext(), ExMainSettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void forceLogout() {
        createAppDialog().showInfoDialog(R.string.configuration_title,
                R.string.url_is_changed_message,
                R.string.ok, new DialogCreator.IOKListener() {
                    @Override
                    public void onOk() {
                        Intent intent = LoginActivity.initActivity(MainActivity.this);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                    }
                });
    }

    @Override
    public void syncDataSuccess() {

    }

    @Override
    public void syncDataError(String error) {
        new AppDialog(this).showErrorDialog(R.drawable.ic_warning_orange_circle_grey,
                R.string.sync_title, R.string.sync_error_msg, null);
    }

    private void goToSettings() {
        ConfigurationObject config = HandleConfigurationIO.readConfig(this);
        if (config == null)
            config = new ConfigurationObject();
        config.appName = getResources().getString(R.string.app_name);
        HandleConfigurationIO.writeConfig(this, config);
        Intent dpIntent = new Intent(this, DelfiPassActivity.class);
        presenter.setURLConfigBeforeUpdate();
        DelfiPassActivity.setValidationCallback(this);
        startActivityForResult(dpIntent, ActivityRequestCode.SERVER_IP_SETTING.ordinal());
    }

    @Override
    public void goToSyncCommunicationAfterExported() {
        ConfigurationObject config = HandleConfigurationIO.readConfig(this);
        if (config != null) {
            if (!(config.ServerIP != null && !config.ServerIP.isEmpty() && !config.ServerPort.isEmpty())) {
                showErrorMessage(new AppException(ErrorCode.ERROR_SERVER_CONFIG));
                return;
            }
            config.autoCloseAfterTransfer = false;
            config.versionName = BuildConfig.VERSION_NAME;
            config.appName = getResources().getString(R.string.app_name);
            CommunicationHandler.start(this, config, new ICommunicationListener() {
                @Override
                public void beforeCommunication() {
                    //export data
                }

                @Override
                public void afterCommunication() {
                    presenter.importDB();
                    presenter.cleanUpData();
                }
            });
        } else {
            showErrorMessage(new AppException(ErrorCode.ERROR_SERVER_CONFIG));
        }
    }

    @Override
    public void saveDataSuccess() {
        presenter.showMenuList(getIntent().getBooleanExtra(IS_MANGER, true));
    }

    private void doLogOut() {
        createAppDialog().showConfirmDialog(
                R.drawable.ic_logout_blue_circle_grey,
                R.string.logout_title, R.string.logout_message,
                R.string.cancel, R.string.logout, new DialogCreator.IYesNoListener() {
                    @Override
                    public void onYes() {
                        presenter.logOut();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

    public void showNeedHelp() {
        final Dialog dialog = new Dialog(this, R.style.Fullscreen_DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(View.inflate(this.getBaseContext().getApplicationContext(), R.layout.activity_need_help, null));
        Button btnBack = (Button) dialog.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog1, int keyCode, KeyEvent mEvent) {
                if (mEvent.getAction() == KeyEvent.ACTION_UP &&
                        (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_ESCAPE || keyCode == KeyEvent.KEYCODE_BACK)) {
                    dialog1.dismiss();
                }

                return false;
            }
        });
        try {
            dialog.getWindow().getAttributes().windowAnimations = R.style.Fullscreen_DialogTheme;
        } catch (Exception e) {
            //ignore
        }
        dialog.show();
    }

    public static Intent initActivity(Context context, boolean isManager) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(IS_MANGER, isManager);
        return intent;
    }


    private void showOptionDialog() {
        MenuOptionsDialog dialog = new MenuOptionsDialog();
        dialog.show(getSupportFragmentManager(), "ShowMenuDialog",
                new MenuOptionsDialog.Listener() {
                    @Override
                    public void setting() {
                        goToSettings();
                    }

                    @Override
                    public void syncServerApi() {
                        presenter.syncData();
                    }

                    @Override
                    public void syncServerCommunication() {
                        presenter.exportDB();
                    }

                    @Override
                    public void goToNeedHelp() {
                        showNeedHelp();
                    }

                    @Override
                    public void goToAboutDevice() {
                        Intent intent = new Intent(MainActivity.this.getBaseContext(), GeneralInfoActivity.class);
                        MainActivity.this.startActivity(intent);
                    }

                    @Override
                    public void goToLogout() {
                        doLogOut();
                    }

                    @Override
                    public void closed() {

                    }
                });
    }
}