import java.util.*;
/**
 * This class represents a Vending Machine Factory.
 * 
 * @author Rowell Herrera and Daniella Ughoc S11A
 */
public class Factory {
    /**
     * This method displays the Welcome Menu. The user can create a vending machine,
     * test a vending machine, or exit the factory.
     */
    public void displayWelcome() {
        int choice = 1;

        do {
            System.out.println("Welcome to the Vending Machine Factory.");
            System.out.println("1 - Create a Vending Machine");
            System.out.println("2 - Test a Vending Machine");
            System.out.println("3 - Exit");
            System.out.print("Input: ");
            choice = SCANNER.nextInt();
            switch(choice) { //switch statement for user's choice
                case 1:
                    displayCreation();
                    break;
                case 2:
                    if (vm == null)
                        System.out.println("No existing vending machine.\n");
                    else
                        displayTesting();
                    break;
                case 3:
                    System.out.println("Thank you for choosing us.");
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        } while (choice != 3);
        SCANNER.close();
    }
    /**
     * This method displays the Create a Vending Machine Menu. The user can create a normal
     * vending machine or a special vending machine. The user can also exit back to the
     * Welcome Menu.
     */
    public void displayCreation() {
        int choice = 1;
        int numSlots=1;
         int numItems=1;

        System.out.println();

        do { //for looping the menu until a vending machine is created or the menu is exited
            System.out.println("Vending Machine Creation Menu:");
            System.out.println("1 - Normal Vending Machine");
            System.out.println("2 - Special Vending Machine");
            System.out.println("3 - Exit");
            System.out.print("Input: ");
            choice = SCANNER.nextInt();
            switch(choice) { //switch statement for user's choice
                case 1:
                    numSlots = getNumberOfSlotsFromUser();
                    numItems = getMinNumberOfItems();
                    vm = new VendingMachine(numSlots, numItems);
                    System.out.println("A new regular vending machine has been created.\n");
                    vm.setNumItems(numItems);
                    choice = 3;
                    break;
                case 2:
                    //System.out.println("Out of order.\n");
                    numSlots = getNumberOfSlotsFromUser();
                    numItems = getMinNumberOfItems();
                    vm=new SpecialVending(numSlots, numItems);
                    System.out.println("A new special vending machine has been created.\n");
                    break;
                case 3:
                    System.out.println("Going back to Welcome Menu...\n");
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        } while (choice != 3);
    }
    /**
     * This method displays the Test a Vending Machine Menu. The user can choose to test the
     * vending features, to test the maintenance features, or to exit back to the Welcome Menu.
     */
    public void displayTesting() {
        int choice = 1;

        System.out.println();

        do { //for looping until the user chooses to exit the Testing Menu
            System.out.println("Testing Menu:");
            System.out.println("1 - Vending Features");
            System.out.println("2 - Maintenance Features");
            System.out.println("3 - Exit");
            System.out.print("Input: ");
            choice = SCANNER.nextInt();
            switch(choice) { //switch statement for the user's choice
                case 1:
                    displayVendingFeatures();
                    break;
                case 2:
                    displayMaintenance();
                    break;
                case 3:
                    System.out.println("Going back to Welcome Menu...\n");
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        } while (choice != 3);
    }
    /**
     * This method displays the Vending Features of the created Vending Machine. The user can
     * choose to insert money, to choose an item, to produce change, or to go back to the 
     * Testing Menu. Displays important error messages such as insufficient payment or no
     * chosen item.
     */
   public void displayVendingFeatures(){
        boolean run = true;

        System.out.println();

        while(run == true){ //for looping until the user chooses to exit the Vending Features Menu
            
            int inp=1;
            System.out.println("Vending Features Menu:");
            System.out.println("1 - Insert Money");
            System.out.println("2 - Choose Item");
            System.out.println("3 - Produce Change");
            System.out.println("4 - Exit");
            System.out.print("Input: ");
            inp = SCANNER.nextInt();
            switch(inp) { //switch statement for user's choice
                case 1:
                    displayCashPayment();
                    break;
                case 2:
                    if (vm.getTotalPayment() != 0)
                        if (!vm.isEmpty())  
                            displayOrder();
                        else
                            System.out.println("Vending Machine is empty.\n");
                    else
                        System.out.println("No cash inserted yet.\n");
                    break;
                case 3:
                    if (vm.getChosen()!=null)
                        displayChange(vm.getTotalCost());
                    else {
                        if (vm.getTotalPayment()!=0)    
                            displayChange(0);
                        else
                            System.out.println("No cash inserted yet.\n");
                    }

                    vm.resetBuying();
                    break;
                case 4:
                    if (vm.getTotalPayment()!=0) {
                        System.out.println("No items purchased. Returning payment.\n");
                        vm.subtractPaymentAndBank();
                    }
                    System.out.println("Going back to Testing Menu...\n");
                    run = false;
                    vm.resetBuying();
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        }
    }
    /**
     * This method displays the Cash Payment User Interface. It will keep asking the user to input
     * a string of Denomination[space]Count until a pair or pairs of integers are input in. If valid,
     * the total payment and the total money in the vending machine is updated.
     */
    public void displayCashPayment() {
        int run = 1;
        String input;
        String[] inputArray;
        int inputSize;
        boolean success = false;

        do { //for looping until the user inputs a valid String of integer pairs
            System.out.println();
            System.out.println("Denominations: 1 5 10 20 50 100 500 1000");
            System.out.println("Format: Denomination count");
            System.out.println("Example: 1 50 5 50");
            System.out.print("Enter: ");
            SCANNER.nextLine();
            input = SCANNER.nextLine();
            inputArray = input.split(" ");
            inputSize = inputArray.length;

            if (inputSize % 2 == 0) { //if the input is valid, updates the total amt of money and the total payment
                for (int i = 0; i < inputSize; i += 2) {
                    success = vm.specReplenish(Integer.parseInt(inputArray[i]), Integer.parseInt(inputArray[i + 1]));
                    if (success)
                        vm.updatePayment(Integer.parseInt(inputArray[i]), Integer.parseInt(inputArray[i + 1]));
                }
            } 
            
            else {
                System.out.println("Invalid length of input.\n");
                run = 2;
                break;
            }

            System.out.println();
        } while (run == 2);
    }
    /**
     * This method displays the Choose an Item Menu. It allows the user to exit if they change their mind about
     * choosing an item. It would also display error messages pertaining to different errors such as not enough
     * change, not enough payment, not enough stock, and item not existing. It updates the chosen item if all
     * conditions are met.
     */
    public void displayOrder() {
        System.out.println();
        vm.displayItems();
        System.out.println("Type exit if you want to cancel.");
        System.out.print("Select the item you would like to purchase: ");
        SCANNER.nextLine();
        String name = SCANNER.nextLine();
        if (name.equalsIgnoreCase("exit")) //if exiting
            System.out.println("Going back to Vending Features Menu...\n");
        else {
            Item item = vm.findItem(name);

            if (item != null) { //if item is found
                if (item.getAvailability()) { //if item is available
                    int price = item.getPrice();
                    if (vm.getTotalPayment() >= price) { //if there's enough payment
                        if (vm.produceChange(price)) { //if there's enough change in the machine
                            if (vm.chooseItem(item)) { //if item is out of stock (fail-safe)
                                System.out.println("Chosen " + item.getName() + "!\n");
                                vm.updateChosen(item);
                            } 
                            
                            else 
                                System.out.println("Item is out of stock.\n");
                        }

                        else
                            System.out.println("Not enough change in the machine. Transaction canceled.\n");
                        
                    } 
                    
                    else 
                        System.out.println("Not enough cash inserted by user.\n");
                } 
                
                else 
                    System.out.println("Item is not available.\n");
                
            } 
            
            else 
                System.out.println("Item not found.\n");
        }
        
    }
    /**
     * This method displays the change of the user. If there is no item chosen, the payment is returned.
     * To get to this method, it's assumed that there is a payment already. It displays error messages
     * when there is no item chosen or there is not enough change in the machine.
     * @param totalPrice the price of the chosen item
     */
    public void displayChange(int totalPrice){
        if (!vm.hasChosen()) { //when there is no chosen item
            System.out.println("No items purchased. Returning payment.\n");
            vm.subtractPaymentAndBank();
        }

        else { //when there is a chosen item
            System.out.println("Payment: " + vm.getTotalPayment());
            System.out.println("Cost: "+totalPrice);
            System.out.println("Expected Change: "+(vm.getTotalPayment()-totalPrice)+ " Php");
            boolean success = vm.subtractPaymentAndBank(vm.getTotalPayment()-totalPrice);
            if (success) { //when there is enough change
                Item item = vm.getChosen();
                item.setQuantity(item.getQuantity() - 1);
                vm.updateTransactions(item);
                System.out.println("Dispensing " + item.getName() + "...\n");
            }
            else { //when there is not enough change
                System.out.println("Not enough small change in the machine. Payment is returned.\n");
                vm.subtractPaymentAndBank(vm.getTotalPayment());
            }
        }
    }
    /**
     * This method displays the Maintenance Features Menu. The user has the option to stock and restock,
     * to edit an item, to remove an item, to collect money, to replenish money, to print transactions,
     * and to exit Testing Menu.
     */
    public void displayMaintenance() {
        int choice = 1;

        System.out.println();

        do { //loop until valid input
            System.out.println("Maintenance Features Menu: ");
            System.out.println("1 - Stock and Restock Item");
            System.out.println("2 - Edit Item");
            System.out.println("3 - Remove Item");
            System.out.println("4 - Collect Money");
            System.out.println("5 - Replenish Money");
            System.out.println("6 - Print Transactions");
            System.out.println("7 - Exit");
            System.out.print("Input: ");
            choice = SCANNER.nextInt();
            switch(choice) { //switch statement for user's choice
                case 1:
                    displayRestock();
                    break;
                case 2:
                    displayEdit();   
                    break;
                case 3:
                    displayRemove(); 
                    break;
                case 4:
                    displayCollect();
                    break;
                case 5:
                    displayReplenish();    
                    break;
                case 6:
                    displayTransactions();
                    break;
                case 7:
                    System.out.println("Going back to Testing Menu...\n");
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        } while (choice != 7);
    }
    /**
     * This method displays the Restock Menu. The user has the option to stock a new item, to restock
     * an item, or to exit the menu. The total limit of slots are 10 slots only with each containing 20
     * items each. Going over these limits result to error messages. The user cannot add a new item 
     * with the same name as one already in the machine. This name also cannot be exit. The user cannot 
     * input a number that is not positive for the price and calories.
     */
    public void displayRestock() {
        int choice = 1;
        int qty = vm.getNumItems();
        String name;
        int price = 1;
        double calories = 1;
        boolean success = true;

        do { //loop until user exits the menu
            System.out.println();
            vm.displayItems();
            System.out.println();
            System.out.println("Restock Menu:");
            System.out.println("1 - Stock New Item");
            System.out.println("2 - Restock Item");
            System.out.println("3 - Exit");
            System.out.print("Input: ");
            choice = SCANNER.nextInt();
            switch(choice) { //switch statement for user's choice
                case 1:
                    do { //loop until valid input
                        if (qty < vm.getNumItems() )
                            System.out.println("Must be a greater than or equal to " + vm.getNumItems());
                        System.out.print("Quantity: ");
                        qty = SCANNER.nextInt();
                    } while(qty <= 0 || qty < vm.getNumItems());

                    SCANNER.nextLine();

                    success = true;
                    boolean isExit = false;

                    do { //loop until valid input
                        isExit = false;
                        if (!success)
                            System.out.println("Item already in the vending machine.");
                        System.out.print("Name: ");
                        name = SCANNER.nextLine();
                        if (name.equalsIgnoreCase("exit")) { //if name is exit
                            isExit = true;
                            System.out.println("Exit name not allowed.");
                        }
                        else {
                            Item i = vm.findItem(name);

                            if (i == null) //if name matches with nothing
                                success = true;
                            else
                                success = false;
                        }
                    } while (!success || isExit);

                    do { //loop until valid input
                        if (price <= 0)
                            System.out.println("Must be a positive number.");
                        System.out.print("Price: ");
                        price = SCANNER.nextInt();
                    } while (price <= 0);

                    do { //loop until valid input
                        if (calories <= 0)
                            System.out.println("Must be a positive number.");
                        System.out.print("Calories: ");
                        calories = SCANNER.nextDouble();
                    } while (calories <= 0);

                    if (vm.stockNew(qty, name, price, calories))
                        System.out.println("Added item.");
                    else
                        System.out.println("Limit reached.");
                    break;

                case 2:
                    System.out.print("Name of item: ");
                    SCANNER.nextLine();
                    name = SCANNER.nextLine();
                    Item i = vm.findItem(name);

                    if (i != null) { //if the item is in the vending machine
                        do { //loop until valid input
                            if (!success)
                                System.out.println("Only positive numbers. Limit of 20 items per slot");
                            System.out.println("Current quantity: " + i.getQuantity());
                            System.out.print("Add Quantity: ");
                            qty = SCANNER.nextInt();
                            success = vm.restockItem(name, qty);
                        } while(!success);
                    }

                    else
                        System.out.println("Restock failed. No such item.\n");

                    break;
                case 3:
                    System.out.println("Going back to Maintenance Features...\n");
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        } while (choice != 3);
    }
    /**
     * This method displays the Edit Item Interface. The quantity must be a positive number less
     * than 20. The name must be a new name not in the vending machine and must not be exit. A 
     * name matching the previous name will be accepted though. The price and calories must not 
     * be equal to 0. Violation of any of these results to an error. The edit fails when there 
     * is no such item matching the name input.
     */
    public void displayEdit() {
        int qty = 1;
        int price = 1;
        double calories = 1;
        String newName;
        boolean success = true;
        
        System.out.println();
        vm.displayItems();   
        System.out.println();
        System.out.print("Name of item: ");
        SCANNER.nextLine();
        String name = SCANNER.nextLine();
        Item i = vm.findItem(name);

        if (i != null) { //if the item is in the vending machine
            do { //loop until valid input
                if (qty <= 0 || qty > 20)
                    System.out.println("Must be a less than 20 positive number.");
                System.out.print("New Quantity: ");
                qty = SCANNER.nextInt();
            } while(qty <= 0 || qty > 20);

            SCANNER.nextLine();

            success = true;
            boolean isExit = false;

            do { //loop until valid input
                isExit = false;
                String origName = i.getName();
                if (!success)
                    System.out.println("Item already in the vending machine.");
                System.out.print("New Name: ");
                newName = SCANNER.nextLine();
                if (newName.equalsIgnoreCase("exit")) { //if name is exit
                    isExit = true;
                    System.out.println("Exit name not allowed.");
                }
                else { 
                    Item it = vm.findItem(newName);

                    if (it == null) //if name does not match any name
                        success = true;
                    else {
                        success = false;
                        if (it.getName().equals(origName)) //if name is the same
                            success = true;
                    }
                }
            } while (!success || isExit);

            do { //loop until valid input
                if (price <= 0)
                    System.out.println("Must be a positive number.");
                System.out.print("New Price: ");
                price = SCANNER.nextInt();
            } while (price <= 0);

            do { //loop until valid input
                if (calories <= 0)
                    System.out.println("Must be a positive number.");
                System.out.print("New Calories: ");
                calories = SCANNER.nextDouble();
            } while (calories <= 0);

            vm.editItem(i, qty, newName, price, calories);

            System.out.println("Edited item.\n");
            vm.displayItems();  
            System.out.println();
        }

        else
            System.out.println("Edit failed. No such item.\n");
    }
    /**
     * This method displays the Remove Interface. The user inputs a name and then the
     * method removes the item if the item exists.
     */
    public void displayRemove() {
        System.out.println();
        vm.displayItems();   
        System.out.println();
        System.out.print("Name of item: ");
        SCANNER.nextLine();
        String name = SCANNER.nextLine();
        Item i = vm.findItem(name);

        if (i!=null) { //if item is found
            vm.removeItem(i);
            System.out.println("Removed item.\n");
            vm.displayItems();  
            System.out.println();
        }

        else   
            System.out.println("No such item to remove.\n");
    }
    /**
     * This method displays the Collect Money Menu. The user has the option to collect
     * all the money in the vending machine, collect specific money denomination, or to
     * exit the program. It displays an error message when the input is not a String of paired
     * integers. It also displays one when there is no money to collect in the vending machine.
     */
    public void displayCollect() {
        int choice = 1;
        String input;
        String[] inputArray;
        int inputSize;
        int amt = 0;

        System.out.println();

        do { //loop until valid input
            System.out.println("Collect Menu:");
            System.out.println("1 - Collect All");
            System.out.println("2 - Collect Specific");
            System.out.println("3 - Exit");
            System.out.print("Input: ");
            choice = SCANNER.nextInt();
            switch(choice) { //switch statement for user's choice
                case 1:
                    vm.collectAll();
                    break;
                case 2:
                    if (vm.getTotalMh() == 0) //if there is no money in the vending machine
                        System.out.println("No money to collect.\n");
                    else {
                        System.out.println("Denominations: 1 5 10 20 50 100 500 1000");
                        System.out.println("Format: Denomination count");
                        System.out.println("Example: 1 50 5 50");
                        System.out.print("Enter: ");
                        SCANNER.nextLine();
                        input = SCANNER.nextLine();
                        inputArray = input.split(" ");
                        inputSize = inputArray.length;
                        if (inputSize%2 == 0) { //if input is valid
                            for(int i=0; i<inputSize; i+=2) {
                                amt += vm.collectSpec(Integer.parseInt(inputArray[i]), Integer.parseInt(inputArray[i+1]));
                            }
                            System.out.println("Collected Php" + amt);
                            vm.displayDenom();
                        }
                        else
                            System.out.println("Invalid length of input.\n");
                    }
                    break;
                case 3:
                    System.out.println("Going back to Maintenance Features Menu...\n");
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        } while(choice != 3);
    }
    /**
     * This method displays the Replenish Menu. The user has the option to do a general replenish, to do a
     * specific replenish, or to exit the menu. A general replenish adds 6000 pesos to the machine in 
     * different denominations while the specific replenish adds according to the input String of paired 
     * integers.
     */
    public void displayReplenish() {
        int choice = 1;
        String input;
        String[] inputArray;
        int inputSize;

        System.out.println();

        do { //loop until valid input
            System.out.println("Replenish Menu:");
            System.out.println("1 - General Replenish (6000)");
            System.out.println("2 - Specific Replenish");
            System.out.println("3 - Exit");
            System.out.print("Input: ");
            choice = SCANNER.nextInt();
            switch(choice) { //switch statement for user's choice
                case 1:
                    vm.genReplenish();
                    vm.displayDenom();
                    break;
                case 2:
                    System.out.println("Denominations: 1 5 10 20 50 100 500 1000");
                    System.out.println("Format: Denomination count");
                    System.out.println("Example: 1 50 5 50");
                    System.out.print("Enter: ");
                    SCANNER.nextLine();
                    input = SCANNER.nextLine();
                    inputArray = input.split(" ");
                    inputSize = inputArray.length;
                    if (inputSize%2 == 0) { //if input is valid
                        for(int i=0; i<inputSize; i+=2) {
                            vm.specReplenish(Integer.parseInt(inputArray[i]), Integer.parseInt(inputArray[i+1]));
                        }
                        vm.displayDenom();
                    }
                    else
                        System.out.println("Invalid length of input.\n");
                    break;
                case 3:
                    System.out.println("Going back to Maintenance Features...\n");
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        } while (choice != 3);
    }
    /**
     * This method displays the transactions of the vending machine. If there have been no
     * transactions made yet, the user is merely informed of that information. If there has
     * been one transaction, the list of transactions, starting inventory, and ending 
     * inventory are displayed.
     */
    public void displayTransactions() {
        if (vm.getTransactions().isEmpty()) //if there have been no transactions yet
            System.out.println("No transactions yet.\n");
        else
            vm.printTransactions();
    }

    public int getNumberOfSlotsFromUser() {
        int minSlots = 8;
        boolean isValidInput = false;
        int numSlots = 0;

        while (!isValidInput) {
            System.out.print("Enter the number of slots for the vending machine (minimum 8): ");
            numSlots = SCANNER.nextInt();
            if (numSlots >= minSlots) {
                isValidInput = true;
            } else {
                System.out.println("Please enter a number of slots that is 8 or higher.");
            }
        }

        return numSlots;
    }

    public int getMinNumberOfItems() { 
        int numItems;
        do {
            System.out.print("Enter the minumum number of items per slot (at least 10): ");
            numItems = SCANNER.nextInt();
            if (numItems < 10) {
                System.out.println("Please enter a value of 10 or greater.");
            }
        } while (numItems < 10);
        return numItems;
    }
    /** the created vending machine */
    private VendingMachine vm;
    /** the input scanner */
    private final Scanner SCANNER = new Scanner(System.in);
}