
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import javax.swing.JOptionPane;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    public Connection getConexao() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/leilao?useSSL=false",
                "root",
                "Ass4ssyn#"
            );
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;        
        }
        
    }
    
}

