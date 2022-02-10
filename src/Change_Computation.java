import java.util.Scanner;

public class Change_Computation {

    /**
     * cost used to save the cost of the item
     */
    private double cost;

    /**
     * int[] of the denominations breakdown of money received in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    private  int[] amountPaid;

    /**
     * calculated variable to put the amount paid in decimal form
     */
    private double totalPaid;

    /**
     * used to allow more accuracy and breakdown the change into bills
     */
    private int amountPaidInBills;

    /**
     * used to allow more accuracy and breakdown the change into coins
     */
    private int amountPaidInChange;

    /**
     * the change in the cash register in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    private int[] changeAvailable;

    /**
     * the change that should be returned to the customer in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    int[] change = new int[8];

    /**
     * constructor, uses 2 parameters and calculates the other instance variables based on those
     * @param cost  cost of the item
     * @param amountPaid  int[] of the denomination breakdown paid in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public Change_Computation(double cost,int[] amountPaid){
        this.cost = cost;
        this.amountPaid = amountPaid;
        this.amountPaidInBills = amountPaid[0]*20 + amountPaid[1]*10 + amountPaid[2]*5 + amountPaid[3]; //just the first 4 to find the total before the decimal
        this.amountPaidInChange = amountPaid[4]*25 + amountPaid[5]*10 + amountPaid[6]*5 + amountPaid[7]*1; //just the last 4 to find the total after the decimal
        if (amountPaidInChange >= 100){  //need to reset upto dollars if cents get above 100
            amountPaidInBills += Math.floor((double)(amountPaidInChange)/100);
            amountPaidInChange = amountPaidInChange%100;
        }

        totalPaid = amountPaidInBills + (double)(amountPaidInChange)/100; //sets the amount paid in decimal form, need double to prevent dropping of decimal
        changeAvailable = new int[8];
        for (int i = 0; i < 8; i++){ // randomly chooses a number 0-15 for each denomination in the cash register
            changeAvailable[i] = (int)(Math.floor(Math.random()*16));
        }
    }

    /**
     * getter for amount paid
     * @return amount paid in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public int[] getAmountPaid() {
        return amountPaid;
    }

    /**
     * setter for amount paid, also recalculates the other instance variables that would be affected
     * @param amountPaid int[] in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public void setAmountPaid(int[] amountPaid) {
        this.amountPaid = amountPaid;
        amountPaidInBills = amountPaid[0]*20 + amountPaid[1]*10 + amountPaid[2]*5 + amountPaid[3];
        amountPaidInChange = amountPaid[4]*25 + amountPaid[5]*10 + amountPaid[6]*5 + amountPaid[7];
        if (amountPaidInChange >= 100){
            amountPaidInBills += Math.floor(amountPaidInChange/100);
            amountPaidInChange = amountPaidInChange%100;
        }
        totalPaid = amountPaidInBills + (double)(amountPaidInChange)/100;
        for (int i = 0; i<8; i++){
            changeAvailable[i] += amountPaid[i];
        }
    }

    /**
     * setter for amount paid in bills
     * @param amountPaidInBills
     */
    public void setAmountPaidInBills(int amountPaidInBills) {
        this.amountPaidInBills = amountPaidInBills;
    }

    /**
     * getter for amount paid in bills
     * @return amount paid in bills
     */
    public int getAmountPaidInBills() {
        return amountPaidInBills;
    }

    /**
     * setter for amount paid in change
     * @param amountPaidInChange
     */
    public void setAmountPaidInChange(int amountPaidInChange) {
        this.amountPaidInChange = amountPaidInChange;
    }

    /**
     * getter for amount paid in change
     * @return amount paid in change
     */
    public int getAmountPaidInChange() {
        return amountPaidInChange;
    }

    /**
     * getter for total paid
     * @return total paid
     */
    public double getTotalPaid() {
        return totalPaid;
    }

    /**
     * setter for total paid
     * @param totalPaid
     */
    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    /**
     * getter for cost
     * @return cost of the item
     */
    public double getCost() {
        return cost;
    }

    /**
     * setter for cost
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * getter for change available
     * @return change available in the register in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public int[] getChangeAvailable() {
        return changeAvailable;
    }

    /**
     * setter for change available
     * @param changeAvailable int[] in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public void setChangeAvailable(int[] changeAvailable) {
        this.changeAvailable = changeAvailable;
    }

    /**
     * getter for change
     * @return change to give in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public int[] getChange() {
        return change;
    }

    /**
     * setter for change
     * @param change int[] in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public void setChange(int[] change) {
        this.change = change;
    }

    /**
     * calulates the change that should be returned and stores it in change in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    public void changeToReturn(){
        if( cost > totalPaid){
            throw new IllegalArgumentException("Not enough paid, amount paid should be larger or equal to the cost");
        }
        amountPaidInBills = (int) Math.floor(totalPaid-cost); //ajusts them down to the change needed to be given
        amountPaidInChange = (int) (((totalPaid*100-cost*100)%100));
        if(amountPaidInBills == 0 && amountPaidInChange == 0){ //if the amount paid is the same as the cost
            for(int i = 0; i < 8; i++){
                change[i] = 0;
            }
        }else{ //goes through each denomination to fill up the change array
            numberOfBills(20,0);
            numberOfBills(10,1);
            numberOfBills(5,2);
            numberOfBills(1,3);
            amountOfChange(25,4);
            amountOfChange(10,5);
            amountOfChange(5,6);
            amountOfChange(1,7);
            if(amountPaidInChange > 0){ //too much change for the register or not the right denominations to fix it
                throw new IllegalArgumentException("not enough change in the register");
            }
        }
    }

    /**
     * helper method to calculate how many of a certain bill needs to be change
     * @param amount multiplier for the specific denomination
     * @param index index in the array of denominations in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    private void numberOfBills(double amount, int index){
        if(amountPaidInBills/amount >= 1.0){ //is there enough change to need this bill type
            if(amountPaidInBills/amount <= changeAvailable[index]){ //is there enough of that bill in the register
                changeAvailable[index] -= (int)(Math.floor(amountPaidInBills/amount));
                change[index] = (int)(Math.floor(amountPaidInBills/amount));
                amountPaidInBills -= (int)(Math.floor(change[index]*amount));

            }else{ // otherwise use all of that bill that you can
                change[index] = changeAvailable[index];
                amountPaidInBills -= changeAvailable[index]*amount;
                changeAvailable[index] = 0;
            }
        }
        if(amount == 1){ // if there wernt enough of all the bills need to make it up in coins
           amountPaidInChange += amountPaidInBills*100;
        }
    }

    /**
     * helper method to determine how many of a specific coin need to be given as change
     * @param amount multiple of a penny the coin is (so a quarter is 25)
     * @param index index in the int[] in order [$20,$10,$5,$1,quarter,dime,nickel,penny]
     */
    private  void amountOfChange(double amount, int index){
        if(amountPaidInChange/amount >= 1.0){
            if(amountPaidInChange/amount <= changeAvailable[index]){
                changeAvailable[index] -= (int)(Math.floor(amountPaidInChange/amount));
                change[index] = (int)(Math.floor(amountPaidInChange/amount));
                amountPaidInChange -= (int)(Math.floor(change[index]*amount));

            }else{
                change[index] = changeAvailable[index];
                amountPaidInChange -= changeAvailable[index]*amount;
                changeAvailable[index] = 0;
            }
        }
    }

    /**
     * main metod to ask the user what translation they are preforming
     * @param args
     */
    public static void main( String args[] ){
       Change_Computation register = new Change_Computation(0.0,new int[]{0,0,0,0,0,0,0,0});
       boolean cont = true;
       Scanner src = new Scanner(System.in);
       while (cont){ // keeps going as long as the user want to continue
           System.out.println("Current amount in the cash register:");  //prints out how many of each denomination the register has
           System.out.println("20 dollar bills: \t" + register.getChangeAvailable()[0]);
           System.out.println("10 dollar bills: \t" + register.getChangeAvailable()[1]);
           System.out.println("5 dollar bills: \t" + register.getChangeAvailable()[2]);
           System.out.println("1 dollar bills: \t" + register.getChangeAvailable()[3]);
           System.out.println("quarters: \t\t\t" + register.getChangeAvailable()[4]);
           System.out.println("dimes: \t\t\t\t" + register.getChangeAvailable()[5]);
           System.out.println("nickels: \t\t\t" + register.getChangeAvailable()[6]);
           System.out.println("pennies: \t\t\t" + register.getChangeAvailable()[7]);
           System.out.print("What did the item cost: ");
           register.setCost(src.nextDouble());
           int[] amountPaid = new int[8];
           System.out.print("how many $20 did you receive: "); // asks user for the exact details as to how they were piad
           amountPaid[0] = src.nextInt();
           System.out.print("how many $10 did you receive: ");
           amountPaid[1] = src.nextInt();
           System.out.print("how many $5 did you receive: ");
           amountPaid[2] = src.nextInt();
           System.out.print("how many $1 did you receive: ");
           amountPaid[3] = src.nextInt();
           System.out.print("how many quarters did you receive: ");
           amountPaid[4] = src.nextInt();
           System.out.print("how many dimes did you receive: ");
           amountPaid[5] = src.nextInt();
           System.out.print("how many nickels did you receive: ");
           amountPaid[6] = src.nextInt();
           System.out.print("how many pennies did you receive: ");
           amountPaid[7] = src.nextInt();
           register.setAmountPaid(amountPaid);
           System.out.println("that totals to: $" + register.getTotalPaid());
           register.changeToReturn();
           System.out.println("give the customer this change: "); // prints out the total amount of each denomination of change
           System.out.println("20 dollar bills: \t" + register.getChange()[0]);
           System.out.println("10 dollar bills: \t" + register.getChange()[1]);
           System.out.println("5 dollar bills: \t" + register.getChange()[2]);
           System.out.println("1 dollar bills: \t" + register.getChange()[3]);
           System.out.println("quarters: \t\t\t" + register.getChange()[4]);
           System.out.println("dimes: \t\t\t\t" + register.getChange()[5]);
           System.out.println("nickels: \t\t\t" + register.getChange()[6]);
           System.out.println("pennies: \t\t\t" + register.getChange()[7]);
           System.out.println();
           System.out.print("Would you like to make another translation (enter true or false):"); // asks the user if they have more translations
           cont = src.nextBoolean();

       }
    }
}

