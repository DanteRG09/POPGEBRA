package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriasImpartidasDAO {

    public boolean assignMateria(int idMateria, String matricula) {
        if (existsAssignment(idMateria, matricula)) {
            JOptionPane.showMessageDialog(null,
                    "La materia ya está asignada a este asesor.",
                    "Asignación duplicada",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO MateriasImpartidas (IdMateriaImpartida, IdMateria, Matricula) VALUES (?, ?, ?)";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, getNextId(conexion));
            stmt.setInt(2, idMateria);
            stmt.setString(3, matricula);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Materia asignada correctamente.",
                        "Asignación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo asignar la materia:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public List<ComboItem> getMateriasByAsesor(String matricula) {
        List<ComboItem> materias = new ArrayList<>();
        String sql = "SELECT mi.IdMateriaImpartida, m.NombreMateria " +
                "FROM MateriasImpartidas mi " +
                "JOIN Materia m ON mi.IdMateria = m.IdMateria " +
                "WHERE mi.Matricula = ? ORDER BY m.IdSemestre, m.NombreMateria";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materias.add(new ComboItem(rs.getString("IdMateriaImpartida"), rs.getString("NombreMateria")));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar materias asignadas:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return materias;
    }

    public List<Integer> getMateriaIdsByAsesor(String matricula) {
        List<Integer> materias = new ArrayList<>();
        String sql = "SELECT IdMateria FROM MateriasImpartidas WHERE Matricula = ?";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materias.add(rs.getInt("IdMateria"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los IDs de las materias asignadas:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return materias;
    }

    public boolean canDeleteAssignment(int idMateriaImpartida) {
        String sql = "SELECT COUNT(*) AS total FROM Asesorias WHERE IdMateriaImpartida = ? AND StatusActiva = TRUE";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idMateriaImpartida);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") == 0;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al verificar asesorías activas:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean deleteAssignment(int idMateriaImpartida) {
        if (!canDeleteAssignment(idMateriaImpartida)) {
            JOptionPane.showMessageDialog(null,
                    "No se puede eliminar esta asignación porque tiene asesorías activas.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String sql = "DELETE FROM MateriasImpartidas WHERE IdMateriaImpartida = ?";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idMateriaImpartida);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Asignación eliminada correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo eliminar la asignación:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    private int getNextId(Connection conexion) throws SQLException {
        String sql = "SELECT COALESCE(MAX(IdMateriaImpartida), 0) + 1 AS nextId FROM MateriasImpartidas";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("nextId");
            }
        }
        return 1;
    }

    private boolean existsAssignment(int idMateria, String matricula) {
        String sql = "SELECT COUNT(*) AS total FROM MateriasImpartidas WHERE IdMateria = ? AND Matricula = ?";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idMateria);
            stmt.setString(2, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al verificar asignación:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
}
