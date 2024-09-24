/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectaco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author LenovoLap
 */
public class ConexionBD {
    Connection con;
    
    public Connection Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/maderos","root","");
            System.out.println("OK :)");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error " + e);
        }
        return con;
    }
    
    public void CerrarConexion(){
        try{
            con.close();
            System.out.println("Sesion cerrada con exito");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
