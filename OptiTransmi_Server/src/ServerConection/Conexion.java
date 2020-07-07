package ServerConection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    
    private Connection connection;

    public Conexion() {
        String driver="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://b6weauyywe5e2pwv3dse-mysql.services.clever-cloud.com:3306/b6weauyywe5e2pwv3dse";
        String user="u61iedj6a3olwg6n";
        String pass="nRryeH8CgyA10kEQlx9p";
        connection=null;
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
    
    private Statement getStatement(){
        try{
            return connection.createStatement();
        } catch(SQLException e){
            return null;
        }
    }
    
    public boolean executeSQL(String SQL){
        try {
            Statement st = getStatement();
            st.execute(SQL);
            return true;
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public ResultSet executeQuery(String query){
        try {
            Statement st = getStatement();
            return st.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
