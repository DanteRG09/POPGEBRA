package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

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

    public String authenticateUser(String idUsuarioOrNombre, String contrasena) {
        String sql = "SELECT TipoUsuario FROM Usuario WHERE (IdUsuario = ? OR Nombre = ?) AND Contrasena = ?";
        System.out.println("[Login] Intentando autenticar: " + idUsuarioOrNombre);

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, idUsuarioOrNombre);
            stmt.setString(2, idUsuarioOrNombre);
            stmt.setString(3, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("TipoUsuario");
                    System.out.println("[Login] Autenticación exitosa, tipoUsuario=" + tipo);
                    return tipo;
                } else {
                    System.out.println("[Login] Autenticación fallida: credenciales no coinciden");
                }
            }
        } catch (SQLException ex) {
            System.out.println("[Login] Error al autenticar usuario: " + ex.getMessage());
            ex.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null,
                    "Error al autenticar usuario:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }
}
