import java.sql.*;
import java.util.ArrayList;


public class Database {
    public Database(){
    }

    public static Connection createConnection() {
        Connection myCon = null;
        try {
            myCon = DriverManager.getConnection("jdbc:mysql://104.154.144.9/yarnstoragesystem", "user", "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myCon;
    }

    public static boolean createUser(String username, String password, String nickname) throws SQLException {
        Statement statement = null;
        Connection connection = createConnection();
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userCountQuery = "SELECT COUNT(Username) from Users";
        assert statement != null;
        ResultSet rs = statement.executeQuery(userCountQuery);
        rs.next();
        int UID = rs.getInt(1);
        String usernameCheckQuery = "SELECT COUNT(Username) FROM Users WHERE Username = \"" + username + "\"";
        ResultSet usernameCheckResult = statement.executeQuery(usernameCheckQuery);
        usernameCheckResult.next();
        if((usernameCheckResult.getInt(1) == 0)) {
            String createUserQuery = "INSERT INTO Users VALUES (\"" + username + "\", \"" + password + "\", " + UID + ",\"" + nickname + "\")";
            String createYarnTableQuery = "CREATE TABLE " + UID + "_Yarns (Color VARCHAR(100), Brand VARCHAR(100), Weight INTEGER, Amount INTEGER)";
            String createProjectsTableQuery = "CREATE TABLE " + UID + "_Projects (Name VARCHAR(40), Status VARCHAR(40), Pattern VARCHAR(100), Yarns_Required LONGTEXT)";
            String createShoppingCartTableQuery = "CREATE TABLE " + UID + "_ShoppingCart (Color VARCHAR(40), Brand VARCHAR(40), Weight INTEGER, Amount INTEGER)";

            statement.executeUpdate(createYarnTableQuery);
            statement.executeUpdate(createProjectsTableQuery);
            statement.executeUpdate(createUserQuery);
            statement.executeUpdate(createShoppingCartTableQuery);
            return true;
        }
        return false;
    }
    public static boolean loginUser(String username, String password) throws SQLException {
        Statement statement = null;
        Connection connection = createConnection();
        try {
            statement = connection.createStatement();
        } catch (Exception e){
            e.printStackTrace();
        }
        String getUserCredentials = "SELECT * FROM Users WHERE Username = \"" + username + "\"";
        ResultSet userResultSet = null;
        try {
            userResultSet = statement.executeQuery(getUserCredentials);
        } catch(Exception e){
            e.printStackTrace();
        }
        String userPass = "";
        while (userResultSet.next()) {
            try {
                userPass = userResultSet.getString("Password");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return userPass.equals(password);
    }

    public static User getUser(String username) throws SQLException {
        Statement statement = null;
        ResultSet userResultSet = null;
        String nickname = "";
        int UID = 0;
        Connection connection = createConnection();
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String getUserQuery = "Select Nickname, UID FROM Users WHERE Username = \"" + username + "\";";
        try {
            userResultSet = statement.executeQuery(getUserQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (userResultSet.next()) {
            nickname = userResultSet.getString("Nickname");
            UID = userResultSet.getInt("UID");
        }
        User user = new User(username, UID, nickname);
        for (Yarn y : getYarns(user)) {
            user.addYarnList(y);
        }
        for (Project p : getProjects(user)) {
            user.addProjectList(p);
        }
        for (Yarn y : getYarnsInCart(user)) {
            user.cartAdd(y);
        }
        return user;
    }

    public static void addYarn(User user, Yarn yarn) {
        Connection connection = createConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String addYarnQuery = "INSERT INTO " + user.getID() + "_Yarns VALUES(\"" + yarn.getColor() +
                "\", \"" + yarn.getBrand() + "\", " + yarn.getWeight() + ", " + yarn.getAmount() + ")";
        try {
            statement.executeUpdate(addYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateYarnAmount(User user, Yarn yarn, String operation) {
        Connection connection = createConnection();
        Statement statement = null;
        ResultSet rs = null;
        String updateYarnAmountQuery = null;
        int currentAmount = 0;
        String getAmountQuery = String.format("SELECT * FROM %d_Yarns WHERE Color = \"%s\" AND Brand = \"%s\" AND " +
                "Weight = %d", user.getID(), yarn.getColor(), yarn.getBrand(), yarn.getWeight());
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(getAmountQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            while (rs.next()) {
                currentAmount = rs.getInt("Amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (operation.equals("ADD")) {
            updateYarnAmountQuery = String.format("UPDATE %d_Yarns SET Amount = %d WHERE Color = \"%s\" AND" +
                            " Brand = \"%s\" AND Weight = %d", user.getID(), (currentAmount + yarn.getAmount()), yarn.getColor(),
                    yarn.getBrand(), yarn.getWeight());
        } else if (operation.equals("REMOVE")) {
            updateYarnAmountQuery = String.format("UPDATE %d_Yarns SET Amount = %d WHERE Color = \"%s\" AND" +
                            " Brand = \"%s\" AND Weight = %d", user.getID(), (currentAmount - yarn.getAmount()), yarn.getColor(),
                    yarn.getBrand(), yarn.getWeight());
        }
        try {
            statement.executeUpdate(updateYarnAmountQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Yarn> getYarns(User user) throws SQLException {
        Connection connection = createConnection();
        Statement statement = null;
        ArrayList<Yarn> listOfYarns = new ArrayList<>();
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String getAllYarnQuery = "SELECT * FROM " + user.getID() + "_Yarns";
        try {
            rs = statement.executeQuery(getAllYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            String color = rs.getString("Color");
            String brand = rs.getString("Brand");
            int weight = rs.getInt("Weight");
            int amount = rs.getInt("Amount");
            Yarn yarn = new Yarn(color, brand, weight, amount);
            listOfYarns.add(yarn);
        }
        return listOfYarns;
    }

    public static ArrayList<Project> getProjects(User user) {
        Connection connection = createConnection();
        Statement statement = null;
        ArrayList<Project> listOfProjects = new ArrayList<>();
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String getAllProjectsQuery = "SELECT * FROM " + user.getID() + "_Projects";
        try {
            ResultSet rs = statement.executeQuery(getAllProjectsQuery);
            while (rs.next()) {
                String name = rs.getString("Name");
                String pattern = rs.getString("Pattern");
                String yarn = rs.getString("Yarns_Required");
                String status = rs.getString("Status");
                ArrayList<Yarn> yarnNeeded = yarnStringToArray(yarn);
                State projectStatus = null;
                switch (status) {
                    case "WIP": {
                        projectStatus = State.WIP;
                        break;
                    }
                    case "InQueue": {
                        projectStatus = State.inQueue;
                        break;
                    }
                    case "Complete": {
                        projectStatus = State.complete;
                        break;
                    }
                }
                Project project = new Project(projectStatus, pattern, yarnNeeded, name);
                listOfProjects.add(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfProjects;
    }

    public static ArrayList<Yarn> yarnStringToArray(String string) {
        String brand = null;
        String color = null;
        int weight = 0;
        int amount = 0;
        ArrayList<Yarn> yarnArray = new ArrayList<>();
        string = string.substring(1, string.length() - 1);
        string = string.replace(" weight ", "");
        string = string.replace(" amount ", "");
        string = string.replace("\n", "");
        String[] split = string.split(",| in color");
        for (int i = 0; i < split.length; i++) {
            if (split[i].charAt(0) == ' ') {
                split[i] = split[i].substring(1);
            }
            switch (i % 4) {
                case 0: {
                    brand = split[i];
                    break;
                }
                case 1: {
                    color = split[i];
                    break;
                }
                case 2: {
                    weight = Integer.parseInt(split[i]);
                    break;
                }
                case 3: {
                    amount = Integer.parseInt(split[i]);
                    Yarn yarn = new Yarn(color, brand, weight, amount);
                    yarnArray.add(yarn);
                    break;
                }
            }

        }
        return yarnArray;
    }

    public static void removeYarn(User user, Yarn yarn) {
        Connection connection = createConnection();
        Statement statement = null;
        String removeYarnQuery = String.format("DELETE FROM %d_Yarns WHERE Color = \"%s\" AND" +
                " Brand = \"%s\" AND Weight = %d", user.getID(), yarn.getColor(), yarn.getBrand(), yarn.getWeight());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(removeYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addProject(User user, Project project) {
        Connection connection = createConnection();
        Statement statement;
        String projectState = "";
        State currentState = project.getStatus();
        switch (currentState) {
            case inQueue: {
                projectState = "InQueue";
                break;
            }
            case WIP: {
                projectState = "WIP";
                break;
            }
            case complete: {
                projectState = "Complete";
                break;
            }
        }
        String addProjectQuery = String.format("INSERT INTO %d_Projects VALUES(\"%s\", \"%s\", \"%s\", \"%s\")", user.getID(),
                project.getName(), projectState, project.getPattern(), project.getYarn());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(addProjectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeProject(User user, String projectName) {
        Connection connection = createConnection();
        Statement statement = null;
        String removeYarnQuery = String.format("DELETE FROM %d_Projects WHERE Name = \"%s\"", user.getID(), projectName);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(removeYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeProjectStatus(State state, String projectName, User user) {
        Connection connection = createConnection();
        Statement statement = null;
        String newState = "";
        switch (state) {
            case inQueue: {
                newState = "InQueue";
                break;
            }
            case WIP: {
                newState = "WIP";
                break;
            }
            case complete: {
                newState = "Complete";
                break;
            }
        }
        String changeProjectStatusQuery = String.format("UPDATE %d_Projects SET Status = \"%s\" WHERE Name = \"%s\"", user.getID(), newState, projectName);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(changeProjectStatusQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addYarnToCart(User user, Yarn yarn) {
        Connection connection = createConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String addYarnQuery = "INSERT INTO " + user.getID() + "_ShoppingCart VALUES(\"" + yarn.getColor() +
                "\", \"" + yarn.getBrand() + "\", " + yarn.getWeight() + ", " + yarn.getAmount() + ")";
        try {
            statement.executeUpdate(addYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateYarninCartAmount(User user, Yarn yarn, String operation) {
        Connection connection = createConnection();
        Statement statement = null;
        ResultSet rs = null;
        String updateYarnAmountQuery = null;
        int currentAmount = 0;
        String getAmountQuery = String.format("SELECT * FROM %d_ShoppingCart WHERE Color = \"%s\" AND Brand = \"%s\" AND " +
                "Weight = %d", user.getID(), yarn.getColor(), yarn.getBrand(), yarn.getWeight());
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(getAmountQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            while (rs.next()) {
                currentAmount = rs.getInt("Amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (operation.equals("ADD")) {
            updateYarnAmountQuery = String.format("UPDATE %d_ShoppingCart SET Amount = %d WHERE Color = \"%s\" AND" +
                            " Brand = \"%s\" AND Weight = %d", user.getID(), (currentAmount + yarn.getAmount()), yarn.getColor(),
                    yarn.getBrand(), yarn.getWeight());
        } else if (operation.equals("REMOVE")) {
            updateYarnAmountQuery = String.format("UPDATE %d_ShoppingCart SET Amount = %d WHERE Color = \"%s\" AND" +
                            " Brand = \"%s\" AND Weight = %d", user.getID(), (currentAmount - yarn.getAmount()), yarn.getColor(),
                    yarn.getBrand(), yarn.getWeight());
        }
        try {
            statement.executeUpdate(updateYarnAmountQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Yarn> getYarnsInCart(User user) throws SQLException {
        Connection connection = createConnection();
        Statement statement = null;
        ArrayList<Yarn> listOfYarns = new ArrayList<>();
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String getAllYarnQuery = "SELECT * FROM " + user.getID() + "_ShoppingCart";
        try {
            rs = statement.executeQuery(getAllYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            String color = rs.getString("Color");
            String brand = rs.getString("Brand");
            int weight = rs.getInt("Weight");
            int amount = rs.getInt("Amount");
            Yarn yarn = new Yarn(color, brand, weight, amount);
            listOfYarns.add(yarn);
        }
        return listOfYarns;
    }

    public static void removeYarnFromCart(User user, Yarn yarn) {
        Connection connection = createConnection();
        Statement statement = null;
        String removeYarnQuery = String.format("DELETE FROM %d_ShoppingCart WHERE Color = \"%s\" AND" +
                " Brand = \"%s\" AND Weight = %d", user.getID(), yarn.getColor(), yarn.getBrand(), yarn.getWeight());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(removeYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
