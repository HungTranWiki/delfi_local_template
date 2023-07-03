package com.delfi.vn.template.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.delfi.vn.template.R;
import com.delfi.vn.template.ui.settings.appid.AppIDSettingActivity;
import com.delfi.vn.template.ui.settings.printip.PrinterIPSettingActivity;
import com.delfi.vn.template.models.enums.ActivityRequestCode;
import com.delfi.vn.template.ui.settings.serverip.ServerIPSettingActivity;


public class ExMainSettingActivity extends com.delfi.core.settings.activities.MainSettingActivity {

    private boolean isChangedURLConfig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout llSettingDetail = findViewById(R.id.llSettingDetail);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.item_setting, null, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent main = new Intent(ExMainSettingActivity.this.getBaseContext(), AppIDSettingActivity.class);
                ExMainSettingActivity.this.startActivity(main);
            }
        });

        llSettingDetail.addView(v);

        View v2 = inflater.inflate(R.layout.item_serverip_setting, null, false);

        v2.setOnClickListener(v1 -> {
            Intent main = new Intent(getBaseContext(), ServerIPSettingActivity.class);
            startActivityForResult(main, ActivityRequestCode.SERVER_IP_SETTING.ordinal());
        });

        llSettingDetail.addView(v2);

        View v3 = inflater.inflate(R.layout.item_printip_setting, null, false);

        v3.setOnClickListener(v1 -> {
            Intent main = new Intent(getBaseContext(), PrinterIPSettingActivity.class);
            startActivity(main);
        });

        llSettingDetail.addView(v3);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode.SERVER_IP_SETTING.ordinal() && resultCode == RESULT_OK) {
            isChangedURLConfig = true;
            setResult(RESULT_OK);
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
            System.exit(0);
        }
    }
}
