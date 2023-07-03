package com.delfi.vn.template.ui.developing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.vn.template.R;

public class NextTimeActivity extends AppCompatActivity {
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_time);
        ImageView icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(view -> finish());
        TextView tvMainTitle = findViewById(R.id.tvMainTitle);
        tvMainTitle.setText(R.string.coming_soon);
    }

}