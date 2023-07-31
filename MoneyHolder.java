/**
 * This class represents a money holder
 * @author Rowell Herrera and Daniella Ughoc S11A
 */
public class MoneyHolder {
    /**
     * This constructor initializes the bills/coins ones, tens, fives, twenties, fifties,
     * hundreds, fiveHundreds, and thousands of the vending machine to be 0. It sets the total
     * to be 0 as well.
     */
    public MoneyHolder() {
        ones = 0;
        fives = 0;
        tens = 0;
        twenties = 0;
        fifties = 0;
        hundreds = 0;
        fiveHundreds = 0;
        thousands = 0;
        updateTotal();
    }

    //TODO: UPDATE
    /**
     * This method adds 6000 in total to the machine in different denominations. To be more specific,
     * 90 one peso, 40 five pesos, 40 ten pesos, 40 twenty pesos, 20 fifty pesos, 10 100 pesos, and
     * 5 500 pesos. It also updates the total.
     */
    public void generalReplenish() {
        ones += 90;
        fives += 40;
        tens += 40;
        twenties += 40;
        fifties += 20;
        hundreds += 10;
        fiveHundreds += 5;
        updateTotal();
    }
    /**
     * This method subtracts the total money by the price and checks if there is enough change.
     * @param price the price of the item
     * @return true or false depending if there is enough change
     */
    public boolean subtractTotal(int price) {
        int[] denom = new int[]{0,0,0,0,0,0,0,0};
        while (price-1000 >= 0 && getThousands() != 0) { //loop until there is not enough 1000 peso bills
            denom[0]++; //subtractThousands(1);
            price-=1000;
        }
        while (price-500 >= 0 && getFiveHundreds() != 0) { //loop until there is not enough 500 peso bills
            denom[1]++; //subtractFiveHundreds(1);
            price-=500;
        }
        while (price-100 >= 0 && getHundreds() != 0) { //loop until there is not enough 100 peso bills
            denom[2]++; //subtractHundreds(1);
            price-=100;
        }
        while (price-50 >= 0 && getFifties() != 0) { //loop until there is not enough 50 peso bills
            denom[3]++; //subtractFifties(1);
            price-=50;
        }
        while (price-20 >= 0 && getTwenties() != 0) { //loop until there is not enough 20 peso bills
            denom[4]++; //subtractTwenties(1);
            price-=20;
        }
        while (price-10 >= 0 && getTens() != 0) { //loop until there is not enough 10 peso coins
            denom[5]++; //subtractTens(1);
            price-=10;
        }
        while (price-5 >= 0 && getFives() != 0) { //loop until there is not enough 5 peso coins
            denom[6]++; //subtractFives(1);
            price-=5;
        }
        while (price-1 >= 0 && getOnes() != 0) { //loop until there is not enough 1 peso coins
            denom[7]++; //subtractOnes(1);
            price-=1;
        }

        if(price!=0) //if there is not enough change
            return false;
        else { //if there is enough change
            subtractThousands(denom[0]);
            subtractFiveHundreds(denom[1]);
            subtractHundreds(denom[2]);
            subtractFifties(denom[3]);
            subtractTwenties(denom[4]);
            subtractTens(denom[5]);
            subtractFives(denom[6]);
            subtractOnes(denom[7]);
            int dnm = 0;
            int totChange = 0;
            for (int i : denom) 
                totChange += i;
            if (totChange!=0) { //if there is no change
                System.out.println("Change: ");
                for (int i : denom) {
                    displayDispensedChange(dnm, i);
                    dnm++;
                }
                System.out.println();

                return true;
            }
            System.out.println("Exact payment received.\n");
            return true;
        }
    }
    /**
     * This method displays the dispensed change.
     * @param denom the denomination of the bill/coin
     * @param count the number of coins/bills
     */
    public void displayDispensedChange(int denom, int count) {
        switch (denom) {
            case 0:
                denom = 1000;
                break;
            case 1:
                denom = 500;
                break;
            case 2:
                denom = 100;
                break;
            case 3:
                denom = 50;
                break;
            case 4:
                denom = 20;
                break;
            case 5:
                denom = 10;
                break;
            case 6:
                denom = 5;
                break;
            case 7:
                denom = 1;
                break;
        }
        if (count != 0)
            System.out.println(count + " " + denom + " pesos (Total: " + count*denom + ").");
    }
    /**
     * This method increments the number of one peso coins by the given parameter.
     * @param m amount to add
     */
    public void addOnes(int m) {
        ones += m;
        updateTotal();
    }
    /**
     * This method increments the number of five peso coins by the given parameter.
     * @param m amount to add
     */
    public void addFives(int m) {
        fives += m;
        updateTotal();
    }
    /**
     * This method increments the number of 10 peso coins by the given parameter.
     * @param m amount to add
     */
    public void addTens(int m) {
        tens += m;
        updateTotal();
    }
    /**
     * This method increments the number of 20 peso bills by the given parameter.
     * @param m amount to add
     */
    public void addTwenties(int m) {
        twenties += m;
        updateTotal();
    }
    /**
     * This method increments the number of 50 peso bills by the given parameter.
     * @param m amount to add
     */
    public void addFifties(int m) {
        fifties += m;
        updateTotal();
    }
    /**
     * This method increments the number of 100 peso bills by the given parameter.
     * @param m amount to add
     */
    public void addHundreds(int m) {
        hundreds += m;
        updateTotal();
    }
    /**
     * This method increments the number of 500 peso bills by the given parameter.
     * @param m amount to add
     */
    public void addFiveHundreds(int m) {
        fiveHundreds += m;
        updateTotal();
    }
    /**
     * This method increments the number of 1000 peso bills by the given parameter.
     * @param m amount to add
     */
    public void addThousands(int m) {
        thousands += m;
        updateTotal();
    }
    /**
     * This method collects all the money from the vending machine.
     */
    public void generalCollect() {
        System.out.println("Collected Php " + total);
        ones = 0;
        fives = 0;
        tens = 0;
        twenties = 0;
        fifties = 0;
        hundreds = 0;
        fiveHundreds = 0;
        thousands = 0;
        updateTotal();
    }
    /**
     * This method decrements the number of 1 peso coins by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 1 peso coins to subtract
     */
    public boolean subtractOnes(int m) {
        if (ones >= m) {
            ones -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough one peso denomination.\n");
        return false;
    }
    /**
     * This method decrements the number of 5 peso coins by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 5 peso coins to subtract
     */
    public boolean subtractFives(int m) {
        if (fives >= m) {
            fives -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough five peso denomination.\n");
        return false;
    }
    /**
     * This method decrements the number of 10 peso coins by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 10 peso coins to subtract
     */
    public boolean subtractTens(int m) {
        if (tens >= m) {
            tens -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough ten peso denomination.\n");
        return false;
    }
    /**
     * This method decrements the number of 20 peso bills by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 20 peso bills to subtract
     */
    public boolean subtractTwenties(int m) {
        if (twenties >= m) {
            twenties -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough twenty peso denomination.\n");
        return false;
    }
    /**
     * This method decrements the number of 50 peso bills by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 50 peso bills to subtract
     */
    public boolean subtractFifties(int m) {
        if (fifties >= m) {
            fifties -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough fifty peso denomination.\n");
        return false;
    }
    /**
     * This method decrements the number of 100 peso bills by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 100 peso bills to subtract
     */
    public boolean subtractHundreds(int m) {
        if (hundreds >= m) {
            hundreds -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough 100 peso denomination.\n");
        return false;
    }
    /**
     * This method decrements the number of 500 peso bills by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 500 peso bills to subtract
     */
    public boolean subtractFiveHundreds(int m) {
        if (fiveHundreds >= m) {
            fiveHundreds -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough 500 peso denomination.\n");
        return false;
    }
    /**
     * This method decrements the number of 1000 peso bills by the given parameter. 
     * @param m amount to subtract
     * @return true or false depending if there is enough 1000 peso bills to subtract
     */
    public boolean subtractThousands(int m) {
        if (thousands >= m) {
            thousands -= m;
            updateTotal();
            return true;
        }
        System.out.println("Not enough 1000 peso denomination.\n");
        return false;
    }
    /**
     * This method updates the total money.
     */
    public void updateTotal() {
        total = ones + fives*5 + tens*10 + twenties*20 + fifties*50 + hundreds*100 + fiveHundreds*500 + thousands*1000;
    }
    /**
     * This method gets the number of 1 peso coins.
     * @return the number of 1 peso coins
     */
    public int getOnes() {
        return ones;
    }
    /**
     * This method gets the number of 5 peso coins.
     * @return the number of 5 peso coins
     */
    public int getFives() {
        return fives;
    }
    /**
     * This method gets the number of 10 peso coins.
     * @return the number of 10 peso coins
     */
    public int getTens() {
        return tens;
    }
    /**
     * This method gets the number of 20 peso bills.
     * @return the number of 20 peso bills
     */
    public int getTwenties() {
        return twenties;
    }
    /**
     * This method gets the number of 50 peso bills.
     * @return the number of 20 peso bills
     */
    public int getFifties() {
        return fifties;
    }
    /**
     * This method gets the number of 100 peso bills.
     * @return the number of 100 peso bills
     */
    public int getHundreds() {
        return hundreds;
    }
    /**
     * This method gets the number of 500 peso bills.
     * @return the number of 500 peso bills
     */
    public int getFiveHundreds() {
        return fiveHundreds;
    }
    /**
     * This method gets the number of 1000 peso bills.
     * @return the number of 1000 peso bills
     */
    public int getThousands() {
        return thousands;
    }
    /**
     * This method gets the total money.
     * @return the total money
     */
    public int getTotal() {
        updateTotal();
        return total;
    }
    /**
     * This method displays denominations, their counts, and the total money.
     */
    public void displayMoney() {
        System.out.println("Vending Machine Money: ");
        System.out.println("Denomination\t\tCount");
        System.out.println("1 peso\t\t\t" + ones);
        System.out.println("5 pesos\t\t\t" + fives);
        System.out.println("10 pesos\t\t" + tens);
        System.out.println("20 pesos\t\t" + twenties);
        System.out.println("50 pesos\t\t" + fifties);
        System.out.println("100 pesos\t\t" + hundreds);
        System.out.println("500 pesos\t\t" + fiveHundreds);
        System.out.println("1000 pesos\t\t" + thousands);
        System.out.println("===============================");
        System.out.println("Total:\t\t\t" + total);
        System.out.println();
    }
    /** the number of 1 peso coins */
    private int ones;
    /** the number of 5 peso coins */
    private int fives;
    /** the number of 10 peso coins */
    private int tens;
    /** the number of 20 peso bills */
    private int twenties;
    /** the number of 50 peso bills */
    private int fifties;
    /** the number of 100 peso bills */
    private int hundreds;
    /** the number of 500 peso bills */
    private int fiveHundreds;
    /** the number of 1000 peso bills */
    private int thousands;
    /** the total money */
    private int total;
}