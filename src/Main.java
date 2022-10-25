//Main Class that will run everything
public class Main {
    public static void main(String [] args)
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

        /* 
        Slot c = new Slot();
        c.addItem(b, 3);
        c.addItem(d,3,7); 
        a.inventory[1][1] = c;
        System.out.println(a.inventory[1][1].items[2].id);
        c.moveItemsForward(1);
        System.out.println(a.inventory[1][1].items[2].id);
        */

        Slot e = new Slot();
        e.addItemAList(b);
        e.addItemAList(d, 3);
        a.inventory[1][2] = e;
        System.out.println("Item in second slot:" + " " + a.inventory[1][2].itemsAList.get(1).id);
        System.out.println("Item in first Slot:" + " " + a.inventory[1][2].itemsAList.get(0).id);

        e.removeItemAList(0);
        System.out.println("Item in first slot after change:" + " " + a.inventory[1][2].itemsAList.get(0).id);

        

    }
}
