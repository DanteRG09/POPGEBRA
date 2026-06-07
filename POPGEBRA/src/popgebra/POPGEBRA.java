/*
*
*Ultima Modificacion: Dante
*
*/
package popgebra;


import ConstruccionSwing.CRUD_Registro;
import mysql.MySQLConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class POPGEBRA {
    /*public static void main(String[] args)  {
        
         MySQLConnect SQL = new MySQLConnect();
        // Llamas al método que tiene la clase y te devuelve una conexión
         Connection conn = SQL.conectarMySQL();
        // Query que usarás para hacer lo que necesites
        try{
            String sSQL =   "";

        // Query
        sSQL =  "INSERT INTO USERS (first_name, last_name) VALUES (?, ?)";
        // PreparedStatement
        PreparedStatement pstm = conn.prepareStatement(sSQL);
        }catch (SQLException e){
            System.out.println(e); 
        }
        
    }*/
    
    
    public static void main(String[] args) {
        CRUD_Registro registro = new CRUD_Registro();
        registro.Registro();
    }
    
}