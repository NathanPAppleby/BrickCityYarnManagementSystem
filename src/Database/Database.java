package Database;
import java.sql.*;


public class Database {
    public Database(){
    }

    public static Connection createConnection(){
        Connection myCon = null;
        try {
            myCon = DriverManager.getConnection("jdbc:mysql://104.154.144.9/yarnstoragesystem", "user", "user");
        } catch (Exception e){
            e.printStackTrace();
        }
        return myCon;
    }
    public static boolean createUser(String username, String password) throws SQLException {
        Statement statement = null;
        Connection connection = createConnection();
        try {
            statement = connection.createStatement();
        } catch (Exception e){
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
            String createUserQuery = "INSERT INTO Users VALUES (\"" + username + "\", \"" + password + "\", " + UID + ")";
            statement.executeUpdate(createUserQuery);
            return true;
        }
        return false;
    }
}
