package com.delfi.vn.template.ui.main.submenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.MainMenu;
import com.delfi.vn.template.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubMainMenuActivity extends AppCompatActivity implements MenuAdapter.OnItemClickListener {

    private List<MainMenu> userMenu = new ArrayList<>();
    private MenuAdapter adapter;

    public static String SUB_MENU_NAME = "MenuName";
    public static String SUB_MENU_LIST = "SubMenus";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main_menu);
        ButterKnife.bind(this);

        initToolbar();

        userMenu = (List<MainMenu>) getIntent().getSerializableExtra(SUB_MENU_LIST);

        adapter = new MenuAdapter();
        adapter.setListener(this);
        adapter.setDataList(this.userMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMenuClick(MainMenu item) {
        if (item.getcClass() != null) {
            Intent intent = new Intent(this, item.getcClass());
            intent.putExtra(Constants.INTENT_SUB_MENU_TYPE, item.getMenuType());
            startActivity(intent);
        }
    }

    private void initToolbar() {
        TextView tvMainTitle = findViewById(R.id.tvMainTitle);
        int menuName = getIntent().getIntExtra(SUB_MENU_NAME, 0);
        if (menuName == 0) {
            String title = getIntent().getStringExtra(SUB_MENU_NAME);
            tvMainTitle.setText(title);
        } else {
            tvMainTitle.setText(menuName);
        }

        ImageView icBack = findViewById(R.id.icBack);
        icBack.setOnClickListener(view -> finish());
    }

}
