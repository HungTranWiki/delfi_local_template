package com.delfi.vn.template.models.printmodes;

import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.utils.printer.model.BxlLabel;
import com.delfi.vn.template.utils.printer.model.BxlPrintOptions;
import com.delfi.vn.template.utils.printer.model.BxlQRBarcode;
import com.delfi.vn.template.utils.printer.model.BxlText;
import com.delfi.vn.template.utils.printer.utils.VNCharacter;

public class Receipt11Label implements PrintItem {
    public String soCT;
    public String khoDen;
    public String khoDi;

    public Receipt11Label(Receipt11 ctItem) {
        this.soCT = ctItem.soCT;
        this.khoDen = VNCharacter.removeAccent(ctItem.toWH);
        this.khoDi = VNCharacter.removeAccent(ctItem.fromWH);
    }

    @Override
    public void generateLabel(BxlPrintOptions options) {
        BxlLabel label = new BxlLabel(options.density, options.labelWidth);
        label.addElement(new BxlText(155, 10, soCT));
        label.addElement(new BxlText(155, 30, khoDen));
        label.addElement(new BxlQRBarcode(150, 50, khoDi));
        label.sendCommand();
    }

    @Override
    public BxlLabel getLabel(BxlPrintOptions options) {
        //Dot = desity 203 8dot, 200 13det
        //W=75 => have 35 chars
        BxlLabel label = new BxlLabel(options.density, options.labelWidth);
        int fontSize = BixolonLabelPrinter.FONT_SIZE_12;
        if (options.isL310()) {
            int yFlag = 40;
            int startX = 20;

            int nextY = 20;
            label.addElement(new BxlQRBarcode(startX + 200, yFlag, soCT, 8));
            nextY = (nextY * 5) + (yFlag + +20);
            int nextX = startX;
            String content = soCT;
            if (soCT.length() < 35) nextX = (startX + ((35 - content.length()) / 2) * 14);
            label.addElement(new BxlText(nextX, nextY, fontSize, content));

            int xContent = 220;
            int xTitle = startX;

            nextY += (yFlag);
            label.addElement(new BxlText(xTitle, nextY, fontSize, "So CT:"));
            label.addElement(new BxlText(xContent, nextY, fontSize, soCT));

            nextY += yFlag;
            label.addElement(new BxlText(xTitle, nextY, fontSize, "Kho Di:"));
            label.addElement(new BxlText(xContent, nextY, fontSize, khoDi));

            nextY += yFlag;
            label.addElement(new BxlText(xTitle, nextY, fontSize, "Kho Den:"));
            label.addElement(new BxlText(xContent, nextY, fontSize, khoDen));

        } else {
            fontSize = BixolonLabelPrinter.FONT_SIZE_12;
//            label.addElement(new BxlText(40, 10, fontSize, ma_vt));
//            label.addElement(new BxlText(50, 45, fontSize, ma_vitri));
//            //8 dot*70 cm=  560 width
//             label.addElement(new BxlQRBarcode(0, 85, ma_vt, 7));
//            label.addElement(new BxlText( 90, 240, fontSize, ma_vt));
            //label.addElement(new BxlText(55, 240, fontSize, so_luong+""));

        }
        return label;
    }
}
