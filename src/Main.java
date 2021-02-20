/*
 * Main program for Yarn Management System
 * @author Ella Cronk
 * @author Zackary Wake
 * @author Nate Appelby
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User currentUser = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello! Would you like to log in or sign up?");
        String input = scan.nextLine();
        int i = 0;
        while(i == 0){
            if (input.equals("log in")) {
                System.out.println("\nEnter Username: ");
                String username = scan.nextLine();
                System.out.println("\nEnter Password: ");
                String password = scan.nextLine();
                boolean loginSuccessful = false;
                try{
                    loginSuccessful = Database.loginUser(username, password);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(loginSuccessful){
                    System.out.println("You have Successfully Logged In!");
                    try {
                        currentUser = Database.getUser(username);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                else {
                    System.out.println("Invalid Credentials!");
                }

            } else if (input.equals("sign up")) {
                System.out.println("\nEnter Username:");
                String username = scan.nextLine();
                System.out.println("\nEnter Password:");
                String password = scan.nextLine();
                System.out.println("\nEnter Preferred Nickname:");
                String nickname = scan.nextLine();
                try {
                    if (Database.createUser(username, password, nickname)) {
                        currentUser = Database.getUser(username);
                        System.out.println("User Registered Successfully!");
                    } else {
                        System.out.println("User Already Exists!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            } else {
                System.out.println("I did not understand that. Please type \"log in\" or \"sign up\"");
                input = scan.nextLine();
            }
        }
        i = 0;

        //TODO if login successful
        assert currentUser != null;
        InputHandler inputHandler = new InputHandler();
        inputHandler.mainMenu(currentUser);
    }
}
