package com.delfi.vn.template.models.printmodes;

import com.delfi.vn.template.utils.printer.model.BxlLabel;
import com.delfi.vn.template.utils.printer.model.BxlPrintOptions;

public interface PrintItem {
    void generateLabel(BxlPrintOptions options);

    BxlLabel getLabel(BxlPrintOptions options);
}
