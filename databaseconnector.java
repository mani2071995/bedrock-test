
String url = "jdbc:mysql://localhost:3306/mydatabase?useSSL=true";
String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD"); 

try (Connection conn = DataSource.getConnectionPool().getConnection(url, user, password)) {
  // use connection
} catch (SQLException e) {
  logger.error("Error connecting to database");
}
