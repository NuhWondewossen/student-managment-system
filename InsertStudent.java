
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class InsertStudent {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String name = input.nextLine();

        System.out.print("Enter student age: ");
        int age = input.nextInt();
        input.nextLine();

        System.out.print("Enter department: ");
        String department = input.nextLine();

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String sql = "INSERT INTO students (name, age, department) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, "root", "");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, department);

            stmt.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        input.close();
    }
}
