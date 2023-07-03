package com.delfi.vn.template.utils.printer.utils;

import java.nio.ByteBuffer;

public class BarcodeGenerator {
    public static final byte[] QR_CODE_MODEL1 = new byte[]{29, 40, 107, 4, 0, 49, 65, 49, 0};
    public static final byte[] QR_CODE_MODEL2 = new byte[]{29, 40, 107, 4, 0, 49, 65, 50, 0};
    public static final byte[] QR_CODE_SIZE = new byte[]{29, 40, 107, 3, 0, 49, 67};
    public static final byte[] QR_CODE_ERROR_CORRECTION_LEVEL = new byte[]{29, 40, 107, 3, 0, 49, 69};
    public static final byte[] QR_CODE_STORE = new byte[]{29, 40, 107};
    public static final byte[] QR_CODE_STORE_PARAMETER = new byte[]{49, 80, 48};
    public static final byte[] QR_CODE_PRINT = new byte[]{29, 40, 107, 3, 0, 49, 81, 48};
    public static final byte[] QR_CODE_VERSION = new byte[]{31, 31, 98};

    private static final int[][][] VALIDATION_TABLE = new int[][][]{{{41, 25, 17}, {34, 20, 14}, {27, 16, 11}, {17, 10, 7}}, {{77, 47, 32}, {63, 38, 26}, {48, 29, 20}, {34, 20, 14}}, {{127, 77, 53}, {101, 61, 42}, {77, 47, 32}, {58, 35, 24}}, {{187, 114, 78}, {149, 90, 62}, {111, 67, 46}, {82, 50, 34}}, {{255, 154, 106}, {202, 122, 84}, {144, 87, 60}, {106, 64, 44}}, {{322, 195, 134}, {255, 154, 106}, {178, 108, 74}, {139, 84, 58}}, {{370, 224, 154}, {293, 178, 122}, {207, 125, 86}, {154, 93, 64}}, {{461, 279, 192}, {365, 221, 152}, {259, 157, 108}, {202, 122, 84}}, {{552, 335, 230}, {432, 262, 180}, {312, 189, 130}, {235, 143, 98}}, {{652, 395, 271}, {513, 311, 213}, {364, 221, 151}, {288, 174, 119}}, {{772, 468, 321}, {604, 366, 251}, {427, 259, 177}, {331, 200, 137}}, {{883, 535, 367}, {691, 419, 287}, {489, 296, 203}, {374, 227, 155}}, {{1022, 619, 425}, {796, 483, 331}, {580, 352, 241}, {427, 259, 177}}, {{1101, 667, 458}, {871, 528, 362}, {621, 376, 258}, {468, 283, 194}}, {{1250, 758, 520}, {991, 600, 412}, {703, 426, 292}, {530, 321, 220}}, {{1408, 854, 586}, {1082, 656, 450}, {775, 470, 322}, {602, 365, 250}}, {{1548, 938, 644}, {1212, 734, 504}, {876, 531, 364}, {674, 408, 280}}, {{1725, 1046, 718}, {1346, 816, 560}, {948, 574, 394}, {746, 452, 310}}, {{1903, 1153, 792}, {1500, 909, 624}, {1063, 644, 422}, {813, 493, 338}}, {{2061, 1249, 858}, {1600, 970, 666}, {1159, 702, 482}, {919, 557, 382}}, {{2232, 1352, 929}, {1708, 1035, 771}, {1224, 742, 509}, {969, 587, 403}}, {{2409, 1460, 1003}, {1872, 1134, 779}, {1358, 823, 565}, {1056, 640, 439}}, {{2620, 1588, 1091}, {2059, 1248, 857}, {1468, 890, 611}, {1108, 672, 461}}, {{2812, 1704, 1171}, {2188, 1326, 911}, {1588, 963, 661}, {1228, 744, 511}}, {{3057, 1853, 1273}, {2395, 1451, 997}, {1718, 1041, 715}, {1286, 779, 535}}, {{3283, 1990, 1367}, {2544, 1542, 1059}, {1804, 1094, 751}, {1425, 864, 593}}, {{3514, 2132, 1465}, {2701, 1637, 1125}, {1933, 1172, 805}, {1501, 910, 625}}, {{3669, 2223, 1528}, {2857, 1732, 1190}, {2085, 1263, 868}, {1581, 958, 658}}, {{3909, 2369, 1628}, {3035, 1839, 1264}, {2181, 1322, 908}, {1677, 1016, 698}}, {{4158, 2520, 1732}, {3289, 1994, 1370}, {2358, 1429, 982}, {1782, 1080, 742}}, {{4417, 2677, 1840}, {3486, 2113, 1452}, {2473, 1499, 1030}, {1897, 1150, 790}}, {{4686, 2840, 1952}, {3693, 2238, 1538}, {2670, 1618, 1112}, {2022, 1226, 842}}, {{4965, 3009, 2068}, {3909, 2369, 1628}, {2805, 1700, 1168}, {2157, 1307, 898}}, {{5253, 3183, 2188}, {4134, 2506, 1722}, {2949, 1787, 1228}, {2301, 1394, 958}}, {{5529, 3351, 2303}, {4343, 2632, 1809}, {3081, 1867, 1283}, {2361, 1431, 983}}, {{5836, 3537, 2431}, {4588, 2780, 1911}, {3244, 1966, 1351}, {2524, 1530, 1051}}, {{6153, 3729, 2563}, {4775, 2894, 1989}, {3417, 2071, 1423}, {2625, 1591, 1093}}, {{6479, 3927, 2699}, {5039, 3054, 2099}, {3599, 2181, 1499}, {2735, 1658, 1139}}, {{6743, 4087, 2809}, {5313, 3220, 2213}, {3791, 2298, 1579}, {2927, 1774, 1219}}, {{7089, 4296, 2953}, {5596, 3391, 2331}, {3993, 2420, 1663}, {3057, 1852, 1273}}};


    public static byte[] getQrCode(byte[] data, int model, int size) {

        if (size <= 0 || size >= 9) {
            size = 3;
        }


        if (data != null && data.length != 0 && (model == 49 || model == 50)) {
            int capacity = QR_CODE_MODEL2.length + QR_CODE_SIZE.length + 1 + QR_CODE_ERROR_CORRECTION_LEVEL.length + 1 + QR_CODE_STORE.length + 2 + QR_CODE_STORE_PARAMETER.length + data.length + QR_CODE_PRINT.length;
            /*if (version > 0) {
                if (version < 1 || version > 40) {
                    return null;
                }

                int type = 0;

                int maxLength;
                for(maxLength = 0; maxLength < data.length; ++maxLength) {
                    int character = data[maxLength] & 255;
                    if ((character < 65 || character > 90) && (character < 97 || character > 122) && character != 32 && character != 36 && character != 37 && character != 42 && character != 43 && character != 45 && character != 46 && character != 47 && character != 58) {
                        if (character < 48 || character > 57) {
                            type = 2;
                            break;
                        }
                    } else {
                        type = 1;
                    }
                }



                maxLength = VALIDATION_TABLE[version - 1][0][type];
                boolean mIsOverFlow = data.length > maxLength;
                if (mIsOverFlow) {

                    ByteBuffer buffer = ByteBuffer.allocate(maxLength);
                    buffer.put(data, 0, maxLength);
                    data = buffer.array();
                    capacity = QR_CODE_VERSION.length + 1 + QR_CODE_MODEL2.length + QR_CODE_SIZE.length + 1 + QR_CODE_ERROR_CORRECTION_LEVEL.length + 1 + QR_CODE_STORE.length + 2 + QR_CODE_STORE_PARAMETER.length + data.length + QR_CODE_PRINT.length;
                } else {


                    capacity += QR_CODE_VERSION.length + 1;
                }
            }*/

            ByteBuffer buffer = ByteBuffer.allocate(capacity);
            /*if (version > 0) {
                if (version >= 20) {
                    version = 6;
                }

                buffer.put(QR_CODE_VERSION);
                buffer.put((byte)version);
            }*/

            if (model == 49) {
                buffer.put(QR_CODE_MODEL1);
            } else {
                buffer.put(QR_CODE_MODEL2);
            }

            buffer.put(QR_CODE_SIZE);
            buffer.put((byte)size);
            buffer.put(QR_CODE_ERROR_CORRECTION_LEVEL);
            buffer.put((byte)48);
            buffer.put(QR_CODE_STORE);
            buffer.put((byte)((data.length + 3) % 256));
            buffer.put((byte)((data.length + 3) / 256));
            buffer.put(QR_CODE_STORE_PARAMETER);
            buffer.put(data);
            buffer.put(QR_CODE_PRINT);
            return buffer.array();
        } else {
            return null;
        }
    }
}
