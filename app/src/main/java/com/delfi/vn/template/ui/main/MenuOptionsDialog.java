package com.delfi.vn.template.ui.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.delfi.vn.template.R;

public class MenuOptionsDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    Button btnOk;

    public interface Listener {

        void setting();

        void syncServerApi();

        void syncServerCommunication();

        void goToNeedHelp();

        void goToAboutDevice();

        void goToLogout();

        void closed();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.dialog_option_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        View lnEditNoteBottom = view.findViewById(R.id.lnViewBottomDialog);
        view.findViewById(R.id.lnSyncDataApi).setOnClickListener(this);
        view.findViewById(R.id.lnSyncDataCommunication).setOnClickListener(this);
        view.findViewById(R.id.lnSetting).setOnClickListener(this);
        view.findViewById(R.id.lnNeedHelp).setOnClickListener(this);
        view.findViewById(R.id.lnAboutDevice).setOnClickListener(this);
        view.findViewById(R.id.imgClose).setOnClickListener(this);
        view.findViewById(R.id.lnLogOut).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnSetting:
                listener.setting();
                dismiss();
                break;
            case R.id.lnSyncDataApi:
                listener.syncServerApi();
                dismiss();
                break;
            case R.id.lnSyncDataCommunication:
                listener.syncServerCommunication();
                dismiss();
                break;
            case R.id.lnNeedHelp:
                listener.goToNeedHelp();
                dismiss();
                break;
            case R.id.lnAboutDevice:
                listener.goToAboutDevice();
                dismiss();
                break;
            case R.id.lnLogOut:
                listener.goToLogout();
                dismiss();
                break;
            case R.id.imgClose:
                dismiss();
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(listener -> {
            FrameLayout bottomSheet = dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
            @SuppressWarnings("rawtypes")
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        btnOk.performClick();
                        return true;
                    }
                }
                return false;
            }
        });
        return dialog;
    }

    @Override
    public void onPause() {
        super.onPause();
        listener.closed();
    }

    public void show(FragmentManager supportFragmentManager, String tag,
                     Listener listener) {
        super.show(supportFragmentManager, tag);
        this.listener = listener;
    }

    private Listener listener;
}
