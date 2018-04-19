package actions.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static callsMethods.Methods.log;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class Powerdialer {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String userName = "GBWebPhoneTest";
        String password = "yt~k$tCW8%Gj";
        String url = "jdbc:sqlserver://172.21.65.14\\\\corporate;DatabaseName=GBWebPhoneTest;portNumber=1438";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url, userName, password);
        log("Got Database connection", "DEBUG");
        return conn;
    }

    public static void updateRecord(Connection con, String dbTable, String dbPhoneNumber) throws SQLException {
        String query;
        query = "INSERT INTO GBWebPhoneTest.dbo." + dbTable + "(phone_number_1)"
                + " VALUES ('" + dbPhoneNumber + "');";
        Statement stmt = con.createStatement();
        stmt.execute(query);
    }

    public static void runSqlQuery(String dbTable, String dbPhoneNumber) throws SQLException, ClassNotFoundException {
        updateRecord(getConnection(), dbTable, dbPhoneNumber);
        log("Add new number: " + dbPhoneNumber + " to " + dbTable + " table in database.", "INFO");
    }
}
