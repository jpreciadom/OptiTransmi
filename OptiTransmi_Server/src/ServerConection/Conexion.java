package ServerConection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {
    
    private Connection connection;

    public Conexion() {
        String driver="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/dboptitransmi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user="root";
        String pass="wigeta777";
        try{
            Class.forName(driver);
            connection=DriverManager.getConnection(url, user, pass);
            connection.setAutoCommit(true);
            System.out.println("Base de datos conectada");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    
    public Statement getStatement(){
        try{
            return connection.createStatement();
        } catch(SQLException e){
            return null;
        }
    }
}
