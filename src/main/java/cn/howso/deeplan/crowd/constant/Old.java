package cn.howso.deeplan.crowd.constant;

/**
 * 年龄
 * */
public enum Old {
    LT20("lt20"),
    FROM20TO30("20-30"),
    FROM30TO40("30-40"),
    FROM40TO50("40-50"),
    FROM50TO60("50-60"),
    GT60("gt60"),
    UNKOWN("unkown");
    private String strvalue;

    private Old(String value) {
        this.strvalue = value;
    }

    public String toStrvalue() {
        return strvalue;
    }

    public static Old fromStrvalue(String strvalue) {
        Old[] values = Old.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].toStrvalue().equals(strvalue)){
                return values[i];
            }
        }
        return null;
    }
}
