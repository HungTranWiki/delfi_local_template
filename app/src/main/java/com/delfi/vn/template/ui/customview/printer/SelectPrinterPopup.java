package com.delfi.vn.template.ui.customview.printer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.enums.MenuCode;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.AppRepositoryImpl;
import com.delfi.vn.template.utils.Constants;

public class SelectPrinterPopup extends BottomSheetDialogFragment {
    private OnPrinterSelected listener;
    private AppRepository appRepository;

    public void setListener(OnPrinterSelected listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_select_printer, container, false);
        ViewGroup btnXT2 = view.findViewById(R.id.btnXTPrinter);
        ViewGroup btnXT5 = view.findViewById(R.id.btnXT5Printer);
        ViewGroup btnL310 = view.findViewById(R.id.btnBTPrinter);
        ViewGroup btnR310 = view.findViewById(R.id.btnR310Printer);
        ImageView btnClose = view.findViewById(R.id.btnClose);
        appRepository = new AppRepositoryImpl(getContext());
        int menu = getArguments().getInt("MENU");
        String defPrinter = appRepository.getDefaultPrinter(menu);

        if (menu == MenuCode.MENU_11.ordinal()
                || menu == MenuCode.MENU_11.ordinal()
                || menu == MenuCode.MENU_11.ordinal()
                || menu == MenuCode.MENU_11.ordinal()) {
            btnXT2.setVisibility(View.GONE);
            btnXT5.setVisibility(View.GONE);
            if (defPrinter.equalsIgnoreCase(Constants.BXL_SPP_L310))
                btnL310.setBackgroundResource(R.color.green);
            if (defPrinter.equalsIgnoreCase(Constants.BXL_SPP_R310))
                btnR310.setBackgroundResource(R.color.green);
        } else {
            btnR310.setVisibility(View.GONE);
            if (defPrinter.equalsIgnoreCase(Constants.BXL_XT2))
                btnXT2.setBackgroundResource(R.color.green);
            if (defPrinter.equalsIgnoreCase(Constants.BXL_XT5))
                btnXT5.setBackgroundResource(R.color.green);
            if (defPrinter.equalsIgnoreCase(Constants.BXL_SPP_L310))
                btnL310.setBackgroundResource(R.color.green);
        }
        btnClose.setOnClickListener(view13 -> dismiss());
        btnXT2.setOnClickListener(view12 -> onSelected(Constants.BXL_XT2));
        btnXT5.setOnClickListener(view12 -> onSelected(Constants.BXL_XT5));
        btnL310.setOnClickListener(view1 -> onSelected(Constants.BXL_SPP_L310));
        btnR310.setOnClickListener(view1 -> onSelected(Constants.BXL_SPP_R310));
        return view;
    }

    void onSelected(String name) {
        listener.onSelected(name);
        dismiss();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setOnShowListener(dialog -> {
            FrameLayout bottomSheet = d.findViewById(android.support.design.R.id.design_bottom_sheet);
            @SuppressWarnings("rawtypes")
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return d;
    }

    public interface OnPrinterSelected {
        void onSelected(String name);
    }
}
