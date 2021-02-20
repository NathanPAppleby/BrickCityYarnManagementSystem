import java.sql.*;



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
        if((usernameCheckResult.getInt(1) == 0)){
            String createUserQuery = "INSERT INTO Users VALUES (\"" + username + "\", \"" + password + "\", " + UID + ",\"" + nickname + "\")";
            statement.executeUpdate(createUserQuery);
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
        return new User(username, UID, nickname);
    }

}
