package com.delfi.vn.template.ui.customview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.delfi.core.controls.styledinput.SimpleStyledEditText;

public class SimpleEditedText extends SimpleStyledEditText {
    public SimpleEditedText(Context context) {
        super(context);
    }

    public SimpleEditedText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleEditedText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected boolean setLockOnInit() {
        return true;
    }

    @Override
    protected boolean setLockOnSuccess() {
        return true;
    }

    @Override
    public void focus() {
        new Handler().postDelayed(super::focus, 200);
    }
}
