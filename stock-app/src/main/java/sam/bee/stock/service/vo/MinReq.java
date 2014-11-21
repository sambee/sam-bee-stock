package sam.bee.stock.service.vo;

/**
 * 分时数据请求
 * @author Administrator
 *
 */
public class MinReq extends Req {

    public String code;
    public byte type;
    public int time;

    public MinReq() {
        super.cmd = 4;
    }

//    public static MinDataVO[] getObj(DataInputStream input) throws IOException {
//        int length = input.readInt();
//        MinDataVO mins[] = new MinDataVO[length];
//        for(int i = 0; i < mins.length; i++) {
//            mins[i] = new MinDataVO();
//            mins[i].time = input.readInt();
//            mins[i].curPrice = input.readFloat();
//            mins[i].totalAmount = input.readLong();
//            mins[i].averPrice = input.readFloat();
//            mins[i].reserveCount = input.readInt();
//        }
//
//        return mins;
//    }
}
