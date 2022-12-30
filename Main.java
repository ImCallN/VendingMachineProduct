import java.io.IOException;
import java.text.ParseException;
import java.util.*;
//Main Class that will run everything
public class Main {
    public static void main(String [] args) throws IOException, ParseException {
        String itemDBPath = "src\\Items.csv";
        String positionDBPath = "src\\Positions.csv";

        //TESTING
        //------------------------------------------------------------------------------------------------------
        VendingMachine testVendingMachine = new VendingMachine(10,10,10);
        testVendingMachine.importCSV(itemDBPath);
        testVendingMachine.importCSV(positionDBPath);
        testVendingMachine.populateRemoveList();
        //------------------------------------------------------------------------------------------------------
        //Main Loop
        Boolean main_loop = true;
        while(main_loop)
        {
            Scanner readIn = new Scanner(System.in);
            System.out.println("Please type 'M' for Management, 'C' for Customer, 'R' for Restocker, or 'Exit' to exit");
            String input = readIn.nextLine();

            switch (input.toLowerCase()) {
                case "m", "management" -> {
                    UserVerification login = new UserVerification();
                    if(login.isAuthorized())
                    {
                        System.out.println("Welcome!");
                        Management managerInterface = new Management(testVendingMachine);
                        testVendingMachine = managerInterface.getVending();
                    }
                    else
                    {
                        System.out.println("Incorrect Username or Password");
                    }

                }
                case "c", "customer" -> {
                    Customer customerInterface = new Customer(testVendingMachine);
                    testVendingMachine = customerInterface.getVendingMachine();
                    testVendingMachine.exportPositionCSV("src\\Positions.csv");
                }
                case "r", "restocker" -> {
                    UserVerification restockLogin = new UserVerification();
                    if(restockLogin.isAuthorized())
                    {
                        System.out.println("Welcome!");
                        Restocker restockerInterface = new Restocker(testVendingMachine);
                        testVendingMachine = restockerInterface.getVendingMachine();
                        testVendingMachine.exportPositionCSV("src\\Positions.csv");
                    }
                    else
                    {
                        System.out.println("Incorrect Username or Password");
                    }
                }
                case "exit" -> {
                    System.out.println("Goodbye");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                    main_loop = false;
                }
            }
        }
    }
}