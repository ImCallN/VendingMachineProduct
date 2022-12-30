import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Item {
    //Class variables
    //------------------------------------------------------------------------------------------------------------------
    int id;                 //The integer used to identify a product. Also known as a primary key in a DB.
    String name;            //Official name of the item product.
    Double price;           //The price of the item.
    Date expirationDate;    //The expiration date of an item in Date format.
    Boolean remove;         //The boolean that will help determine if an item needs to be removed (expiration or recall)
    //------------------------------------------------------------------------------------------------------------------

    //Constructors
    //------------------------------------------------------------------------------------------------------------------
    //Creates an item with all vars. ExpirationDateString needs to be in "MM/dd/yyyy" format
    public Item(int id, String name, Double price, String expirationDateString) throws ParseException
    {
        setName(name);
        setID(id);
        setPrice(price);
        setExpirationDate(expirationDateString);
        setRemove(false);
    }

    //Creates a copy of an item object
    public Item(Item item)
    {
        setName(item.getName());
        setID(item.getID());
        setPrice(item.getPrice());
        setExpirationDate(item.getExpirationDate());
        setRemove(item.getRemove());
    }
    //------------------------------------------------------------------------------------------------------------------

    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public int getID()
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
    //-------------------------------------------------------------------------------------------------------------------

    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setID(int id)
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
    public void setExpirationDate(String expirationDateString) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date tempDate = sdf.parse(expirationDateString);
        this.expirationDate = tempDate;
    }
    public void setRemove(Boolean expired)
    {
        this.remove = expired;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    //------------------------------------------------------------------------------------------------------------------

    //Methods
    //------------------------------------------------------------------------------------------------------------------
    public boolean isExpired()
    {
        Date tempDate = new Date();
        if(expirationDate.getTime() - tempDate.getTime() <= 0)
            return true;
        return false;
    }
}
