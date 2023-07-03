package com.delfi.vn.template.models.servermodels;

import com.delfi.vn.template.utils.DateTime;

public class GetReceipt11DetailRequest {
    private String soCT;
    private String fromdate;
    private String todate;

    public GetReceipt11DetailRequest(String soCT, String fromDate) {
        this.soCT = soCT;
        this.fromdate = fromDate;
        this.todate = DateTime.getCurrentTimeServer();
    }
}
