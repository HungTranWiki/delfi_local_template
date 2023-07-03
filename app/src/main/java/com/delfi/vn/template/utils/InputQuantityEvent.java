package com.delfi.vn.template.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;

public class InputQuantityEvent {
    private static InputQuantityEvent instance;
    boolean isTouchAsScroll = false;
    private float mMaxValue;
    private float mMinValue;
    private EditText edtQty;
    private long startTime = 0;
    private long endTime = 0;
    private Handler handler = new Handler();
    private Runnable plusRunnable = new Runnable() {
        @Override
        public void run() {
            onQuantityTenthRoundUp();
            handler.postDelayed(this, 1000);
        }
    };
    private Runnable minusRunnable = new Runnable() {
        @Override
        public void run() {
            onQuantityTenthRoundDown();
            handler.postDelayed(this, 1000);
        }
    };

    private InputQuantityEvent() {

    }

    public static InputQuantityEvent getInstance() {
        if (instance == null)
            instance = new InputQuantityEvent();
        return instance;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void attach(final EditText edtQty) {
        this.attach(edtQty, Float.MAX_VALUE, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void attach(final EditText edtQty, float maxValue, float minValue) {
        this.edtQty = edtQty;
        this.mMaxValue = maxValue;
        this.mMinValue = minValue;
        this.edtQty.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.e("onTouch", event.toString());
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //record the start time
                    startTime = event.getEventTime();
                    if (edtQty.getWidth() - event.getX() <= edtQty.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()) {
                        handler.removeCallbacks(minusRunnable);
                        handler.postDelayed(plusRunnable, 1500);
                        return true;
                    } else if (event.getX() <= edtQty.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                        handler.removeCallbacks(plusRunnable);
                        handler.postDelayed(minusRunnable, 1500);
                        return true;
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
                    //record the end time
                    isTouchAsScroll = event.getActionMasked() == MotionEvent.ACTION_CANCEL;
                    endTime = event.getEventTime();
                }
                //verify
                if (endTime - startTime >= 1000) {
                    //we have a 1000ms duration touch
                    handler.removeCallbacks(plusRunnable);
                    handler.removeCallbacks(minusRunnable);
                    startTime = 0;
                    endTime = 0;
                    return true; //notify that you handled this event (do not propagate)
                }
                if (endTime - startTime > 0) {
                    if (isTouchAsScroll) {
                        handler.removeCallbacks(plusRunnable);
                        handler.removeCallbacks(minusRunnable);
                        isTouchAsScroll = false;
                        return true;
                    }
                    if (edtQty.getWidth() - event.getX() <= edtQty.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()) {
                        onQuantityPlus();
                        handler.removeCallbacks(plusRunnable);
                        return true;
                    } else if (event.getX() <= edtQty.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                        onQuantityMinus();
                        handler.removeCallbacks(minusRunnable);
                        return true;
                    } else if(edtQty.getWidth() - event.getX() > edtQty.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()
                            && event.getX() < (edtQty.getWidth() - edtQty.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        handler.removeCallbacks(plusRunnable);
                        handler.removeCallbacks(minusRunnable);
                        isTouchAsScroll = false;
                    }
                }
                return false;
            }
        });
    }

    public void onQuantityMinus() {
        try {
            if (!edtQty.isEnabled())
                return;
            if (edtQty.getText().length() == 0) {
                edtQty.setText("0");
                edtQty.selectAll();
                return;
            }
            BigDecimal value = new BigDecimal(edtQty.getText().toString());
            BigDecimal ndChar = value.subtract(new BigDecimal("1"));

            if (ndChar.floatValue() <= mMinValue) {
                edtQty.setText(formatFloatValue(mMinValue));
                edtQty.selectAll();
                return;
            }

            edtQty.setText(ndChar.toString());
            edtQty.selectAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onQuantityPlus() {
        try {
            if(edtQty.getError()!= null && !edtQty.getError().toString().isEmpty()){
                edtQty.setError(null);
                return;
            }
            if (!edtQty.isEnabled())
                return;
            if (edtQty.getText().length() == 0) {
                edtQty.setText("1");
                edtQty.selectAll();
                return;
            }
            float value = Float.parseFloat(edtQty.getText().toString());
            if (value >= mMaxValue) {
                edtQty.setText(formatFloatValue(mMaxValue));
                edtQty.selectAll();
                return;
            }
            edtQty.setText(formatFloatValue(value + 1));
            edtQty.selectAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onQuantityTenthRoundUp() {
        try {
            if (!edtQty.isEnabled())
                return;
            if (edtQty.getText().length() == 0) {
                if (mMaxValue < 10)
                    edtQty.setText(formatFloatValue(mMaxValue));
                else
                    edtQty.setText("10");
                edtQty.selectAll();
                return;
            }
            BigDecimal value = new BigDecimal(edtQty.getText().toString());
            BigDecimal ndChar = value.add(new BigDecimal("10"));

            if (ndChar.floatValue() >= mMaxValue) {
                edtQty.setText(formatFloatValue(mMaxValue));
                edtQty.selectAll();
                return;
            }
            edtQty.setText(ndChar.toString());
            edtQty.selectAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onQuantityTenthRoundDown() {
        try {
            if (!edtQty.isEnabled())
                return;
            if (edtQty.getText().length() == 0) {
                if (mMinValue > -10)
                    edtQty.setText(formatFloatValue(mMinValue));
                else
                    edtQty.setText("-10");
                edtQty.selectAll();
                return;
            }
            BigDecimal value = new BigDecimal(edtQty.getText().toString());
            BigDecimal ndChar = value.subtract(new BigDecimal("10"));

            if (ndChar.floatValue() <= mMinValue) {
                edtQty.setText(formatFloatValue(mMinValue));
                edtQty.selectAll();
                return;
            }
            edtQty.setText(ndChar.toString());
            edtQty.selectAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String formatFloatValue(float v) {
        if (v % 1.0 != 0)
            return String.format("%s", v);
        else
            return String.format("%.0f", v);
    }
}
