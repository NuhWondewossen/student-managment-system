
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchStudent {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/studentdb";
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection con = DriverManager.getConnection(url, "root", "");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, 2);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Department: " + rs.getString("department"));
            } else {
                System.out.println("Student not found!");
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
