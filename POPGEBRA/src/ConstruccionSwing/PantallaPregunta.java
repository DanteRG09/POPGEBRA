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

import javax.swing.*;
import java.awt.*;

public class PantallaPregunta extends JFrame {

    public PantallaPregunta() {

        setTitle("POPGEBRA");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel();
        principal.setBackground(new Color(30, 30, 35));
        principal.setLayout(new BorderLayout());

       

        JPanel superior = new JPanel();

        superior.setBackground(Color.WHITE);

        JProgressBar barra = new JProgressBar();

        barra.setValue(30);

        barra.setPreferredSize(
                new Dimension(1000, 25));

        JLabel lblNumero = new JLabel("2/7");

        lblNumero.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        28));

        superior.add(barra);
        superior.add(Box.createHorizontalStrut(20));
        superior.add(lblNumero);

  

        JPanel centro = new JPanel();

        centro.setBackground(
                new Color(30, 30, 35));

        centro.setLayout(
                new GridLayout(
                        1,
                        2,
                        80,
                        0));

     

        JPanel izquierda = new JPanel();

        izquierda.setBackground(
                new Color(30, 30, 35));

        izquierda.setLayout(
                new BoxLayout(
                        izquierda,
                        BoxLayout.Y_AXIS));

        JLabel imagen = new JLabel("(3x + 4y)³");

        imagen.setOpaque(true);

        imagen.setBackground(
                new Color(220, 225, 230));

        imagen.setForeground(Color.BLUE);

        imagen.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        70));

        imagen.setHorizontalAlignment(
                SwingConstants.CENTER);

        imagen.setPreferredSize(
                new Dimension(450, 300));

        imagen.setMaximumSize(
                new Dimension(450, 300));

        imagen.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        JLabel texto = new JLabel(
                "Resuelve el siguiente ejercicio:");

        texto.setForeground(Color.WHITE);

        texto.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        22));

        texto.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        izquierda.add(Box.createVerticalStrut(80));
        izquierda.add(imagen);
        izquierda.add(Box.createVerticalStrut(40));
        izquierda.add(texto);



        JPanel derecha = new JPanel();

        derecha.setBackground(
                new Color(30, 30, 35));

        derecha.setLayout(
                new BoxLayout(
                        derecha,
                        BoxLayout.Y_AXIS));

        JButton btn1 = crearBotonRespuesta();
        JButton btn2 = crearBotonRespuesta();
        JButton btn3 = crearBotonRespuesta();
        JButton btn4 = crearBotonRespuesta();

        derecha.add(Box.createVerticalStrut(100));

        derecha.add(btn1);

        derecha.add(Box.createVerticalStrut(55));

        derecha.add(btn2);

        derecha.add(Box.createVerticalStrut(55));

        derecha.add(btn3);

        derecha.add(Box.createVerticalStrut(55));

        derecha.add(btn4);

    

        centro.add(izquierda);
        centro.add(derecha);

        principal.add(
                superior,
                BorderLayout.NORTH);

        principal.add(
                centro,
                BorderLayout.CENTER);

        add(principal);
    }

    private JButton crearBotonRespuesta() {

        JButton boton =
           new BotonOvalado("Respuesta");

        boton.setMaximumSize(
                new Dimension(550, 60));

        boton.setPreferredSize(
                new Dimension(550, 60));

        boton.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        20));

        boton.setBackground(
                new Color(230, 180, 40));

        boton.setForeground(
                Color.GRAY);

        boton.setBorder(
                BorderFactory.createLineBorder(
                        Color.WHITE,
                        0));

        boton.setFocusPainted(false);

        return boton;
    }



    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new PantallaPregunta()
                    .setVisible(true);

        });
    }
}
