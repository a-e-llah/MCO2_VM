import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class represents a vending machine.
 * 
 * @author Rowell Herrera and Daniella Ughoc S11A
 */
public class VendingMachine {
    /**
     * This constructor initializes the slots of the machine, the start inventory,
     * the end inventory, the transactions, and the money holder. The slots, start,
     * and end inventories have an initial size of 8.
     */
    public VendingMachine(int numSlots, int numItems) {
        this.numSlots = numSlots;
        slots = new ArrayList<Item>(numSlots);
        mh = new MoneyHolder();
        this.numItems=numItems;
        startInventory = new ArrayList<Item>(numItems);
        endInventory = new ArrayList<Item>(numItems);
        transactions = new ArrayList<Item>();
    }
    /**
     * This method increments the total payment by the product of the given count
     * and denomination. 
     * @param denomination the value of the bill/coin
     * @param count the number of bills/coins
     */
    public void updatePayment(int denomination, int count) {
        totalPayment += denomination*count;
    }
    /**
     * This method subtracts the total payment and the total money in the money holder
     * by the change.
     * @param change the change of the user
     * @return true or false depending if there is enough change in the vending machine
     */
    public boolean subtractPaymentAndBank(int change) {
        boolean success = mh.subtractTotal(change);
        if (success) { //if there is enough change in the machine
            totalPayment -= getTotalCost();
            return true;
        }
        mh.subtractTotal(getTotalPayment());
        return false;
    }
    /**
     * This method subtracts the total payment and the total money in the money holder by
     * the payment.
     */
    public void subtractPaymentAndBank() {
        mh.subtractTotal(getTotalPayment());
    }
    /**
     * This method adds the item to the list of transactions.
     * @param item the item to be added
     */
    public void updateTransactions(Item item) {
        transactions.add(item);
    }
    /**
     * This method updates the chosen item and updates the machine that
     * the user has chosen an item.
     * @param item the chosen item
     */
    public void updateChosen(Item item) {
        chosenItem = item;
        hasChosen = true;
    }
    /**
     * This method returns the address of the chosen item.
     * @return the address of the chosen item
     */
    public Item getChosen() {
        return chosenItem;
    }
    /**
     * This method returns the price of the chosen item.
     * @return the price of the chosen item
     */
    public int getTotalCost() {
        int total = chosenItem.getPrice();
        return total;
    }
    /**
     * This method resets the attributes related to buying an item to
     * their default values (the total payment, the chosen item, and if 
     * there is a chosen item).
     */
    public void resetBuying() {
        hasChosen = false;
        totalPayment = 0;
        chosenItem = null;
    }
    /**
     * This method is used for checking if there is a chosen item.
     * @return true or false depending if there is a chosen item
     */
    public boolean hasChosen() {
        return hasChosen;
    }
    /**
     * This method checks if there is enough money in the vending
     * machine to produce change.
     * @param totalPrice the total price of the chosen item
     * @return true or false depending if there is enough change in
     * the vending machine
     */
    public boolean produceChange(int totalPrice) {
        int totalCashInAmount = totalPayment;
        
        int change = totalCashInAmount - totalPrice;
    
        return change >= 0;
    }
    /**
     * This method checks if an item can be chosen by the user.
     * @param item the item to be chosen
     * @return true or false depending on the availability of the item
     */
    public boolean chooseItem(Item item) {
        boolean success = false;
        if (item.getAvailability()) 
            success = true;
        
        return success;
    }
    /**
     * This method displays the items in the vending machine.
     */
    public void displayItems() {
        String available = "No";
        if (isEmpty()) //if empty
            System.out.println("Vending Machine:\nEmpty.");

        else { //if not empty
            System.out.println("Vending Machine:");
            System.out.println("Available\t\tQuantity\t\tName\t\t\tPrice(Php)\t\tCalories");
            for (Item s : slots) {
                if (s==null) {
                    break;
                }

                if(s.getAvailability())
                    available = "Yes";
                else
                    available = "No";

                System.out.printf("%s\t\t%10d\t\t\t%-20s\t%-10d\t\t%-6.2f\n", available, s.getQuantity(), s.getName(), s.getPrice(), s.getCalories());
            }
        }
    }
    /**
     * This method displays the start inventory.
     */
    public void displayStartInv() {
        System.out.println("Start Inventory:");
        System.out.println("Quantity\t\tName\t\t\tPrice(Php)");
        for (Item s : startInventory) {
            if (s==null) {
                break;
            }

            System.out.printf("%d\t\t\t%-20s\t%-10d\n", s.getQuantity(), s.getName(), s.getPrice());
        }
    }
    /**
     * This method displays the end inventory.
     */
    public void displayEndInv() {
        System.out.println("End Inventory:");
        System.out.println("Quantity\t\tName\t\t\tPrice(Php)");
        for (Item s : endInventory) {
            if (s==null) {
                break;
            }

            System.out.printf("%d\t\t\t%-20s\t%-10d\n", s.getQuantity(), s.getName(), s.getPrice());
        }
    }
    /**
     * This method checks if there are any items in the vending machine.
     * @return true or false depending if there is at least one item
     */
    public boolean isEmpty() {
        boolean isEmpty = false;
        
        if(slots.isEmpty() == true) {
            isEmpty = true;
        }

        return isEmpty;
    }
    /**
     * This method checks if a new item can still be added.
     * @return true or false depending if the total limit of 10 has been reached
     */
    public boolean isFull() {
        return slots.size() >= numSlots;
    }
    /**
     * This method stocks a new item in a new slot in the vending machine.
     * @param qty the quantity of the item
     * @param name the name of the item
     * @param price the price of the item
     * @param calories the calories of the item
     * @return true or false depending if the item has been added.
     */
    public boolean stockNew(int qty, String name, int price, double calories) {
        Item temp = new Item(qty, name, price, calories);

        if (isFull())
            return false;

        slots.add(temp);
        startInventory.clear();
        for (Item i : slots) {
            Item insert = new Item(i);
            startInventory.add(insert);
        }
        transactions.clear();
        
        return true;
    }
    /**
     * This method finds an item in the vending machine.
     * @param name the name of the item
     * @return true or false depending if the item exists.
     */
    public Item findItem(String name) {
        for (Item i : slots) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }

        return null;
    }
    /**
     * This method restocks an item.
     * @param name the name of the item
     * @param qty the quantity to be added
     * @return true or false depending if the restock is successful
     */
    public boolean restockItem(String name, int qty) {
        if (findItem(name).restock(qty)) {
            startInventory.clear();
            for (Item i : slots) {
                Item insert = new Item(i);
                startInventory.add(insert);
            }
            transactions.clear();
            return true;
        }

        return false;
    }
    /**
     * This method edits an item.
     * @param item the item to be edited
     * @param qty the new quantity of the item
     * @param name the new name of the item
     * @param price the new price of the item
     * @param calories the calories of the item
     * @return true or false depending if the edit is successful
     */
    public boolean editItem(Item item, int qty, String name, int price, double calories) {
        Item temp = new Item(qty, name, price, calories);
        for (Item i : slots) {
            if (item == i) {
                slots.set(slots.indexOf(i), temp);
                startInventory.clear();
                for (Item it : slots) {
                    Item insert = new Item(it);
                    startInventory.add(insert);
                }
                transactions.clear();
                return true;
            }
        }

        return false;
    }
    /**
     * This method removes an item.
     * @param i the item to be removed
     * @return true or false depending if the item is removed
     */
    public boolean removeItem(Item i) {
        slots.remove(i);
        startInventory.clear();
        for (Item it : slots) {
            Item insert = new Item(it);
            startInventory.add(insert);
        }
        transactions.clear();
        return true;
    }
    /**
     * This method displays the denominations and counts of the money in the
     * money holder.
     */
    public void displayDenom() {
        mh.displayMoney();
    }
    /**
     * This method collects all the money in the vending machine.
     */
    public void collectAll() {
        if (mh.getTotal() == 0)
            System.out.println("No money to collect.\n");
        else {
            mh.generalCollect();
            displayDenom();
        }
    }
    /**
     * This method collects a specific denomination from the money holder.
     * @param denom the denomination of the coin/bill
     * @param count the number of bills/coins
     * @return the money collected
     */
    public int collectSpec(int denom, int count) {
        int amt=0;
        boolean success = false;
        switch (denom) { //switch statement for the different denominations
            case 1:
                if(mh.subtractOnes(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            case 5:
                if(mh.subtractFives(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            case 10:
                if(mh.subtractTens(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            case 20:
                if(mh.subtractTwenties(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            case 50:
                if(mh.subtractFifties(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            case 100:
                if(mh.subtractHundreds(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            case 500:
                if(mh.subtractFiveHundreds(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            case 1000:
                if(mh.subtractThousands(count)) {
                    System.out.println("Collected " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                    success = true;
                }
                break;
            default:
                System.out.println("Invalid denomination input.");
                success = false;
        }

        if (success) 
            amt = count * denom;

        return amt;
    }
    /**
     * This method gets the total money in the vending machine
     * @return the total money in the vending machine
     */
    public int getTotalMh() {
        return mh.getTotal();
    }
    /**
     * This method adds 6000 in different denominations to the money holder.
     */
    public void genReplenish() {
        mh.generalReplenish();
    }
    /**
     * This method adds a specific number of denomination to the money holder.
     * @param denom the denomination of the bill/coin
     * @param count the count of the bills/coins
     * @return true or false depending if the collection was successful
     */
    public boolean specReplenish(int denom, int count) {
        switch (denom) { //switch statement for different denominations
            case 1:
                mh.addOnes(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            case 5:
                mh.addFives(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            case 10:
                mh.addTens(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            case 20:
                mh.addTwenties(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            case 50:
                mh.addFifties(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            case 100:
                mh.addHundreds(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            case 500:
                mh.addFiveHundreds(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            case 1000:
                mh.addThousands(count);
                System.out.println("Added " + count + " " + denom + " pesos (Total: " + count*denom + ").");
                break;
            default:
                System.out.println("Invalid denomination input.");
                return false;
        }

        return true;
    }
    /**
     * This method prints the transactions, the start inventory, and the end inventory.
     */
    public void printTransactions() {
        endInventory.clear();
        endInventory.addAll(slots);
        System.out.println("Transactions:");
        System.out.println("Name\t\tPrice(Php)");
        for (Item i : transactions) {
            System.out.printf("%s\t\t%-5d\n", i.getName(), i.getPrice());
        }
        System.out.println();
        displayStartInv();
        System.out.println();
        displayEndInv();
        System.out.println();
    }
    /**
     * This method gets the list of items in the machine
     * @return the list of items in the machine
     */
    public ArrayList<Item> getSlots() {
        return slots;
    }
    /**
     * This method gets the total payment of the user
     * @return the total payment of the user
     */
    public int getTotalPayment() {
        return totalPayment;
    }

    public int getNumItems() {
        return numItems;
    }
    /**
     * This method gets the list of transactions
     * @return the list of transactions
     */
    public ArrayList<Item> getTransactions() {
        return transactions;
    }
    /**
     * This method gets the end inventory.
     * @return the end inventory
     */
    public ArrayList<Item> getEndInventory() {
        return endInventory;
    }
    /**
     * This method gets the start inventory.
     * @return the start inventory
     */
    public ArrayList<Item> getStartInventory() {
        return startInventory;
    }

    
    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }


    /** the list of items in the vending machine */
    private ArrayList<Item> slots;
    /** the money holder */
    private MoneyHolder mh;
    /** the list of transactions */
    private ArrayList<Item> transactions;
    /** the total payment */
    private int totalPayment = 0;
    /** the chosen item */
    private Item chosenItem;
    /** the existence of the chosen item */
    private boolean hasChosen = false;
    /** the start inventory */
    private ArrayList<Item> startInventory;
    /** the end inventory */
    private ArrayList<Item> endInventory;

    private int numSlots;
    private int numItems;

    
}