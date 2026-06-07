package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsesorDAO {

    public boolean saveAsesor(String matricula, String idUsuario) {
        String sql = "INSERT INTO Asesor (Matricula, IdUsuario) VALUES (?, ?)";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            stmt.setString(2, idUsuario);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Registro de asesor completado correctamente.",
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo completar el registro de asesor:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
}
