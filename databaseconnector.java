
try (Connection conn = DriverManager.getConnection(...)) {
  // use connection 
} catch (SQLException e) {
  // handle error
}
