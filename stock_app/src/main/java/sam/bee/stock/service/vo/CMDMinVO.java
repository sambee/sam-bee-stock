// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:45:22 

//source File Name:   CMDMinVO.java

package sam.bee.stock.service.vo;

import java.io.DataInputStream;
import java.io.IOException;

import sam.bee.stock.vo.MinDataVO;

// Referenced classes of package gnnt.util.service.HQVO:
//            CMDVO

public class CMDMinVO extends CMDVO {

    public String code;
    public byte type;
    public int time;

    public CMDMinVO() {
        super.cmd = 4;
    }

    public static MinDataVO[] getObj(DataInputStream input) throws IOException {
        int length = input.readInt();
        MinDataVO mins[] = new MinDataVO[length];
        for(int i = 0; i < mins.length; i++) {
            mins[i] = new MinDataVO();
            mins[i].time = input.readInt();
            mins[i].curPrice = input.readFloat();
            mins[i].totalAmount = input.readLong();
            mins[i].averPrice = input.readFloat();
            mins[i].reserveCount = input.readInt();
        }

        return mins;
    }
}
