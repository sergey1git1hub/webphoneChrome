package actions.database;

import java.sql.*;

import static actions.database.Powerdialer.getConnection;
import static callsMethods.Methods.log;

/**
 * Created by SChubuk on 11.04.2018.
 */
public class Webphone {
    public static boolean isLogoutRecordPresent(String dateBeforeLogout, String username, int poolingInterval, int waitTime) throws SQLException, ClassNotFoundException, InterruptedException {
        for (int i = 0; i < waitTime; i += poolingInterval) {

            if (isLogoutRecordPresentIteration(dateBeforeLogout, username)) {
                return true;
            }
            log("Wait before checking DB.", "DEBUG");
            Thread.sleep(poolingInterval * 1000);

        }
        log("Check that logout record is present for user " + username, "INFO");
        return false;
    }

    public static boolean isLogoutRecordPresentIteration(String dateBeforeLogout, String username) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();
        Statement statement = null;
        String query = "select *, username from wbp_user_log  inner join wbp_user on " +
                "wbp_user_log.user_id = wbp_user.id where log_date>'" + dateBeforeLogout + "'" +
                "and log_type = 'logout' and username = '" + username + "';";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                log("Logout record found.", "INFO");
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    log(resultSetMetaData.getColumnName(i) + ": " + columnValue + "|", "DEBUG");
                }
                //System.out.println("");

                String dbUserName = resultSet.getString("username");
                String logType = resultSet.getString("log_type");

                System.out.println(dbUserName);
                System.out.println(logType);

                boolean usernameMatches = username.equals(dbUserName);
                boolean logTypeMatches = logType.equals("logout");

                if (usernameMatches && logTypeMatches) {
                    return true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return false;
    }

}
