package cn.howso.deeplan.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static final String toMD5(String content) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");
            md.update(content.getBytes("utf-8"));
            byte[] buf = md.digest();
            return byte2hex(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String byte2hex(byte[] b){
        StringBuilder hashString = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hashString.append("0");
            hashString.append(stmp);
        }
        return hashString.toString();
    }

    public static void main(String[] args) {
        String content = "123456a";
        System.out.println(toMD5(content));
    }
}
