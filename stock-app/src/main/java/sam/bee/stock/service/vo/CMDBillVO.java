package sam.bee.stock.service.vo;

import java.io.DataInputStream;
import java.io.IOException;

import sam.bee.stock.vo.BillDataVO;

public class CMDBillVO extends Req {

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
