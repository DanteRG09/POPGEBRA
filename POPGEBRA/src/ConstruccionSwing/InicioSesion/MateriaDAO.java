package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {

    public List<ComboItem> getAllMaterias() {
        List<ComboItem> materias = new ArrayList<>();
        String sql = "SELECT IdMateria, NombreMateria FROM Materia ORDER BY IdSemestre, NombreMateria";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                materias.add(new ComboItem(rs.getString("IdMateria"), rs.getString("NombreMateria")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las materias:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        return materias;
    }
}
