
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateStudent {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/studentdb";

        String sql = "UPDATE students SET age = ? WHERE id = ?";

        try (Connection con = DriverManager.getConnection(url, "root", "");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, 23);
            stmt.setInt(2, 1);

            int rows = stmt.executeUpdate();

            System.out.println(rows + " student record updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
