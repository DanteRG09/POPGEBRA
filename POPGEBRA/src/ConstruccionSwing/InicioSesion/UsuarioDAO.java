package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean saveUsuario(String idUsuario, String nombre, String apellido, String contrasena, String tipoUsuario) {
        String sql = "INSERT INTO Usuario (IdUsuario, Nombre, Apellido, Contrasena, TipoUsuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, contrasena);
            stmt.setString(5, tipoUsuario);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Usuario registrado correctamente. Ahora completa los datos faltantes.",
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo registrar el usuario:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
}
