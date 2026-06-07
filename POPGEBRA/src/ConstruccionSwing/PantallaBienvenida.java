/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConstruccionSwing;

/**
 *
 * @author Saul
 */
import ConstruccionSwing.InicioSesion.Login;
import ConstruccionSwing.InicioSesion.RegistroUsuario;
import Factories.BotonOvalado;
import Factories.PanelRedondeado;
import GlobalConfig.TamañosColoresPosicion;

import javax.swing.*;
import java.awt.*;

public class PantallaBienvenida extends JFrame {

    public PantallaBienvenida() {

        TamañosColoresPosicion valores = new TamañosColoresPosicion();

        setTitle("POPGEBRA");

        setSize(valores.getTamañoVentana(0), valores.getTamañoVentana(1));
        getContentPane().setBackground(Color.decode(valores.getColorFondo()));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

   

        JPanel principal = new JPanel(new GridBagLayout());
        principal.setOpaque(false);

    

        PanelRedondeado tarjeta =
                new PanelRedondeado(60);

        tarjeta.setBackground(Color.decode(valores.getColorPanel()));
        tarjeta.setPreferredSize(valores.getPosicionDelPanelRegistro());

        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS));


        JLabel lblBienvenido =
                new JLabel(
                        "¡BIENVENIDO!");

        lblBienvenido.setFont(
                new Font(
                        "Courier New",
                        Font.BOLD,
                        45));

        lblBienvenido.setForeground(
                Color.BLACK);

        lblBienvenido.setAlignmentX(
                Component.CENTER_ALIGNMENT);

    

        JLabel lblSubtitulo =
                new JLabel(
                        "<html><div style='text-align:center;'>Inicia sesión o crea tu cuenta</div></html>");

        lblSubtitulo.setFont(
                new Font(
                        "Courier New",
                        Font.PLAIN,
                        25));

        lblSubtitulo.setForeground(
                Color.BLACK);

        lblSubtitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT);
        lblSubtitulo.setHorizontalAlignment(
                SwingConstants.CENTER);
        lblSubtitulo.setMaximumSize(
                new Dimension(
                        380,
                        100));

  

        JPanel panelBotones =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                20,
                                0));

        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        BotonOvalado btnLogin =
                new BotonOvalado(
                        "INICIAR SESIÓN");

        BotonOvalado btnRegistro =
                new BotonOvalado(
                        "REGISTRARSE");

        configurarBoton(btnLogin);

        configurarBoton(btnRegistro);

        btnLogin.addActionListener(e -> {
            dispose();
            new Login().show();
            
        });
        btnRegistro.addActionListener(e -> {
            dispose();
            new RegistroUsuario().Registro();
        });

        panelBotones.add(btnLogin);

        panelBotones.add(
                Box.createHorizontalStrut(
                        20));

        panelBotones.add(btnRegistro);

   

        tarjeta.add(
                Box.createVerticalStrut(
                        40));

        tarjeta.add(lblBienvenido);

        tarjeta.add(
                Box.createVerticalStrut(
                        20));

        tarjeta.add(lblSubtitulo);

        tarjeta.add(
                Box.createVerticalGlue());

        tarjeta.add(panelBotones);

        tarjeta.add(
                Box.createVerticalStrut(
                        60));

        principal.add(tarjeta);

        add(principal);
    }

  

    private void configurarBoton(
            JButton boton) {

        boton.setPreferredSize(
                new Dimension(
                        280,
                        90));

        boton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22));

        boton.setBackground(
                new Color(
                        243,
                        180,
                        45));

        boton.setForeground(
                Color.WHITE);

        boton.setBorder(
                BorderFactory.createLineBorder(
                        Color.WHITE,
                        0));

        boton.setFocusPainted(
                false);
    }



    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    new PantallaBienvenida()
                            .setVisible(true);

                });
    }
}
