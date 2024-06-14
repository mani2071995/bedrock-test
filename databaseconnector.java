
String url = "jdbc:mysql://localhost:3306/mydatabase?useSSL=truerequireSSL=true";

try (Connection conn = connectionPool.getConnection(url, getUserName(), getPassword()) {
  // use connection 
} catch (CustomDBException e) {
  logger.error("Error connecting to database", e); 
}
