import java.util.ArrayList;

public class Slot {

    //Global Variables
    String location;                                        //Location of the slot in the vending machine, used like an identifier
    int currentCount;                                       //Current count of the items in the slot
    int depth;                                              //The size of the slot. According to docs, slots are typically 15 units long. This can also be set by the vending machine object
    Item items[];                                           //Or vice versa to above, a static array
    Boolean hasExpired = false;

    //Constructors
    //-------------------------------------------------------------------------------------------
    //A default slot object with a depth of 15
    public Slot()
    {
        location = null;
        currentCount = 0;
        depth = 15;
        items = new Item[depth];
    }
    
    //populates a default slot of depth 15 with an item
    public Slot(Item item)
    {
        location = null;
        currentCount = 15;
        depth = 15;
        items = new Item[depth];
        addItemtoEmptySlot(item, currentCount);
    }
    //Constructor that takes all the class variables as arguments and populates a slot object with the item specified
    public Slot(String location, int count, int depth, Item item)
    {
        this.location = location;
        this.currentCount = count;
        this.depth = depth;
        items = new Item[depth];
        addItemtoEmptySlot(item, count);
    }
    

    //Slot Item that only takes the depth as an argument
    public Slot(int depth)
    {
        this.depth = depth;
        items = new Item[depth];
        this.location = null;
        this.currentCount = 0;

    }

    public Slot(Slot slot)
    {
        this.location = slot.getlocation();
        //this.currentCount = slot.getCurrentCount();
        this.depth = slot.getDepth();
        //this.items = slot.getItems();
        this.items = slot.items;

    }
    
    //Constructor of an empty slot with a location and another slot item
    public Slot(String location, Slot slot)
    {
        this.location = location;
        this.depth = slot.depth;
        this.items = new Item[depth];
    

    }

    //Constructor of a slot with a location and depth
    public Slot(String location, int depth)
    {
        this.location = location;
        this.depth = depth;
        this.currentCount = 0;
        items = new Item[depth];
    }

    
    //Setters
    //------------------------------------------------------------------------------------
    public void setLocation(String location)
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
    public void setHasExpired(Boolean bool)
    {
        this.hasExpired = bool;
    }
 

    //Getters
       //------------------------------------------------------------------------------------
    public String getlocation()
    {
        return location;
    }
    public int getDepth()
    {
        return depth;
    }
    public Item[] getItems()
    {
        return items;
    }
    public Boolean getHasExpired()
    {
        return hasExpired;
    }


    //Methods
    //-------------------------------------------------------------------------------------

    //Method that will add an amount of items into the item array, can only be used when the slot is empty
    public void addItemtoEmptySlot(Item item, int amount)
    {
        for(int i = 0; i < amount; i++)
        {
            items[i] = item; 
        }
    }

    //Method that will add a count of items based on a starting position.
    public void addItem(Item item, int count, int start)
    {
        if (start >= 0 && (start + count) <= depth)
        {
            for(int i = start; i < start + count; i++)
            {
                items[i] = item;
            }
        }
    }

    //Method to be run after a customer has purchased an item. This will move
    //all of the items in a slot "quantity" spaces forward. Purchase method
    //needs to make sure that the customer is not buying too many items or items
    //that don't exist.
    public void moveItemsForward(int quantity)
    {
        for(int i = 0; i < depth - quantity; i++)
        {
            items[i] = items[i + quantity];
        }
        this.currentCount = currentCount - quantity;
    }


    //Returns an arraylist of items that have the expired boolean
    public ArrayList<Item> getRemoveList()
    {
        ArrayList<Item> temp = new ArrayList<Item>();
        for(Item a : items)
        {
            if(a != null) //Make sure there is an item there to check
            {
                if(a.isExpired())
                {
                    a.setRemove(true);
                    setHasExpired(true);
                    temp.add(a);
                }
            }
        }
        return temp;
    }
}
