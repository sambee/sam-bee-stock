// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:45:22 

//source File Name:   CMDSortVO.java

package sam.bee.stock.service.vo;

import java.io.DataInputStream;
import java.io.IOException;

import sam.bee.stock.vo.ProductDataVO;

// Referenced classes of package gnnt.util.service.HQVO:
//            CMDVO

public class CMDSortVO extends CMDVO {

	
    public byte sortBy;
    public int start;
    public int end;
    public byte isDescend;

    public CMDSortVO() {
        super.cmd = 3;
    }

    public static ProductDataVO[] getObj(DataInputStream input) throws IOException {
        ProductDataVO values[] = new ProductDataVO[input.readInt()];
        for(int i = 0; i < values.length; i++) {
            values[i] = new ProductDataVO();
            values[i].code = input.readUTF();
            values[i].yesterBalancePrice = input.readFloat();
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
            values[i].buyAmount = new int[1];
            values[i].buyAmount[0] = input.readInt();
            values[i].sellAmount = new int[1];
            values[i].sellAmount[0] = input.readInt();
            values[i].buyPrice = new float[1];
            values[i].buyPrice[0] = input.readFloat();
            values[i].sellPrice = new float[1];
            values[i].sellPrice[0] = input.readFloat();
        }

        return values;
    }
}
