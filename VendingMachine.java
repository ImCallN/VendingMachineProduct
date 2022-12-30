import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;


public class VendingMachine {
    //Class Variables
    String location;                                                    //Geographical location of the vending machine
    int columnSize;                                                     //amount of columns in vending machine
    int rowSize;                                                        //amount of rows in vending machine
    int depth;                                                          //depth of each slot in vending machine
    ArrayList<Integer> removeList = new ArrayList<Integer>();           //List of items that are expired or need to be removed
    ArrayList<String> addItem = new ArrayList<String>();                //List of items that need to be added to the vending machine
    ArrayList<Item> itemList = new ArrayList<Item>();                   //An arraylist containing information on the items inside our database/vending machine
    Slot[][] inventory;                                                 //The 2D array representing our data


    //Constructors
    //------------------------------------------------------------------------------------------------------------------
    public VendingMachine(int row, int column, int depth) {
        setRowSize(row);
        setColumnSize(column);
        setDepth(depth);
        inventory = new Slot[row][column];
        populateRemoveList();
    }

    public VendingMachine(VendingMachine vendingMachine) {
        setLocation(vendingMachine.getLocation());
        setColumnSize(vendingMachine.getColumnSize());
        setRowSize(vendingMachine.getRowSize());
        setDepth(vendingMachine.getDepth());
        setInventory(vendingMachine.getInventory());
        addItem = vendingMachine.addItem;
        itemList = vendingMachine.itemList;
        populateRemoveList();
    }

    //Getters
    //------------------------------------------------------------------------------------------------------------------
    //Returns the rowSize of the vending Machine
    public int getRowSize() {
        return rowSize;
    }

    //Returns the ColumnSize of the vending machine
    public int getColumnSize() {
        return columnSize;
    }

    //Returns the location of the vending machine
    public String getLocation() {
        return location;
    }

    //Returns the depth of the vending machine
    public int getDepth() {
        return depth;
    }

    //Returns max amount of items that can be in a vending machine
    public int getMaxInventory() {
        return getRowSize() * getColumnSize() * getDepth();
    }

    public int getInventory2D() {
        return getRowSize() * getColumnSize();
    }

    //Returns sum of items in vending machine
    public int getInventoryCount() {
        return inventoryCount();
    }

    //Returns a slot object from position integers
    public Slot getSlot(int rowPos, int colPos) {
        if (withinBounds(rowPos, colPos)) {
            return inventory[rowPos][colPos];
        }
        return null;
    }

    //Method to return the price of an object in the itemList arrayList
    public double getPrice(int id)
    {
        double price = 0; 
        for(Item a : itemList)
        {
            if(a.getID() == id)
            {
                price = a.getPrice();
            }
        }
        return price;
    }

    //Returns the name of an item in a slot
    public String getItemName(int x, int y)
    {
        int itemID = inventory[x][y].getItemID();
        return Objects.requireNonNull(itemInSlot(itemID)).getName();
    }

    //Returns available inventory in vending machine
    public int getInventoryAvailable() {
        return getMaxInventory() - getInventoryCount();
    }

    public ArrayList<Integer> getRemoveList() {
        return removeList;
    }

    public ArrayList<String> getAddItems() {
        return addItem;
    }

    public Slot[][] getInventory() {
        return inventory;
    }

    //Setters
    //------------------------------------------------------------------------------------------------------------------
    //Calculates all of the items in the vending machine


    public void setSlot(int x, int y, Slot slot)
    {
        inventory[x][y] = slot;
    }
    public void setVendingMachine(VendingMachine vending)
    {
        setLocation(vending.getLocation());
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setInventory(Slot[][] inventory) {
        this.inventory = inventory;
    }


    //Methods
    //------------------------------------------------------------------------------------------------------------------


    //PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------
    //Get amount of items in the vending machine currently
    public int inventoryCount() {
        int count = 0;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if(inventory[i][j] != null)
                {
                    count += inventory[i][j].currentCount;
                }
            }
        }
        return count;
    }

    //Creates a remove list for restockers
    public void populateRemoveList() {
        for (Item a : itemList) {
            if (a.isExpired() || a.remove) {
                removeList.add(a.getID());
            }
        }
    }


    //Will go through the removeList and clear all slots that contain item_ids that are also contained in the remove list
    public void initRemove()
    {
        for(int a : removeList)
        {
            clearSlotsWithID(a);
        }
    }

    //Adds a specified amount of specified items to a slot. Basically overwrites a slot with 
    public void addItem(int item_id, int rowPos, int colPos, int amount) {

        if (withinBounds(rowPos, colPos)) {
            if (itemIDExists(item_id)) {
                if (slotExists(rowPos,colPos) && getSlot(rowPos, colPos).itemID == item_id) {
                    getSlot(rowPos, colPos).addAmount(amount);
                }
                Slot newSlot = new Slot(item_id, depth, amount);
                inventory[rowPos][colPos] = newSlot;
            }
        }
    }

    //Adds a string containing item id, location, and amount into the add item list
    public void inputToAddList(int id, int xPos, int yPos, int amount)
    {
        if(itemIDExists(id))
        {
            addItem.add(id + "," + xPos + "," + yPos + "," + amount);
        }
    }


    //------------------------------------------------------------------------------------------------------------------

    //PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------

    //Returns the name of an item from the item id
    private String getNameFromID(int id)
    {
        String name = null;
        for(Item a : itemList)
        {
            if(a.getID() == id)
            {
                name = a.getName();
            }
        }
        return name;
    }

    //Returns true or false if a slot object exists at that position
    private boolean slotExists(int xPos,int yPos)
    {
        return inventory[xPos][yPos] != null;
    }

    //Clears the slots that contain the item id.
    private void clearSlotsWithID(int id)
    {
        ArrayList<String> positions = getPositions(id);
        for(String s : positions) {
            inventory[getInfoFromString(s,0)][getInfoFromString(s,1)] = null;
        }
    }

    //Checks to make sure that the item id exists in our list of items. 
    public boolean itemIDExists(int item_id) {
        for (Item a : itemList) {
            if (a.getID() == item_id) {
                return true;
            }
        }
        return false;
    }

    //Checks to make sure that the given position is within the bounds of the inventory[][]
    private boolean withinBounds(int rowPos, int colPos) {
        return rowPos <= rowSize && colPos <= columnSize && rowPos >= 0 && colPos >= 0;
    }

    //Loop through item list and return the item object that matches the item_id
    private Item itemInSlot(int item_id)
    {
        for(Item a : itemList)
        {
            if(a.getID() == item_id) return a;
        }
        return null;
    }

    //grabs the integer value from the string delimited by commas
    private int getInfoFromString(String stringIn, int stringPos)
    {
        String[] splitString;
        splitString = stringIn.split(",");
        if(stringPos < splitString.length && stringPos > -1)
        {
            return Integer.parseInt(splitString[stringPos]);
        }
        return 0;
    }

    //Returns an arrayList of positions that contain the item_id
    private ArrayList<String> getPositions(int item_id)
    {
        ArrayList<String> positions = new ArrayList<String>();

        //loop through main inventory
        for(int i = 0; i < inventory.length; i ++)
        {
            for(int j = 0; j < inventory[1].length; j++)
            {

                if(inventory[i][j] != null && inventory[i][j].getItemID() == item_id)
                {
                    positions.add(i + "," + j);
                }
            }
        }
        return positions;
    }



    //Will update the addItem List by subtracting information. If the amount in the last part of the input
    //matches the amount in the last part of the addItem list, then remove the element in the list.
    //Else if the (last part of s) - (last part of stringIn) > 0, change the last part of s on the add list

    //POSSIBLE REMOVAL OF THIS METHOD HERE

    /*
    public void removeItemOrAmountFromAddItemList(String stringIn)
    {
        for(String s : addItem) {
            int sameIDPos = 0;
            //verification of info in the addItem list from the input. We need to check the first three, the last
            //digit is variable.
            for (int i = 0; i < 3; i++) {
                if (getInfoFromString(s, i) == getInfoFromString(stringIn, i)) {
                    sameIDPos++;
                }
            }
            //If the same
            if (sameIDPos == 3 && getInfoFromString(stringIn,3) >= 0)
            {
                int remainder = getInfoFromString(s,3) - getInfoFromString(stringIn,3);
                if( remainder == 0)
                {
                    addItem.remove(s);
                }
                else if(remainder > 0 && remainder <= depth)
                {
                    String replacement = getInfoFromString(s,0) + "," + getInfoFromString(s,1) + "," + getInfoFromString(s,2) + "," + remainder;
                    addItem.set(addItem.indexOf(s),replacement);
                }
            }
        }
    }

     */

    //------------------------------------------------------------------------------------------------------------------
    //PRINT METHODS
    //------------------------------------------------------------------------------------------------------------------
    public void printSlotsInInventory()
    {
        for (Slot[] slots : inventory) {
            for (int j = 0; j < inventory[1].length; j++) {
                if (slots[j] != null) {
                    Slot tempSlot = slots[j];
                    System.out.println("[Item ID: " + tempSlot.getItemID() + ", Current Count: " + tempSlot.getCurrentCount() + "]");
                }
            }
        }
        System.out.println();
    }


    //Prints out the position of the occupied slots, the 
    public void printInventoryForCustomer()
    {
        for(int i = 0; i < inventory.length; i++)
        {
            for(int j = 0; j < inventory[1].length; j++)
            {
                if(inventory[i][j] != null)
                {
                    Slot tempSlot = inventory[i][j];
                    Item item = itemInSlot(tempSlot.getItemID());;
                    assert item != null;
                    System.out.println("Position: (" + i + "," + j + ") Name: " + item.getName() + " Price: " + item.getPrice() + " Current Amount in Inventory: " + tempSlot.getCurrentCount());
                    
                }
            }
        }
    }


    //Prints out the strings in the addItem List
    public void printAddItemList()
    {
        if(addItem != null)
        {
            System.out.println("The format goes in 'item_id, row position, column position, amount'");
            for(String a : addItem)
            {
                System.out.println(a);
            }
        }
    }


    //Prints out the removeItemList
    public void printRemoveItemList()
    {
        System.out.println("REMOVE LIST:");
        for(Integer a : removeList)
        {
            System.out.println("Item Name: " + getNameFromID(a));
            ArrayList<String> positions = getPositions(a);
            System.out.println("    Positions: ");
            for(String b : positions)
            {
                System.out.println(b);
            }
        }
    }



    //prints the item database
    private void printDB(String filename) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(filename));
        String currentLine;
        while ((currentLine = csvReader.readLine()) != null)
        {
            System.out.println(currentLine);
        }
    }
    public void printPositionDB() throws IOException
    {
        printDB("src\\Positions.csv");
    }

    public void printItemDB() throws IOException {
        printDB("src\\Items.csv");
    }
    //------------------------------------------------------------------------------------------------------------------


    //CSV OPERATIONS
    //------------------------------------------------------------------------------------------------------------------


    //Counts the number of lines in the csv file
    private int readLineCount(String filename) throws IOException
    {
        int positionCounter = 0;
        BufferedReader csvReader = new BufferedReader(new FileReader(filename));
        while((csvReader.readLine()) != null)
        {
            positionCounter++;
        }
        csvReader.close();
        return positionCounter;
    }

    //Imports csv file and populates either the slots/positions 2D array or the itemList arrayList based on
    //whether the file name contains "slots"/"positions" or "items".
    public void importCSV(String filename) throws IOException, ParseException {
        BufferedReader csvReader = new BufferedReader(new FileReader(filename));
        String line;

        int positionCounter = readLineCount(filename);
        while ((line = csvReader.readLine()) != null) {
            String[] data = line.split(",");
            if (filename.toString().toLowerCase().contains("item"))                                              //Checks if this is the items csv or not
            {
                Item temp = new Item(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), data[3]);
                itemList.add(temp);
            } 
            else if (filename.toString().toLowerCase().contains("position")) {

                //Make sure that CSV information will fit into the 2D array
                if (positionCounter > getInventory2D()) {
                    System.out.println("Too many items in DB, cannot insert this to Vending Machine");
                } 
                else 
                {
                    Slot tempSlot = new Slot(Integer.parseInt(data[0]), getDepth(), Integer.parseInt(data[3]));
                    inventory[Integer.parseInt(data[1])][Integer.parseInt(data[2])] = tempSlot;
                }
            }
            else {
                System.out.println("We're sorry, but we cannot confidently read this file.");
                System.out.println("Please change the file name to contain 'position' or 'item'.");
            }
        }
        csvReader.close();
    }

    //Exports information to a csv file based on information passed along. This info will update either the
    //slots/positions csv file or the item
    void exportPositionCSV(String filename) {
        FileWriter fileWriter;
        try {
            File csvExportFile = new File(filename);

            fileWriter = new FileWriter(csvExportFile);

            for (int i = 0; i < inventory.length; i++) {
                
                for (int j = 0; j < inventory[1].length; j++) {
                    
                    if(inventory[i][j] != null) {
                        String line = String.valueOf(inventory[i][j].getItemID()) +
                                ',' +
                                i +
                                ',' +
                                j +
                                ',' +
                                inventory[i][j].getCurrentCount();
                        fileWriter.write(line);
                        fileWriter.write('\n');
                    }
                }
            }
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error in VendingMachine.exportCSV: Issue with the file name or pathing.");
        }
    }
}

    //------------------------------------------------------------------------------------------------------------------
