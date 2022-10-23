import java.util.Date;

public class Item {
    //Vars
    String id;
    Double price;
    Date expirationDate;
    Boolean remove;

    //Constructors
    public Item(String id, Double price)
    {
        this.id = id;
        this.price = price;
    }

    public Item(String id, Double price, Date expirationDate)
    {
        this.id = id;
        this.price = price;
        this.expirationDate = expirationDate;
        remove = false;
    }

    public Item(Item item)
    {
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

    public void setRemove(Boolean remove)
    {
        this.remove = remove;
    }
}
