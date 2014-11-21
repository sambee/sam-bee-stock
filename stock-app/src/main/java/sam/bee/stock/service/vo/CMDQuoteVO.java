// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:45:22 

//source File Name:   CMDQuoteVO.java

package sam.bee.stock.service.vo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import sam.bee.cache.H2DatabaseCache;
import sam.bee.stock.service.HQServiceUtil;
import sam.bee.stock.vo.ProductDataVO;

// Referenced classes of package gnnt.util.service.HQVO:
//            CMDVO

public class CMDQuoteVO extends Req {

    public String codeList[][];
    public byte isAll;

    public CMDQuoteVO() {
        codeList = new String[0][0];
        isAll = 0;
        super.cmd = 2;
    }

    public static ProductDataVO[] getObj(DataInputStream input) throws IOException {
        byte isAll = input.readByte();
        ProductDataVO values[] = new ProductDataVO[input.readInt()];
        for(int i = 0; i < values.length; i++) {
            values[i] = new ProductDataVO();
            values[i].code = input.readUTF();
            values[i].time = HQServiceUtil.getDate(input.readInt(), input.readInt());
            values[i].closePrice = input.readFloat();
            values[i].openPrice = input.readFloat();
            values[i].highPrice = input.readFloat();
            values[i].lowPrice = input.readFloat();
            values[i].curPrice = input.readFloat();
            values[i].totalAmount = input.readLong();
            values[i].totalMoney = input.readDouble();
            values[i].curAmount = input.readInt();
            values[i].amountRate = input.readFloat();
            values[i].balancePrice = input.readFloat();
            values[i].reserveCount = input.readInt();
            values[i].yesterBalancePrice = input.readFloat();
            values[i].reserveChange = input.readInt();
            if(isAll == 1) {
                values[i].inAmount = input.readInt();
                values[i].outAmount = input.readInt();
                values[i].buyAmount = new int[input.readInt()];
                for(int j = 0; j < values[i].buyAmount.length; j++)
                    values[i].buyAmount[j] = input.readInt();

                values[i].sellAmount = new int[input.readInt()];
                for(int j = 0; j < values[i].sellAmount.length; j++)
                    values[i].sellAmount[j] = input.readInt();

                values[i].buyPrice = new float[input.readInt()];
                for(int j = 0; j < values[i].buyPrice.length; j++)
                    values[i].buyPrice[j] = input.readFloat();

                values[i].sellPrice = new float[input.readInt()];
                for(int j = 0; j < values[i].sellPrice.length; j++)
                    values[i].sellPrice[j] = input.readFloat();

            }
        }

        return values;
    }
    
  
}
