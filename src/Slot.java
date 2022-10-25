import java.util.ArrayList;

/*There's two ways that we can go about this one.
*One is to use an arraylist for the item list in the slot
*With this, it is easy to add and remove objects from the list
*
*
*/


public class Slot {

    //Global Variables
    String location;                                    //Location of the slot in the vending machine, used like an identifier
    int currentCount;                                   //Current count of the items in the slot
    int depth;                                          //The size of the slot. According to docs, slots are typically 15 units long
    ArrayList<Item> itemsAList = new ArrayList<Item>();      //Arraylist to contain the items that are in the slot
    Item items[];                                           //Or vice versa to above, a static array

    //Constructors

    //A default slot object with a depth of 15
    public Slot()
    {
        location = null;
        currentCount = 0;
        depth = 15;
        items = new Item[depth];
    }
    public Slot(int currentCount, Item item)
    {
        
    }

    public Slot(int depth)
    {
        this.depth = depth;
        items = new Item[depth];
    }

    public Slot(Slot slot)
    {
        this.location = slot.getlocation();
        //this.currentCount = slot.getCurrentCount();
        this.depth = slot.getDepth();
        //this.items = slot.getItems();
        this.items = slot.items;

    }
    
    public Slot(String location)
    {
        this.location = location;
    }

    public Slot(String location, int depth)
    {
        this.location = location;
        this.depth = depth;
    }


    //Setters
    public void setlocation(String location)
    {
        this.location = location;
    }

    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    public void standardDepth()
    {
        depth = 15;
    }

    public void setCurrentCount(int count)
    {
        this.currentCount = count;
    }

    /* 
    public void setCurrentCount()
    {
        currentCount = getCurrentCount();
    }
    */


    //Getters
    public String getlocation()
    {
        return location;
    }
    public int getDepth()
    {
        return depth;
    }

    public void addItem(Item item, int count)
    {
        for(int i = 0; i < count; i++)
        {
            items[i] = item; 
        }
    }

    public void addItem(Item item, int lower, int upper)
    {
        if (lower >= 0 && upper <= depth)
        {
            for(int i = lower; i < upper; i++)
            {
                items[i] = item;
            }
        }
    }

    public void moveItemsForward(int quantity)
    {
        for(int i = 0; i < depth - quantity; i++)
        {
            items[i] = items[i + quantity];
        }
    }

       //TO BE USED WITH ARRAYLIST
    public int getCurrentCount()
    {
        return itemsAList.size();
    }
    

    public ArrayList<Item> getItems()
    {
        return itemsAList;
    }
    //Add items into the arraylist
    public void addItemAList(Item item)
    {
        itemsAList.add(item);
    }
    public void addItemAList(Item item, int quantity)
    {
        for(int i = 0; i < quantity; i++)
        {
            itemsAList.add(item);
        }
    }
    public void removeItemAList(int index)
    {
        itemsAList.remove(index);
    }
     
}
