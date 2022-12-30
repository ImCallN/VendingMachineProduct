import java.util.ArrayList;

public class Slot {

    //Global Variables
    int itemID;                                             //Identifying integer to match to item database
    int currentCount;                                       //Current count of the items in the slot
    int depth;                                              //The size of the slot. According to docs, slots are typically 15 units long. This can also be set by the vending machine object
    int standardDepth = 15;                                 //Standard depth of a vending machine slot

    //Constructors
    //-------------------------------------------------------------------------------------------
    //A default slot object with a depth of 15
    public Slot()
    {
        setItemID(0);
        setCurrentCount(0);
        setDepth(standardDepth);
    }
    
    //populates a default slot of depth 15 with an item
    public Slot(Slot slot)
    {
        setItemID(slot.getItemID());
        setCurrentCount(slot.getCurrentCount());
        setDepth(slot.getDepth());
    }
    public Slot(int itemID)
    {
        setItemID(itemID);
        setCurrentCount(standardDepth);
        setDepth(standardDepth);
    }
    public Slot(int itemID, int depth)
    {
        setItemID(itemID);
        setCurrentCount(depth);
        setDepth(depth);
    }
    public Slot(int itemID, int depth, int currentCount)
    {
        setItemID(itemID);
        setDepth(depth);
        setCurrentCount(currentCount);
    }
    
    //Setters
    //------------------------------------------------------------------------------------

    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    public void setCurrentCount(int count) {this.currentCount = count;}

    public void setItemID(int itemID) {this.itemID = itemID; }

    //Getters
    //------------------------------------------------------------------------------------
    public int getDepth()
    {
        return depth;
    }
    public int getItemID(){return itemID; }
    public int getCurrentCount() { return currentCount; }
    //Methods
    //-------------------------------------------------------------------------------------
    public void removeItem(int amount)
    {
        if(amount <= currentCount)
        {
            currentCount -= amount;
        }
        else{System.out.println("You are trying to remove more items than what is in the slot");}
    }

    public void addAmount(int amount)
    {
        if(amount > 0 && (amount + currentCount) < depth)
        {
            currentCount += amount;
        }
        else {System.out.println("You are trying to add more items than what the machine can hold in one slot");}
    }
}
