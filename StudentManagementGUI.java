
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentManagementGUI extends JFrame {

    private final Color background = new Color(18, 22, 40);
    private final Color panelColor = new Color(29, 35, 59);
    private final Color purple = new Color(124, 92, 255);
    private final Color white = new Color(240, 243, 255);

    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField departmentField = new JTextField();
    private final JTextField searchField = new JTextField();

    private DefaultTableModel model;
    private JTable table;

    public StudentManagementGUI() {
        setTitle("Student Hub | Management System");
        setSize(1050, 650);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(background);
        setLayout(new BorderLayout(15, 15));

        JLabel title = new JLabel("  STUDENT HUB");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(white);
        title.setBorder(BorderFactory.createEmptyBorder(20, 15, 5, 10));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBackground(background);
        content.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));

        JLabel heading = new JLabel("Student Management");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(white);

        JLabel subtitle = new JLabel("Manage your student records in one place");
        subtitle.setForeground(new Color(170, 180, 205));

        JPanel headingPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headingPanel.setBackground(background);
        headingPanel.add(heading);
        headingPanel.add(subtitle);

        JPanel top = new JPanel(new BorderLayout(10, 15));
        top.setBackground(background);
        top.add(headingPanel, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 4, 12, 8));
        form.setBackground(panelColor);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 64, 95)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        addField(form, "Student ID", idField);
        addField(form, "Full Name", nameField);
        addField(form, "Age", ageField);
        addField(form, "Department", departmentField);

        top.add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setBackground(background);

        JButton add = button("+ Add Student", purple);
        JButton update = button("Update Age", new Color(45, 130, 210));
        JButton delete = button("Delete", new Color(210, 65, 85));
        JButton clear = button("Clear Fields", new Color(75, 85, 115));

        actions.add(add);
        actions.add(update);
        actions.add(delete);
        actions.add(clear);

        top.add(actions, BorderLayout.SOUTH);
        content.add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Student Name", "Age", "Department"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(32);
        table.setBackground(panelColor);
        table.setForeground(white);
        table.setGridColor(new Color(55, 64, 95));
        table.setSelectionBackground(new Color(75, 65, 140));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(42, 49, 78));
        table.getTableHeader().setForeground(white);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);

        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(background);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(background);

        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBackground(panelColor);
        searchField.setForeground(white);
        searchField.setCaretColor(white);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 75, 110)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton search = button("Search", purple);
        JButton refresh = button("Refresh Table", new Color(75, 85, 115));

        JPanel searchButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        searchButtons.setBackground(background);
        searchButtons.add(search);
        searchButtons.add(refresh);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButtons, BorderLayout.EAST);

        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        content.add(tablePanel, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);

        add.addActionListener(e -> addStudent());
        update.addActionListener(e -> updateStudent());
        delete.addActionListener(e -> deleteStudent());
        clear.addActionListener(e -> clearFields());
        search.addActionListener(e -> searchStudents());
        refresh.addActionListener(e -> loadStudents());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                ageField.setText(model.getValueAt(row, 2).toString());
                departmentField.setText(model.getValueAt(row, 3).toString());
            }
        });

        loadStudents();
    }

    private void addField(JPanel panel, String label, JTextField field) {
        JPanel item = new JPanel(new BorderLayout(0, 5));
        item.setBackground(panelColor);

        JLabel text = new JLabel(label);
        text.setForeground(white);

        field.setBackground(background);
        field.setForeground(white);
        field.setCaretColor(white);

        item.add(text, BorderLayout.NORTH);
        item.add(field, BorderLayout.CENTER);
        panel.add(item);
    }

    private JButton button(String text, Color color) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private Connection connect() throws SQLException {
        return DatabaseConnection.connect();
    }

    private void loadStudents() {
        model.setRowCount(0);

        try (Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT id, name, age, department FROM students")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department")
                });
            }
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void addStudent() {
        if (nameField.getText().trim().isEmpty()
                || ageField.getText().trim().isEmpty()
                || departmentField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter name, age, and department.");
            return;
        }

        try {
            int age = Integer.parseInt(ageField.getText().trim());

            if (age < 1 || age > 120) {
                JOptionPane.showMessageDialog(this,
                        "Enter an age from 1 to 120.");
                return;
            }

            String sql = "INSERT INTO students (name, age, department) VALUES (?, ?, ?)";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nameField.getText().trim());
                ps.setInt(2, age);
                ps.setString(3, departmentField.getText().trim());
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Student added successfully!");
            clearFields();
            loadStudents();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number.");
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            int age = Integer.parseInt(ageField.getText().trim());

            if (age < 1 || age > 120) {
                JOptionPane.showMessageDialog(this,
                        "Enter an age from 1 to 120.");
                return;
            }

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                         "UPDATE students SET age = ? WHERE id = ?")) {
                ps.setInt(1, age);
                ps.setInt(2, id);

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Age updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "Student ID not found.");
                }
            }

            loadStudents();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid student ID and age.");
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Permanently delete student ID " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                         "DELETE FROM students WHERE id = ?")) {
                ps.setInt(1, id);

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Student deleted.");
                } else {
                    JOptionPane.showMessageDialog(this, "Student ID not found.");
                }
            }

            clearFields();
            loadStudents();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid student ID.");
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void searchStudents() {
        String keyword = searchField.getText().trim();
        model.setRowCount(0);

        String sql = "SELECT id, name, age, department FROM students "
                + "WHERE name LIKE ? OR department LIKE ?";

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("department")
                    });
                }
            }
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        departmentField.setText("");
        table.clearSelection();
    }

    private void showError(SQLException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Database error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementGUI().setVisible(true);
        });
    }
}
