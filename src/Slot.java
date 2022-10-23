import java.util.ArrayList;

public class Slot {
    String location;
    int currentCount;
    int depth;
    ArrayList<Item> items = new ArrayList<Item>();

    public Slot(int depth)
    {
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

    public void setCurrentCount(int count)
    {
        this.currentCount = count;
    }


    //Getters
    public String getlocation()
    {
        return location;
    }
    public int getDepth()
    {
        return depth;
    }
    public int getCurrentCount()
    {
        return currentCount;
    }
}
