package db;

/**
 *
 * @author mateu
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseHelper {
    private static DatabaseHelper ourInstance = new DatabaseHelper();

    public static DatabaseHelper getInstance() {
        return ourInstance;
    }

    private DatabaseHelper() {
    }

    public static Connection getConnection() {
        String connUrl = "jdbc:postgresql://localhost:5432/moviedb";
        String dbUser = "root";
        String dbPassword = "";

        Connection tempConn;
        try {
            Class.forName("org.postgresql.jdbc.Driver");
            
            tempConn = DriverManager.getConnection(connUrl, dbUser, dbPassword);
            tempConn = tempConn != null && tempConn.isValid(10) ? tempConn : null;
        } catch (Exception e) {
            String status = "Exceção lançada: " + e.getMessage();
            System.out.println(status);
            System.out.println(status);
            tempConn = null;
        }
        if (tempConn == null) {
            System.out.println("Conexão mal-sucedida para a URL: [ " + connUrl + " ]");
        }
        return tempConn;
    }

    public static int insertInto(Connection conn, String tableName, Map<String, String> fieldValueMap) {
        String fields = fieldValueMap.keySet().stream().collect(Collectors.joining(", ", "(", ")"));
        String values = fieldValueMap.values().stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining("', '", "('", "')"));

        try {
            return conn.prepareStatement("INSERT INTO " + tableName + ' ' + fields + " VALUES " + values)
                    .executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public static boolean closeConnection(Connection conn) {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static int update(Connection conn,
                             String tableName,
                             Map<String, String> fieldValueMap,
                             Map<String, String> whereCond
    ) {
        // UPDATE `movies` SET `title`=[value-2],`genre`=[value-5], `launch_year`=[value-6] WHERE `movies`.`id`=[value-1]
        String fieldReceivesValue = fieldValueMap.entrySet().stream()
                .map(entry -> "`" + entry.getKey() + "`='" + entry.getValue().toUpperCase() + "'")
                .collect(Collectors.joining(", "));

        String whereCondition = whereCond.entrySet().stream()
                .map(entry -> "`" + tableName + "`.`" + entry.getKey() + "`='" + entry.getValue().toUpperCase() + "'")
                .collect(Collectors.joining(" AND "));

        try {
            return conn.prepareStatement("UPDATE " + tableName + " SET " + fieldReceivesValue + " WHERE " + whereCondition)
                    .executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int delete(Connection conn,
                             String tableName,
                             Map<String, String> identifierFieldValueMap
    ) {
        // DELETE FROM `movies` WHERE `movies`.`id` = 'X'
        String whereCondition = identifierFieldValueMap.entrySet().stream()
                .map(entry -> "`" + tableName + "`.`" + entry.getKey() + "`='" + entry.getValue().toUpperCase() + "'")
                .collect(Collectors.joining(" AND "));

        try {
            return conn.prepareStatement("DELETE FROM `" + tableName + "` WHERE "+ whereCondition)
                    .executeUpdate();

        } catch (SQLException e) {
            return -1;
        }
    }
}