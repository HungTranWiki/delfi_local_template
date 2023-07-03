package com.delfi.vn.template.ui.customview.calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.delfi.vn.template.R;
import com.delfi.vn.template.utils.DateTime;

public class CalendarOrderPopup extends BottomSheetDialogFragment {
    private CalendarListener listener;
    private MyDatePicker myDatePicker;
    private String date;

    public interface CalendarListener {
        void onDateSelected(String date);
    }

    public void setListener(CalendarListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.dialog_calendar_popup, container, false);
        myDatePicker = view.findViewById(R.id.myDatePicker);
        Button buttonCancel = view.findViewById(R.id.btnCancel);
        buttonCancel.setOnClickListener(view1 -> {
            dismiss();
        });
        Button buttonOk = view.findViewById(R.id.btnOk);
        buttonOk.setOnClickListener(view1 -> {
            if (listener != null)
                listener.onDateSelected(myDatePicker.getStringDate());
            dismiss();
        });
        myDatePicker.setmOnDateChangedListener((view12, year, monthOfYear, dayOfMonth) -> {

        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        try {
            if (date == null || date.length() == 0)
                myDatePicker.setDate(DateTime.getCurrentDateLocal(), "dd/MM/yyyy");
            else
                myDatePicker.setDate(date, "dd/MM/yyyy");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void show(FragmentManager supportFragmentManager, String tag, String date) {
        super.show(supportFragmentManager, tag);
        this.date = date;
    }
}
