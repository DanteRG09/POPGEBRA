package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsesorDAO {

    public List<ComboItem> getAllAsesores() {
        List<ComboItem> asesores = new ArrayList<>();
        String sql = "SELECT a.Matricula, u.Nombre, u.Apellido " +
                "FROM Asesor a " +
                "LEFT JOIN Usuario u ON a.IdUsuario = u.IdUsuario " +
                "ORDER BY a.Matricula";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String matricula = rs.getString("Matricula");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String label = matricula;
                if (nombre != null && apellido != null) {
                    label = String.format("%s - %s %s", matricula, nombre, apellido);
                }
                asesores.add(new ComboItem(matricula, label));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar asesores:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return asesores;
    }

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
