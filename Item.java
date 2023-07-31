/**
 * This class represents an Item
 * @author Rowell Herrera and Daniella Ughoc S11A
 */
public class Item {
    /**
     * This constructor initializes the quantity, the name, the price, and the calories
     * of the item based on the given parameters. The availability is set to true as well.
     * @param qty the quantity of the item
     * @param n the name of the item
     * @param p the price of the item
     * @param c the calories of the item
     */
    public Item(int qty, String n, int p, double c) {
        quantity = qty;
        name = n;
        price = p;
        calories = c;
        availability = true;
    }
    /**
     * This constructor initializes the item based on the item given. It makes a copy of the 
     * item with a different address.
     * @param i the item to be copied
     */
    public Item(Item i) {
        quantity = i.getQuantity();
        name = i.getName();
        price = i.getPrice();
        calories = i.getCalories();
        availability = i.getAvailability();
    }
    /**
     * This method gets the availability of the item.
     * @return the availability of the item
     */
    public boolean getAvailability() {
        updateAvailability();
        return availability;
    }
    /**
     * This method gets the quantity of the item.
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * This method gets the name of the item.
     * @return the name of item
     */
    public String getName() {
        return name;
    }
    /**
     * This method gets the price of the item.
     * @return the price of the item
     */
    public int getPrice() {
        return price;
    }
    /**
     * This method gets the calories of the item.
     * @return the calories of the item
     */
    public double getCalories() {
        return calories;
    }
    /**
     * This method updates the availability of the item.
     * based on its quantity
     */
    public void updateAvailability() {
        if (quantity>0)
            availability = true;
        else
            availability = false;
    }
    /**
     * This method sets the price of the item.
     * @param p the new price of the item
     */
    public void setPrice(int p) {
        price = p;
    }
    /**
     * This method sets the calories of the item.
     * @param c the new calories of the item
     */
    public void setCalories(double c) {
        calories = c;
    }
    /**
     * This method restocks the quantity of the item.
     * @param qty the amount to restock
     * @return true or false depending if the restock was successful
     */
    public boolean restock(int qty) {
        if (quantity+qty < 21 && qty > 0 ) {
            quantity+= qty;
            return true;
        }
        return false;
    }
    /**
     * This method sets the name of the item.
     * @param n the new name of the item
     */
    public void setName(String n) {
        name = n;
    }
    /**
     * This method sets the quantity of the item.
     * @param qty the new quantity of the item.
     */
     public void setQuantity(int qty) {
        quantity=qty;
    }

    /** the availability of the item */
    private boolean availability;
    /** the quantity of the item */
    private int quantity;
    /** the name of the item */
    private String name;
    /** the price of the item */
    private int price;
    /** the calories of the item */
    private double calories;
}