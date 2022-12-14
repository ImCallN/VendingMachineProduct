import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Item {
    //Vars
    String id;
    String name;
    Double price;
    Date expirationDate;
    Boolean remove;

    //Constructors
    public Item(String id, Double price)
    {
        this.id = id;
        this.price = price;
        remove = false;
    }

    //Creates an item with all vars. ExpirationDateString needs to be in "dd-mm-yyyy" format
    public Item(String name, String id, Double price, String expirationDateString) throws ParseException
    {
        this.name = name;
        this.id = id;
        this.price = price;
        expirationDate = setExpirationDate(expirationDateString);
        remove = false;
    }

    public Item() {}

    public Item(String name, String id, double price)
    {
        this.name = name;
        this.id = id;
        this.price = price;
        remove = false;
    }

    public Item(Item item)
    {
        this.name = item.getName();
        this.id = item.getID();
        this.price = item.getPrice();
        this.expirationDate = item.getExpirationDate();
        this.remove = item.getRemove();
    }

    //Getters
    public String getID()
    {
        return id;
    }
    public Double getPrice()
    {
        return price;
    }
    public Date getExpirationDate()
    {
        return expirationDate;
    }
    public Boolean getRemove()
    {
        return remove;
    }
    public String getName()
    {
        return name;
    }
   

    //Setters
    public void setID(String id)
    {
        this.id = id;
    }
    public void setPrice(Double price)
    {
        this.price = price;
    }
    public void setExpirationDate(Date expirationDate)
    {
        this.expirationDate = expirationDate;
    }
    //Converts the expirationDateString to a date
    public Date setExpirationDate(String expirationDateString) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        Date tempDate = sdf.parse(expirationDateString);
        return tempDate;
    }
    public void setRemove(Boolean expired)
    {
        this.remove = expired;
    }
    public void setName(String name)
    {
        this.name = name;
    }
 
    public boolean isExpired()
    {
        Date tempDate = new Date();
        if(expirationDate.getTime() - tempDate.getTime() <= 0)
            return true;
        return false;
    }
}
