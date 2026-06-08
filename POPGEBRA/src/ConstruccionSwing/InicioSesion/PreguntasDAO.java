package ConstruccionSwing.InicioSesion;

import mysql.MySQLConnect;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntasDAO {

    /**
     * Crea una pregunta con sus respuestas (opción múltiple) y marca la respuesta correcta.
     * @param idCuestionario id del cuestionario
     * @param numeroPregunta número de la pregunta dentro del cuestionario
     * @param textoPregunta texto de la pregunta
     * @param idTipoPregunta id del tipo (usar id existente para opción múltiple)
     * @param respuestas lista de textos de respuestas (mínimo 2)
     * @param indiceRespuestaCorrecta índice en la lista (0-based) de la respuesta correcta
     * @return id de la pregunta creada o -1 en error
     */
    public int createPreguntaWithRespuestas(int idCuestionario, int numeroPregunta, String textoPregunta,
                                            int idTipoPregunta, List<String> respuestas, int indiceRespuestaCorrecta) {
        if (respuestas == null || respuestas.size() < 2) {
            JOptionPane.showMessageDialog(null, "Se requieren al menos 2 respuestas.", "Validación", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        if (indiceRespuestaCorrecta < 0 || indiceRespuestaCorrecta >= respuestas.size()) {
            JOptionPane.showMessageDialog(null, "Índice de respuesta correcta inválido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return -1;
        }

        Connection conexion = null;
        try {
            conexion = new MySQLConnect().conectarMySQL();
            conexion.setAutoCommit(false);

            int idPregunta = getNextId(conexion, "Preguntas", "IdPregunta");
            String insertPregunta = "INSERT INTO Preguntas (IdPregunta, NumeroPregunta, Pregunta, IdTipoPregunta, IdCuestionario) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psPreg = conexion.prepareStatement(insertPregunta)) {
                psPreg.setInt(1, idPregunta);
                psPreg.setInt(2, numeroPregunta);
                psPreg.setString(3, textoPregunta);
                psPreg.setInt(4, idTipoPregunta);
                psPreg.setInt(5, idCuestionario);
                psPreg.executeUpdate();
            }

            // Insertar respuestas
            int baseRespuestaId = getNextId(conexion, "Respuestas", "IdRespuesta");
            String insertRespuesta = "INSERT INTO Respuestas (IdRespuesta, IdPregunta, Respuesta) VALUES (?, ?, ?)";
            int idRespuestaCorrecta = -1;
            try (PreparedStatement psResp = conexion.prepareStatement(insertRespuesta)) {
                for (int i = 0; i < respuestas.size(); i++) {
                    int idResp = baseRespuestaId + i;
                    psResp.setInt(1, idResp);
                    psResp.setInt(2, idPregunta);
                    psResp.setString(3, respuestas.get(i));
                    psResp.executeUpdate();
                    if (i == indiceRespuestaCorrecta) idRespuestaCorrecta = idResp;
                }
            }

            // Insertar respuesta correcta
            int idRespCorrectaRow = getNextId(conexion, "RespuestasCorrectas", "IdRespuestaCorrecta");
            String insertRespCorrecta = "INSERT INTO RespuestasCorrectas (IdRespuestaCorrecta, IdPregunta, IdRespuesta) VALUES (?, ?, ?)";
            try (PreparedStatement psRC = conexion.prepareStatement(insertRespCorrecta)) {
                psRC.setInt(1, idRespCorrectaRow);
                psRC.setInt(2, idPregunta);
                psRC.setInt(3, idRespuestaCorrecta);
                psRC.executeUpdate();
            }

            conexion.commit();
            JOptionPane.showMessageDialog(null, "Pregunta y respuestas creadas correctamente.", "Creación", JOptionPane.INFORMATION_MESSAGE);
            return idPregunta;
        } catch (SQLException ex) {
            try {
                if (conexion != null) conexion.rollback();
            } catch (SQLException e) {
                // ignore
            }
            JOptionPane.showMessageDialog(null, "Error al crear la pregunta:\n" + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            return -1;
        } finally {
            try {
                if (conexion != null) conexion.setAutoCommit(true);
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public List<String[]> getPreguntasByCuestionario(int idCuestionario) {
        List<String[]> preguntas = new ArrayList<>();
        String sql = "SELECT IdPregunta, NumeroPregunta, Pregunta, IdTipoPregunta FROM Preguntas WHERE IdCuestionario = ? ORDER BY NumeroPregunta";
        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCuestionario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    preguntas.add(new String[]{
                            rs.getString("IdPregunta"),
                            rs.getString("NumeroPregunta"),
                            rs.getString("Pregunta"),
                            rs.getString("IdTipoPregunta")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar las preguntas:\n" + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        return preguntas;
    }

    public List<String> getRespuestasByPregunta(int idPregunta) {
        List<String> respuestas = new ArrayList<>();
        String sql = "SELECT Respuesta FROM Respuestas WHERE IdPregunta = ? ORDER BY IdRespuesta";
        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPregunta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    respuestas.add(rs.getString("Respuesta"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar las respuestas:\n" + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        return respuestas;
    }

    public String getTipoPreguntaById(int idTipoPregunta) {
        String tipo = "";
        String sql = "SELECT TipoPregunta FROM TipoPregunta WHERE IdTipoPregunta = ?";
        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idTipoPregunta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tipo = rs.getString("TipoPregunta");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar el tipo de pregunta:\n" + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        return tipo;
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
