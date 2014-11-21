package sam.bee.stock.gui;

/**
 * 
 * @author Administrator
 *
 */
public class Packet_MultiQuote {

	/**
	 * 股标类型
	 */
    public byte currentStockType;
    
    /**
     * 得多少列进行排序
     */
    public byte sortBy;
    
    /**
     * 升序/排序
     */
    public byte isDescend;
    
    /**
     * 开始
     */
    public int iStart;
    
    /**
     * 结束
     */
    public int iEnd;
    
    /**
     * 股票总数
     */
    public int iCount;

}
