package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuestionarioDAO {

    public int createCuestionario(String nombre, Date fechaCreacion, String matricula) {
        String sql = "INSERT INTO Cuestionarios (IdCuestionario, Nombre, FechaCreacion, Matricula) VALUES (?, ?, ?, ?)";
        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            int nextId = getNextId(conexion, "Cuestionarios", "IdCuestionario");
            stmt.setInt(1, nextId);
            stmt.setString(2, nombre);
            stmt.setDate(3, fechaCreacion);
            stmt.setString(4, matricula);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cuestionario creado correctamente.", "Creación", JOptionPane.INFORMATION_MESSAGE);
                return nextId;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo crear el cuestionario:\n" + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    public List<ComboItem> getCuestionariosByAsesor(String matricula) {
        List<ComboItem> list = new ArrayList<>();
        String sql = "SELECT IdCuestionario, Nombre FROM Cuestionarios WHERE Matricula = ? ORDER BY FechaCreacion DESC";
        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new ComboItem(rs.getString("IdCuestionario"), rs.getString("Nombre")));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los cuestionarios:\n" + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public List<String[]> getAllCuestionariosByAsesor(String matricula) {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT IdCuestionario, Nombre, FechaCreacion FROM Cuestionarios WHERE Matricula = ? ORDER BY FechaCreacion DESC";
        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new String[]{
                            rs.getString("IdCuestionario"),
                            rs.getString("Nombre"),
                            rs.getString("FechaCreacion")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los cuestionarios:\n" + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    private int getNextId(Connection conexion, String table, String idColumn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + idColumn + "), 0) + 1 AS nextId FROM " + table;
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("nextId");
        }
        return 1;
    }
}
