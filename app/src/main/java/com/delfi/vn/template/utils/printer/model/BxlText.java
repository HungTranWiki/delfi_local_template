package com.delfi.vn.template.utils.printer.model;

import android.graphics.Typeface;

import com.bp.BP;

/*
 * Created by DTO-BTAD on 6/10/2021.
 */
public class BxlText extends BxlElement {
    private String text;
    private int fontSize;
    private Typeface fontName;
    private int fontStyle;

    public BxlText(int x, int y, Typeface font, int fontStyle, int fontSize, String text) {
        super.setPositionX(x);
        super.setPositionY(y);
        this.fontName = font;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.text = text;
    }

    public BxlText(int x, int y, String text) {
        this(x, y, Typeface.SANS_SERIF, Typeface.NORMAL, 13, text);
    }

    public BxlText(int x, int y, int fontSize, String text) {
        this(x, y, Typeface.SANS_SERIF, Typeface.NORMAL, fontSize, text);
    }

    public BxlText(int x, int y, int fontStyle, int fontSize, String text) {
        this(x, y, Typeface.SANS_SERIF, fontStyle, fontSize, text);
    }

    @Override
    protected void getCommand() {
        BP.ecTextOut(getPositionX(), getPositionY(), Typeface.SANS_SERIF, fontStyle, fontSize, 0,
                false, false, false, text);
        //void AsiaFont_TextOut(BP.AsianFontID FontID, int PosX, int PoxY, int Mul_X, int Mul_Y, int spaceChar, String RotationStyle, String Data, BP.AsiaEncoding encoding)
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public boolean getBold() {
        return fontStyle == 1;
    }

    public String getText() {
        return text;
    }

    public Typeface getFontName() {
        return fontName;
    }
}
