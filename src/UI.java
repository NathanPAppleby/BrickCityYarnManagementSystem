import java.util.Scanner;

/* Class for the UI output, routes input to correct classes
 * @author Ella Cronk
 * @author Zackary Wake
 * @author Nate Appleby
 */
public class UI {
    public static void main(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello! Would you like to log in or sign up?");
        String input = scan.nextLine();
        int i = 0;
        while(i == 0){
            if (input.equals("log in")) {
                //TODO USE LOG IN
                i++;
            } else if (input.equals("sign up")) {
                //TODO USE SIGN UP
                i++;
            } else {
                System.out.println("I did not understand that. Please type \"log in\" or \"sign up\"");
            }
        }
        i = 0;

        //TODO if login successful
        System.out.println("Hello PLACEHOLDER! Please select a menu option by typing the number \n next to the option to be used!" );
        System.out.println("(1) Check Yarn");
        System.out.println("(2) Check Projects");
        System.out.println("(3) Check Shopping Cart");
        System.out.println("(4) Log out and Exit");
        int menuChoice = scan.nextInt();
        while(i == 0){
            if(menuChoice == 1){
                //TODO LIST YARN NAMES IN DATABASE, ADD OPTIONS FOR MORE DETAILS, ADDING, OR DELETING, AND BACK TO MENU
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
