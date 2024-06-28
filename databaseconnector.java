java
// File: DatabaseConnector.java  

import java.sql.*;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "root";
    private String password;

    public DatabaseConnector(String password) {
        this.password = password; 
    }

    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, this.password);  
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
