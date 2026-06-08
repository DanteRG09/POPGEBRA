package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuestionarioAsesoriaDAO {

    public boolean assignCuestionarioToAsesoria(int idCuestionario, int idAsesoria) {
        try (Connection conexion = new MySQLConnect().conectarMySQL()) {
            ensureRelationTableExists(conexion);
            String checkSql = "SELECT COUNT(*) FROM CuestionariosAsesorias WHERE IdCuestionario = ? AND IdAsesoria = ?";
            try (PreparedStatement checkStmt = conexion.prepareStatement(checkSql)) {
                checkStmt.setInt(1, idCuestionario);
                checkStmt.setInt(2, idAsesoria);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;
                    }
                }
            }

            String sql = "INSERT INTO CuestionariosAsesorias (IdRelacion, IdCuestionario, IdAsesoria) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                int nextId = getNextId(conexion, "CuestionariosAsesorias", "IdRelacion");
                stmt.setInt(1, nextId);
                stmt.setInt(2, idCuestionario);
                stmt.setInt(3, idAsesoria);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo asignar el cuestionario a la asesoría:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public List<String[]> getActiveAsesoriasWithCuestionarios(String matriculaAsesor) {
        List<String[]> result = new ArrayList<>();
        String columnName = detectMatriculaColumn();
        if (columnName == null) {
            JOptionPane.showMessageDialog(null,
                    "No se encontró la columna 'Matricula' ni 'MatriculaAsesor' en MateriasImpartidas.",
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
            return result;
        }
        String sql;
        try (Connection conexion = new MySQLConnect().conectarMySQL()) {
            ensureRelationTableExists(conexion);
            sql = "SELECT a.IdAsesoria, a.Nombre AS NombreAsesoria, m.NombreMateria, " +
                    "GROUP_CONCAT(c.IdCuestionario ORDER BY c.IdCuestionario SEPARATOR ', ') AS IdCuestionario, " +
                    "GROUP_CONCAT(c.Nombre ORDER BY c.IdCuestionario SEPARATOR ', ') AS NombreCuestionario " +
                    "FROM Asesorias a " +
                    "INNER JOIN MateriasImpartidas mi ON a.IdMateriaImpartida = mi.IdMateriaImpartida " +
                    "INNER JOIN Materia m ON mi.IdMateria = m.IdMateria " +
                    "LEFT JOIN CuestionariosAsesorias ca ON ca.IdAsesoria = a.IdAsesoria " +
                    "LEFT JOIN Cuestionarios c ON c.IdCuestionario = ca.IdCuestionario " +
                    "WHERE mi." + columnName + " = ? AND a.StatusActiva = TRUE " +
                    "GROUP BY a.IdAsesoria, a.Nombre, m.NombreMateria " +
                    "ORDER BY a.Nombre";

            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, matriculaAsesor);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        result.add(new String[]{
                                rs.getString("IdAsesoria"),
                                rs.getString("NombreAsesoria"),
                                rs.getString("NombreMateria"),
                                rs.getString("IdCuestionario"),
                                rs.getString("NombreCuestionario")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las asesorías con cuestionarios:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    private String detectMatriculaColumn() {
        String columnName = null;
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'MateriasImpartidas' " +
                "AND (COLUMN_NAME = 'Matricula' OR COLUMN_NAME = 'MatriculaAsesor')";
        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String col = rs.getString("COLUMN_NAME");
                if ("Matricula".equalsIgnoreCase(col)) {
                    columnName = "Matricula";
                    break;
                }
                if ("MatriculaAsesor".equalsIgnoreCase(col)) {
                    columnName = "MatriculaAsesor";
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al detectar columna de matrícula:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return columnName;
    }

    public List<ComboItem> getCuestionariosByAsesoria(int idAsesoria) {
        List<ComboItem> items = new ArrayList<>();
        String sql = "SELECT c.IdCuestionario, c.Nombre FROM CuestionariosAsesorias ca " +
                "INNER JOIN Cuestionarios c ON ca.IdCuestionario = c.IdCuestionario " +
                "WHERE ca.IdAsesoria = ? ORDER BY c.Nombre";
        try (Connection conexion = new MySQLConnect().conectarMySQL()) {
            ensureRelationTableExists(conexion);
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setInt(1, idAsesoria);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        items.add(new ComboItem(rs.getString("IdCuestionario"), rs.getString("Nombre")));
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los cuestionarios de la asesoría:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return items;
    }

    private int getNextId(Connection conexion, String tableName, String idColumn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + idColumn + "), 0) + 1 AS nextId FROM " + tableName;
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("nextId");
        }
        return 1;
    }

    private boolean tableExists(Connection conexion, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private void ensureRelationTableExists(Connection conexion) throws SQLException {
        if (!tableExists(conexion, "CuestionariosAsesorias")) {
            String sql = "CREATE TABLE IF NOT EXISTS CuestionariosAsesorias (" +
                    "IdRelacion INT PRIMARY KEY, " +
                    "IdCuestionario INT NOT NULL, " +
                    "IdAsesoria INT NOT NULL, " +
                    "CONSTRAINT FK_CuestionariosAsesorias_Cuestionario FOREIGN KEY (IdCuestionario) REFERENCES Cuestionarios(IdCuestionario), " +
                    "CONSTRAINT FK_CuestionariosAsesorias_Asesoria FOREIGN KEY (IdAsesoria) REFERENCES Asesorias(IdAsesoria), " +
                    "CONSTRAINT UQ_Cuestionario_Asesoria UNIQUE (IdCuestionario, IdAsesoria)" +
                    ")";
            try (Statement stmt = conexion.createStatement()) {
                stmt.executeUpdate(sql);
            }
        }
    }
}
