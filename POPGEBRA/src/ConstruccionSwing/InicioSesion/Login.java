package ConstruccionSwing.InicioSesion;

import ConstruccionSwing.PopGebraAsesorias;
import ConstruccionSwing.PopGebraUI;

import javax.swing.*;
import java.awt.*;

public class Login {
    private JFrame VLogin;
    private JTextField TXTFUsuario;
    private JPasswordField PWFContrasena;

    public void show() {
        VLogin = new JFrame("Iniciar Sesión");
        VLogin.setSize(420, 320);
        VLogin.setLocationRelativeTo(null);
        VLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VLogin.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Courier New", Font.BOLD, 28));

        TXTFUsuario = new JTextField(20);
        PWFContrasena = new JPasswordField(20);

        JButton btnIngresar = new JButton("Ingresar");

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        VLogin.add(lblTitulo, c);

        c.gridy = 1;
        c.gridwidth = 1;
        VLogin.add(new JLabel("Usuario:"), c);
        c.gridx = 1;
        VLogin.add(TXTFUsuario, c);

        c.gridx = 0;
        c.gridy = 2;
        VLogin.add(new JLabel("Contraseña:"), c);
        c.gridx = 1;
        VLogin.add(PWFContrasena, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        VLogin.add(btnIngresar, c);

        btnIngresar.addActionListener(e -> authenticateAndRoute());

        VLogin.setVisible(true);
    }

    private void authenticateAndRoute() {
        String usuario = TXTFUsuario.getText().trim();
        String contrasena = new String(PWFContrasena.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(VLogin,
                    "Por favor ingresa usuario y contraseña.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipo = new UsuarioDAO().authenticateUser(usuario, contrasena);

        if (tipo == null) {
            JOptionPane.showMessageDialog(VLogin,
                    "Usuario o contraseña incorrectos.",
                    "Autenticación fallida",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        VLogin.dispose();
        if (tipo.equalsIgnoreCase("Alumno")) {
            new PopGebraUI().setVisible(true);
        } else {
            new PopGebraAsesorias().setVisible(true);
        }
    }
}
