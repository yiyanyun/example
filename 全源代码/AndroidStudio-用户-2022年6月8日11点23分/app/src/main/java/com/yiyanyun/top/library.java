package com.yiyanyun.top;

import android.os.Build;
import java.security.MessageDigest;
import java.util.UUID;

public class library {

    public static String mac() {
        String m_szDevIDShort = "35" + Build.BOARD.length() % 10
                + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
                + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
                + Build.HOST.length() % 10 + Build.ID.length() % 10
                + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
                + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
                + Build.TYPE.length() % 10 + Build.USER.length() % 10;
        String serial = "serial";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = android.os.Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static String get_mac(){
        return md5encryption(mac());
    }

    public static String get_text(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Throwable) {
            Throwable th = (Throwable) value;
            java.io.StringWriter writer = new java.io.StringWriter();
            java.io.PrintWriter printWriter = new java.io.PrintWriter(writer);
            th.printStackTrace(printWriter);
            return writer.toString();
        } else if (value.getClass().isArray()) {
            return java.util.Arrays.deepToString((Object[]) value);
        } else {
            return String.valueOf(value);
        }
    }

    public static String get_time = get_text(System.currentTimeMillis());

    public static String md5encryption(String value){
        char hexDigits[] = { '0', '1', '2', '3', '4','5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = value.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
