package cn.howso.deeplan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
    public static byte[] gzip(byte[] content) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream out = new GZIPOutputStream(/*new FileOutputStream("d:/test-gzip.txt2.gz")*/bos);
        out.write(content);
        out.close(); 
        return bos.toByteArray();
    }
}
