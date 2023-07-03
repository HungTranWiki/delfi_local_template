package com.delfi.vn.template.ui.base.recordsandprinter;

import com.delfi.core.controls.editableview.interfaces.IItem;
import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.ui.base.BaseView;

public class BaseRecordsAndPrintContract {
    interface View extends BaseView {
            void updateAfterPrint(IItem item);

    }

    interface Presenter {
    }
}
