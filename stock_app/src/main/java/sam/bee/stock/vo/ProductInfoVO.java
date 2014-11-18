// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:40:59 

//source File Name:   ProductInfoVO.java

package sam.bee.stock.vo;

import java.util.Date;

public class ProductInfoVO {

    /** 股票代码 */public String code;
    /** 股票名称 */public String name;
    /** 类型 */public int type;
    /** 开盘时*/ public int openTime;
    /** 交易序号*/public int tradeSecNo[];
    /** 关闭时间 */public int closeTime;
    /** 最后更新时间 */public Date modifyTime;
    
    public static final int TYPE_INVALID = -1;
    public static final int TYPE_COMMON = 0;
    public static final int TYPE_CANCEL = 1;
    public static final int TYPE_INDEX = 2;
    public static final int TYPE_INDEX_MAIN = 3;
    public static final int TYPE_SERIES = 4;
    public static final int TYPE_PAUSE = 5;
    public static final int TYPE_FINISHIED = 6;
    
    public int status;
    
    /** 拼音名称 */ public String pinyin[];
    /** 单价 */ public float fUnit;

    public ProductInfoVO() {
        tradeSecNo = new int[1];
        pinyin = new String[0];
        fUnit = 1.0F;
    }

    public String toString() {
        String sep = "\n";
        StringBuffer sb = new StringBuffer();
        sb.append("**" + getClass().getName() + "**" + sep);
        sb.append("Code:" + code + sep);
        sb.append("Name:" + name + sep);
        sb.append("Type:" + type + sep);
        sb.append("CloseTime:" + closeTime + sep);
        sb.append("CreateTime:" + openTime + sep);
        sb.append("ModifyTime:" + modifyTime + sep);
        sb.append("Status:" + status + sep);
        sb.append("pyName.length:" + pinyin.length + sep);
        for(int i = 0; i < pinyin.length; i++)
            sb.append("pyName[" + i + "]:" + pinyin[i] + sep);

        sb.append(sep);
        return sb.toString() + super.toString();
    }
}
