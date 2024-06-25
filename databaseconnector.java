Here are some ways to improve the code:

1. Don't hardcode credentials:
   - Move the username and password to environment variables or a configuration file that is read at runtime
   - Consider integrating with a secret management service like AWS Secrets Manager

2. Handle exceptions better:
   - Don't just print the stack trace, wrap and re-throw as a custom exception or return an error response
   - Add better logging to capture errors

3. Use connection pooling:
   - Creating new connections is expensive, use a pool like HikariCP for better performance

4. Parameterize queries:
   - Use prepared statements with bind variables instead of concatenating strings to prevent SQL injection  

5. Improve security:
   - Use the principle of least privilege for database credentials
   - Consider enabling SSL connections for encrypted traffic
   - Validate user input and sanitize data

Here is an example fixing some of these issues:

```
// Read from secure config 
String url = config.getDbUrl();
String username = config.getDbUsername();
String password = config.getDbPassword();

// Use connection pool
try (Connection conn = connectionPool.getConnection(url, username, password)) {

  // Parameterized query
  String sql = "SELECT * FROM users WHERE id = ?";
  try (PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setInt(1, userId);  
    ResultSet rs = ps.executeQuery();
    // ...
  }

} catch (SQLException e) {
  // Log and handle error  
  logger.error("DB error", e);
  throw new DatabaseException(e);  
}
```