package com.delfi.vn.template.utils;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class InputScanQREvent {
    private static InputScanQREvent instance;
    private EditText edtQty;
    private InputScanQREventListener listener;

    public interface InputScanQREventListener{
        void onRequireScanner();
    }

    private InputScanQREvent() {

    }

    public static InputScanQREvent getInstance() {
        if (instance == null)
            instance = new InputScanQREvent();
        return instance;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void attach(final EditText edtQty, InputScanQREventListener listener ) {
        this.listener = listener;
        this.edtQty = edtQty;
        this.edtQty.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //record the start time
                    if (edtQty.getWidth() - event.getX() <= (edtQty.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()+10)) {
                        listener.onRequireScanner();
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
