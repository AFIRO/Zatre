package persistence;

public class Connectie {
    public static final String JDBC_URL = "jdbc:mysql://ID372560_SDProjectG101.db.webhosting.be";
    public static final String userName = "ID372560_SDProjectG101";
    public static final String password = "SDPdatabankG101";
}
/*
Connection con = null;

String url = "jdbc:mysql://localhost:3306/some_db";
String username = "some_username":
String password = "some_password";

try {
  Class.forName("com.mysql.jdbc.Driver");
  con = DriverManager.getConnection(url, username, password);

  System.out.println("Connected!");

} catch (SQLException ex) {
    throw new Error("Error ", ex);
} finally {
  try {
    if (con != null) {
        con.close();
    }
  } catch (SQLException ex) {
      System.out.println(ex.getMessage());
  }
}*/
