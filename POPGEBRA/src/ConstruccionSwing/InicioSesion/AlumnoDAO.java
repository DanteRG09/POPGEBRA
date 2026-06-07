package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlumnoDAO {

    public boolean saveAlumno(String boleta, String idUsuario, String grupo, String carrera) {
        String sql = "INSERT INTO Alumno (Boleta, IdUsuario, IDGrupo, IdCarrera) VALUES (?, ?, ?, ?)";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, boleta);
            stmt.setString(2, idUsuario);
            stmt.setString(3, grupo);
            stmt.setString(4, carrera);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Registro de alumno completado correctamente.",
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo completar el registro de alumno:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
}
