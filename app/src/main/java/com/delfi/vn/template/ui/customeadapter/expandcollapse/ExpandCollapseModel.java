package com.delfi.vn.template.ui.customeadapter.expandcollapse;

public class ExpandCollapseModel<T> {
    public boolean isExpanding;
    public T data;

    ExpandCollapseModel(T data) {
        this.isExpanding = false;
        this.data = data;
    }
}
