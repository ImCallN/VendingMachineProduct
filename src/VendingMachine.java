import java.util.ArrayList;


public class VendingMachine
{
    String location;
    int columnSize;
    int rowSize;
    int depth;
    int maxInventory;
    int inventoryAvailable;
    ArrayList<Item> removeList = new ArrayList<Item>();
    ArrayList<Item> addItem = new ArrayList<Item>();
    Slot inventory[][];

    public VendingMachine(int row, int column, int depth)
    {
        //2D array with height = row and width = column of slot objects(constructor with depth)
         inventory = new Slot[row][column];
         standardPopulateInventory();
    }

    //public custSelect(List)
    //public calculateTotal(double)
    //public heartbeat
    public void standardPopulateInventory()
    {
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < columnSize; j++)
            {
                inventory[i][j] = new Slot(depth);
            }
        }
    }


    public void populateRemoveList()
    {
        //Get current date
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < columnSize; j++)
            {
                //Call slot function to check expiration dates on items. 
                //The question is do we return the slot for expired items or do we return all of the individual items?
                
            }
        }
    }
}
