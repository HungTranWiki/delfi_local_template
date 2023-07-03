package com.delfi.vn.template.models.servermodels;

import com.delfi.vn.template.utils.DateTime;

public class GetReceipt11Request {
    private String fromdate;
    private String todate;

    public GetReceipt11Request(String fromDate) {
        this.fromdate = fromDate;
        this.todate = DateTime.getCurrentTimeServer();
    }
}
