package com.delfi.vn.template.utils.printer.utils;

import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by DTO-BTAD on 8/30/2021.
 */
public final class ESCCommands {
    public static final byte[] MACRO_START_END = {0x1d, 0x3a};
    public static final byte[] PAGE_MODE = {0x1b, 0x4c};
    public static final byte[] PRINT_MODE = {0x1b, 0x21};
    public static final byte[] FORM_FEED = {0x0c};
    public static final byte[] HW_INIT = {0x1b, 0x40};          // Clear data in buffer and reset modes
    public static final byte[] BEEPER = {0x1b, 0x42, 0x05, 0x09}; // Beeps 5 times for 9*50ms each time
    public static final byte[] PRINT_LINE_FEED = {0x0a};          // Print and line feed
    // Text format
    public static final byte[] CHAR_CODE_VI = {0x1b, 0x74, 0x41};
    public static final byte[] TXT_NORMAL = {0x1b, 0x21, 0x00}; // Normal text
    public static final byte[] TXT_2HEIGHT = {0x1b, 0x21, 0x10}; // Double height text
    public static final byte[] TXT_2WIDTH = {0x1b, 0x21, 0x20}; // Double width text
    public static final byte[] TXT_4SQUARE = {0x1b, 0x21, 0x30}; // Quad area text
    public static final byte[] PAPER_FULL_CUT = {0x1d, 0x56, 0x65, 0x01}; // Full cut paper
    public static final byte[] PAPER_PART_CUT = {0x1d, 0x56, 0x01}; // Partial cut paper

    public static List<Byte> printText(String title, String value, boolean bold, byte[] align) {
        return printText(title, value, bold, align, true);
    }

    public static List<Byte> printText(String title, String value, boolean bold, byte[] align, boolean useLF) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(Bytes.asList(align));
        bytes.addAll(Bytes.asList(title.concat(": ").getBytes()));
        if (bold)
            bytes.addAll(Bytes.asList(FontStyle.BOLD));
        bytes.addAll(Bytes.asList((value == null ? "" : value).getBytes()));
        if (bold)
            bytes.addAll(Bytes.asList(FontStyle.NORMAL));
        if (useLF)
            bytes.addAll(Bytes.asList(PRINT_LINE_FEED));
        return bytes;
    }
    public static List<Byte> printText(String text) {
        return printText(text, FontFamily.FONT_A, TextAlignment.LEFT, true);
    }
    public static List<Byte> printText(String text, byte[] align) {
        return printText(text, FontFamily.FONT_A, align, true);
    }
    public static List<Byte> printText(String text,byte[] fontSize, byte[] align) {
        return printText(text,fontSize, align, true);
    }

    public static List<Byte> printText(String text, byte[] align, boolean useLF) {
        return printText(text, FontFamily.FONT_A, align, useLF);
    }
    public static List<Byte> printText(String text, byte[] fontSize, byte[] align, boolean useLF) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(Bytes.asList(fontSize));
        bytes.addAll(Bytes.asList(align));
        bytes.addAll(Bytes.asList(text.getBytes()));
        bytes.addAll(Bytes.asList(FontStyle.NORMAL));
        if (useLF)
            bytes.addAll(Bytes.asList(PRINT_LINE_FEED));
        return bytes;
    }

    public static List<Byte> printQRCode(String value, byte[] align) {
        return printQRCode(value, 5, align);
    }
    public static List<Byte> printQRCode(String value, int width, byte[] align) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(Bytes.asList(align));
        bytes.addAll(Bytes.asList(BarcodeGenerator.getQrCode(value.getBytes(), 50, width)));
        return bytes;
    }

    public static List<Byte> executeMacro(int loop) {
        return Bytes.asList(new byte[]{0x1d, 0x5e, ESCUtils.intToByte(loop), 0x05, 0x00});
    }

    public static class TextDecoration {
        public static final byte[] NONE = {0x1b, 0x2d, 0x00}; // Underline font OFF
        public static final byte[] UNDERLINE_1 = {0x1b, 0x2d, 0x01}; // Underline font 1-dot ON
        public static final byte[] UNDERLINE_2 = {0x1b, 0x2d, 0x02}; // Underline font 2-dot ON
    }

    public static class FontFamily {
        public static final byte[] FONT_A = {0x1b, 0x4d, 0x00}; // Font label_qty A (12x24)
        public static final byte[] FONT_B = {0x1b, 0x4d, 0x01}; // Font label_qty B (9x17)
    }

    public static class FontStyle {
        public static final byte[] NORMAL = {0x1b, 0x47, 0x00}; // Bold font OFF
        public static final byte[] BOLD = {0x1b, 0x47, 0x01}; // Bold font ON
    }

    public static class TextAlignment {
        public static final byte[] LEFT = {0x1b, 0x61, 0x00}; // Left justification
        public static final byte[] CENTER = {0x1b, 0x61, 0x01}; // Centering
        public static final byte[] RIGHT = {0x1b, 0x61, 0x02}; // Right justification
    }

    public static class Barcode {
        public static final byte[] TXT_OFF = {0x1d, 0x48, 0x00}; // HRI printBarcode chars OFF
        public static final byte[] TXT_ABV = {0x1d, 0x48, 0x01}; // HRI printBarcode chars above
        public static final byte[] TXT_BLW = {0x1d, 0x48, 0x02}; // HRI printBarcode chars below
        public static final byte[] TXT_BTH = {0x1d, 0x48, 0x03}; // HRI printBarcode chars both above and below
        public static final byte[] FONT_A = {0x1d, 0x66, 0x00}; // Font label_qty A for HRI printBarcode chars
        public static final byte[] FONT_B = {0x1d, 0x66, 0x01}; // Font label_qty B for HRI printBarcode chars

        public static final byte[] HEIGHT = {0x1d, 0x68, 0x50}; // Barcode Height [1-255]
        public static final byte[] WIDTH = {0x1d, 0x77, 0x03}; // Barcode Width  [2-6]

        public static final byte[] UPC_A = {0x1d, 0x6b, 0x00}; // Barcode label_qty UPC-A
        public static final byte[] UPC_E = {0x1d, 0x6b, 0x01}; // Barcode label_qty UPC-E
        public static final byte[] EAN13 = {0x1d, 0x6b, 0x02}; // Barcode label_qty EAN13
        public static final byte[] EAN8 = {0x1d, 0x6b, 0x03}; // Barcode label_qty EAN8
        public static final byte[] CODE39 = {0x1d, 0x6b, 0x04}; // Barcode label_qty CODE39
        public static final byte[] ITF = {0x1d, 0x6b, 0x05}; // Barcode label_qty ITF
        public static final byte[] NW7 = {0x1d, 0x6b, 0x06}; // Barcode label_qty NW7
    }
}
