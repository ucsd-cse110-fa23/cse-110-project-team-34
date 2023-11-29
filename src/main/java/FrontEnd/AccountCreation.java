package FrontEnd;

import java.util.*;

public class AccountCreation {
    Scanner scans = new Scanner(System.in);
    String username;
    String password;

    public void addUserName() {

        System.out.println("Enter your username");
        username = scans.nextLine();
        if (userNameTaken(username) == true) {
            System.out.println("User name taken, try again");
            addUserName();
        } else {
            addPassword();
        }

    }

    public void addPassword() {
        System.out.println("Enter your password");
        password = scans.nextLine();
        

    }

    public boolean userNameTaken(String attempt) {
        // needs to be completed
        return false;
    }

}