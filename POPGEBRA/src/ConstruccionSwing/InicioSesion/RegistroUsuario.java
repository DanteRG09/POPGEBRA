package ConstruccionSwing.InicioSesion;

import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;
import GlobalConfig.Texto;
import mysql.MySQLConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
    private JLabel LBLEnviar;
    private JTextField TXTFUsuario = new JTextField("Ingrese su usuario", 20);
    private JTextField TXTFNombre = new JTextField("Ingrese su Nombre", 20);
    private JTextField TXTFApellido = new JTextField("Ingrese su Apellido", 20);
    private JTextField TXTFContraseña = new JTextField("Ingrese su Contraseña", 20);
    private JComboBox<String> CBXTipoUsuario;
    JComponentOvalBtn BTNEnviar = new JComponentOvalBtn(30);

    public void Registro() {
        VRegistro.setLayout(new GridBagLayout());
        InterfazRegistro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        contenedorFormulario = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        VRegistro.setSize(valores.getTamañoVentana(0), valores.getTamañoVentana(1));
        InterfazRegistro.setPreferredSize(valores.getPosicionDelPanelRegistro());
        contenedorFormulario.setPreferredSize(valores.getPosicionFormularioR());

        LBLTitulo = new JLabel("Registro", SwingConstants.CENTER);
        LBLSubtitulo = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        LBLTipoUsuario = new JLabel("Tipo de usuario", SwingConstants.CENTER);
        LBLEnviar = new JLabel("Continuar", SwingConstants.CENTER);

       
        TXTFNombre.setHorizontalAlignment(JTextField.CENTER);
        TXTFApellido.setHorizontalAlignment(JTextField.CENTER);
        TXTFContraseña.setHorizontalAlignment(JTextField.CENTER);

        CBXTipoUsuario = new JComboBox<>(new String[]{"Alumno", "Asesor"});
        CBXTipoUsuario.setFont(new Font("Courier New", Font.PLAIN, 18));
        CBXTipoUsuario.setPreferredSize(new Dimension(250, 40));
        CBXTipoUsuario.setBackground(Color.WHITE);

        BTNEnviar.setText("CONTINUAR");
        BTNEnviar.setFont(new Font("Courier New", Font.BOLD, 18));
        BTNEnviar.setPreferredSize(new Dimension(200, 50));
        BTNEnviar.setBackground(new Color(243, 180, 45));
        BTNEnviar.setForeground(Color.WHITE);
        BTNEnviar.setContentAreaFilled(false);
        BTNEnviar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        VRegistro.getContentPane().setBackground(Color.decode(valores.getColorFondo()));
        InterfazRegistro.setBackground(Color.decode(valores.getColorPanel()));
        contenedorFormulario.setOpaque(false);

        LBLEnviar.setEnabled(false);
        TXTFUsuario.setOpaque(false);
        TXTFUsuario.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        TXTFNombre.setOpaque(false);
        TXTFNombre.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        TXTFApellido.setOpaque(false);
        TXTFApellido.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        TXTFContraseña.setOpaque(false);
        TXTFContraseña.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));

        LBLTitulo.setForeground(Color.BLACK);
        LBLSubtitulo.setForeground(Color.BLACK);
        LBLTipoUsuario.setForeground(Color.BLACK);
        LBLEnviar.setForeground(Color.WHITE);

        LBLTitulo.setFont(new Font("Courier New", Font.BOLD, 45));
        LBLSubtitulo.setFont(new Font("Courier New", Font.PLAIN, 25));
        LBLTipoUsuario.setFont(new Font("Courier New", Font.PLAIN, 22));
        LBLEnviar.setFont(new Font("Courier New", Font.PLAIN, 15));

        configurarPlaceholder(TXTFUsuario, "Ingrese su usuario");
        configurarPlaceholder(TXTFNombre, "Ingrese su Nombre");
        configurarPlaceholder(TXTFApellido, "Ingrese su Apellido");
        configurarPlaceholder(TXTFContraseña, "Ingrese su Contraseña");

        VRegistro.add(InterfazRegistro);
        InterfazRegistro.add(contenedorFormulario);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weighty = 0.0;
        c.insets = new Insets(0, 0, 0, 0);
        contenedorFormulario.add(LBLTitulo, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 10, 0);
        contenedorFormulario.add(LBLSubtitulo, c);

        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 20, 0);
        contenedorFormulario.add(TXTFUsuario, c);

        c.gridy = 3;
        contenedorFormulario.add(TXTFNombre, c);

        c.gridy = 4;
        contenedorFormulario.add(TXTFApellido, c);

        c.gridy = 5;
        contenedorFormulario.add(TXTFContraseña, c);

        c.gridy = 6;
        contenedorFormulario.add(LBLTipoUsuario, c);

        c.gridy = 7;
        contenedorFormulario.add(CBXTipoUsuario, c);

        c.gridy = 8;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, -65, 0);
        contenedorFormulario.add(LBLEnviar, c);

        c.gridy = 9;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(20, 0, 0, 0);
        contenedorFormulario.add(BTNEnviar, c);

        BTNEnviar.addActionListener(e -> {
            String idUsuario = TXTFUsuario.getText().trim();
            String nombre = TXTFNombre.getText().trim();
            String apellido = TXTFApellido.getText().trim();
            String contrasena = TXTFContraseña.getText().trim();
            String tipoUsuario = CBXTipoUsuario.getSelectedItem().toString();

            if (!validarCampo(idUsuario, "Ingrese su usuario") ||
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
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(textoGuia)) {
                    campo.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(textoGuia);
                }
            }
        });
    }
}
