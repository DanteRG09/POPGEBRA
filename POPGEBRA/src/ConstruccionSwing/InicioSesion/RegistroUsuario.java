package ConstruccionSwing.InicioSesion;

import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;
import GlobalConfig.Texto;
import mysql.MySQLConnect;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author santi
 */
public class RegistroUsuario {
    private TamañosColoresPosicion valores = new TamañosColoresPosicion();
    private Texto Fuente = new Texto();

    private JFrame VRegistro = new JFrame("Registro");
    private JComponentOval InterfazRegistro = new JComponentOval(60);
    private JPanel contenedorFormulario;
    private JLabel LBLTitulo;
    private JLabel LBLSubtitulo;
    private JLabel LBLTipoUsuario;
    private JTextField TXTFUsuario = new JTextField("Ingrese su boleta o su matrícula", 20);
    private JTextField TXTFNombre = new JTextField("Ingrese su Nombre", 20);
    private JTextField TXTFApellido = new JTextField("Ingrese su Apellido", 20);
    private JPasswordField TXTFContraseña = new JPasswordField("Ingrese su Contraseña", 20);
    private JComboBox<String> CBXTipoUsuario;
    JComponentOvalBtn BTNEnviar = new JComponentOvalBtn(30);
    JComponentOvalBtn BTNLogin;
    JComponentOvalBtn BTNVolver;

    public void Registro() {
        VRegistro.setLayout(new GridBagLayout());
        InterfazRegistro.setLayout(new GridBagLayout());
        contenedorFormulario = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        VRegistro.setSize(valores.getTamañoVentana(0), valores.getTamañoVentana(1));
        InterfazRegistro.setPreferredSize(valores.getPosicionDelPanelRegistro());
        contenedorFormulario.setPreferredSize(valores.getPosicionFormularioR());

        LBLTitulo = new JLabel("Registro", SwingConstants.CENTER);
        LBLSubtitulo = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        LBLTipoUsuario = new JLabel("Tipo de usuario", SwingConstants.CENTER);

        CBXTipoUsuario = new JComboBox<>(new String[]{"Alumno", "Asesor"});
        CBXTipoUsuario.setFont(new Font("Courier New", Font.PLAIN, 18));
        Dimension comboDimension = new Dimension(260, 40);
        CBXTipoUsuario.setPreferredSize(comboDimension);
        CBXTipoUsuario.setMinimumSize(comboDimension);
        CBXTipoUsuario.setMaximumSize(comboDimension);
        CBXTipoUsuario.setBackground(Color.WHITE);
        CBXTipoUsuario.setForeground(Color.BLACK);

        BTNEnviar.setText("CONTINUAR");
        BTNEnviar.setFont(new Font("Courier New", Font.BOLD, 18));
        BTNEnviar.setPreferredSize(new Dimension(220, 50));
        BTNEnviar.setBackground(new Color(243, 180, 45));
        BTNEnviar.setForeground(Color.WHITE);
        BTNEnviar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        BTNLogin = new JComponentOvalBtn(30);
        BTNLogin.setText("INICIAR SESIÓN");
        BTNLogin.setPreferredSize(new Dimension(220, 50));
        BTNLogin.setBackground(new Color(220, 80, 80));
        BTNLogin.setForeground(Color.WHITE);
        BTNLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        BTNVolver = new JComponentOvalBtn(30);
        BTNVolver.setText("VOLVER");
        BTNVolver.setPreferredSize(new Dimension(220, 50));
        BTNVolver.setBackground(new Color(160, 160, 160));
        BTNVolver.setForeground(Color.WHITE);
        BTNVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        VRegistro.getContentPane().setBackground(Color.decode(valores.getColorFondo()));
        InterfazRegistro.setBackground(Color.decode(valores.getColorPanel()));
        InterfazRegistro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
        contenedorFormulario.setOpaque(false);

        Font campoFont = new Font("Courier New", Font.PLAIN, 18);
        Border campoBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12));
        Dimension campoDimension = new Dimension(360, 45);

        TXTFUsuario.setFont(campoFont);
        TXTFUsuario.setHorizontalAlignment(JTextField.CENTER);
        TXTFUsuario.setOpaque(true);
        TXTFUsuario.setBackground(Color.WHITE);
        TXTFUsuario.setForeground(Color.GRAY);
        TXTFUsuario.setBorder(campoBorder);
        TXTFUsuario.setPreferredSize(campoDimension);
        TXTFUsuario.setMinimumSize(campoDimension);
        TXTFUsuario.setMaximumSize(campoDimension);

        TXTFNombre.setFont(campoFont);
        TXTFNombre.setHorizontalAlignment(JTextField.CENTER);
        TXTFNombre.setOpaque(true);
        TXTFNombre.setBackground(Color.WHITE);
        TXTFNombre.setForeground(Color.GRAY);
        TXTFNombre.setBorder(campoBorder);
        TXTFNombre.setPreferredSize(campoDimension);
        TXTFNombre.setMinimumSize(campoDimension);
        TXTFNombre.setMaximumSize(campoDimension);

        TXTFApellido.setFont(campoFont);
        TXTFApellido.setHorizontalAlignment(JTextField.CENTER);
        TXTFApellido.setOpaque(true);
        TXTFApellido.setBackground(Color.WHITE);
        TXTFApellido.setForeground(Color.GRAY);
        TXTFApellido.setBorder(campoBorder);
        TXTFApellido.setPreferredSize(campoDimension);
        TXTFApellido.setMinimumSize(campoDimension);
        TXTFApellido.setMaximumSize(campoDimension);

        TXTFContraseña = new JPasswordField("Ingrese su Contraseña", 20);
        TXTFContraseña.setFont(campoFont);
        TXTFContraseña.setHorizontalAlignment(JTextField.CENTER);
        TXTFContraseña.setOpaque(true);
        TXTFContraseña.setBackground(Color.WHITE);
        TXTFContraseña.setForeground(Color.GRAY);
        TXTFContraseña.setBorder(campoBorder);
        TXTFContraseña.setPreferredSize(campoDimension);
        TXTFContraseña.setMinimumSize(campoDimension);
        TXTFContraseña.setMaximumSize(campoDimension);
        TXTFContraseña.setEchoChar((char) 0);

        LBLTitulo.setForeground(Color.BLACK);
        LBLSubtitulo.setForeground(Color.BLACK);
        LBLTipoUsuario.setForeground(Color.BLACK);

        LBLTitulo.setFont(new Font("Courier New", Font.BOLD, 45));
        LBLSubtitulo.setFont(new Font("Courier New", Font.PLAIN, 25));
        LBLTipoUsuario.setFont(new Font("Courier New", Font.PLAIN, 22));

        configurarPlaceholder(TXTFUsuario, "Ingrese su boleta o su matrícula");
        configurarPlaceholder(TXTFNombre, "Ingrese su Nombre");
        configurarPlaceholder(TXTFApellido, "Ingrese su Apellido");
        configurarPlaceholder((JTextField) TXTFContraseña, "Ingrese su Contraseña");

        VRegistro.add(InterfazRegistro);
        InterfazRegistro.add(contenedorFormulario);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weighty = 0.0;
        c.insets = new Insets(0, 0, 0, 0);
        contenedorFormulario.add(LBLTitulo, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 15, 0);
        contenedorFormulario.add(LBLSubtitulo, c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 12, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        contenedorFormulario.add(TXTFUsuario, c);

        c.gridy = 3;
        contenedorFormulario.add(TXTFNombre, c);

        c.gridy = 4;
        contenedorFormulario.add(TXTFApellido, c);

        c.gridy = 5;
        contenedorFormulario.add(TXTFContraseña, c);

        c.gridy = 6;
        c.insets = new Insets(20, 0, 10, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        contenedorFormulario.add(LBLTipoUsuario, c);

        c.gridy = 7;
        c.insets = new Insets(0, 0, 20, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        contenedorFormulario.add(CBXTipoUsuario, c);

        c.gridy = 8;
        c.insets = new Insets(10, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        contenedorFormulario.add(BTNEnviar, c);

        c.gridy = 9;
        c.insets = new Insets(10, 0, 0, 0);
        contenedorFormulario.add(BTNLogin, c);

        c.gridy = 10;
        c.insets = new Insets(10, 0, 20, 0);
        contenedorFormulario.add(BTNVolver, c);

        BTNLogin.addActionListener(e -> {
            VRegistro.dispose();
            new Login().show();
        });

        BTNVolver.addActionListener(e -> {
            VRegistro.dispose();
            new ConstruccionSwing.PantallaBienvenida().setVisible(true);
        });

        BTNEnviar.addActionListener(e -> {
            String idUsuario = TXTFUsuario.getText().trim();
            String nombre = TXTFNombre.getText().trim();
            String apellido = TXTFApellido.getText().trim();
            String contrasena = new String(((JPasswordField) TXTFContraseña).getPassword()).trim();
            String tipoUsuario = CBXTipoUsuario.getSelectedItem().toString();

            if (!validarCampo(idUsuario, "Ingrese su boleta o su matrícula") ||
                !validarCampo(nombre, "Ingrese su Nombre") ||
                !validarCampo(apellido, "Ingrese su Apellido") ||
                !validarCampo(contrasena, "Ingrese su Contraseña")) {
                JOptionPane.showMessageDialog(VRegistro,
                        "Por favor llena todos los campos de usuario correctamente.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (registrarUsuario(idUsuario, nombre, apellido, contrasena, tipoUsuario)) {
                if (tipoUsuario.equals("Alumno")) {
                    new RegistroAlumnoCompleto(idUsuario).Registro();
                } else {
                    new RegistroAsesorCompleto(idUsuario).Registro();
                }
                VRegistro.dispose();
            }
        });

        VRegistro.setVisible(true);
        VRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean validarCampo(String texto, String placeholder) {
        return texto != null && !texto.isEmpty() && !texto.equals(placeholder);
    }

    private boolean registrarUsuario(String idUsuario, String nombre, String apellido, String contrasena, String tipoUsuario) {
        return new UsuarioDAO().saveUsuario(idUsuario, nombre, apellido, contrasena, tipoUsuario);
    }

    private void configurarPlaceholder(JTextField campo, String textoGuia) {
        if (campo instanceof JPasswordField) {
            ((JPasswordField) campo).setEchoChar((char) 0);
        }
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(textoGuia)) {
                    campo.setText("");
                    if (campo instanceof JPasswordField) {
                        ((JPasswordField) campo).setEchoChar('•');
                    }
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(textoGuia);
                    if (campo instanceof JPasswordField) {
                        ((JPasswordField) campo).setEchoChar((char) 0);
                    }
                    campo.setForeground(Color.GRAY);
                }
            }
        });
        campo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (campo.getText().equals(textoGuia)) {
                    campo.setText("");
                    if (campo instanceof JPasswordField) {
                        ((JPasswordField) campo).setEchoChar('•');
                    }
                    campo.setForeground(Color.BLACK);
                }
            }
        });
    }
}
