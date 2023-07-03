package com.delfi.vn.template.ui.customview.bottompopup;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.vn.template.R;


public class InformationBottomDialog extends BottomSheetDialogFragment {

    Button btnOk;
    boolean isClickedOk = false;

    public interface Listener {
        void cancel();

        void ok();

        void closed();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.dialog_info_bottom, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        View lnBottom = view.findViewById(R.id.lnBottom);
        if (iconResource != null)
            ((ImageView) lnBottom.findViewById(R.id.imgTitle)).setImageResource(iconResource);
        if (title != null && !title.isEmpty())
            ((TextView) lnBottom.findViewById(R.id.tvTitle)).setText(title);
        if (message != null && !message.isEmpty())
            ((TextView) lnBottom.findViewById(R.id.tvMessage)).setText(message);

        btnOk = lnBottom.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> {
            if (!isClickedOk) {
                isClickedOk = true;
                if (listener != null)
                    listener.ok();
                dismiss();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnOk.performClick();
            }
        }, 3000);
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
        if (listener != null) {
            listener.closed();
        }
    }

    public void show(FragmentManager supportFragmentManager, String tag,
                     int iconResource, String title, String description,
                     Listener listener) {
        super.show(supportFragmentManager, tag);
        this.iconResource = iconResource;
        this.title = title;
        this.message = description;
        this.listener = listener;
    }

    public void show(FragmentManager supportFragmentManager, String tag,
                     Listener listener) {
        super.show(supportFragmentManager, tag);
        this.listener = listener;
    }

    private Integer iconResource = null;
    private String title = "";
    private String message = "";
    private Listener listener;
}
