// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:45:22 

//source File Name:   CMDMarketSortVO.java

package sam.bee.stock.service.vo;

import java.io.DataInputStream;
import java.io.IOException;

// Referenced classes of package gnnt.util.service.HQVO:
//            CMDVO, MarketStatusVO

public class CMDMarketSortVO extends Req {

    public int num;

    public CMDMarketSortVO() {
        super.cmd = 8;
    }

    public static MarketStatusVO[] getObj(DataInputStream input) throws IOException {
        MarketStatusVO values[] = new MarketStatusVO[input.readInt()];
        for(int i = 0; i < values.length; i++) {
            values[i] = new MarketStatusVO();
            values[i].code = input.readUTF();
            values[i].cur = input.readFloat();
            values[i].status = input.readByte();
            values[i].value = input.readFloat();
        }

        return values;
    }
}
