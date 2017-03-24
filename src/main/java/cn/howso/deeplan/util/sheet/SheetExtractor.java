package cn.howso.deeplan.util.sheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
/**
 * 
 * @ClassName SheetExtracter
 * @Description 电子表格文件内容抽取器
 * demo：SheetExtractor.of(in,".xlsx").extract();
 * @author zhoujiaping
 * @Date 2017年2月16日 上午10:34:12
 * @version 1.0.0
 */
public abstract class SheetExtractor{
    public static SheetExtractor of(InputStream in,String suffix){
        SheetExtractor extractor = null;
        if(Objects.equals(suffix, ".csv")){
            extractor = new CsvExtractor(in,suffix);
        }else if(Objects.equals(suffix, ".xlsx")){
            extractor = new ExcelExtractor(in,suffix);
        }else if(Objects.equals(suffix, ".xls")){
            extractor = new ExcelExtractor(in,suffix);
        }else{
            throw new RuntimeException("unsupport suffix "+suffix);
        }
        return extractor;
    }
    public abstract List<List<String>> extract() throws IOException;
}
