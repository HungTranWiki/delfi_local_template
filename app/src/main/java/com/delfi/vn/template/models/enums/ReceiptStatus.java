package com.delfi.vn.template.models.enums;

public enum ReceiptStatus {
    PROCESSING("processing"),
    WAIT_ACCEPT("wait_accept"),
    WAIT_DELIVERY("waiting_delivery"),
    DELIVERY("delivery"),
    DELIVERED("delivered"),
    DONE("done");

    private String status;

    ReceiptStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
