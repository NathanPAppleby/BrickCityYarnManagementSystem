package Database;
import java.sql.*;


public class Database {
    public static Connection createConnection(){
        Connection myCon = null;
        try {
            myCon = DriverManager.getConnection("jdbc:mysql://104.154.144.9/yarnstoragesystem", "user", "user");
        } catch (Exception e){
            e.printStackTrace();
        }
        return myCon;
    }

    public static void main(String[] args) {
        createConnection();
    }
}
