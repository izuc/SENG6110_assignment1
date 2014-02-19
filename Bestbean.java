import java.util.Scanner;
/*
* The Bestbean class is the main class of the application. It will prompt for input 
* and display the appropriate output to the user. It also only has knowledge of the Store class.
* @author Lance Baker.
*/
public class Bestbean {
	private static final String WELCOME_MESSAGE = "Welcome to Bestbean coffee replenishment system";
	private static final String INPUT_STORE_NAME = "Please, input the name of the store:";
	private static final String INPUT_PRODUCT_NAME = "Please, input the name of the product:";
	private static final String INPUT_DEMAND_RATE = "Please, input the demand rate of %s:";
	private static final String INPUT_SETUP_COST = "Please, input the setup cost of %s:";
	private static final String INPUT_UNIT_COST = "Please, input the unit cost of %s:";
	private static final String INPUT_INVENTORY_COST = "Please, input the inventory cost of %s:";
	private static final String INPUT_SELLING_COST = "Please, input the selling price of %s:";
	private static final String INPUT_WEEKS = "Please, input the number of weeks:";
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String ERROR_INVALID_INPUT = "Sorry, you have entered invalid input.";
	private static final String ERROR_STORE = "Sorry, %s is not included in our list of stores.\nPlease start the system again.";
	private static final String ERROR_PRODUCT = "Sorry, %s is not included in our list of products.\nPlease start the system again.";
	private static final String ERROR_LESS_THAN_OR_ZERO = "Sorry, the value entered must be greater than zero."; 
	private static final String STORE_LAMBTON = "Lambton";
	private static final String STORE_CALLAGHAN = "Callaghan";
	private static final String PRODUCT_COFFEE = "Coffee";
	private static final String PRODUCT_CHOCOLATE = "Chocolate";
	private static final String PRODUCT_TEA = "Tea";
	private static final String PRODUCT_CAKE = "Cake";
	private static final String PRODUCT_PIE = "Pie";
	
	// The Scanner object instance, stored as a static reference.
	private static Scanner console = new Scanner(System.in);
	
	/*
	* The static validStore method accepts a String, and it will
	* compare it against the valid stores (ignoring case). If it is
	* a match, it will return boolean true, otherwise false.
	*/
	private static boolean validStore(String storeName) {
		return (storeName.equalsIgnoreCase(STORE_LAMBTON) 
				|| storeName.equalsIgnoreCase(STORE_CALLAGHAN));
	}
	
	/*
	* The static validProduct method accepts a String, and it will
	* compare it against the valid products (ignoring case). If it is
	* a match, it will return boolean true, otherwise false.
	*/
	private static boolean validProduct(String productName) {
		return (productName.equalsIgnoreCase(PRODUCT_COFFEE)
				|| productName.equalsIgnoreCase(PRODUCT_CHOCOLATE)
				|| productName.equalsIgnoreCase(PRODUCT_TEA)
				|| productName.equalsIgnoreCase(PRODUCT_CAKE)
				|| productName.equalsIgnoreCase(PRODUCT_PIE));
	}
	
	/*
	* The static inputString method accepts a String message. It will display
	* the message to the user and then prompt for input. It will return the 
	* input as a String.
	*/
	private static String inputString(String message) {
		System.out.println(message); // Displays messahe to user.
		return console.nextLine(); // Returns the inputted line as a String.
	}
	
	/*
	* The static inputInteger method accepts a String message. It will display
	* the message to the user and then prompt for an Integer. If the user enters
	* a value less than or equal to zero it will throw an IllegalArgumentException 
	* containing an error message.
	*/
	private static int inputInteger(String message) {
		// Displays message to user.
		System.out.println(message);
		// Gets the inputted value.
		int value = console.nextInt();
		if (value <= 0) { // If the value is less than or equal to zero it will throw an exception.
			throw new IllegalArgumentException(ERROR_LESS_THAN_OR_ZERO);
		}
		// Returns the value if everything is okay.
		return value;
	}
	
	/*
	* The static inputDouble method accepts a String messsage. It will display
	* the message to the user and then prompt for a Double. If the user enters
	* a value less than or equal to zero it will throw an IllegalArgumentException 
	* containing an error message.
	*/
	private static double inputDouble(String message) {
		// Displays message to user.
		System.out.println(message);
		// Gets the inputted value.
		double value = console.nextDouble();
		if (value <= 0) { // If the value is less than or equal to zero it will throw an exception.
			throw new IllegalArgumentException(ERROR_LESS_THAN_OR_ZERO);
		}
		// Returns the value if everything is okay.
		return value;
	}
	
	/*
	* The main method.
	*/
	public static void main(String[] args) {
		try {
			// Displays a welcome message.
			System.out.println(WELCOME_MESSAGE + NEW_LINE);
			// Gets the store name by prompting for input. It will convert it to lowercase.
			String storeName = inputString(INPUT_STORE_NAME).toLowerCase();
			// Changes the first letter of the store name to upper case.
			storeName = storeName.substring(0,1).toUpperCase() + storeName.substring(1);
			// If the store name is valid.
			if (validStore(storeName)) {
				// It will then instantiate a new Store, passing in the store's name to the constructor.
				Store store = new Store(storeName);
				// Gets the product name by prompting for input. It will convert it to lowercase.
				String productName = inputString(INPUT_PRODUCT_NAME).toLowerCase();
				// Changes the first letter of the product name to upper case.
				productName = productName.substring(0,1).toUpperCase() + productName.substring(1);
				// If the product name is valid.
				if (validProduct(productName)) {
					try {
						// It will then add then process the following arguments. It will first input an
						// integer for the demand rate, then input a doubles for the - setup cost, unit cost,
						// inventory cost, and selling cost. If everything is okay, meaning it hasn't thrown an
						// exception, then it will pass all those values into the addProduct method of the store object.
						// This will then instantiate a new product object containing those values, which will then
						// be used to view the replacement strategy.
						store.addProduct(productName, 
									inputInteger(String.format(INPUT_DEMAND_RATE, productName)),
									inputDouble(String.format(INPUT_SETUP_COST, productName)),
									inputDouble(String.format(INPUT_UNIT_COST, productName)),
									inputDouble(String.format(INPUT_INVENTORY_COST, productName)),
									inputDouble(String.format(INPUT_SELLING_COST, productName)));
						// It first inputs an integer value for the number of weeks. It will then 
						// pass that value into the replacementStrategy method of the store object, which
						// will then return a String and outputted via the println.
						System.out.println(store.replacementStrategy(inputInteger(INPUT_WEEKS)));
					// It will catch a IllegalArgumentException.
					} catch (IllegalArgumentException ex) {
						// Then display the message to the user.
						System.out.println(ex.getMessage());
					}
				} else {
					// If the product is not valid, it will then display a error message.
					System.out.println(String.format(ERROR_PRODUCT, productName));
				}
			} else {
				// If the store is not valid, it will then display a error message.
				System.out.println(String.format(ERROR_STORE, storeName));
			}
		// Any exceptions are caught here. It will only be exceptions relating
		// to input (such as entering non-numeric values when it expects a integer or double).
		} catch (Exception ex) {
			// Show a message to the user indicating they've entered invalid input.
			System.out.println(ERROR_INVALID_INPUT);
		}
	}
}