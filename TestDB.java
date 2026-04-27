import java.sql.Connection;
import java.sql.DriverManager;

public class TestDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_activity_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "Bansulal@0110";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("SUCCESS: Connection established!");
            conn.close();
        } catch (Exception e) {
            System.out.println("FAILURE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
