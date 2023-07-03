package com.delfi.vn.template.utils.printer.constant;

/*
 * Created by DTO-BTAD on 6/10/2021.
 */
public enum  BxlPPP {
    DPI_203(8), DPI_300(12), DPI_600(23.5F);

    private float dotByMm;

    private BxlPPP(float dotByMm) {
        this.dotByMm = dotByMm;
    }

    /**
     * @return the dotByMm
     */
    public float getDotByMm() {
        return dotByMm;
    }
    public int getName() {
        if(dotByMm==8)
            return 203;
        else if(dotByMm==12)
            return 300;
        else
            return 600;
    }
}
