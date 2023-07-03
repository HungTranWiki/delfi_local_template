package com.delfi.vn.template.utils.printer.model;

import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.printer.constant.BxlPPP;

public class BxlPrintOptions {
    public String name;//printer name
    public int labelWidth;
    public int orientation;
    public int speed;
    public int darkness;
    public int gap;
    public int top;
    public int left;
    public BxlPPP density;
    public boolean isL310(){
        return name.equalsIgnoreCase(Constants.BXL_SPP_L310);
    }
}
