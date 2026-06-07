package ConstruccionSwing.InicioSesion;

import ConstruccionSwing.PopGebraAsesorias;
import mysql.MySQLConnect;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author santi
 */

public class RegistroAsesorCompleto {
    private final String idUsuario;
    private JFrame VAsesor;
    private JLabel LBLMatriculaValue;

    public RegistroAsesorCompleto(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void Registro() {
        VAsesor = new JFrame("Completar registro - Asesor");
        VAsesor.setSize(500, 380);
        VAsesor.setLocationRelativeTo(null);
        VAsesor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VAsesor.setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(238, 238, 238));
        GridBagConstraints c = new GridBagConstraints();

        JLabel titulo = new JLabel("Datos faltantes para asesor");
        titulo.setFont(new Font("Courier New", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setFont(new Font("Courier New", Font.PLAIN, 18));
        LBLMatriculaValue = new JLabel(idUsuario, SwingConstants.CENTER);
        LBLMatriculaValue.setFont(new Font("Courier New", Font.PLAIN, 18));
        LBLMatriculaValue.setPreferredSize(new Dimension(250, 40));
        LBLMatriculaValue.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
        panel.add(lblMatricula, c);

        c.gridx = 1;
        panel.add(LBLMatriculaValue, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(30, 20, 20, 20);
        panel.add(btnGuardar, c);

        VAsesor.add(panel);

        btnGuardar.addActionListener(e -> {
            if (registrarAsesor(idUsuario)) {
                VAsesor.dispose();
                new PopGebraAsesorias().setVisible(true);
            }
        });

        VAsesor.setVisible(true);
    }

    private boolean registrarAsesor(String matricula) {
        return new AsesorDAO().saveAsesor(matricula, idUsuario);
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
}
