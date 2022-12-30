//------------------------------------------------------------------------------------------------------------------------
//Main Idea of the Class
    //User enters item position
        //Take user input
        //Parse for position or 'P'
            //Check if slot exists with item_id
                //if it exists, does it exist on the remove list? Is currentCount <= 0?
                    //If either are true, print sorry and continue loop.
                //Check if slot exists in itemSelection
                        //if itemSelection slot.currentCount > vendingMachine slot.currentCount
                            //Print sorry, you have selected the max amount
                        //else set the currentCount of the itemSelection slot to slot.currentCount += amount
                //if no, create slot object, set the currentCount = 1 and add to list (or 'amount' as long as amount < slot.currentCount)
                //Print item(s) added
            //Add price to total
            //If 'P'
                //Loop through itemSelection List
                //Add totals
                //Display total
                //Wait for input
                //Compare Input
                    //If input < total
                        //total -= input
                        //Goto Display Total
                    //If input == total
                        //Say thank you
                        //Close loop
                        //Update Vending Machine
                        //Update DB
                    //Else
                        //Calculate change owed by input - total



        
//----------------------------------------------------------------------------------------------------------------------

//KNOWN ISSUES
//======================================================================================================================
    //Input verification for transaction loop. If user enters anything other than specified format it will crash the machine
    //This is to be fixed in later updates with input verification
//======================================================================================================================


//Package Imports
//----------------------------------------------------------------------------------------------------------------------
import java.util.ArrayList;
import java.util.Scanner;

//----------------------------------------------------------------------------------------------------------------------
//THE CLASS
//----------------------------------------------------------------------------------------------------------------------
public class Customer
{
    //Class Variables
    //------------------------------------------------------------------------------------------------------------------
    boolean customer_loop;
    VendingMachine baseVendingMachine;
    //------------------------------------------------------------------------------------------------------------------

    //Constructors
    //------------------------------------------------------------------------------------------------------------------
    public Customer(VendingMachine newMachine)
    {
        baseVendingMachine = newMachine;
        customer_loop = true;
        loop();
    }
    //------------------------------------------------------------------------------------------------------------------

    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public VendingMachine getVendingMachine()
    {
        return baseVendingMachine;
    }
    public boolean getLoopBoolean()
    {
        return customer_loop;
    }
    //------------------------------------------------------------------------------------------------------------------
    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setVendingMachine(VendingMachine baseVendingMachine)
    {
        this.baseVendingMachine = baseVendingMachine;
    }
    public void setLoopBoolean(boolean customer_loop)
    {
        this.customer_loop = customer_loop;
    }
    //------------------------------------------------------------------------------------------------------------------


    //Methods
    //------------------------------------------------------------------------------------------------------------------
    //Loop method
    private void loop()
    {
        while(customer_loop)
        {
            //Main Prompt
            System.out.println("Welcome to the Customer interface, Please type 'T' to start a transaction or 'Exit' to Exit");
            //Scanner to read user input
            Scanner readIn = new Scanner(System.in);
            String input = readIn.nextLine();

            //Input Verification
            if(input.toLowerCase().equals("t") || input.toLowerCase().equals("transaction"))
            {
                beginTransactionLoop();
            }
            else if(input.toLowerCase().equals("exit"))
            {
                customer_loop = false;
            }
        }
    }

    //Selection and Transaction. Should split this apart into two methods, will do later
    private void beginTransactionLoop()
    {
        //Create a temporary vending machine object
        VendingMachine tempVending = baseVendingMachine;

        //ArrayList to hold the slots that contain the items the customer would like to purchase.
        //We can get the information of the items by comparing the item_id of the slot object to the item list of the vending machine
        ArrayList<String> positionInput = new ArrayList<String>();
        double totalPrice = 0;
        boolean purchase_loop = true;

        //Loop to start item selection
        while(purchase_loop)
        {
            tempVending.printInventoryForCustomer();
            
            System.out.println("To add an item to selection, enter the position of the item seperated by a comma, followed by the amount of items you" +
                " would like to purchase, EX: '1,4,1' would select position (1,4) with amount 1. To finish purchasing, enter 'P'. To cancel transaction, type 'exit'");
            
            Scanner readInput = new Scanner(System.in);
            String lineIn = readInput.nextLine();

            if(lineIn.toLowerCase().equals("p")) {
                totalPrice = calculatePrice(positionInput);
                if (totalPrice != 0) {
                    beginPaymentLoop(totalPrice, tempVending);
                }
                purchase_loop = false;
            }
            else{
                addToItemSelection(lineIn,positionInput);
                updateVendingMachineInventory(lineIn,positionInput,tempVending);
            }
        }
    }

    //Loop for payments (You will be locked in until the correct amount is paid)
    //  Will modify in future
    private void beginPaymentLoop(double readInPrice, VendingMachine temp)
    {
        double totalPrice = readInPrice;
        boolean paymentLoop = true;
        while(paymentLoop)
        {
            System.out.println("Your total is: $" + totalPrice);
            System.out.println("Please enter your cash:");
            Scanner readIn = new Scanner(System.in);
            String input = readIn.nextLine();
            double convertToDouble = Double.parseDouble(input);
            totalPrice -= convertToDouble;
            if(totalPrice <= 0)
            {
                System.out.println("Your Change is : $" + (totalPrice *= -1));
                setVendingMachine(temp);
                paymentLoop = false;
            }
        }
    }


    //Method to return the value of the position int from the string form that is in the input arraylist
    //Arguments of the String that we want split, which should be in the form of int,int
    //Because of this, we can "always" assume that the stringPos int will either be 0,1,or 2, with 0 being the 
    //X pos and 1 being the Y pos, and 2 being the amount of item
    private int getInfoFromString(String input, int stringPos)
    {
        String[] splitString;
        splitString = input.split(",");
        if(stringPos < splitString.length && stringPos > -1)
        {
            return Integer.parseInt(splitString[stringPos]);
        }
        return 0;
    }

    //Calculates the total price of the list of items
    private double calculatePrice(ArrayList<String> items)
    {
        double totalPrice = 0;
        for(String a : items)
        {
            int item_id = baseVendingMachine.getSlot(getInfoFromString(a,0), getInfoFromString(a, 1)).getItemID();
            double initPrice = baseVendingMachine.getPrice(item_id);
            initPrice *= getInfoFromString(a,2);
            totalPrice += initPrice;
        }
        return totalPrice;
    }

    //Verifies that the user has only input either a comma or numbers 
    private boolean inputVerification(String stringIn)
    {
        char[] charArray = stringIn.toCharArray();
        for(char a : charArray)
        {
            if((int) a == 44 || (int) a >= 48 && (int) a <= 57){return true;}
            else return false;
        }
        return true;
    }

    //Boolean that will return whether there is enough items in the slot to purchase

    private boolean isEnoughInSlot(String stringIn)
    {
        return getInfoFromString(stringIn, 2) <= amountInSlot(stringIn);
    }

    //Returns the number of items in the slot currently
    private int amountInSlot(String stringIn)
    {
        return baseVendingMachine.getSlot(getInfoFromString(stringIn,0),getInfoFromString(stringIn,1)).getCurrentCount();
    }

    //Boolean to return whether an item is contained in our position list currently
    private boolean itemInArray(String stringIn, ArrayList<String> array)
    {
        for(String s : array)
        {
            if(getInfoFromString(stringIn,0) == getInfoFromString(s,0) && getInfoFromString(stringIn,1) == getInfoFromString(s,1))
            {
                return true;
            }
        }
        return false;
    }

    //Method to add an item to our selection list after some checks
    private void addToItemSelection(String stringIn, ArrayList<String> array)
    {
        //Checks if the slot already exists in our purchase list
        if(itemInArray(stringIn,array))
        {
            //If slot exists, just update the amount of items being purchased as long as it is less than or equal to the amount of items in the slot currently
            for(String s : array)
            {
                int newAmount = getInfoFromString(stringIn,2) + getInfoFromString(s,2);
                System.out.println("New Amount: " + newAmount);
                System.out.println("CurrentCount: " + amountInSlot(s));
                if(newAmount <= amountInSlot(s))
                {
                    //Update the amount of items being purchased
                    String newString = getInfoFromString(s,0) + "," + getInfoFromString(s,1) + "," + newAmount;
                    array.set(array.indexOf(s),newString);   
                }
                else{
                    System.out.println("======================================================================");
                    System.out.println("You have selected more items than what was available, please try again");
                    System.out.println("======================================================================");} 
            }
        }
        //Else if there is no listing in the list, add it
        else 
        {
            if(isEnoughInSlot(stringIn))
            {
                array.add(stringIn);
            }
            
            else{
                System.out.println("======================================================================");
                System.out.println("You have selected more items than what was available, please try again");
                System.out.println("======================================================================");}      
        }
    } 


    //Updates the vending machine argument with the newly modified data
    private void updateVendingMachineInventory(String stringIn, ArrayList<String> array, VendingMachine vending)
    {
        if(amountInSlot(stringIn) - getInfoFromString(stringIn,2) >= 0)
        {
            int x = getInfoFromString(stringIn, 0);
            int y = getInfoFromString(stringIn,1);
            Slot temp = vending.getSlot(x,y);
            temp.setCurrentCount(temp.getCurrentCount() - getInfoFromString(stringIn, 2));
            vending.setSlot(x,y,temp);
        }
    }

    //Makes sure the string arg is in numeric or '.'
    private boolean inputWithinNumeric(String stringIn)
    {
        char[] charArray = stringIn.toCharArray();
        for(char a : charArray)
        {
            if((int) a == 46 || (int) a >= 48 && (int) a <= 57){}
            else return false;
        }
        return true;
    }




    //------------------------------------------------------------------------------------------------------------------
}
