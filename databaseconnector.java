java
String url = "jdbc:mysql://localhost:3306/mydatabase?useSSL=true"; 

try (Connection conn = DataSource.getConnection(url, System.getenv("DB_USER"), System.getenv("DB_PASSWORD")) {
  // use connection 
}
