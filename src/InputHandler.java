import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.util.ArrayList;
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
            projectMenu(currentUser);
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
        name = scan.nextLine().toLowerCase();
        System.out.println("Yarn Color: ");
        color = scan.nextLine().toLowerCase();
        System.out.println("Yarn Weight: ");
        weight = scan.nextInt();
        System.out.println("Yarn Amount in Yards: ");
        amount = scan.nextInt();
        Yarn yarn = new Yarn(color, name, weight, amount);
        boolean b = false;
        for(int f = 0; f < currentUser.getYarnList().size(); f++){
            if(yarn == currentUser.getYarnList().get(f)){
                currentUser.getYarnList().get(f).addYarnAmount(amount);
                System.out.println("Yarn already existed - added amount to storage!");
                b = true;
            }
        }
        if(!b) {
            currentUser.addYarnList(yarn);
            System.out.println("Yarn Added");
        }
    }

    public void removeYarnMenu(User currentUser){
        Scanner scan = new Scanner(System.in);
        boolean v = false;
        System.out.println("Please enter exact Yarn name: ");
        String name = scan.nextLine().toLowerCase();
        System.out.println("Enter exact color: ");
        String color = scan.nextLine().toLowerCase();
        System.out.println("Enter amount to remove: ");
        int amount = scan.nextInt();
        for(int f = 0; f < currentUser.getYarnList().size(); f++){
            if(name.equals(currentUser.getYarnList().get(f).getBrand()) && color.equals(currentUser.getYarnList().get(f).getColor())){
                if(currentUser.getYarnList().get(f).getAmount() - amount > 0){
                    currentUser.getYarnList().get(f).removeYarnAmount(amount);
                    System.out.println("Yarn amount removed - amount remaining is " + currentUser.getYarnList().get(f).getAmount());
                    v = true;
                }
                else {
                    currentUser.removeYarnList(currentUser.getYarnList().get(f));
                    System.out.println("Yarn removed");
                    v = true;
                }
            }
        }
        if(!v){
            System.out.println("No yarns were found matching that name and color");
        }
    }

    //PATTERN MENUS
    public void projectMenu(User currentUser){
        Scanner scan = new Scanner(System.in);
        System.out.println("Select a menu option for Projects");
        System.out.println("(1) Check List");
        System.out.println("(2) Add Project");
        System.out.println("(3) Remove Project");
        System.out.println("(4) Change Project Status");
        System.out.println("(5) Back");
        int choice = scan.nextInt();
        if(choice == 1){
            projectCheckMenu(currentUser);
            projectMenu(currentUser);
        }
        else if(choice == 2){
            projectAddList(currentUser);
            projectMenu(currentUser);
        }
        else if(choice == 3){
            projectRemoveList(currentUser);
            projectMenu(currentUser);
        }
        else if(choice == 4){
            projectStatusChange(currentUser);
            projectMenu(currentUser);
        }
    }

    public void projectCheckMenu(User currentUser){
        System.out.println("What list would you like to see?\n");
        System.out.println("(1) All Projects");
        System.out.println("(2) Projects in Queue");
        System.out.println("(3) Projects in WIP");
        System.out.println("(4) Completed Projects");
        System.out.println("(5) Back");
        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt();
        if(c == 1){
            projectCheckAll(currentUser);
            projectCheckMenu(currentUser);
        }
        else if(c == 2){
            projectCheckQueue(currentUser);
            projectCheckMenu(currentUser);
        }
        else if(c == 3){
            projectCheckWIP(currentUser);
            projectCheckMenu(currentUser);
        }
        else if(c == 4){
            projectCheckComplete(currentUser);
            projectCheckMenu(currentUser);
        }
    }

    public void projectCheckAll(User currentUser){
        for(int f = 0; f < currentUser.getProjectList().size(); f++) {
            System.out.println(currentUser.getProjectList().get(f) + "\n");
        }
    }

    public void projectCheckQueue(User currentUser){
        for(int f = 0; f < currentUser.getProjectList().size(); f++) {
            if(currentUser.getProjectList().get(f).getStatus() == State.inQueue){
                System.out.println(currentUser.getProjectList().get(f) + "\n");
            }
        }
    }

    public void projectCheckWIP(User currentUser){
        for(int f = 0; f < currentUser.getProjectList().size(); f++) {
            if(currentUser.getProjectList().get(f).getStatus() == State.WIP){
                System.out.println(currentUser.getProjectList().get(f) + "\n");
            }
        }
    }

    public void projectCheckComplete(User currentUser){
        for(int f = 0; f < currentUser.getProjectList().size(); f++) {
            if(currentUser.getProjectList().get(f).getStatus() == State.complete){
                System.out.println(currentUser.getProjectList().get(f) + "\n");
            }
        }
    }

    public void projectAddList(User currentUser){
        Scanner scan = new Scanner(System.in);
        System.out.println("Fill out project template: \nProject Name: ");
        String name = scan.nextLine().toLowerCase();
        System.out.println("Link to pattern: ");
        String pattern = scan.nextLine().toLowerCase();
        System.out.println("Fill out yarn specifications below. Continue adding yarn until finished, when all yarn \nis added, enter a \'0\' for the Yarn Name: \n");
        String brandname = "";
        boolean v = false;
        ArrayList<Yarn> yarnProjectList = new ArrayList<>();
        while(!v) {
            System.out.println("Yarn/Brand Name: ");
            brandname = scan.nextLine().toLowerCase();
            if(brandname.equals("0"))
                break;
            System.out.println("Yarn Color: ");
            String color = scan.nextLine().toLowerCase();
            System.out.println("Yarn Weight: ");
            int weight = scan.nextInt();
            System.out.println("Yarn Amount in Yards: ");
            int amount = scan.nextInt();
            scan.nextLine();
            Yarn yarn = new Yarn(color, brandname, weight, amount);
            yarnProjectList.add(yarn);
        }
        Project project = new Project(State.inQueue, pattern, yarnProjectList, name);
        currentUser.addProjectList(project);
        System.out.println("Project added to queue.");
    }

    public void projectRemoveList(User currentUser){
        Scanner scan = new Scanner(System.in);
        boolean v = false;
        System.out.println("Please enter exact project name to be removed: \n");
        String name = scan.nextLine().toLowerCase();
        for(int f = 0; f < currentUser.getProjectList().size(); f++){
            if(name.equals(currentUser.getProjectList().get(f).getName())){
                currentUser.removeProjectList(currentUser.getProjectList().get(f));
                System.out.println("Project removed");
                v = true;
            }
        }
        if(!v){
            System.out.println("No projects were found matching that name");
        }
    }

    public void projectStatusChange(User currentUser){
        Scanner scan = new Scanner(System.in);
        boolean v = false;
        System.out.println("Please enter exact project name: \n");
        String name = scan.nextLine().toLowerCase();
        for(int f = 0; f < currentUser.getProjectList().size(); f++){
            if(name.equals(currentUser.getProjectList().get(f).getName())){
                System.out.println("What state would you like the project to be in?\n(1) Queue\n(2) WIP\n(3) Complete");
                int c = scan.nextInt();
                if(c == 1) {
                    currentUser.getProjectList().get(f).changeProjectStatus(currentUser.getProjectList().get(f), State.inQueue);
                }
                else if(c == 2){
                    currentUser.getProjectList().get(f).changeProjectStatus(currentUser.getProjectList().get(f), State.WIP);
                }
                else if(c == 3){
                    currentUser.getProjectList().get(f).changeProjectStatus(currentUser.getProjectList().get(f), State.complete);
                }
                else{
                    System.out.println("I didn't understand that request.");
                    projectStatusChange(currentUser);
                }
            }
        }

    }
}
