package cn.howso.deeplan.util;


public class Base64Data {
    private String type;
    private String data;
    
    /**
     * 将data:image/png;base64,iVBORw0KGgoAAAANSUhE...
     * 转换成
     * type:image/png
     * data:iVBORw0KGgoAAAANSUhE...
     * */
    public static Base64Data from(String data){
        int i = data.indexOf(',');
        Base64Data base64data = new Base64Data();
        String prev = data.substring(0, i);//data:image/png;base64
        base64data.setType(prev.split("[:;]")[1]);
        base64data.setData(data.substring(i+1));
        return base64data;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
