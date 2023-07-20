package Clases;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Daniel
 */
public class ConexionORCL {
    Connection conectar=null;
    public Connection conexion(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conectar=DriverManager.getConnection("jdbc:oracle:thin:@id","user","user");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return conectar;
    }    
}

