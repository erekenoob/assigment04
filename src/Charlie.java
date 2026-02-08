
import java.sql.*;

public class Charlie {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "admin";
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            setupStudent1(conn);

            createStudent2Table(conn);

            copyAndTransformStudents(conn);

            System.out.println("Данные из Student1 успешно скопированы и преобразованы в Student2.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupStudent1(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS Student1 CASCADE");
            stmt.executeUpdate("CREATE TABLE Student1 (username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(50) NOT NULL, fullname VARCHAR(200) NOT NULL)");
            stmt.executeUpdate("INSERT INTO Student1 VALUES ('jdoe', 'pass123', 'John K Smith')");
            stmt.executeUpdate("INSERT INTO Student1 VALUES ('asmith', 'pass456', 'Alice Wonderland')");
            stmt.executeUpdate("INSERT INTO Student1 VALUES ('bwayne', 'pass789', 'Bruce Wayne')");
            System.out.println("Тестовая таблица Student1 создана и заполнена.");
        }
    }

    private static void createStudent2Table(Connection conn) throws SQLException {
        String sql = "CREATE TABLE Student2 ("
                + "username VARCHAR(50) NOT NULL PRIMARY KEY, "
                + "password VARCHAR(50) NOT NULL, "
                + "firstname VARCHAR(100), "
                + "lastname VARCHAR(100))";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS Student2 CASCADE");
            stmt.executeUpdate(sql);
            System.out.println("Таблица 'Student2' создана.");
        }
    }

    private static void copyAndTransformStudents(Connection conn) throws SQLException {
        String selectSQL = "SELECT username, password, fullname FROM Student1";
        String insertSQL = "INSERT INTO Student2 (username, password, firstname, lastname) VALUES (?, ?, ?, ?)";

        try (Statement selectStmt = conn.createStatement();
             ResultSet rs = selectStmt.executeQuery(selectSQL);
             PreparedStatement insertPstmt = conn.prepareStatement(insertSQL)) {

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");

                String[] nameParts = fullname.split(" ");
                String firstname = "";
                String lastname = "";

                if (nameParts.length > 0) {
                    firstname = nameParts[0];
                }
                if (nameParts.length > 1) {
                    lastname = nameParts[nameParts.length - 1];
                }

                insertPstmt.setString(1, username);
                insertPstmt.setString(2, password);
                insertPstmt.setString(3, firstname);
                insertPstmt.setString(4, lastname);
                insertPstmt.executeUpdate();
            }
        }
    }
}
