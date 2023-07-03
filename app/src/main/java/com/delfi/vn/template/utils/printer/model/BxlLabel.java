package com.delfi.vn.template.utils.printer.model;

import com.bp.BP;
import com.delfi.vn.template.utils.printer.constant.BxlPPP;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by DTO-BTAD on 6/10/2021.
 */
public class BxlLabel {
    private int labelWidth;
    private int labelLength;
    private BxlPPP density;
    private List<BxlElement> bxlElements;

    public BxlLabel() {
        bxlElements = new ArrayList<>();
    }

    public BxlLabel(BxlPPP dpi, int labelWidth) {
        this();
        this.density = dpi;
        this.labelWidth = labelWidth;
    }

    public int getLabelWidth() {
        return labelWidth;
    }

    public BxlPPP getDensity() {
        return density;
    }

    public int getLabelLength() {
        return labelLength;
    }

    public void setLabelLength(int labelLength) {
        this.labelLength = labelLength;
    }

    /**
     * Function to add Element on etiquette.
     * <p>
     * Element is abstract, you should use one of child Element( BxlText, BxlQRBarcode, etc)
     *
     * @param bxlElement
     * @return
     */
    public BxlLabel addElement(BxlElement bxlElement) {
        bxlElements.add(bxlElement);
        return this;
    }
    /**
     * @return the bxlElements
     */
    public List<BxlElement> getBxlElements() {
        return bxlElements;
    }

    /**
     * @param elements the bxlElements to set
     */
    public void setBxlElements(List<BxlElement> elements) {
        this.bxlElements = bxlElements;
    }

    public void sendCommand() {
        BP.setup(String.valueOf(labelWidth), "8", "2", "0", "3", "0");
        BP.sendCommand("$L");
        for (BxlElement bxlElement : bxlElements) {
            bxlElement.getCommand();
        }
        BP.sendCommand("$E");
    }
}
