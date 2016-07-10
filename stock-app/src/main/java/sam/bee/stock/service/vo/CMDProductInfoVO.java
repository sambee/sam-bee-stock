// NMI's Java Code Viewer 6.0a
// www.trinnion.com/javacodeviewer

// Registered to Evaluation Copy                                      
// Generated PGFZKD AyTB 14 2007 15:45:22 

//source File Name:   CMDProductInfoVO.java

package sam.bee.stock.service.vo;

import sam.bee.stock.gui.Application;
import sam.bee.stock.trade.Market;
import sam.bee.stock.vo.ProductInfoVO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static sam.bee.stock.Const.STOCK_CODE;
import static sam.bee.stock.Const.STOCK_NAME;

// Referenced classes of package gnnt.util.service.HQVO:
//            CMDVO, ProductInfoListVO

public class CMDProductInfoVO extends Req {


	
    public int date;
    public int time;

    public CMDProductInfoVO() {
        super.cmd = 1;
    }

//    /**
//     * @deprecated
//     * @param input
//     * @return
//     * @throws IOException
//     */
//    public static ProductInfoListVO getObj(DataInputStream input) throws IOException {
//        ProductInfoListVO productInfoList = new ProductInfoListVO();
//        productInfoList.date = input.readInt();
//        productInfoList.time = input.readInt();
//        int length = input.readInt();
//        ProductInfoVO productInfos[] = new ProductInfoVO[length];
//        for(int i = 0; i < productInfos.length; i++) {
//            productInfos[i] = new ProductInfoVO();
//            productInfos[i].code = input.readUTF();
//            productInfos[i].status = input.readInt();
//            productInfos[i].fUnit = input.readFloat();
//            productInfos[i].name = input.readUTF();
//            productInfos[i].pinyin = new String[input.readInt()];
//            for(int j = 0; j < productInfos[i].pinyin.length; j++)
//                productInfos[i].pinyin[j] = input.readUTF();
//
//            productInfos[i].tradeSecNo = new int[input.readInt()];
//            for(int j = 0; j < productInfos[i].tradeSecNo.length; j++)
//                productInfos[i].tradeSecNo[j] = input.readInt();
//
//            if(productInfos[i].fUnit <= 0.0F)
//                productInfos[i].fUnit = 1.0F;
//        }
//
//        productInfoList.productInfos = productInfos;
//        return productInfoList;
//    }

    public static ProductInfoListVO getObj() throws IOException {
       Market market =  Application.getInstance().market;
       List<Map<String, String>> getStockInfos = market.getStockInfos();
       ProductInfoVO productInfos[] = new ProductInfoVO[getStockInfos.size()];
       ProductInfoListVO productInfoList = new ProductInfoListVO();
        Map map;
        for(int i = 0; i < productInfos.length; i++) {
            map = getStockInfos.get(i);
            productInfos[i] = new ProductInfoVO();
            productInfos[i].code = (String) map.get(STOCK_CODE);
            productInfos[i].status = 1;
            productInfos[i].fUnit = 1;
            productInfos[i].name = (String) map.get(STOCK_NAME);
            productInfos[i].pinyin = new String[]{ (String) map.get(STOCK_CODE)};

            productInfos[i].tradeSecNo = new int[]{1};

            if(productInfos[i].fUnit <= 0.0F)
                productInfos[i].fUnit = 1.0F;
        }
       return productInfoList;
    }
}
