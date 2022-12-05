import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

// import com.mysql.cj.xdevapi.Statement;

public class testJDBC {
  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/text", "admin", "1234");
      java.sql.Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from haha;");
      while (rs.next()) {
        System.out.println(stmt.getResultSet().getString("id"));
        
      }
      conn.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
