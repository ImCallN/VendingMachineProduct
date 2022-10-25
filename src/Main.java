import java.text.ParseException;

//Main Class that will run everything
public class Main {
    public static void main(String [] args) throws ParseException
    {
        //System.out.println("Welcome to xyz Vending Machine Software Product! Are you a customer, restocker, or management?");
            //Take input
        //If customer, show customer operations
        //If restocker, show restocker operations
        //Management, show management operations
        //To Exit, type Exit



        //Testing Stuff
        VendingMachine a = new VendingMachine(5,4,5);
        Item b = new Item("100", 1.99);
        Item d = new Item("40A", 2.99);
        Item f = new Item("Chips", "AB100", 4.99,"03-15-2023");
        Item g = new Item("Cookies", "AB412", 1.99,"03-15-1990");


        f.setRemove(true);

        Slot q = new Slot(f);
        Slot h = new Slot(g);
    
        a.inventory[1][2] = q;
        a.inventory[0][0] = h;
        
        
    }
}
