package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorariosDAO {

    public boolean addHorario(Time horarioIni, Time horarioFin, String diasAsignados, int idAsesoria) {
        String sql = "INSERT INTO Horarios (IdHorario, HorarioIni, HorarioFin, DiasAsignados, IdAsesoria) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, getNextId(conexion));
            stmt.setTime(2, horarioIni);
            stmt.setTime(3, horarioFin);
            stmt.setString(4, diasAsignados);
            stmt.setInt(5, idAsesoria);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo agregar el horario:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public List<String[]> getHorariosByAsesoria(int idAsesoria) {
        List<String[]> horarios = new ArrayList<>();
        String sql = "SELECT IdHorario, HorarioIni, HorarioFin, DiasAsignados FROM Horarios " +
                "WHERE IdAsesoria = ? ORDER BY HorarioIni";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAsesoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] horario = {
                            rs.getString("IdHorario"),
                            rs.getString("HorarioIni"),
                            rs.getString("HorarioFin"),
                            rs.getString("DiasAsignados")
                    };
                    horarios.add(horario);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los horarios:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return horarios;
    }

    public boolean deleteHorario(int idHorario) {
        String sql = "DELETE FROM Horarios WHERE IdHorario = ?";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idHorario);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo eliminar el horario:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    private int getNextId(Connection conexion) throws SQLException {
        String sql = "SELECT COALESCE(MAX(IdHorario), 0) + 1 AS nextId FROM Horarios";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("nextId");
            }
        }
        return 1;
    }
}
