
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteStudent {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/studentdb";
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = DriverManager.getConnection(url, "root", "");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, 1); // Student ID to delete

            int rows = stmt.executeUpdate();

            System.out.println(rows + " student record deleted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

