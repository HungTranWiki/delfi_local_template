package com.delfi.vn.template.utils.printer;

import android.content.Context;
import android.util.Log;

import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.AppRepositoryImpl;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.printer.model.BxlLabel;

/*
 * Created by DTO-BTAD on 6/1/2021.
 */
public class BXLPrinter {
    private static BXLPrinter instance = new BXLPrinter();
    private BaseBXLPrinter printer;
    private AppRepository appRepository;
    private Context context;

    public static BXLPrinter getInstance() {
        return instance;
    }

    public void with(Context context) {
        Log.d("BXLPrinter", "Initial printer");
        this.context = context;
        appRepository = new AppRepositoryImpl(context);
        String defPrinter = appRepository.getWorkingPrinter();
        if (printer == null) {
            if (defPrinter.equalsIgnoreCase(Constants.BXL_XT2))
                printer = new BXLWifiXT2Printer(context);
            else if (defPrinter.equalsIgnoreCase(Constants.BXL_XT5))
                printer = new BXLWifiXT5Printer(context);
            else
                printer = new BXLMobileLabelPrinter(context);
        }
    }

    public void reCreate() {
        Log.d("BXLPrinter", "Recreate printer");
        close();
        String defPrinter = appRepository.getWorkingPrinter();
        if (defPrinter.equalsIgnoreCase(Constants.BXL_XT2))
            printer = new BXLWifiXT2Printer(context);
        else if (defPrinter.equalsIgnoreCase(Constants.BXL_XT5))
            printer = new BXLWifiXT5Printer(context);
        else
            printer = new BXLMobileLabelPrinter(context);
    }

    public boolean isConnected() {
        return printer.isConnected();
    }

    public boolean connect(String address) {
        return printer.connect(address);
    }

    public void print(BxlLabel label) {
        setup((int) label.getDensity().getDotByMm(),
                (int) label.getDensity().getDotByMm() * label.getLabelWidth(),
                     label.getLabelLength());
        printer.print(label);
    }

    /**
     * Init printer settings
     *
     * @param dpi       Print density
     * @param labelSize Label size in mm
     */
    public void setup(int dpi, int labelSize,int labelLength) {
        printer.setup(labelSize, 8, 2, 0, 3, 0);
        printer.setLength(labelLength, 0, BixolonLabelPrinter.MEDIA_TYPE_CONTINUOUS, 0);
        printer.setDensity(dpi);
    }

    public void setMargin(int left, int top) {
        printer.setMargin(left, top);
    }

    public void close() {
        if(printer != null) {
            printer.disconnect();
        }
    }

    public void setLength(int lenght, int gap, int media, int offset){

    }
}
