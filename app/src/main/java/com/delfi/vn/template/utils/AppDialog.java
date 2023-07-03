package com.delfi.vn.template.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.delfi.core.common.SoundManager;
import com.delfi.core.controls.DialogCreator;
import com.delfi.vn.template.R;
import com.delfi.vn.template.utils.dialog.OptionYesNoListener;

public class AppDialog extends DialogCreator {
    public Context context;

    public AppDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void showInfoDialog(int titleResourceId, int messageResourceId,
                               int titleOkButtonResourceId,
                               final DialogCreator.IOKListener listener) {
        String title = context.getString(titleResourceId);
        String message = context.getString(messageResourceId);
        String titleOkButton = context.getString(titleOkButtonResourceId);
        showInfoDialog(title, message, titleOkButton, listener);
    }

    public void showInfoDialog(String title, String message, String titleOkButton, final DialogCreator.IOKListener listener) {
        new DialogCreator(context).info(title, message, titleOkButton, listener);
    }

    public void showConfirmDialog(int titleIconResourceId, int titleResourceId, int messageResourceId,
                                  int titleLeftButtonResourceId, int titleRightButtonResourceId,
                                  final DialogCreator.IYesNoListener listener) {
        String title = context.getString(titleResourceId);
        String message = context.getString(messageResourceId);
        String titleLeftButton = context.getString(titleLeftButtonResourceId);
        String titleRightButton = context.getString(titleRightButtonResourceId);
        showConfirmDialog(titleIconResourceId, title, message, titleLeftButton, titleRightButton, listener);
    }

    public void showInfoDialog(int titleIconResourceId, int titleResourceId, int messageResourceId,
                               int titleButtonResourceId,
                               final DialogCreator.IYesNoListener listener) {
        String title = context.getString(titleResourceId);
        String message = context.getString(messageResourceId);
        String titleLeftButton = "";
        String titleRightButton = context.getString(titleButtonResourceId);
        showConfirmDialog(titleIconResourceId, title, message, titleLeftButton, titleRightButton, listener);
    }


    public void showConfirmDialog(int titleIconResourceId, String title, String message,
                                  String titleLeftButton, String titleRightButton,
                                  final DialogCreator.IYesNoListener listener) {
        super.requestWindowFeature(1);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View view = View.inflate(super.getContext(), R.layout.app_dialog_confirm, null);
        super.setContentView(view);
        ImageView imgHeader = view.findViewById(R.id.imageHeader);
        TextView editTitle = view.findViewById(R.id.header);
        TextView editText = view.findViewById(R.id.message);
        Button buttonNo = view.findViewById(R.id.buttonNo);
        Button buttonYes = view.findViewById(R.id.buttonYes);

        imgHeader.setImageResource(titleIconResourceId);
        if (title.length() > 0) {
            editTitle.setText(title);
        }
        editText.setText(message);
        if (titleLeftButton.isEmpty()) {
            buttonNo.setVisibility(View.GONE);
        } else {
            buttonNo.setVisibility(View.VISIBLE);
            buttonNo.setText(titleLeftButton);
        }

        if (titleRightButton.isEmpty()) {
            buttonYes.setVisibility(View.GONE);
        } else {
            buttonYes.setVisibility(View.VISIBLE);
            buttonYes.setText(titleRightButton);
        }

        buttonNo.setOnClickListener(view1 -> {
            if (listener != null) {
                listener.onCancel();
            }

            AppDialog.super.dismiss();
        });
        buttonYes.setOnClickListener(view12 -> {
            if (listener != null) {
                listener.onYes();
            }

            AppDialog.super.dismiss();
        });
        super.setCanceledOnTouchOutside(false);
        super.setCancelable(false);
        super.setOnKeyListener((dialog, keyCode, event) -> {
            if (event.getAction() == 1 && (keyCode == 4 || keyCode == 111)) {
                if (listener != null) {
                    listener.onCancel();
                }

                dialog.dismiss();
            }

            if (event.getAction() == 1 && keyCode == 66) {
                if (listener != null) {
                    listener.onYes();
                }

                dialog.dismiss();
            }

            return false;
        });
        super.show();
    }


    public void showErrorDialog(int titleIconResourceId, int titleResourceId, int messageResourceId, final DialogCreator.IOKListener listener) {
        String title = context.getString(titleResourceId);
        String message = context.getString(messageResourceId);
        showErrorDialog(titleIconResourceId, title, message, listener);
    }

    public void showErrorDialog(int titleIconResourceId, String title, String message, final DialogCreator.IOKListener listener) {
        super.requestWindowFeature(1);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View view = View.inflate(super.getContext(), R.layout.dialog_error, null);
        super.setContentView(view);

        ImageView imgHeader = view.findViewById(R.id.imageHeader);
        imgHeader.setImageResource(titleIconResourceId);

        TextView tvTitle = view.findViewById(R.id.header);
        TextView tvMessage = view.findViewById(R.id.message);
        tvTitle.setText(title);
        tvMessage.setText(message);

        Button btnClose = view.findViewById(R.id.buttonOk);
        btnClose.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOk();
            }
            AppDialog.super.dismiss();
        });
        super.setCanceledOnTouchOutside(false);
        super.setCancelable(false);
        super.setOnKeyListener((dialog, keyCode, event) -> {
            if (event.getAction() == 1 && (keyCode == 66 || keyCode == 111 || keyCode == 4)) {
                if (listener != null) {
                    listener.onOk();
                }

                dialog.dismiss();
            }

            return false;
        });
        super.show();
        SoundManager.getInstance().PlayError(super.getContext());
    }

    public void showErrorDialog(String message) {
        String title = context.getString(R.string.dialog_error_title);
        showErrorDialog(R.drawable.ic_warning_orange_circle_grey, title, message, null);
    }

    public void showErrorDialog(int messageResourceId) {
        String message = context.getString(messageResourceId);
        String title = context.getString(R.string.dialog_error_title);
        showErrorDialog(R.drawable.ic_warning_orange_circle_grey, title, message, null);
    }

    public void showDeleteOptionDialog(
            final OptionYesNoListener listener) {
        super.requestWindowFeature(1);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View view = View.inflate(super.getContext(), R.layout.menu11_delete_confirm_dialog, null);
        super.setContentView(view);
        ImageView imgHeader = view.findViewById(R.id.imageHeader);
        Button buttonNo = view.findViewById(R.id.buttonNo);
        Button buttonYes = view.findViewById(R.id.buttonYes);
        RadioButton rbnOption1 = view.findViewById(R.id.rbnOutOfStock);
        RadioButton rbnOption2 = view.findViewById(R.id.rbnPickLater);
        rbnOption2.setChecked(true);
        buttonNo.setOnClickListener(view1 -> {
            if (listener != null) {
                listener.onCancel();
            }

            AppDialog.super.dismiss();
        });
        buttonYes.setOnClickListener(view12 -> {
            if (listener != null) {
                listener.onYes(rbnOption1.isChecked(), rbnOption2.isChecked());
            }

            AppDialog.super.dismiss();
        });
        super.setCanceledOnTouchOutside(false);
        super.setCancelable(false);
        super.setOnKeyListener((dialog, keyCode, event) -> {
            if (event.getAction() == 1 && (keyCode == 4 || keyCode == 111)) {
                if (listener != null) {
                    listener.onCancel();
                }

                dialog.dismiss();
            }

            if (event.getAction() == 1 && keyCode == 66) {
                if (listener != null) {
                    listener.onYes(rbnOption1.isChecked(), rbnOption2.isChecked());
                }

                dialog.dismiss();
            }

            return false;
        });
        super.show();
    }
}
