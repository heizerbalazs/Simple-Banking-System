package banking.db;

import banking.model.Account;
import banking.io.CodeSerializer;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;

public class DataBaseConnection {
    private static String DB_NAME;

    public DataBaseConnection(String dbName) {
        DB_NAME = dbName;
        createDB();
    }

    private void createDB() {
        String url = "jdbc:sqlite:./" + DB_NAME;


        String crateTable = "CREATE TABLE IF NOT EXISTS card ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "number TEXT,"
                + "pin TEXT,"
                + "balance INTEGER DEFAULT 0"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            statement.execute(crateTable);

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() {
        String url = "jdbc:sqlite:./" + DB_NAME;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void insertNewAccount(int[] cardNumber, int[] pin) {
        String insertCard = "INSERT INTO card (number, pin) VALUES (?, ?);";

        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(insertCard);
            statement.setString(1, CodeSerializer.serialize(cardNumber));
            statement.setString(2, CodeSerializer.serialize(pin));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(int[] cardNumber) {
        String deleteCard = "DELETE FROM card WHERE number=?;";

        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(deleteCard);

            statement.setString(1, CodeSerializer.serialize(cardNumber));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBalance(int[] cardNumber, int newBalance) {
        String updateCard = "UPDATE card SET balance=? WHERE number=?;";

        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(updateCard);

            statement.setInt(1, newBalance);
            statement.setString(2, CodeSerializer.serialize(cardNumber));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Account getAccountByCardNumber(int[] cardNumber) {
        Account account = null;
        int[] cardNumberSQL = new int[16];
        int[] pinSQL = new int[4];
        int balance;

        String selectAccount = String.format(
                "SELECT * FROM card WHERE number=\"%s\"",
                CodeSerializer.serialize(cardNumber)
        );

        try (Connection conn = connect();
             Statement selectStatement = conn.createStatement();
             ResultSet resultSet = selectStatement.executeQuery(selectAccount)) {
            if (resultSet.next()) {
                cardNumberSQL = CodeSerializer.deserialize(resultSet.getString("number"));
                pinSQL = CodeSerializer.deserialize(resultSet.getString("pin"));
                balance = resultSet.getInt("balance");
                account = new Account(cardNumberSQL, pinSQL);
                account.setBalance(balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }
}
