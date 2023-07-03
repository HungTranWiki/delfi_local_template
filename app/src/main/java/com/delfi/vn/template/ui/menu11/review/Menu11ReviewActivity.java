package com.delfi.vn.template.ui.menu11.review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.delfi.core.controls.editableview.interfaces.IItem;
import com.delfi.core.controls.listswipeview.model.ActionView;
import com.delfi.core.controls.listswipeview.model.BottomView;
import com.delfi.core.controls.listswipeview.model.ConfigListView;
import com.delfi.core.controls.listswipeview.model.SurfaceView;
import com.delfi.core.controls.listswipeview.model.ViewSize;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;
import com.delfi.vn.template.ui.base.BaseRecordsActivity;

import java.util.ArrayList;
import java.util.List;

public class Menu11ReviewActivity extends BaseRecordsActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeaderTitle(getResources().getString(R.string.manager_menu11_title));
    }

    @Override
    protected IItem getConfigBuilder() {
        return new SavedMenu11();
    }

    @Override
    protected ConfigListView getConfigListView() {
        List<SurfaceView> surfaceView = new ArrayList<>();
        SurfaceView s1 = new SurfaceView(R.id.tvReceiptId, "soCT");
        surfaceView.add(s1);

        SurfaceView s2 = new SurfaceView(R.id.tvName, "maVT");
        surfaceView.add(s2);

        List<BottomView> bottomViews = new ArrayList<>();
        BottomView v1 = new BottomView(ActionView.DELETE, getResources().getColor(R.color.red), R.mipmap.ico_delete_w);
        v1.setSizeIcon(new ViewSize(R.dimen.icon_bottom_size_24, R.dimen.icon_bottom_size_24));
        bottomViews.add(v1);

        BottomView v2 = new BottomView(ActionView.EDIT, getResources().getColor(R.color.blue), R.drawable.ic_edit_white);
        v2.setSizeIcon(new ViewSize(R.dimen.icon_bottom_size_24, R.dimen.icon_bottom_size_24));
        bottomViews.add(v2);

        ConfigListView _config = new ConfigListView.Builder(R.layout.item_menu11_review, surfaceView, bottomViews)
                .setHeightItem(LinearLayout.LayoutParams.WRAP_CONTENT)
                .isShowIcon(true)
                .setDragEdges(ConfigListView.DragEdge.Left, ConfigListView.DragEdge.Right)
                .build();
        return _config;
    }

    @Override
    protected Class getEditRecordClass() {
        return null;
    }

    public static Intent initActivity(Activity activity) {
        Intent intent = new Intent(activity, Menu11ReviewActivity.class);
        return intent;
    }

}