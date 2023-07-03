package com.delfi.vn.template.models.servermodels.base;

public class CBaseAXResponse<T> {
    public T data;

    public CBaseAXResponse(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response {" +
                ", data=" + data +
                '}';
    }
}
