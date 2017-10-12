package cn.howso.deeplan.util.sheet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * csv文件解析器
 * 
 * @author zhoujiaping
 *
 */
public class CsvExtractor extends SheetExtractor{
    private InputStream in;
    public CsvExtractor(InputStream in,String suffix) {
        this.in = in;
    }
    @Override
    public List<List<String>> extract() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"gbk"));
        int len = -1;
        char[] buf = new char[1024];
        StringBuilder sb = new StringBuilder();
        while((len=reader.read(buf))>-1){
            sb.append(new String(buf,0,len));
        }
        String text = sb.toString();
        return this.parse(text);
    }
	private List<List<String>> parse(String text) {
		Assert.isTrue(StringUtils.isNotBlank(text));
		// 系统行分隔符分割一行
		String[] rows = text.trim().split("\\s*" + System.lineSeparator() + "\\s*");
		List<List<String>> table = new ArrayList<>();
		String[] cols = null;
		for (String row : rows) {
			// 逗号分隔列。逗号两边可以有空白符。因为有时候excel另存为csv会自动添加空格。
			cols = row.trim().split("\\s*,\\s*");
			table.add(Arrays.asList(cols));
		}
		return table;

	}
}
