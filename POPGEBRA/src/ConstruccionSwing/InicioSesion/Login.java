package ConstruccionSwing.InicioSesion;

import ConstruccionSwing.AdminHome;
import ConstruccionSwing.PopGebraAsesorias;
import ConstruccionSwing.PopGebraUI;
import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;
import GlobalConfig.Texto;

import javax.swing.*;
import java.awt.*;

public class Login {
    private TamañosColoresPosicion valores = new TamañosColoresPosicion();
    private Texto Fuente = new Texto();

    private JFrame VLogin;
    private JComponentOval InterfazLogin;
    private JPanel contenedorFormulario;
    private JTextField TXTFUsuario;
    private JPasswordField PWFContrasena;
    private JComponentOvalBtn BTNIngresar;

    public void show() {
        VLogin = new JFrame("Iniciar Sesión");
        VLogin.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        VLogin.setSize(valores.getTamañoVentana(0), valores.getTamañoVentana(1));
        InterfazLogin = new JComponentOval(60);
        InterfazLogin.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        contenedorFormulario = new JPanel(new GridBagLayout());
        contenedorFormulario.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        InterfazLogin.setPreferredSize(valores.getPosicionDelPanelRegistro());

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Courier New", Font.BOLD, 45));

        TXTFUsuario = new JTextField("Ingrese su usuario", 20);
        PWFContrasena = new JPasswordField(20);
        BTNIngresar = new JComponentOvalBtn(30);
        BTNIngresar.setText("INGRESAR");

        TXTFUsuario.setHorizontalAlignment(JTextField.CENTER);
        PWFContrasena.setHorizontalAlignment(JTextField.CENTER);
        BTNIngresar.setPreferredSize(new Dimension(200, 50));

        configurarPlaceholder(TXTFUsuario, "Ingrese su usuario");

        VLogin.getContentPane().setBackground(Color.decode(valores.getColorFondo()));
        InterfazLogin.setBackground(Color.decode(valores.getColorPanel()));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);
        contenedorFormulario.add(lblTitulo, c);

        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 10, 0);
        contenedorFormulario.add(TXTFUsuario, c);

        c.gridy = 2;
        contenedorFormulario.add(PWFContrasena, c);

        c.gridy = 3;
        c.insets = new Insets(20, 0, 0, 0);
        contenedorFormulario.add(BTNIngresar, c);

        InterfazLogin.add(contenedorFormulario);
        VLogin.add(InterfazLogin);

        BTNIngresar.addActionListener(e -> authenticateAndRoute());

        VLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VLogin.setVisible(true);
    }

    private void authenticateAndRoute() {
        String usuario = TXTFUsuario.getText().trim();
        String contrasena = new String(PWFContrasena.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty() || usuario.equals("Ingrese su usuario")) {
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
        } else if (tipo.equalsIgnoreCase("Asesor")) {
            new PopGebraAsesorias().setVisible(true);
        } else if (tipo.equalsIgnoreCase("Administrador") || tipo.equalsIgnoreCase("Admin")) {
            new AdminHome().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(VLogin,
                    "Tipo de usuario desconocido: " + tipo,
                    "Autenticación fallida",
                    JOptionPane.ERROR_MESSAGE);
        }
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
