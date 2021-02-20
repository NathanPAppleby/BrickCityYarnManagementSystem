/*
 * Main program for Yarn Management System
 * @author Ella Cronk
 * @author Zackary Wake
 * @author Nate Appelby
 */

import java.util.Scanner;
import Database.Database;

public class Main {
    public static void main() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello! Would you like to log in or sign up?");
        String input = scan.nextLine();
        int i = 0;
        while(i == 0){
            if (input.equals("log in")) {
                //TODO USE LOG IN
                i++;
            } else if (input.equals("sign up")) {
                System.out.println("\nEnter Username:\n");
                String username = scan.nextLine();
                System.out.println("\nEnter Password:\n");
                String password = scan.nextLine();
                try {
                    if(Database.createUser(username, password)){
                        System.out.println("User Registered Successfully!");
                    }
                    else{
                        System.out.println("User Already Exists!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            } else {
                System.out.println("I did not understand that. Please type \"log in\" or \"sign up\"");
            }
        }
        i = 0;

        //TODO if login successful
        System.out.println("Hello PLACEHOLDER! Welcome to the Brick City Yarn Management System.\n" +
                "Please select a menu option by typing the number next to the option to be used!" );
        System.out.println("(1) Check Yarn");
        System.out.println("(2) Check Projects");
        System.out.println("(3) Check Shopping Cart");
        System.out.println("(4) Log out and Exit");
        int menuChoice = scan.nextInt();
        while(i == 0){
            if(menuChoice == 1){

                i++;
            }
            else if(menuChoice == 2){
                //TODO LIST PROJECTS IN DATABASE, ADD OPTIONS FOR OPENING PROJECTS, MOVING THEM TO ANOTHER STATE, ADDING, OR DELETING, AND BACK TO MENU
                i++;
            }
            else if(menuChoice == 3){
                //TODO LIST PROJECT NAMES IN SHOPPING CART, ADD OPTIONS FOR OPENING PROJECTS TO CART, REMOVING FROM CART INTO ANOTHER QUEUE, AND BACK TO MENU
                i++;
            }
            else if(menuChoice == 4){
                //TODO LOG USER OUT AND QUIT PROGRAM
                i++;
            }
            else{
                System.out.println("I did not understand that. Please enter the number of the menu option you would like.");
            }
        }
    }
}
