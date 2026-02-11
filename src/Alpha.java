
import java.sql.*;
import java.util.Scanner;

public class Alpha {

    public static void main(String[] args) {
        String dbHost = "localhost";
        String dbPort = "5432";
        String username = "postgres";
        String password = "admin";
        // ---------------------------------------------

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя базы данных: ");
        String dbName = scanner.nextLine();
        System.out.print("Введите имя таблицы: ");
        String tableName = scanner.nextLine();
        scanner.close();

        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Соединение с базой данных '" + dbName + "' установлено.");

            String sql = "SELECT * FROM " + tableName;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", metaData.getColumnName(i));
                }
                System.out.println();
                System.out.println("-".repeat(20 * columnCount));

                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-20s", resultSet.getString(i));
                    }
                    System.out.println();
                }
            }

        } catch (SQLException e) {
            System.out.println("Ошибка SQL: " + e.getMessage());
            e.printStackTrace();
        }



    }
}
