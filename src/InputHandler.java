import java.util.Scanner;

public class InputHandler {

    public void mainMenu(User currentUser){
        Scanner scan = new Scanner(System.in);
        System.out.println(String.format("Hello %s! Welcome to the Brick City Yarn Management System.\n" +
                "Please select a menu option by typing the number next to the option to be used!", currentUser.getNickname()));
        System.out.println("(1) Yarn");
        System.out.println("(2) Projects");
        System.out.println("(3) Shopping Cart");
        System.out.println("(4) Log out and Exit");
        int menuChoice = scan.nextInt();
        if (menuChoice == 1) {
            yarnMenu(currentUser);
            mainMenu(currentUser);
        }
        //Project Menu
        else if (menuChoice == 2) {
            //TODO LIST PROJECTS IN DATABASE, ADD OPTIONS FOR OPENING PROJECTS, MOVING THEM TO ANOTHER STATE, ADDING, OR DELETING, AND BACK TO MENU
            mainMenu(currentUser);
        }

        //Shopping Cart Menu
        else if (menuChoice == 3) {
            //TODO LIST PROJECT NAMES IN SHOPPING CART, ADD OPTIONS FOR OPENING PROJECTS TO CART, REMOVING FROM CART INTO ANOTHER QUEUE, AND BACK TO MENU
            mainMenu(currentUser);
        }

        //Logout
        else if(menuChoice == 4){
            System.out.println("Goodbye " + currentUser.getNickname() + "!");
            System.exit(0);
        }
        else{
            System.out.println("I did not understand that. Please enter the number of the menu option you would like.");
            mainMenu(currentUser);
        }

    }

    //YARN MENUS
    public void yarnMenu(User currentUser){
        Scanner scan = new Scanner(System.in);
        System.out.println("Select a menu option for Yarn");
        System.out.println("(1) Check List");
        System.out.println("(2) Add Yarn");
        System.out.println("(3) Remove Yarn");
        System.out.println("(4) Back");
        int choice = scan.nextInt();
        if(choice == 1){
            System.out.println("\n");
            printYarnMenu(currentUser);
            yarnMenu(currentUser);
        }
        else if(choice == 2){
            addYarnMenu(currentUser);
            yarnMenu(currentUser);
        }
        else if(choice == 3){
            removeYarnMenu(currentUser);
            yarnMenu(currentUser);
        }
    }

    public void printYarnMenu(User currentUser){
        for(int f = 0; f < currentUser.getYarnList().size(); f++) {
            System.out.println(currentUser.getYarnList().get(f) + "\n");
        }
    }

    public void addYarnMenu(User currentUser){
        Scanner scan = new Scanner(System.in);
        String name = "";
        String color = "";
        int amount;
        int weight;
        System.out.println("Fill out yarn specifications below: \nYarn/Brand Name: ");
        name = scan.nextLine();
        System.out.println("Yarn Color: ");
        color = scan.nextLine();
        System.out.println("Yarn Weight: ");
        weight = scan.nextInt();
        System.out.println("Yarn Amount: ");
        amount = scan.nextInt();
        Yarn yarn = new Yarn(color, name, weight, amount);
        currentUser.addYarnList(yarn);
        System.out.println("Yarn Added");
    }

    public void removeYarnMenu(User currentUser){
        Scanner scan = new Scanner(System.in);
        boolean v = false;
        System.out.println("Please enter exact Yarn name to be removed: \n");
        String name = scan.nextLine();
        for(int f = 0; f < currentUser.getYarnList().size(); f++){
            if(name.equals(currentUser.getYarnList().get(f).getBrand())){
                currentUser.removeYarnList(currentUser.getYarnList().get(f));
                System.out.println("Yarn removed");
                v = true;
            }
        }
        if(!v){
            System.out.println("No yarns were found matching that name");
        }
    }

    //PATTERN MENUS
    public void patternMenu(User currentUser){

    }
}
