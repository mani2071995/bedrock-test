
// Read username/password from config file
String configUserName = readConfig("db.user"); 
String configPassword = readConfig("db.password");

// Use integrated authentication
Connection conn = DriverManager.getConnection(URL); 

// Use prepared statements 
String sql = "SELECT * FROM Users WHERE Id = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setInt(1, id);
