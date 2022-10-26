import java.util.ArrayList;


public class VendingMachine
{
    //Class Vars
    String location;                                            //Geo location of the vending machine
    int columnSize;                                             //amount of columns in vending machine
    int rowSize;                                                //amount of rows in vending machine
    int depth;                                                  //depth of each slot in vending machine
    ArrayList<Item> removeList = new ArrayList<Item>();         //List of items that are expired or need to be removed
    ArrayList<Item> addItem = new ArrayList<Item>();            //List of items that need to be added to the vending machine
    ArrayList<String> expiredSlots = new ArrayList<String>();   //List of slots that contain expired items
    Slot inventory[][];                                         //The 2D array representing our data

    //Constructors
    //-------------------------------------------------------------------------------------------------------------------------
    public VendingMachine(int row, int column, int depth)
    {
        //2D array with height = row and width = column of slot objects(constructor with depth)
        rowSize = row;
        columnSize = column;
        this.depth = depth;
        inventory = new Slot[row][column];
        standardPopulateInventory();
    }

    public VendingMachine(VendingMachine vendingMachine)
    {
        this.location = vendingMachine.getLocation();
        this.columnSize = vendingMachine.getColumnSize();
        this.rowSize = vendingMachine.getRowSize();
        this.depth = vendingMachine.getDepth();

    }

    //public custSelect(List)
    //public calculateTotal(double)
    //public heartbeat

    //Getters
    //---------------------------------------------------------------------------------------------------------------------------
    //Returns the rowSize of the vending Machine
    public int getRowSize()
    {
        return rowSize;
    }

    //Returns the ColumnSize of the vending machine
    public int getColumnSize()
    {
        return columnSize;
    }

    //Returns the location of the vending machine
    public String getLocation()
    {
        return location;
    }

    //Returns the depth of the vending machine
    public int getDepth()
    {
        return depth;
    }

    //Returns max amount of items that can be in a vending machine
    public int getMaxInventory()
    {
        return getRowSize() * getColumnSize() * getDepth();
    }

    //Returns sum of items in vending machine
    public int getInventoryCount()
    {
       return setInventoryCount();
    }

    //Returns available inventory in vending machine
    public int getInventoryAvailable()
    {
        return getMaxInventory() - getInventoryCount();
    }

    public ArrayList<Item> getRemoveList()
    {
        return removeList;
    }
   
    public ArrayList<Item> getAddItems()
    {
        return addItem;
    }

    public ArrayList<String> getExpiredSlots()
    {
        return expiredSlots;
    }

    public Slot[][] getInventory()
    {
        return inventory;
    }

    //Setters
    //---------------------------------------------------------------------------------------------------------------------------
    //Calculates all of the items in the vending machine
    public int setInventoryCount()
    {
        int count = 0;
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < columnSize; j++)
            {
                count += inventory[i][j].currentCount;
            }
        }
        return count;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    public void setColumnSize(int columnSize)
    {
        this.columnSize = columnSize;
    }
    public void setRowSize(int rowSize)
    {
        this.rowSize = rowSize;
    }
    public void setDepth(int depth)
    {
        this.depth = depth;
    }


    //Methods
    //----------------------------------------------------------------------------------------------------------------------------
    //Populates a vending machine with empty slot items with a depth of depth
    public void standardPopulateInventory()
    {
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < columnSize; j++)
            {
                inventory[i][j] = new Slot(depth);
                inventory[i][j].setLocation(i + " " + j);
            }
        }
    }

    //Creates a remove list for resotockers
    public void populateRemoveList()
    {
        //Get current date
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < columnSize; j++)
            {
                if(inventory[i][j] != null)
                {
                    System.out.println("On slot : " + i + " " + j);
                    removeList.addAll(inventory[i][j].getRemoveList());
                    if(inventory[i][j].hasExpired)
                        expiredSlots.add(i + " " + j);
                }
            }
        }
    }

    //Print Functions
    //-----------------------------------------------------------------------------------
    //Prints the name of items that need to be removed along with their ids
    public void printRemoveList()
    {
        for(Item i : removeList)
        {
            System.out.println("Expired: " + i.id + " " + i.name);
        }
    }

    //Prints the slots that contain expired items
    public void printRemoveSlots()
    {
        for(String s : expiredSlots)
        {
            System.out.println("Expired Slots: " + s);
        }
    }

}
