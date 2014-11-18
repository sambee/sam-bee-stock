// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:45:22 

//source File Name:   CMDBillVO.java

package sam.bee.stock.service.vo;

import java.io.DataInputStream;
import java.io.IOException;

import sam.bee.stock.vo.BillDataVO;

// Referenced classes of package gnnt.util.service.HQVO:
//            CMDVO

public class CMDBillVO extends CMDVO {

    public String code;
    public byte type;
    public int time;

    public CMDBillVO() {
        super.cmd = 5;
    }

    public static BillDataVO[] getObj(DataInputStream input) throws IOException {
        int length = input.readInt();
        BillDataVO bills[] = new BillDataVO[length];
        for(int i = 0; i < bills.length; i++) {
            bills[i] = new BillDataVO();
            bills[i].time = input.readInt();
            bills[i].reserveCount = input.readInt();
            bills[i].buyPrice = input.readFloat();
            bills[i].curPrice = input.readFloat();
            bills[i].sellPrice = input.readFloat();
            bills[i].totalAmount = input.readLong();
            bills[i].totalMoney = input.readDouble();
            bills[i].openAmount = input.readInt();
            bills[i].closeAmount = input.readInt();
            bills[i].balancePrice = input.readFloat();
        }

        return bills;
    }
}
