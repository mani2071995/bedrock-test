java
// Load credentials from secure storage
String url = "jdbc:mysql://localhost:3306/mydatabase?useSSL=true"; 
String user = System.getenv("DB_USER");
String password = getFromSecureStorage("DB_PASSWORD");

// Use connection pool
HikariDataSource dataSource = new HikariDataSource();
dataSource.setJdbcUrl(url); 
dataSource.setUsername(user);
dataSource.setPassword(password);

Connection connection = dataSource.getConnection(); 
