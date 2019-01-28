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
        String dbUser = "postgres";
        String dbPassword = "admin";
        
        System.out.println("Solicitou conexão");

        Connection tempConn = null;
        try {
            Class.forName("org.postgresql.Driver");
            tempConn = DriverManager.getConnection(connUrl, "postgres", "admin");
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
        System.out.println("Insert into");
        String values = fieldValueMap.values().stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining("', '", "(nextval('movies_sequence'), '", "')"));
        System.out.println(values);
        try {
            return conn.prepareStatement("INSERT INTO " + tableName + " VALUES " + values).executeUpdate();
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

    public static int update(Connection conn, String tableName,String newtitle, String id) {
        try {
            return conn.prepareStatement("UPDATE " + tableName + " SET TITULO = '" + newtitle.toUpperCase() + "' WHERE ID = '" + id +"';")
                    .executeUpdate();

        } catch (SQLException e) {
            return -1;
        }
    }

    public static int delete(Connection conn, String tableName, String id){
        try {
            return conn.prepareStatement("DELETE FROM " + tableName + " WHERE ID = '"+ id +"'")
                    .executeUpdate();

        } catch (SQLException e) {
            return -1;
        }
    }
}