package cn.howso.deeplan.util;

import java.util.Arrays;
import java.util.Objects;

public class ArrayUtils {
    public static void main(String[] args) {
        byte[] b1 = new byte[]{1,3};
        byte[] b2 = new byte[]{1,3};
        System.out.println(Objects.equals(b1, b2));
        String[] s1 = new String[]{"1"};
        String[] s2 = new String[]{"2"};
        byte[] res = concat(b1,b2);
        System.out.println(res);
        
        byte[] dest = new byte[res.length-b1.length];
        System.arraycopy(res, b1.length, dest, 0, dest.length);
        System.out.println(dest);
    }
    public static <T> T[] concat(T[] arr1,T[] arr2){
        T[] res = Arrays.copyOf(arr1, arr1.length+arr2.length);
        System.arraycopy(arr2, 0, res, arr1.length, arr2.length);
        return res;
    }
    public static byte[] concat(byte[] arr1,byte[] arr2){
        byte[] res = Arrays.copyOf(arr1, arr1.length+arr2.length);
        System.arraycopy(arr2, 0, res, arr1.length, arr2.length);
        return res;
    }
}
