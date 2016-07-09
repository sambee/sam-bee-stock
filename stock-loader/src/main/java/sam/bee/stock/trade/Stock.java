package sam.bee.stock.trade;

public class Stock {

	int unit;
	double price;
	String name;
	
	
	public Stock(String name, int unit, double price) {
		super();
		this.unit = unit;
		this.price = price;
		this.name = name;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
