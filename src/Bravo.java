
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Bravo {


    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    private static final String FILE_PATH = "C:\\Users\\Timing\\IdeaProjects\\Assigment oop\\src\\assigment04\\quiz.txt";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            createQuizTable(conn);
            populateQuizTable(conn, FILE_PATH);
            System.out.println("Таблица Quiz успешно создана и заполнена данными.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void createQuizTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE Quiz ("
                + "questionId INT, "
                + "question VARCHAR(4000), "
                + "choicea VARCHAR(1000), "
                + "choiceb VARCHAR(1000), "
                + "choicec VARCHAR(1000), "
                + "choiced VARCHAR(1000), "
                + "answer VARCHAR(5))";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS Quiz");
            stmt.executeUpdate(createTableSQL);
            System.out.println("Таблица 'Quiz' создана.");
        }
    }

    private static void populateQuizTable(Connection conn, String filePath) throws IOException, SQLException {
        String insertSQL = "INSERT INTO Quiz(questionId, question, choicea, choiceb, choicec, choiced, answer) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            String line;
            int questionId = 0;
            String question = "", choiceA = "", choiceB = "", choiceC = "", choiceD = "", answer = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (Character.isDigit(line.charAt(0)) && line.contains(".")) {
                    if (questionId > 0) {
                        pstmt.setInt(1, questionId);
                        pstmt.setString(2, question);
                        pstmt.setString(3, choiceA);
                        pstmt.setString(4, choiceB);
                        pstmt.setString(5, choiceC);
                        pstmt.setString(6, choiceD);
                        pstmt.setString(7, answer);
                        pstmt.addBatch();
                    }
                    questionId++;
                    question = line.substring(line.indexOf('.') + 1).trim();
                } else if (line.startsWith("A.")) {
                    choiceA = line.substring(2).trim();
                } else if (line.startsWith("B.")) {
                    choiceB = line.substring(2).trim();
                } else if (line.startsWith("C.")) {
                    choiceC = line.substring(2).trim();
                } else if (line.startsWith("D.")) {
                    choiceD = line.substring(2).trim();
                } else if (line.startsWith("ANSWER:")) {
                    answer = line.substring(7).trim();
                }
            }

            if (questionId > 0) {
                pstmt.setInt(1, questionId);
                pstmt.setString(2, question);
                pstmt.setString(3, choiceA);
                pstmt.setString(4, choiceB);
                pstmt.setString(5, choiceC);
                pstmt.setString(6, choiceD);
                pstmt.setString(7, answer);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Данные из файла '" + filePath + "' загружены в таблицу.");
        }
    }
}
