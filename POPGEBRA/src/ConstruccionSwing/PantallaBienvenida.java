/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConstruccionSwing;

/**
 *
 * @author Saul
 */
import Factories.BotonOvalado;
import Factories.PanelRedondeado;

import javax.swing.*;
import java.awt.*;

public class PantallaBienvenida extends JFrame {

    public PantallaBienvenida() {

        setTitle("POPGEBRA");

        setSize(1600, 900);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

   

        JPanel principal =
                new JPanel() {

                    @Override
                    protected void paintComponent(
                            Graphics g) {

                        super.paintComponent(g);

                        Graphics2D g2 =
                                (Graphics2D) g;

                        GradientPaint gp =
                                new GradientPaint(
                                        0,
                                        0,
                                        Color.BLACK,
                                        0,
                                        getHeight(),
                                        Color.LIGHT_GRAY);

                        g2.setPaint(gp);

                        g2.fillRect(
                                0,
                                0,
                                getWidth(),
                                getHeight());

                        g2.setColor(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        60));

                        g2.setFont(
                                new Font(
                                        "Arial",
                                        Font.BOLD,
                                        90));

                        for (int y = -100;
                             y < getHeight() + 200;
                             y += 220) {

                            for (int x = -200;
                                 x < getWidth() + 200;
                                 x += 400) {

                                g2.rotate(
                                        Math.toRadians(-35),
                                        x,
                                        y);

                                g2.drawString(
                                        "POPGEBRA",
                                        x,
                                        y);

                                g2.rotate(
                                        Math.toRadians(35),
                                        x,
                                        y);
                            }
                        }
                    }
                };

        principal.setLayout(
                new GridBagLayout());

    

        PanelRedondeado tarjeta =
                new PanelRedondeado(60);

        tarjeta.setBackground(
                new Color(
                        255,
                        220,
                        0));

        tarjeta.setPreferredSize(
                new Dimension(
                        650,
                        650));

        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS));


        JLabel lblBienvenido =
                new JLabel(
                        "¡BIENVENIDO!");

        lblBienvenido.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        58));

        lblBienvenido.setForeground(
                Color.BLACK);

        lblBienvenido.setAlignmentX(
                Component.CENTER_ALIGNMENT);

    

        JLabel lblLogo =
                new JLabel(
                        "POPGEBRA");

        lblLogo.setFont(
                new Font(
                        "Serif",
                        Font.BOLD
                                | Font.ITALIC,
                        85));

        lblLogo.setForeground(
                Color.BLACK);

        lblLogo.setAlignmentX(
                Component.CENTER_ALIGNMENT);

  

        JPanel panelBotones =
                new JPanel();

        panelBotones.setOpaque(false);

        BotonOvalado btnLogin =
                new BotonOvalado(
                        "INICIAR SESIÓN");

        BotonOvalado btnRegistro =
                new BotonOvalado(
                        "REGISTRARSE");

        configurarBoton(btnLogin);

        configurarBoton(btnRegistro);

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
                        50));

        tarjeta.add(lblLogo);

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
