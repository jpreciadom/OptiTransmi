package ServerConection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {
    
    String driver="com.mysql.cj.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/dboptitransmi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String user="root";
    String pass="wigeta777";
    Connection conn=null;
    Statement st=null;
    ResultSet rs=null;

    public Conexion() {
            try{
                Class.forName(driver);
                conn=DriverManager.getConnection(url, user, pass);
                st=conn.createStatement();
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                JOptionPane.showMessageDialog(null,"Conexion realizada exitosamente");
            }
    }
    
}
