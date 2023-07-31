/**
 * This class is the driver class of the Vending Machine Factory.
 * @author Rowell Herrera and Daniella Ughoc S11A
 */
public class FactoryDriver {
    /**
     * This method is the main method
     * @param args the array of strings passed to the main function
     */
    public static void main(String[] args) {
        factory = new Factory();
        factory.displayWelcome();
    }
    /** the vending machine factory */
    private static Factory factory;
}