package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsesoriasDAO {

    public int createAsesoria(String nombre, String periodoEscolar, int cupoDisponible,
                                  Date fechaIni, Date fechaFin, boolean statusActiva,
                                  boolean statusHorario, int idMateriaImpartida) {
        String sql = "INSERT INTO Asesorias (IdAsesoria, Nombre, PeriodoEscolar, CupoDisponible, " +
                "FechaIni, FechaFin, StatusActiva, StatusHorario, IdMateriaImpartida) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            int nextId = getNextId(conexion);
            stmt.setInt(1, nextId);
            stmt.setString(2, nombre);
            stmt.setString(3, periodoEscolar);
            stmt.setInt(4, cupoDisponible);
            stmt.setDate(5, fechaIni);
            stmt.setDate(6, fechaFin);
            stmt.setBoolean(7, statusActiva);
            stmt.setBoolean(8, statusHorario);
            stmt.setInt(9, idMateriaImpartida);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Asesoría creada correctamente.",
                        "Creación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                return nextId;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo crear la asesoría:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return -1;
    }

    public List<ComboItem> getAsesoriassByMateria(int idMateriaImpartida) {
        List<ComboItem> asesorias = new ArrayList<>();
        String sql = "SELECT IdAsesoria, Nombre FROM Asesorias WHERE IdMateriaImpartida = ? " +
                "ORDER BY Nombre";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idMateriaImpartida);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    asesorias.add(new ComboItem(rs.getString("IdAsesoria"), rs.getString("Nombre")));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las asesorías:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return asesorias;
    }

    public List<ComboItem> getAsesoriasActivas() {
        List<ComboItem> asesorias = new ArrayList<>();
        String sql = "SELECT IdAsesoria, Nombre FROM Asesorias WHERE StatusActiva = TRUE " +
                "ORDER BY Nombre";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                asesorias.add(new ComboItem(rs.getString("IdAsesoria"), rs.getString("Nombre")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las asesorías activas:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return asesorias;
    }

    public List<String[]> getAsesoriasActivasByAsesor(String matriculaAsesor) {
        List<String[]> asesorias = new ArrayList<>();
        // Detectar el nombre de columna en MateriasImpartidas (Matricula o MatriculaAsesor)
        String columnName = null;
        try (Connection conexion = new MySQLConnect().conectarMySQL()) {
            String colCheck = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'MateriasImpartidas'";
            StringBuilder found = new StringBuilder();
            try (PreparedStatement ps = conexion.prepareStatement(colCheck);
                 ResultSet rsCols = ps.executeQuery()) {
                while (rsCols.next()) {
                    String col = rsCols.getString("COLUMN_NAME");
                    found.append(col).append(",");
                    if ("Matricula".equalsIgnoreCase(col)) {
                        columnName = "Matricula";
                    } else if ("MatriculaAsesor".equalsIgnoreCase(col)) {
                        // preferir Matricula si existe; sólo asignar si Matricula no fue encontrada
                        if (columnName == null) columnName = "MatriculaAsesor";
                    }
                }
            }

            System.out.println("DEBUG: columnas encontradas en MateriasImpartidas: " + found.toString());
            if (columnName == null) {
                System.out.println("DEBUG: no se encontró 'Matricula' ni 'MatriculaAsesor'.");
                JOptionPane.showMessageDialog(null,
                        "No se encontró la columna 'Matricula' ni 'MatriculaAsesor' en MateriasImpartidas. Revise la consola para más detalles.",
                        "Error de base de datos",
                        JOptionPane.ERROR_MESSAGE);
                return asesorias;
            }

                String sql = "SELECT a.IdAsesoria, a.Nombre, m.NombreMateria, a.PeriodoEscolar, " +
                    "a.CupoDisponible, a.FechaIni, a.FechaFin " +
                    "FROM Asesorias a " +
                    "INNER JOIN MateriasImpartidas mi ON a.IdMateriaImpartida = mi.IdMateriaImpartida " +
                    "INNER JOIN Materia m ON mi.IdMateria = m.IdMateria " +
                    "WHERE mi." + columnName + " = ? AND a.StatusActiva = TRUE " +
                    "ORDER BY a.Nombre";

            System.out.println("DEBUG: SQL a ejecutar: " + sql);
            System.out.println("DEBUG: Param[1] = " + matriculaAsesor);

            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, matriculaAsesor);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String[] asesoria = {
                                rs.getString("IdAsesoria"),
                                rs.getString("Nombre"),
                                rs.getString("NombreMateria"),
                                rs.getString("PeriodoEscolar"),
                                rs.getString("CupoDisponible"),
                                rs.getString("FechaIni"),
                                rs.getString("FechaFin")
                        };
                        asesorias.add(asesoria);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("DEBUG: SQLException SQLState=" + ex.getSQLState() + " ErrorCode=" + ex.getErrorCode());
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las asesorías del asesor:\n" + ex.toString() + "\nRevise la consola para la traza completa.",
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return asesorias;
    }

    public boolean deleteAsesoria(int idAsesoria) {
        String sql = "DELETE FROM Asesorias WHERE IdAsesoria = ?";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAsesoria);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null,
                        "Asesoría eliminada correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo eliminar la asesoría:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public List<String[]> getAllAsesorias() {
        List<String[]> result = new ArrayList<>();
        String sql = "SELECT a.IdAsesoria, a.Nombre, m.NombreMateria, a.PeriodoEscolar, a.CupoDisponible, a.FechaIni, a.FechaFin, a.StatusActiva " +
                "FROM Asesorias a " +
                "INNER JOIN MateriasImpartidas mi ON a.IdMateriaImpartida = mi.IdMateriaImpartida " +
                "INNER JOIN Materia m ON mi.IdMateria = m.IdMateria " +
                "ORDER BY a.Nombre";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(new String[]{
                        rs.getString("IdAsesoria"),
                        rs.getString("Nombre"),
                        rs.getString("NombreMateria"),
                        rs.getString("PeriodoEscolar"),
                        rs.getString("CupoDisponible"),
                        rs.getString("FechaIni"),
                        rs.getString("FechaFin"),
                        rs.getString("StatusActiva")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las asesorías:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    private int getNextId(Connection conexion) throws SQLException {
        String sql = "SELECT COALESCE(MAX(IdAsesoria), 0) + 1 AS nextId FROM Asesorias";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("nextId");
            }
        }
        return 1;
    }

    public int getNextAsesoriId() {
        try (Connection conexion = new MySQLConnect().conectarMySQL()) {
            return getNextId(conexion);
        } catch (SQLException ex) {
            return 1;
        }
    }
}
