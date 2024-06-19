java
// Load credentials safely from secrets manager
String url = "jdbc:mysql://localhost:3306/db?useSSL=true";
String user = secretsManager.getDatabaseUser(); 
String password = secretsManager.getDatabasePassword();

// Use connection pool
HikariDataSource dataSource = new HikariDataSource();
dataSource.setURL(url); 
dataSource.setUsername(user);
dataSource.setPassword(password);

try {
    Connection conn = dataSource.getConnection(); 
    // ... use connection
} catch (SQLException e) {
    logger.log(e.getMessage());
}
