package sam.bee.stock.event;

public abstract class HttpProxy {

	public void askForData(Message msg){
		throw new NotImplementedException();
	};
	
	public static HttpProxy getInstance(){
		return new HttpStockLoader();
	}
}
