/*
* The Product class holds all the required instance data that is applicable for each product. It encapsulates
* the attributes by providing getters & setters. It also contains a method to calculate the Economic Order Quantity.
* @author Lance Baker (c3128034).
*/
public class Product {
	private String productName; // The product name.
	private int demandRate; // The product demand rate.
	private double setupCost; // The setup cost for each order.
	private double unitCost; // The individual cost of each unit.
	private double inventoryCost; // The cost to keep it in the inventory.
	private double sellingCost; // The selling cost.
	
	/*
	* The Product constructor accepts the product name, the demand rate, the setup cost, the unit cost,
	* the inventory cost, and the selling cost.
	*/
	public Product(String productName, int demandRate, double setupCost, 
										double unitCost, double inventoryCost, double sellingCost) {
		this.setProductName(productName);
		this.setDemandRate(demandRate);
		this.setSetupCost(setupCost);
		this.setUnitCost(unitCost);
		this.setInventoryCost(inventoryCost);
		this.setSellingCost(sellingCost);
	}
	
	/*
	* The getter for the product name.
	*/
	public String getProductName() {
		return this.productName;
	}
	
	/*
	* The setter for the product name.
	*/
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/*
	* The getter for the demand rate.
	*/
	public int getDemandRate() {
		return this.demandRate;
	}

	/*
	* The setter for the demand rate.
	*/
	public void setDemandRate(int demandRate) {
		this.demandRate = demandRate;
	}
	
	/*
	* The getter for the setup cost.
	*/
	public double getSetupCost() {
		return this.setupCost;
	}
	
	/*
	* The setter for the setup cost.
	*/
	public void setSetupCost(double setupCost) {
		this.setupCost = setupCost;
	}
	
	/*
	* The getter for the unit cost.
	*/
	public double getUnitCost() {
		return this.unitCost;
	}
	
	/*
	* The setter for the unit cost.
	*/
	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}
	
	/*
	* The getter for the inventory cost.
	*/
	public double getInventoryCost() {
		return this.inventoryCost;
	}
	
	/*
	* The setter for the inventory cost.
	*/
	public void setInventoryCost(double inventoryCost) {
		this.inventoryCost = inventoryCost;
	}
	
	/*
	* The getter for the selling cost.
	*/
	public double getSellingCost() {
		return this.sellingCost;
	}
	
	/*
	* The setter for the selling cost.
	*/
	public void setSellingCost(double sellingCost) {
		this.sellingCost = sellingCost;
	}
	
	/*
	* The calcOrderQuantity method is used to calculate the Economic Order Quantity (which is the optimal order quantity).
	* It will round the calculated double and return the value as an int.
	*/
	public int calcOrderQuantity() {
		return (int)Math.round(Math.sqrt((2 * (this.getSetupCost() * this.getDemandRate())) / this.getInventoryCost()));
	}
}