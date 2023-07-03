package com.delfi.vn.template.mappers;

import com.delfi.vn.template.models.dbmodels.SavedMenu11;
import com.delfi.vn.template.models.servermodels.PostMenu11Request;

public class PostMenu11Mapper extends Mapper<SavedMenu11, PostMenu11Request.CTRequest> {
    @Override
    public PostMenu11Request.CTRequest map(SavedMenu11 element) {
        if (element == null)
            return null;
        PostMenu11Request.CTRequest data = new PostMenu11Request.CTRequest();
        data.barcode = element.barcode;
        data.sku_label = element.productId;
        data.item_id = element.maVT;
        data.config_id = element.configId;
        data.color_id = element.colorId;
        data.style_id = element.styleId;
        data.unit_id = element.unit;
        data.order_qty = element.soLuongYeuCau;
        data.actual_received_qty = element.soLuongDaQuet;
        data.remaining_qty = element.missingQty;
        return data;
    }

    @Override
    public SavedMenu11 reverseMap(PostMenu11Request.CTRequest element) {
        return null;
    }
}