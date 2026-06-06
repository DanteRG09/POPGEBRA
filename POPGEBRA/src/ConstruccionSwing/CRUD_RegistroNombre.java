/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConstruccionSwing;

/**
 *
 * @author etnad
 */
import GlobalConfig.colores;

import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import Factories.JComponentOvalTxtField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CRUD_RegistroNombre extends JFrame{

    public CRUD_RegistroNombre(){
        setTitle("Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.decode(colores.ColorFondo));
        
        JPanel PanelInt = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp =new GradientPaint(0,0,Color.BLACK,0,getHeight(),Color.LIGHT_GRAY);
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.setColor(new Color(255,255,255,60));
                g2.setFont(new Font("Arial",Font.BOLD,90));
                for (int y = -100; y < getHeight() + 200; y += 220) {
                    for (int x = -200; x < getWidth() + 200; x += 400) {
                        g2.rotate(Math.toRadians(-35),x,y);
                        g2.drawString("POPGEBRA",x,y);
                        g2.rotate(Math.toRadians(35),x,y);
                    }
                }
            }
            @Override
            public Dimension getPreferredSize() {
            return new Dimension(2000,2000);
            }
        };
        PanelInt.setLayout(new GridBagLayout());
        add(PanelInt);
        PanelInt.add(OvaloAmarillo());
    }
    
    private JPanel OvaloAmarillo(){
        JComponentOval InterfazRegistro;
        InterfazRegistro  = new JComponentOval(60);
        InterfazRegistro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        InterfazRegistro.setPreferredSize(new Dimension(1000,400));
        InterfazRegistro.setBackground(Color.decode(colores.ColorPanel));
        InterfazRegistro.add(Contenedor());
        return InterfazRegistro;
    }
    
    private JPanel Contenedor(){
        JPanel contenedorFormulario;
        contenedorFormulario = new JPanel();
        contenedorFormulario.setLayout(new GridBagLayout());
        contenedorFormulario.setPreferredSize(new Dimension(1000,400));
        contenedorFormulario.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.gridwidth = 0; c.weighty = 0.0;
        c.insets = new Insets(0, 0, 250, 500);
        contenedorFormulario.add(Texto("POPGEBRA", colores.Blanco, 60, "BOLD"),c);
        c.insets = new Insets(0, 0, 150, 500);
        contenedorFormulario.add(Texto("Registro", colores.Blanco, 40, "BOLD"),c);
        c.insets = new Insets(0, 0, 25, 500);
        contenedorFormulario.add(Texto("Ingrese tu nombre", colores.Blanco, 20, "ITALIC"),c);
        
        c.insets = new Insets(100, 0, 0, 500);
        contenedorFormulario.add(Boton("Siguiente", colores.Blanco, colores.Negro),c);
        
        c.insets = new Insets(0, 400, 300, 0);
        contenedorFormulario.add(ContenedorTexto("Ingrese su Nombre"),c);
        c.insets = new Insets(0, 400, 150, 0);
        contenedorFormulario.add(ContenedorTexto("Ingrese su Apellido Paterno"),c);
        c.insets = new Insets(0, 400, 0, 0);
        contenedorFormulario.add(ContenedorTexto("Ingrese su Apellido Materno"),c);
        c.insets = new Insets(0, 400, -150, 0);
        contenedorFormulario.add(ContenedorTexto("Ingrese su Apodo(opcional)"),c);
        
        c.insets = new Insets(0, 0, 75, 500);
        c.ipadx = 400;
        c.ipady = 300;
        contenedorFormulario.add(Oval(),c);
        return contenedorFormulario;
    }
    
    private JPanel Oval(){
        JComponentOval Oval;
        Oval  = new JComponentOval(60);
        Oval.getMinimumSize();
        Oval.setBackground(Color.BLACK);
        Oval.setEnabled(false);
        return Oval;
    }
    
    private JLabel Texto(String txt, String color, int tamaño, String font){
        JLabel Texto = new JLabel(txt, SwingConstants.CENTER);
        Texto.setForeground(Color.decode(color));
        Font Font0;
        switch(font){
            case "PLAIN":
                Font0=new Font("Courier New", Font.PLAIN, tamaño);
                break;
            case "BOLD":
                Font0=new Font("Courier New", Font.BOLD, tamaño);
                break;
            case "ITALIC":
                Font0=new Font("Courier New", Font.ITALIC, tamaño);
                break;
            default:
                Font0=new Font("Courier New", Font.PLAIN, tamaño);
        }
        Texto.setFont(Font0);
        Texto.setEnabled(true);
        return Texto;
    }
    
    private JTextField ContenedorTexto(String Texto){
        JComponentOvalTxtField ContenedorTexto = new JComponentOvalTxtField(60);
        ContenedorTexto.setText(Texto);
        ContenedorTexto.setFont((new Font("Courier New", Font.BOLD, 15)));
        ContenedorTexto.setForeground(Color.WHITE);
        ContenedorTexto.setPreferredSize(new Dimension(400, 30));
        ContenedorTexto.setOpaque(false);
        ContenedorTexto.setBackground(Color.BLACK);
        configurarPlaceholder(ContenedorTexto, Texto);
        ContenedorTexto.setHorizontalAlignment(JTextField.CENTER);
        ContenedorTexto.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0), 0));
        return ContenedorTexto;
    }
    
    private JButton Boton(String Texto, String color, String color0){
        JComponentOvalBtn botonOvalado = new JComponentOvalBtn(40);
        botonOvalado.setText(Texto);
        botonOvalado.setPreferredSize(new Dimension(200, 40));
        botonOvalado.setBackground(Color.decode(color));
        botonOvalado.setForeground(Color.decode(color0));
        botonOvalado.setContentAreaFilled(false);
        botonOvalado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botonOvalado.setFocusPainted(false); 
        botonOvalado.setBorderPainted(false);
        hoverEfect(botonOvalado);
        return botonOvalado;
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
    
    private void hoverEfect(JComponentOvalBtn boton) {
        boton.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(Color.decode("#333333"));
                boton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.WHITE);
                boton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                boton.setBackground(Color.BLACK);
                boton.setForeground(Color.WHITE);
                SwingUtilities.invokeLater(() -> {new CRUD_RegistroDatos().setVisible(true);});
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boton.setBackground(Color.WHITE);
                boton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setBackground(Color.BLACK);
                boton.setForeground(Color.WHITE);
            }
        });
    }
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {new CRUD_RegistroNombre().setVisible(true);});
    }
    
}    
