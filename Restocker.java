import java.io.IOException;
import java.util.*;

public class Restocker
{
    VendingMachine tempVending;
    //DESCRIPTION OF CLASS ABILITIES
    //Get List of "Remove" items
    //Get list of "Add" items
    //Update list of "Add" items by taking input of an item and quantity and subtracting that from the list

    //Loop flag
    private boolean restocker_loop;

    //Constructor
    public Restocker(VendingMachine tempVending)  {
        setVendingMachine(tempVending);
        setRestockLoop(true);
        loop();
    }

    //------------------------------------------------------------------------------------------------------------------
    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setVendingMachine(VendingMachine tempVending)
    {
        this.tempVending = tempVending;
    }
    public void setRestockLoop(Boolean restocker_loop)
    {
        this.restocker_loop = restocker_loop;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public VendingMachine getVendingMachine()
    {
        return this.tempVending;
    }
    public Boolean getRestockLoop()
    {
        return this.restocker_loop;
    }
    //------------------------------------------------------------------------------------------------------------------

    //Methods
    //------------------------------------------------------------------------------------------------------------------
    //Restocker Loop
    private void loop() {
        while(restocker_loop)
        {
            //Main Prompt
            System.out.println("Welcome to the Restocking Interface. Please select 'Add List' by typing 'A', 'Remove List' by typing 'R' or Exit by typing 'Exit'");
            //Scanner to read user input
            Scanner readIn = new Scanner(System.in);
            String input = readIn.nextLine();

            //Input Verification
            if(input.toLowerCase().equals("a") || input.toLowerCase().equals("add list"))
            {
                addItemLoop();
            }
            else if(input.toLowerCase().equals("r") || input.toLowerCase().equals("remove list"))
            {
                removeItemLoop();
            }
            else if(input.toLowerCase().equals("exit"))
            {
                restocker_loop = false;
            }
        }
    }

    //Returns the additemList from the vending machine
    private ArrayList<String> getAddList(VendingMachine temp)
    {
        return temp.getAddItems();
    }

    private int getInfoFromString(String input, int stringPos)
    {
        String[] splitString;
        splitString = input.split(",");
        if(stringPos < splitString.length && stringPos > -1)
        {
            int output = Integer.parseInt(splitString[stringPos]);
            return output;
        }
        return 0;
    }


    //Loop to go inside the loop above. This loop will be when someone selects the add item list option.
    //'exit' to exit and enter item_id, rowPos, colPos, amount as input in the form #,#,#,#.
    //Need to think about adding items dynamically
        //Display add item list
        //input info
        //is position data valid?
        //is amount valid?
        //If the slot is null, create a new slot with the input
        //update add item list where the item exists to reflect additions
            //inside the update check if the amount = 0. If it does, just remove the slot.

   public void addItemLoop()
    {
        ArrayList<String> inputRecord = new ArrayList<String>();
        ArrayList<String> returnList = new ArrayList<String>();
        Boolean loopFlag = true;
        if(tempVending.addItem.size() == 0) {
            System.out.println("There is nothing in the add items list");
            loopFlag = false;
        }
        while(loopFlag)
        {
            tempVending.printAddItemList();
            System.out.println("Type in 'id,row position,column position,amount added' to confirm items added to vending machine, or 'e' to exit");
            Scanner readInput = new Scanner(System.in);
            String input = readInput.nextLine();
            if(input.toLowerCase().equals("e"))
            {
                loopFlag = false;
            }
            else {

                //Input verification Here:
                //-----------------------
                if(inputVerification(input))
                {
                    inputRecord.add(input);
                    addItem(getInfoFromString(input,0),getInfoFromString(input,1),getInfoFromString(input,2),getInfoFromString(input,3),tempVending);
                    tempVending.addItem = returnDifference(tempVending.addItem,inputRecord);
                    System.out.println("Items Added!");
                }
            }
        }
        //Update the inventory?
    }

    //Loop to go through the remove item list, then prompt the restocker
    private void removeItemLoop()
    {
        Boolean loopFlag = true;
        {

            tempVending.printRemoveItemList();
            System.out.println("Once all items have been removed, please press 'e' to exit. You can press 'l' to reprint the list");
            while(loopFlag)
            {
                Scanner reader = new Scanner(System.in);
                String in = reader.next();
                if(in.toLowerCase().equals("e")) {
                    tempVending.initRemove();
                    loopFlag = false;
                }
                else {System.out.println("Please type 'e' to exit");}
            }
        }
    }

    //Adds items to a vending machine at a specified position with a specified amount
    private void addItem(int id, int x, int y, int amount, VendingMachine vending)
    {
        vending.addItem(id,x,y,amount);
    }

    private boolean inputVerification(String stringIn)
    {
        if(stringIn.contains(","));
        {
            if(stringIn.split(",").length == 4)
            {
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> returnDifference(ArrayList<String> base, ArrayList<String> alt)
    {
        ArrayList<String> difference = new ArrayList<String>();
        for(String r : base)
        {
            boolean exists = false;
            for(String a : alt)
            {
                if(r.equals(a))
                {
                    exists = true;
                }
            }
            if(!exists)
            {
                difference.add(r);
            }
        }
        return difference;
    }
}
