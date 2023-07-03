package com.delfi.vn.template.models.servermodels;

import java.util.List;

public class PostMenu11Request {
    public PHRequest data;


    public PostMenu11Request() {
        this.data = new PHRequest();
    }

    public static class PHRequest{
        public String order_id;
        public String from_warehouse;
        public String to_warehouse;
        public String created_date;
        public String ship_date;
        public String received_date;
        public List<CTRequest> line_items;
    }

    public static class CTRequest{
        public String barcode;
        public String sku_label;
        public String item_id;
        public String config_id;
        public String color_id;
        public String style_id;
        public String size_id;
        public String unit_id;
        public float order_qty;
        public float actual_received_qty;
        public float remaining_qty;
    }
}