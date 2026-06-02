/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConstruccionSwing;

/**
 *
 * @author Dante
 */
import GlobalConfig.PosicionTexto;
import GlobalConfig.TamañosColoresPosicion;
import GlobalConfig.Texto;

import Factories.JComponentOval;
import Factories.JComponentOvalBtn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.table.DefaultTableModel;

public class CRUD_Registro{

    private TamañosColoresPosicion valores = new TamañosColoresPosicion();
    private Texto Fuente = new Texto();
    private int x = 0;
    private int y = 1;
    
    //Instancias
    private JFrame VRegistro = new JFrame("Resgitro");
    private JComponentOval InterfazRegistro = new JComponentOval(60);
    private JPanel contenedorFormulario;
    private JLabel LBLTitulo;
    private JLabel LBLSubtitulo;
    private JLabel LBLEnviar;
    private JTextField TXTFNombre = new JTextField("Ingrese su Nombre", 20);
    private JTextField TXTFApellido = new JTextField("Ingrese su Apellido", 20);
    private JTextField TXTFCarrera = new JTextField("Ingrese su Carrera", 20);
    private JTextField TXTFBoleta = new JTextField("Ingrese su Boleta", 20);
    private JTextField TXTFContraseña = new JTextField("Ingrese su Contraseña", 20);
    JComponentOvalBtn BTNEnviar = new JComponentOvalBtn(30);
    public void Registro(){    
        //Layout
        VRegistro.setLayout(new GridBagLayout());
        InterfazRegistro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        contenedorFormulario = new JPanel(new GridBagLayout());
        //GridLayout Restricciones
        GridBagConstraints c = new GridBagConstraints();
        //Disposciones
        VRegistro.setSize(valores.getTamañoVentana(x), valores.getTamañoVentana(y));
        InterfazRegistro.setPreferredSize(valores.getPosicionDelPanelRegistro());
        contenedorFormulario.setPreferredSize(valores.getPosicionFormularioR());
        LBLTitulo = new JLabel("Registro", SwingConstants.CENTER);
        LBLSubtitulo = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        LBLEnviar = new JLabel("Enviar", SwingConstants.CENTER);
        TXTFNombre.setHorizontalAlignment(JTextField.CENTER);
        TXTFApellido.setHorizontalAlignment(JTextField.CENTER);
        TXTFCarrera.setHorizontalAlignment(JTextField.CENTER);
        TXTFBoleta.setHorizontalAlignment(JTextField.CENTER);
        TXTFContraseña.setHorizontalAlignment(JTextField.CENTER);
        BTNEnviar.setPreferredSize(valores.getTamañoBoton());
        //Colores
        VRegistro.getContentPane().setBackground(Color.decode(valores.getColorFondo()));
        InterfazRegistro.setBackground(Color.decode(valores.getColorPanel()));
        contenedorFormulario.setOpaque(false);
        LBLEnviar.setEnabled(false);
        TXTFNombre.setOpaque(false);
        TXTFNombre.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        TXTFApellido.setOpaque(false);
        TXTFApellido.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        TXTFCarrera.setOpaque(false);
        TXTFCarrera.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        TXTFBoleta.setOpaque(false);
        TXTFBoleta.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        TXTFContraseña.setOpaque(false);
        TXTFContraseña.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        BTNEnviar.setBackground(Color.BLACK);
        BTNEnviar.setContentAreaFilled(false);
        BTNEnviar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        LBLTitulo.setForeground(Color.BLACK);
        LBLSubtitulo.setForeground(Color.BLACK);
        LBLEnviar.setForeground(Color.WHITE);
        //Fuente del texto
        LBLTitulo.setFont(new Font("Courier New", Font.BOLD, 45));
        LBLSubtitulo.setFont(new Font("Courier New", Font.PLAIN, 25));
        LBLEnviar.setFont(new Font("Courier New", Font.PLAIN, 15));
        //FocusListener
        configurarPlaceholder(TXTFNombre, "Ingrese su Nombre");
        configurarPlaceholder(TXTFApellido, "Ingrese su Apellido");
        configurarPlaceholder(TXTFCarrera, "Ingrese su Carrera");
        configurarPlaceholder(TXTFBoleta, "Ingrese su Boleta");
        configurarPlaceholder(TXTFContraseña, "Ingrese su Contraseña");
        //Matriz de Disposicion
        VRegistro.add(InterfazRegistro);
        InterfazRegistro.add(contenedorFormulario);
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weighty = 0.0;
        c.insets = new Insets(0, 0, 0, 0);
        contenedorFormulario.add(LBLTitulo,c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weighty = 0.0;
        c.insets = new Insets(0, 0, 10, 0);
        contenedorFormulario.add(LBLSubtitulo, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weighty = 0.0;
        c.insets = new Insets(0, 0, 20, 0);
        contenedorFormulario.add(TXTFNombre, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weighty = 0.0;
        c.insets = new Insets(25, 0, 25, 0);
        contenedorFormulario.add(TXTFApellido, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weighty = 0.0;
        c.insets = new Insets(25, 0, 25, 0);
        contenedorFormulario.add(TXTFCarrera, c);
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.weighty = 0.0;
        c.insets = new Insets(25, 0, 25, 0);
        contenedorFormulario.add(TXTFBoleta, c);
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2;
        c.weighty = 0.0;
        c.insets = new Insets(25, 0, 25, 0);
        contenedorFormulario.add(TXTFContraseña, c);
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 1;
        c.weighty = 0.0;
        c.insets = new Insets(0, 0, -65, 0);
        contenedorFormulario.add(LBLEnviar, c);
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(20, 0, 0, 0);
        contenedorFormulario.add(BTNEnviar, c);
        //Hacer Visible y salir al Cerrar
        VRegistro.setVisible(true);
        VRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    
    private void configurarPlaceholder(JTextField campo, String textoGuia) {
        campo.addFocusListener(new java.awt.event.FocusListener() {
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
