package com.delfi.vn.template.ui.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.delfi.core.controls.styledinput.SimpleStyledWrapper;
import com.delfi.core.log.LogEventArgs;
import com.delfi.core.log.LogLevel;
import com.delfi.core.log.Logger;
import com.delfi.vn.template.R;

import java.lang.reflect.Field;

public class MySimpleStyleWrapper extends SimpleStyledWrapper {
    public MySimpleStyleWrapper(Context context) {
        super(context);
    }

    public MySimpleStyleWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySimpleStyleWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Typeface setFontTypeface() {
        try {
            return ResourcesCompat.getFont(getContext(), R.font.montserrat_regular);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return super.setFontTypeface();
    }

    @Override
    protected boolean setChangeTextColorOnError() {
        return super.setChangeTextColorOnError();
    }

    @Override
    protected boolean setChangeUnderlineColorOnError() {
        return true;
    }

    public void setLabel(String label) {
        try {
            Field labelField = SimpleStyledWrapper.class.getDeclaredField("tvLabel");
            labelField.setAccessible(true);
            TextView tv = (TextView) labelField.get(this);
            if (tv != null) {
                tv.setText(label);
            }
        } catch (Exception var5) {
            Logger.getInstance().logMessage(new LogEventArgs(LogLevel.VERBOSE, "BaseStyledWrapper: " + var5.getMessage(), (Throwable) null));
            var5.printStackTrace();
        }
    }
}
