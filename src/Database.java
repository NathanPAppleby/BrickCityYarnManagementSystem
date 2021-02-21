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
            String createProjectsTableQuery = "CREATE TABLE " + UID + "_Projects (Status VARCHAR(40), Pattern VARCHAR(100), Yarns_Required LONGTEXT)";
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
        String checkForExistingYarnQuery = "SELECT COUNT(*) from " + user.getID() + "_Yarns WHERE Color = \"" +
                yarn.getColor() + "\" AND Brand = \"" + yarn.getBrand() + "\" AND Weight = " + yarn.getWeight() + ";";
        try {
            rs = statement.executeQuery(checkForExistingYarnQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String addYarnQuery = "INSERT INTO " + user.getID() + "_Yarns VALUES(\"" + yarn.getColor() +
                "\", \"" + yarn.getBrand() + "\", " + yarn.getAmount() + ", " + yarn.getWeight() + ")";
        try {
            statement.executeUpdate(addYarnQuery);
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

}
