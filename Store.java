/*
* The Store class contains the store name, and also a reference to the Product object. At the moment the store
* can only have a singular product. It contains a method to add a product to the store (it just overwrites the 
* previous reference with a newly created product, but in the future it will be changed to add the product to a list).
* It also contains a method calculating the replacement strategy (based on a number of weeks), and will return 
* the data as a String.
* @author Lance Baker (c3128034).
*/
public class Store {
	private static final String STRATEGY_MESSAGE = "The replacement strategy of %s store for %s is";
	private static final String COLUMN_WEEK = "Week";
	private static final String COLUMN_QUANTITY_ORDERED = "Quantity Ordered";
	private static final String COLUMN_DEMAND = "Demand";
	private static final String COLUMN_INVENTORY = "Inventory";
	private static final String DISPLAY_TOTAL_COST = "The total cost is: $%,.2f";
	private static final String DISPLAY_TOTAL_PROFIT = "The total profit is: $%,.2f";
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String STRATEGY_ERROR = "Error: not possible to have a replacement strategy with the inputs given.";
	private static final String PRODUCT_ERROR = "Error: must add a product first.";
	private String name; // The store name attribute.
	private Product product; // The Product object reference.
	
	/*
	* The Store constructor accepts the store name.
	*/
	public Store(String name) {
		this.setName(name);
	}
	
	/*
	* The getter for the store name.
	*/
	public String getName() {
		return this.name;
	}
	
	/*
	* The setter for the store name.
	*/
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	* The addProduct method receives parameters which are used for creating a new Product. Since in the future,
	* the store will have multiple products, it was chosen to implement the name as addProduct; which will
	* then be able to easily be changed to just add the product into a List of Product. Until then however,
	* the store will only have a single product, so it will just be assigned to the product instance attribute.
	*/
	public void addProduct(String productName, int demandRate, double setupCost, 
										double unitCost, double inventoryCost, double sellingCost) {
		this.product = new Product(productName, demandRate, setupCost, unitCost, inventoryCost, sellingCost);	
	}
	
	/*
	* The calcRemainder method is used to calculate the remainding inventory level. It recieves 
	* the current inventory, the order quantity, the current week, and the weeks. It will iterate
	* through the remainding weeks, calculating and then returning then final inventory level.
	*/
	private int calcRemainder(int inventory, int orderQuantity, int week, int weeks) {
		// Iterates through the remainding weeks.
		for (int i = week; i <= weeks; i++) {
			// If the inventory is less than the demand rate.
			if (inventory < this.product.getDemandRate()) {
				// It will order more.
				inventory += orderQuantity;
			}
			// Deducts the demand rate from the inventory.
			inventory -= this.product.getDemandRate();
		}
		// Returns the final inventory value.
		return inventory;
	}
	
	/*
	* The calcLastOrder method is used to calculate which week the last order occurred which will then 
	* be used inorder to know when to deduct the remainding inventory value; allowing the inventory to be 
	* zero at the end of the strategey. It will perform the calculations as normal, but willl take note of
	* the last week of when a order occurred. It will then return the week.
	*/
	private int calcLastOrder(int orderQuantity, int weeks) {
		int inventory = 0, last = 0;
		// Iterates throughout the weeks.
		for (int week = 1; week <= weeks; week++) {
			// If the inventory is less than the demand rate.
			if (inventory < this.product.getDemandRate()) {
				// It will order more products.
				inventory += orderQuantity;
				// Takes note of the order week.
				last = week;
			}
			// Then it will deduct the demand rate from the inventory.
			inventory -= this.product.getDemandRate();
		}
		// Returns the week the last order took place.
		return last;
	}
	
	/*
	* The replacementStrategy method accepts a value indicating the weeks the strategy will
	* be projected for. The output will solely depend on the Product. It will construct a grid 
	* for the number of weeks, with each week consisting of the order quantity, demand rate, and the inventory level.
	* The total cost, and profit is also appended in the output; with the whole thing being returned as a String. 
	*/
	public String replacementStrategy(int weeks) {
		// The StringBuilder is used to build (concatenate) the output String.
		StringBuilder output = new StringBuilder();
		if (this.product != null) { // If the product was actually added.
			// Calculates the Economic Order Quantity.
			int orderQuantity = this.product.calcOrderQuantity();
			// If the calculated order quantity is actually greater than the demand rate.
			if (orderQuantity > this.product.getDemandRate()) {
				int inventory = 0; // Used to keep track of the inventory.
				int orderCount = 0; // Counts the number of orders.
				double totalOrdered = 0; // Sums the total products ordered.
				double inventoryCount = 0; // Sums the total inventory in stock.
				// Calculates when the last order will take place.
				int lastOrder = this.calcLastOrder(orderQuantity, weeks);
				// Appends the headings to the output.
				output.append(NEW_LINE + String.format(STRATEGY_MESSAGE, this.getName(), this.product.getProductName()) + NEW_LINE);
				output.append(NEW_LINE + String.format("%10s %20s %10s %10s", COLUMN_WEEK, COLUMN_QUANTITY_ORDERED, COLUMN_DEMAND, COLUMN_INVENTORY) + NEW_LINE);
				// Iterates through each week.
				for (int week = 1; week <= weeks; week++) {
					boolean order = false; // Resets the order boolean.
					// If the week is the last order.
					if (week == lastOrder) {
						// It will then deduct the remainding inventory from the order quantity.
						orderQuantity -= this.calcRemainder(inventory, orderQuantity, week, weeks);
					}
					// If the inventory is less than the demand rate.
					if (inventory < this.product.getDemandRate()) {
						// It will then order more products.
						inventory += orderQuantity;
						// Keeps track of the total amount of products ordered.
						totalOrdered += orderQuantity; 
						order = true; // Flags for the order quantity to be shown.
						orderCount++; // Increments the order count.
					}
					// Deducts the demand rate from the inventory.
					inventory -= this.product.getDemandRate();
					inventoryCount += inventory; // Adds the inventory to the total inventory count.
					// Appends a row to the output. Containing the week, the quantity ordered, the demand rate, and the inventory level.
					output.append(String.format("%10d %20d %10d %10d", week, ((order) ? orderQuantity : 0), this.product.getDemandRate(), inventory) + NEW_LINE);
				}
				// Calculates the total cost. Being the ((setup cost * order count) + (total ordered * unit cost)) + inventory count * inventory cost.
				double totalCost = (((this.product.getSetupCost() * orderCount) + (totalOrdered * this.product.getUnitCost())) + (inventoryCount * this.product.getInventoryCost()));
				// Calculates the profit. Being the (demand rate * weeks * selling cost) - the total cost.
				double profit = (this.product.getDemandRate() * weeks * this.product.getSellingCost()) - totalCost;
				// Appends the total cost to the output.
				output.append(NEW_LINE + String.format(DISPLAY_TOTAL_COST, totalCost) + NEW_LINE);
				// Appends the profit to the output.
				output.append(String.format(DISPLAY_TOTAL_PROFIT, profit) + NEW_LINE);	
			} else {
				// The strategy cannot be calculated since the quantity order is less than the demand rate.
				output.append(STRATEGY_ERROR);
			}
		} else {
			// The product wasn't added, so it will display an error.
			output.append(PRODUCT_ERROR);
		}
		return output.toString(); // Returns the built output as a String.
	}
}