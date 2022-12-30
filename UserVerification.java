import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserVerification {
    String username;
    String password;

    boolean authorized = false;
    public UserVerification() throws IOException {
        getInfo();
    }

    //Checks to see if the user is allowed to access the information
    private boolean authorization(String user, String pass) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src\\Users.csv"));
        String line;
        while ((line = csvReader.readLine()) != null) {
            if((user + "," + pass).equals(line))
            {
                return true;
            }
        }
        return false;
    }

    //Gets info from the user
    private void getInfo()
    {
        boolean userLoop = true;
        while(userLoop)
        {
            System.out.println("Please enter your username: ");
            Scanner readIn = new Scanner(System.in);
            username = readIn.nextLine();
            userLoop = false;
        }

        boolean passLoop = true;
        while(passLoop)
        {
            System.out.println("Please enter your password: ");
            Scanner readPass = new Scanner(System.in);
            password = readPass.nextLine();
            passLoop = false;
        }
    }

    public boolean isAuthorized() throws IOException {
        return authorization(username,password);
    }
}



