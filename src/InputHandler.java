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
            cartMenu(currentUser);
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
        if (currentUser.hasYarn(yarn)) {
            currentUser.getYarn(yarn).addYarnAmount(amount);
            Database.updateYarnAmount(currentUser, yarn, "ADD");
            System.out.println("Yarn already existed - added amount to storage!");
            b = true;
        }
        if (!b) {
            currentUser.addYarnList(yarn);
            System.out.println("Yarn Added");
            Database.addYarn(currentUser, yarn);
        }
    }

    public void removeYarnMenu(User currentUser) {
        Scanner scan = new Scanner(System.in);
        boolean v = false;
        System.out.println("Please enter exact Yarn name: ");
        String name = scan.nextLine().toLowerCase();
        System.out.println("Enter exact color: ");
        String color = scan.nextLine().toLowerCase();
        System.out.println("Enter exact Weight: ");
        int weight = scan.nextInt();
        System.out.println("Enter amount to remove: ");
        int amount = scan.nextInt();
        Yarn yarn = new Yarn(color, name, weight, amount);
        if (currentUser.hasYarn(yarn)) {
            if (currentUser.getYarn(yarn).getAmount() - amount > 0) {
                currentUser.getYarn(yarn).removeYarnAmount(amount);
                Database.updateYarnAmount(currentUser, yarn, "REMOVE");
                System.out.println("Yarn amount removed - amount remaining is " + currentUser.getYarn(yarn).getAmount());
                v = true;
            } else {
                Database.removeYarn(currentUser, yarn);
                currentUser.removeYarnList(currentUser.getYarn(yarn));
                System.out.println("Yarn removed");
                v = true;
            }
        }
        if (!v) {
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
        System.out.println("Fill out yarn specifications below. Continue adding yarn until finished, when all yarn \nis added, enter a '0' for the Yarn Name: \n");
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
        Database.addProject(currentUser, project);
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
                Database.removeProject(currentUser, name);
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
                    Database.changeProjectStatus(State.inQueue, name, currentUser);
                }
                else if(c == 2){
                    currentUser.getProjectList().get(f).changeProjectStatus(currentUser.getProjectList().get(f), State.WIP);
                    Database.changeProjectStatus(State.WIP, name, currentUser);
                }
                else if(c == 3){
                    currentUser.getProjectList().get(f).changeProjectStatus(currentUser.getProjectList().get(f), State.complete);
                    Database.changeProjectStatus(State.complete, name, currentUser);
                }
                else{
                    System.out.println("I didn't understand that request.");
                    projectStatusChange(currentUser);
                }
            }
        }
    }

    public void cartMenu(User currentUser){
        Scanner scan = new Scanner(System.in);
        System.out.println("Select a menu option for the Shopping Cart");
        System.out.println("(1) Check Cart");
        System.out.println("(2) Add Yarn to Cart");
        System.out.println("(3) Remove Yarn from Cart");
        System.out.println("(4) Add Yarn from Project to Cart");
        System.out.println("(5) Back");
        int choice = scan.nextInt();
        if(choice == 1){
            cartView(currentUser);
            cartMenu(currentUser);
        }
        else if(choice == 2){
            cartAdd(currentUser);
            cartMenu(currentUser);
        }
        else if(choice == 3){
            cartRemove(currentUser);
            cartMenu(currentUser);
        }
        else if(choice == 4){
            cartAddFromProject(currentUser);
            cartMenu(currentUser);
        }
    }

    public void cartView(User currentUser){
        for(int f = 0; f < currentUser.getShoppingCart().size(); f++) {
            System.out.println(currentUser.getShoppingCart().get(f) + "\n");
        }
    }

    public void cartAdd(User currentUser){
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
        if (currentUser.hasYarnInCart(yarn)) {
            currentUser.getYarnFromCart(yarn).addYarnAmount(amount);
            Database.updateYarninCartAmount(currentUser, yarn, "ADD");
            System.out.println("Yarn already existed - added amount to cart!");
            b = true;
        }
        if (!b) {
            currentUser.cartAdd(yarn);
            Database.addYarnToCart(currentUser, yarn);
            System.out.println("Yarn Added");
        }
    }

    public void cartRemove(User currentUser){
        Scanner scan = new Scanner(System.in);
        boolean v = false;
        System.out.println("Please enter exact Yarn name: ");
        String name = scan.nextLine().toLowerCase();
        System.out.println("Enter exact color: ");
        String color = scan.nextLine().toLowerCase();
        System.out.println("Enter exact Weight: ");
        int weight = scan.nextInt();
        System.out.println("Enter amount to remove: ");
        int amount = scan.nextInt();
        Yarn yarn = new Yarn(color, name, weight, amount);
        if (currentUser.hasYarnInCart(yarn)) {
            if (currentUser.getYarnFromCart(yarn).getAmount() - amount > 0) {
                currentUser.getYarnFromCart(yarn).removeYarnAmount(amount);
                Database.updateYarninCartAmount(currentUser, yarn, "REMOVE");
                System.out.println("Yarn amount removed - amount remaining is " + currentUser.getYarnFromCart(yarn).getAmount());
                v = true;
            } else {
                Database.removeYarnFromCart(currentUser, yarn);
                currentUser.cartRemove(currentUser.getYarnFromCart(yarn));
                System.out.println("Yarn removed");
                v = true;
            }
        }
        if (!v) {
            System.out.println("No yarns were found matching that name and color");
        }
    }

    public void cartAddFromProject(User currentUser){
        boolean v = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter project name: ");
        String projectName = scan.nextLine();
        for(int f = 0; f < currentUser.getProjectList().size(); f++){
            if(projectName.equals(currentUser.getProjectList().get(f).getName())){
                for(int u = 0; u < currentUser.getProjectList().get(f).getYarn().size(); u++){
                    currentUser.cartAdd(currentUser.getProjectList().get(f).getYarn().get(u));
                    Database.addYarnToCart(currentUser, currentUser.getProjectList().get(f).getYarn().get(u));
                }
                System.out.println("Yarn from project " + currentUser.getProjectList().get(f).getName() + " added to cart");
                v = true;
            }
        }
        if(!v){
            System.out.println("No projects were found matching that name.");
        }
    }
}
