import java.io.*;
import java.util.*;


public class Management {
    //------------------------------------------------------------------------------------------------------------------
    //Class Vars
    //------------------------------------------------------------------------------------------------------------------
    boolean management_loop;
    ArrayList<String> itemsToAddToDB = new ArrayList<>();
    VendingMachine tempVending;

    //------------------------------------------------------------------------------------------------------------------
    //Constructors
    //------------------------------------------------------------------------------------------------------------------
    public Management(VendingMachine tempVending) throws IOException {
        setVending(tempVending);
        setManagement_loop(true);
        loop();
    }

    //------------------------------------------------------------------------------------------------------------------
    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setVending(VendingMachine tempVending) {
        this.tempVending = tempVending;
    }

    public void setManagement_loop(Boolean management_loop) {
        this.management_loop = management_loop;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public VendingMachine getVending() {
        return this.tempVending;
    }
    //------------------------------------------------------------------------------------------------------------------
    //Main Loop
    //------------------------------------------------------------------------------------------------------------------
    private void loop() throws IOException {
        while (management_loop) {
            //Main Prompt
            System.out.println("Welcome to the Management Interface. To show a list of commands, type 'Help'. To Exit, type 'Exit'");
            //Scanner to read user input
            Scanner readIn = new Scanner(System.in);
            String input = readIn.nextLine();

            String[] args = input.split(" ");

            switch (args[0].toLowerCase()) {
                //Primary commands:
                case "exit" -> setManagement_loop(false);
                case "help" -> printHelp();

                case "printlist" -> printList();


                //Location commands:
                case "getlocation" -> getLocation(tempVending);
                case "setlocation" -> setLocation(tempVending, args[1]);


                case "getcurrentcount" -> getCurrentCount();
                case "getmaxcount" -> getMaxCount();
                case "getdepth" -> getDepth();
                case "getcolumncount" -> getColumnCount();
                case "getrowcount" -> getRowCount();

                //Database commands:
                case "additemtodatabaselist" -> addToItemDB(args[1]);
                case "removeitemfromdatabase" -> {
                    removeItemInDB(args[1]);
                    System.out.println("Item Removed from DB");
                }
                case "invview" -> printPositionDB();
                case "itemview" -> printItemDB();
                case "updateitemdb" -> {
                    writeToItemCsv(itemsToAddToDB);
                    System.out.println("Successfully added items to database");
                }

                //Add Item List commands:
                case "additemview" -> printAddList();
                case "additemtoaddlist" -> addItemToAddList(args[1]);
                case "removeitemfromaddlist" -> removeFromAddList(args[1]);


                //Remove Item List commands:
                case "additemtoremove" -> addItemToRemoveList(Integer.parseInt(args[1]));
                case "removeitemview" -> tempVending.printRemoveItemList();
                case "removefromremove" -> removeItemFromRemoveList(Integer.parseInt(args[1]));
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------
    private void printHelp() {
        //Primary display:
        System.out.println("=================================================================================================================================");
        System.out.println("List of Commands:");
        System.out.println("=================================================================================================================================");
        System.out.println("help:                                                                       Displays a list of commands.");
        System.out.println("exit:                                                                       Exits the management interface");

        //General commands:
        System.out.println("getCurrentCount:                                                            Returns the count of all items in the vending machine");
        System.out.println("getMaxCount:                                                                Returns the count of the maximum items that can be in a machine");
        System.out.println("getDepth:                                                                   Returns the depth of the vending machine");
        System.out.println("getColumnCount:                                                             Returns the amount of columns in the vending machine");
        System.out.println("getRowCount:                                                                Returns the amount of rows in the vending machine");

        //Location commands:
        System.out.println("getLocation:                                                                Displays the location of the machine");
        System.out.println("setLocation [location]:                                                     Sets the location of the vending machine");

        //Database commands:
        System.out.println("invView:                                                                    Displays the inventory of the current vending machine");
        System.out.println("itemView:                                                                   Displays the items in the database");
        System.out.println("addItemToDataBaseList [item_id,name,price,MM/DD/YYYY]:                      Adds an item to the list of items that will be added to DB when the updateItemDB command is run");
        System.out.println("updateItemDB:                                                               Updates the item Database with your added info");
        System.out.println("removeItemFromDataBase [item_id]:                                           Removes an item from the item DB");

        //Remove List commands:
        System.out.println("removeItemView:                                                             Displays the items in the remove item list");
        System.out.println("addItemToRemove [item_id]:                                                  Adds an item to the remove list by the item id");
        System.out.println("removeFromRemove [item_id]:                                                 Removes the specified item from the remove list");

        //Add List commands:
        System.out.println("addItemView:                                                                Displays the items in the add item list");
        System.out.println("addItemToAddList [item_id,row position,column position,amount]:             Adds a request to the add item list");
        System.out.println("removeItemFromAddList [item_id,row position,column position,amount]:        Removes the specified element from the add item list");
        System.out.println("=================================================================================================================================");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add item List methods
    //------------------------------------------------------------------------------------------------------------------
    private void addItemToAddList(String stringIn) {
        tempVending.addItem.add(stringIn);
    }

    private void removeFromAddList(String stringIn) {
        tempVending.addItem.remove(stringIn);
    }

    private void printAddList()
    {
        tempVending.printAddItemList();
    }

    //------------------------------------------------------------------------------------------------------------------
    //Remove Item List methods
    //------------------------------------------------------------------------------------------------------------------
    private void addItemToRemoveList(int id){
        //replace arg with check
        if(tempVending.itemIDExists(id))
        {
            tempVending.removeList.add(id);
        }
    }
    private void removeItemFromRemoveList(int id){
        if(tempVending.itemIDExists(id))
        {
            tempVending.removeList.remove((Integer) id);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Database methods
    //------------------------------------------------------------------------------------------------------------------
    private void printItemDB() throws IOException {
        tempVending.printItemDB();
    }
    private void printPositionDB() throws IOException {
        tempVending.printInventoryForCustomer();
    }
    private void addToItemDB(String stringIn)
    {
        itemsToAddToDB.add(stringIn);
    }



    private void writeToItemCsv(ArrayList<String> array)
    {
        FileWriter fileWriter;
        try {
            File csvExportFile = new File("src\\Items.csv");
            fileWriter = new FileWriter(csvExportFile,true);
            for(String input : array)
            {
                fileWriter.append("\n");
                fileWriter.append(input);
            }
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error with writing to the item database");
        }
    }

    private void removeItemInDB(String target) throws IOException {
        ArrayList<String> newFile = new ArrayList<String>();
        FileWriter fileWriter;
        BufferedReader csvReader = new BufferedReader(new FileReader("src\\Items.csv"));
        String line;
        while((line = csvReader.readLine())!= null)
        {
            if(!line.equals(target))
            {
                newFile.add(line);
            }
        }
        try
        {
            File csvExportFile = new File("src\\Items.csv");
            fileWriter = new FileWriter(csvExportFile);
            {
                for(String input : newFile)
                {
                    fileWriter.write(input);
                    fileWriter.write("\n");
                }
            }
            fileWriter.close();
        } catch(IOException e)
        {
            System.out.println("Error when removing Item from DB");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //General methods
    //------------------------------------------------------------------------------------------------------------------
    private void getCurrentCount()
    {
        System.out.println("Current items in vending machine: "  + tempVending.getInventoryCount());
    }
    private void getMaxCount()
    {
        System.out.println("Maximum amount of items that can be in vending machine: " + tempVending.getMaxInventory());
    }
    private void getDepth()
    {
        System.out.println("Depth: " + tempVending.getDepth());
    }
    private void getColumnCount()
    {
        System.out.println("Column count: " + tempVending.getColumnSize());
    }
    private void getRowCount()
    {
        System.out.println("Row count: " + tempVending.getRowSize());
    }
    //------------------------------------------------------------------------------------------------------------------
    //Location methods
    //------------------------------------------------------------------------------------------------------------------
    private void getLocation(VendingMachine tempVend) {
        System.out.println(tempVend.getLocation());
    }

    private void setLocation(VendingMachine tempVend, String location) {
        tempVend.setLocation(location);
        System.out.println("Location set to: " + location);
    }

    private void printList()
    {
        for(String a : itemsToAddToDB)
        {
            System.out.println(a);
        }
    }



}

