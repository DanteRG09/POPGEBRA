package ConstruccionSwing.InicioSesion;


import ConstruccionSwing.PopGebraUI;
import mysql.MySQLConnect;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author santi
 */

public class RegistroAlumnoCompleto {

    private final String idUsuario;
    private JFrame VAlumno;
    private JLabel LBLBoletaValue;
    private JComboBox<ComboItem> CBXGrupo;
    private JComboBox<ComboItem> CBXCarrera;

    public RegistroAlumnoCompleto(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void Registro() {
        VAlumno = new JFrame("Completar registro - Alumno");
        VAlumno.setSize(520, 520);
        VAlumno.setLocationRelativeTo(null);
        VAlumno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VAlumno.setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(238, 238, 238));
        GridBagConstraints c = new GridBagConstraints();

        JLabel titulo = new JLabel("Datos faltantes para alumno");
        titulo.setFont(new Font("Courier New", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblBoleta = new JLabel("Boleta:");
        lblBoleta.setFont(new Font("Courier New", Font.PLAIN, 18));
        LBLBoletaValue = new JLabel(idUsuario, SwingConstants.CENTER);
        LBLBoletaValue.setFont(new Font("Courier New", Font.PLAIN, 18));
        LBLBoletaValue.setPreferredSize(new Dimension(250, 40));
        LBLBoletaValue.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel lblGrupo = new JLabel("Grupo:");
        lblGrupo.setFont(new Font("Courier New", Font.PLAIN, 18));
        CBXGrupo = new JComboBox<>();

        JLabel lblCarrera = new JLabel("Carrera:");
        lblCarrera.setFont(new Font("Courier New", Font.PLAIN, 18));
        CBXCarrera = new JComboBox<>();

        JButton btnGuardar = new JButton("Finalizar registro");
        btnGuardar.setFont(new Font("Courier New", Font.BOLD, 18));
        btnGuardar.setBackground(new Color(243, 180, 45));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(260, 50));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(20, 20, 20, 20);
        panel.add(titulo, c);

        c.gridwidth = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 20, 10, 20);
        panel.add(lblBoleta, c);

        c.gridx = 1;
        panel.add(LBLBoletaValue, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(lblGrupo, c);

        c.gridx = 1;
        panel.add(CBXGrupo, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(lblCarrera, c);

        c.gridx = 1;
        panel.add(CBXCarrera, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(30, 20, 20, 20);
        panel.add(btnGuardar, c);

        VAlumno.add(panel);

        cargarGrupos();
        cargarCarreras();

        btnGuardar.addActionListener(e -> {
            ComboItem grupo = (ComboItem) CBXGrupo.getSelectedItem();
            ComboItem carrera = (ComboItem) CBXCarrera.getSelectedItem();

            if (grupo == null || carrera == null) {
                JOptionPane.showMessageDialog(VAlumno,
                        "Por favor completa todos los campos.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (registrarAlumno(idUsuario, grupo.getKey(), carrera.getKey())) {
                VAlumno.dispose();
                new PopGebraUI().setVisible(true);
            }
        });

        VAlumno.setVisible(true);
    }

    private void cargarGrupos() {
        List<ComboItem> grupos = new ArrayList<>();
        String sql = "SELECT IDGrupo FROM Grupo";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                grupos.add(new ComboItem(rs.getString("IDGrupo"), rs.getString("IDGrupo")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(VAlumno,
                    "Error al cargar los grupos:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        for (ComboItem grupo : grupos) {
            CBXGrupo.addItem(grupo);
        }
    }

    private void cargarCarreras() {
        List<ComboItem> carreras = new ArrayList<>();
        String sql = "SELECT IdCarrera, NombreCarrera FROM Carrera";

        try (Connection conexion = new MySQLConnect().conectarMySQL();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                carreras.add(new ComboItem(rs.getString("IdCarrera"), rs.getString("NombreCarrera")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(VAlumno,
                    "Error al cargar las carreras:\n" + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }

        for (ComboItem carrera : carreras) {
            CBXCarrera.addItem(carrera);
        }
    }

    private boolean registrarAlumno(String boleta, String grupo, String carrera) {
        return new AlumnoDAO().saveAlumno(boleta, idUsuario, grupo, carrera);
    }

    private void configurarPlaceholder(JTextField campo, String textoGuia) {
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campo.getText().equals(textoGuia)) {
                    campo.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(textoGuia);
                }
            }
        });
    }

    private static class ComboItem {
        private final String key;
        private final String label;

        public ComboItem(String key, String label) {
            this.key = key;
            this.label = label;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
