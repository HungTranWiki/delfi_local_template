package com.delfi.vn.template.services.api;

import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Product;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;
import com.google.gson.Gson;

import java.util.List;

public class DummyData {

    public static List<Product> dummyProductList() {
        DummyProductList list = (new Gson()).fromJson(strProducts, DummyProductList.class);
        return list.data;
    }

    public static List<Warehouse> dummyWarehouseList() {
        DummyData.WarehouseList list = (new Gson()).fromJson(strWarehouses, DummyData.WarehouseList.class);
        return list.data;
    }

    public static List<Receipt11> dummyReceipt11() {
        DummyData.DummyReceipt11 list = (new Gson()).fromJson(strReceipt11, DummyData.DummyReceipt11.class);
        return list.data;
    }

    public static List<Receipt11Detail> dummyReceipt11Detail() {
        DummyData.DummyReceipt11Detail list = (new Gson()).fromJson(strReceiptDetail, DummyData.DummyReceipt11Detail.class);
        return list.data;
    }

    private class DummyProductList {
        List<Product> data;
    }

    private class WarehouseList {
        List<Warehouse> data;
    }

    private class DummyReceipt11 {
        List<Receipt11> data;
    }

    private class DummyReceipt11Detail {
        List<Receipt11Detail> data;
    }

    private static String strReceiptDetail = "{\"data\":[{\"OrderId\":\"SB.C221208.CK.001\",\"ProductId\":\"31112\",\"DisplayProductId\":\"31112\",\"ItemId\":\"31112\",\"ConfigId\":\"\",\"SizeId\":\"\",\"ColorId\":\"\",\"StyleId\":\"\",\"Unit\":\"PC\",\"Barcode\":\"5702016888348\",\"Quantity\":40.0000000000000000,\"MissingQty\":0.0000000000000000,\"Note\":\"\",\"Status\":0},{\"OrderId\":\"SB.C221208.CK.001\",\"ProductId\":\"42117\",\"DisplayProductId\":\"42117\",\"ItemId\":\"42117\",\"ConfigId\":\"\",\"SizeId\":\"\",\"ColorId\":\"\",\"StyleId\":\"\",\"Unit\":\"PC\",\"Barcode\":\"5702016890914\",\"Quantity\":30.0000000000000000,\"MissingQty\":0.0000000000000000,\"Note\":\"\",\"Status\":0},{\"OrderId\":\"SB.C221208.CK.001\",\"ProductId\":\"43189\",\"DisplayProductId\":\"43189\",\"ItemId\":\"43189\",\"ConfigId\":\"\",\"SizeId\":\"\",\"ColorId\":\"\",\"StyleId\":\"\",\"Unit\":\"PC\",\"Barcode\":\"5702016909159\",\"Quantity\":10.0000000000000000,\"MissingQty\":0.0000000000000000,\"Note\":\"\",\"Status\":0},{\"OrderId\":\"SB.C221208.CK.001\",\"ProductId\":\"60319\",\"DisplayProductId\":\"60319\",\"ItemId\":\"60319\",\"ConfigId\":\"\",\"SizeId\":\"\",\"ColorId\":\"\",\"StyleId\":\"\",\"Unit\":\"PC\",\"Barcode\":\"5702017161037\",\"Quantity\":210.0000000000000000,\"MissingQty\":0.0000000000000000,\"Note\":\"\",\"Status\":0},{\"OrderId\":\"SB.C221208.CK.001\",\"ProductId\":\"71761\",\"DisplayProductId\":\"71761\",\"ItemId\":\"71761\",\"ConfigId\":\"\",\"SizeId\":\"\",\"ColorId\":\"\",\"StyleId\":\"\",\"Unit\":\"PC\",\"Barcode\":\"5702017117263\",\"Quantity\":1.0000000000000000,\"MissingQty\":0.0000000000000000,\"Note\":\"\",\"Status\":0},{\"OrderId\":\"SB.C221208.CK.001\",\"ProductId\":\"76203\",\"DisplayProductId\":\"76203\",\"ItemId\":\"76203\",\"ConfigId\":\"\",\"SizeId\":\"\",\"ColorId\":\"\",\"StyleId\":\"\",\"Unit\":\"PC\",\"Barcode\":\"5702017154190\",\"Quantity\":300.0000000000000000,\"MissingQty\":0.0000000000000000,\"Note\":\"\",\"Status\":0}],\"HasError\":false,\"ErrorMessages\":[]}";

    private static String strReceipt11 = "{\"data\":[" +
            "{\"OrderId\":\"SB.C221208.CK.000\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"YSL_HCM\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.001\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"AD-LTDUYEN\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.002\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"YSL_HCM\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.003\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"YSL_HCM\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.004\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"AD-LTDUYEN\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.005\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"YSL_HCM\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.006\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"AD-LTDUYEN\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.007\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"YSL_HCM\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.008\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"YSL_HCM\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}," +
            "{\"OrderId\":\"SB.C221208.CK.009\",\"FromWarehouse\":\"DBS_BN\",\"ToWarehouse\":\"YSL_HCM\",\"StatusId\":0,\"CreatedDateTime\":\"2022-12-08T08:27:46\",\"ShipDate\":\"2022-12-08T00:00:00\",\"ReceiveDate\":\"2022-12-08T00:00:00\",\"Lines\":null,\"Note\":\"\"}" +
            "],\"HasError\":false,\"ErrorMessages\":[]}";

    private static String strWarehouses = "{\"data\":[{\"WarehouseCode\":\"AD-LTDUYEN\",\"WarehouseName\":\"Kho vật tư Miền Bắc - Thanh Nga\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":\"Lô A85-A86/I, Đường số 5, Khu công nghiệp Vĩnh Lộc, Xã Bà Điểm, Huyện Hóc Môn, TP.HCM, Huyện Vĩnh Lộc, Tỉnh Thanh Hóa,%1\",\"AddressNo\":\"Lô A85-A86/I, Đường số 5, Khu công nghiệp Vĩnh Lộc, Xã Bà Điểm, Huyện Hóc Môn, TP.HCM\",\"Ward\":\"Lô A85-A86/I, Đường số 5, Khu công nghiệp Vĩnh Lộc, Xã Bà Điểm, Huyện Hóc Môn, TP.HCM\",\"District\":\"Huyện Vĩnh Lộc\",\"Province\":\"Thanh Hóa\"},{\"WarehouseCode\":\"ADMIN-MKD\",\"WarehouseName\":\"Kho NV Lý Mỹ Vân\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null},{\"WarehouseCode\":\"ANHTHU\",\"WarehouseName\":\"Kho NV Anh Thư_sales Co.op\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":\"without county, %1\",\"AddressNo\":\"without county\",\"Ward\":\"without county\",\"District\":\"\",\"Province\":\"\"},{\"WarehouseCode\":\"XUANHOA\",\"WarehouseName\":\"KHO SALE XUÂN HÒA QUẢN LÝ\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null},{\"WarehouseCode\":\"XULY-TO\",\"WarehouseName\":\"XULY-TO\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null},{\"WarehouseCode\":\"YENHDY\",\"WarehouseName\":\"Hoàng Dương Ý Yên – Team Education\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null},{\"WarehouseCode\":\"YSL_HCM\",\"WarehouseName\":\"Yusen Logistics Việt Nam\",\"ContactName\":\"Trần Quốc Việt\",\"ContactPhone\":\"0909250234\",\"Address\":\"KCN VSIP 1, số 6 đường Độc Lập, Thành phố Thuận An, Tỉnh Bình Dương,%1\",\"AddressNo\":\"KCN VSIP 1, số 6 đường Độc Lập\",\"Ward\":\"KCN VSIP 1, số 6 đường Độc Lập\",\"District\":\"Thành phố Thuận An\",\"Province\":\"Bình Dương\"},{\"WarehouseCode\":\"ZUZU_MB\",\"WarehouseName\":\"Kho ZUZU Miền Bắc\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null},{\"WarehouseCode\":\"ZUZU_MN\",\"WarehouseName\":\"ZUZU_Miền Nam\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":\"33-35 D4 KDC HIMLAM., Quận 7, TP.Hồ Chí Minh,%1\",\"AddressNo\":\"33-35 D4 KDC HIMLAM.\",\"Ward\":\"33-35 D4 KDC HIMLAM.\",\"District\":\"Quận 7\",\"Province\":\"TPHCM\"},{\"WarehouseCode\":\"ZZA001\",\"WarehouseName\":\"Cửa hàng ZUZU NGUYỄN TRÃI\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":\"771-773 NGUYỄN TRÃI, Phường 11, Quận 5, TP.Hồ Chí Minh,%1\",\"AddressNo\":\"771-773 NGUYỄN TRÃI, Phường 11\",\"Ward\":\"771-773 NGUYỄN TRÃI, Phường 11\",\"District\":\"Quận 5\",\"Province\":\"TPHCM\"},{\"WarehouseCode\":\"ZZA002\",\"WarehouseName\":\"MYKINGDOM SA ĐÉC\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null},{\"WarehouseCode\":\"ZZA003\",\"WarehouseName\":\"ZU Zu NGUYỄN ĐÌNH CHIỂU - BẾN TRE\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null},{\"WarehouseCode\":\"ZZB001\",\"WarehouseName\":\"CH ZUZU Doi Can\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":\"81 Doi Can, Quận Ba Đình, TP. Hà Nội,%1\",\"AddressNo\":\"81 Doi Can\",\"Ward\":\"81 Doi Can\",\"District\":\"Quận Ba Đình\",\"Province\":\"Hà Nội\"},{\"WarehouseCode\":\"ZZB003\",\"WarehouseName\":\"Cửa hàng ZUZU Miền Bắc 03\",\"ContactName\":null,\"ContactPhone\":null,\"Address\":null,\"AddressNo\":null,\"Ward\":null,\"District\":null,\"Province\":null}],\"HasError\":false,\"ErrorMessages\":[]}\n";

    private static String strProducts = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"ProductId\": \"311121\",\n" +
            "      \"DisplayProductId\": \"31112\",\n" +
            "      \"ItemId\": \"311122\",\n" +
            "      \"ConfigId\": \"con31112\",\n" +
            "      \"SizeId\": \"size31112\",\n" +
            "      \"ColorId\": \"\\bcol31112\",\n" +
            "      \"StyleId\": \"style31112\",\n" +
            "      \"Barcode\": \"3111231112\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": \"product 31112\",\n" +
            "      \"ProductNameVI\": \"product 31112\",\n" +
            "      \"ProductNameEN\": \"product 31112\",\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"421171\",\n" +
            "      \"DisplayProductId\": \"42117\",\n" +
            "      \"ItemId\": \"421172\",\n" +
            "      \"ConfigId\": \"con42117\",\n" +
            "      \"SizeId\": \"size42117\",\n" +
            "      \"ColorId\": \"color42117\",\n" +
            "      \"StyleId\": \"style42117\",\n" +
            "      \"Barcode\": \"4211742117\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": \"proudct 42117\",\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"431891\",\n" +
            "      \"DisplayProductId\": \"43189\",\n" +
            "      \"ItemId\": \"431892\",\n" +
            "      \"ConfigId\": \"con43189\",\n" +
            "      \"SizeId\": \"size43189\",\n" +
            "      \"ColorId\": \"col43189\",\n" +
            "      \"StyleId\": \"style43189\",\n" +
            "      \"Barcode\": \"4318943189\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": \"product 43189\",\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"603191\",\n" +
            "      \"DisplayProductId\": \"60319\",\n" +
            "      \"ItemId\": \"603192\",\n" +
            "      \"ConfigId\": \"con60319\",\n" +
            "      \"SizeId\": \"size60319\",\n" +
            "      \"ColorId\": \"col60319\",\n" +
            "      \"StyleId\": \"style60319\",\n" +
            "      \"Barcode\": \"6031960319\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": null,\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"717611\",\n" +
            "      \"DisplayProductId\": \"71761\",\n" +
            "      \"ItemId\": \"717612\",\n" +
            "      \"ConfigId\": \"conf71761\",\n" +
            "      \"SizeId\": \"size71761\",\n" +
            "      \"ColorId\": \"color71761\",\n" +
            "      \"StyleId\": \"style71761\",\n" +
            "      \"Barcode\": \"7176171761\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": \"product 71761\",\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"762031\",\n" +
            "      \"DisplayProductId\": \"76203\",\n" +
            "      \"ItemId\": \"762032\",\n" +
            "      \"ConfigId\": \"conf76203\",\n" +
            "      \"SizeId\": \"\",\n" +
            "      \"ColorId\": \"color76203\",\n" +
            "      \"StyleId\": \"\",\n" +
            "      \"Barcode\": \"7620376203\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": null,\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"5056768\",\n" +
            "      \"DisplayProductId\": \"5056768\",\n" +
            "      \"ItemId\": \"5056768\",\n" +
            "      \"ConfigId\": \"\",\n" +
            "      \"SizeId\": \"\",\n" +
            "      \"ColorId\": \"\",\n" +
            "      \"StyleId\": \"\",\n" +
            "      \"Barcode\": \"50567685056768\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": null,\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"5056769\",\n" +
            "      \"DisplayProductId\": \"5056769\",\n" +
            "      \"ItemId\": \"5056769\",\n" +
            "      \"ConfigId\": \"c5056769\",\n" +
            "      \"SizeId\": \"s5056769\",\n" +
            "      \"ColorId\": \"c5056769\",\n" +
            "      \"StyleId\": \"s5056769\",\n" +
            "      \"Barcode\": \"5056769 5056769\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": null,\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"5056770\",\n" +
            "      \"DisplayProductId\": \"5056770\",\n" +
            "      \"ItemId\": \"5056770\",\n" +
            "      \"ConfigId\": \"conf5056770\",\n" +
            "      \"SizeId\": \"size5056770\",\n" +
            "      \"ColorId\": \"color5056770\",\n" +
            "      \"StyleId\": \"style5056770\",\n" +
            "      \"Barcode\": \"50567705056770\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": null,\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"ProductId\": \"5056771\",\n" +
            "      \"DisplayProductId\": \"5056771\",\n" +
            "      \"ItemId\": \"5056771\",\n" +
            "      \"ConfigId\": \"conf5056771\",\n" +
            "      \"SizeId\": \"size5056771\",\n" +
            "      \"ColorId\": \"color5056771\",\n" +
            "      \"StyleId\": \"style5056771\",\n" +
            "      \"Barcode\": \"50567715056771\",\n" +
            "      \"UnitCode\": null,\n" +
            "      \"ProductName\": \"product 5056771\",\n" +
            "      \"ProductNameVI\": null,\n" +
            "      \"ProductNameEN\": null,\n" +
            "      \"Brand\": null,\n" +
            "      \"Theme\": null,\n" +
            "      \"RetailPrice\": 0.0,\n" +
            "      \"UnitPerCarton\": 0.0,\n" +
            "      \"UnitDepth\": 0.0,\n" +
            "      \"UnitHeight\": 0.0,\n" +
            "      \"UnitWeight\": 0.0,\n" +
            "      \"UnitWidth\": 0.0,\n" +
            "      \"UnitCBM\": 0.0,\n" +
            "      \"BoxDepth\": 0.0,\n" +
            "      \"BoxHeight\": 0.0,\n" +
            "      \"BoxWeight\": 0.0,\n" +
            "      \"BoxWidth\": 0.0,\n" +
            "      \"CartonDepth\": 0.0,\n" +
            "      \"CartonHeight\": 0.0,\n" +
            "      \"CartonWeight\": 0.0,\n" +
            "      \"CartonWidth\": 0.0\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
