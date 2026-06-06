/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConstruccionSwing;

/**
 *
 * @author etnad
 */

import Factories.BotonOvalado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PopGebraUI extends JFrame {

    public PopGebraUI() {

        setTitle("POPGEBRA");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel principal = new JPanel(new BorderLayout());

   
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(300, 800));
        menu.setBackground(new Color(214, 165, 52));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("POPGEBRA");
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        menu.add(Box.createVerticalStrut(30));
        menu.add(logo);
        menu.add(Box.createVerticalStrut(40));

        JButton btnCuestionarios = crearBoton("CUESTIONARIOS");
        JButton btnAsesorias = crearBoton("ASESORIAS");
        JButton btnHorarios = crearBoton("HORARIOS INSCRITOS");
        JButton btnClasificaciones = crearBoton("CLASIFICACIONES");
        JButton btnRespondidos = crearBoton("CUESTIONARIOS RESUELTOS");

        menu.add(btnCuestionarios);
        menu.add(Box.createVerticalStrut(5));
        menu.add(crearLinea());

        menu.add(Box.createVerticalStrut(5));
        menu.add(btnAsesorias);
        menu.add(Box.createVerticalStrut(5));
        menu.add(crearLinea());

        menu.add(Box.createVerticalStrut(5));
        menu.add(btnHorarios);
        menu.add(Box.createVerticalStrut(5));
        menu.add(crearLinea());

        menu.add(Box.createVerticalStrut(5));
        menu.add(btnClasificaciones);
        menu.add(Box.createVerticalStrut(5));
        menu.add(crearLinea());

        menu.add(Box.createVerticalStrut(5));

        btnRespondidos.setBackground(new Color(180, 140, 40));
        menu.add(btnRespondidos);

     
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(25, 25, 25));

        JLabel titulo = new JLabel("CUESTIONARIOS RESUELTOS");
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Impact", Font.PLAIN, 50));

        contenido.add(titulo, BorderLayout.NORTH);

    
        String columnas[] = {
                "NO°",
                "NOMBRE",
                "CAL",
                "REALIZÓ",
                "TIEMPO",
                "FECHA"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (int i = 1; i <= 15; i++) {

            modelo.addRow(new Object[]{
                    i,
                    "NOMBRE " + i,
                    10,
                    "ASESOR",
                    "1:00",
                    "02/02/26"
            });
        }

        JTable tabla = new JTable(modelo);

        tabla.setRowHeight(45);

        tabla.setFont(new Font("Arial", Font.BOLD, 16));

        tabla.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 16));

        tabla.setGridColor(new Color(214, 165, 52));

        tabla.setShowGrid(true);

        tabla.setSelectionBackground(
                new Color(214, 165, 52));

        tabla.getTableHeader().setBackground(
                new Color(214, 165, 52));

        tabla.getTableHeader().setForeground(
                Color.BLACK);

        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelTabla = new JPanel(new BorderLayout());

        panelTabla.setBorder(
                BorderFactory.createLineBorder(
                        new Color(214, 165, 52),
                        3,
                        true
                )
        );

        panelTabla.add(scroll);

        panelTabla.setPreferredSize(
                new Dimension(700, 500)
        );

        JPanel centro = new JPanel(new GridBagLayout());

        centro.setBackground(new Color(25, 25, 25));

        centro.add(panelTabla);

        contenido.add(centro, BorderLayout.CENTER);

    
        principal.add(menu, BorderLayout.WEST);
        principal.add(contenido, BorderLayout.CENTER);

        add(principal);
    }


    private JButton crearBoton(String texto) {

        JButton boton = new BotonOvalado(texto);

        boton.setMaximumSize(
                new Dimension(260, 60));

        boton.setFont(
                new Font("Arial", Font.BOLD, 16));

        boton.setBackground(
                new Color(214, 165, 52));

        boton.setForeground(Color.BLACK);

        boton.setAlignmentX(
                Component.CENTER_ALIGNMENT);

       
        boton.setBorder(
                BorderFactory.createLineBorder(
                        Color.WHITE,
                        0));

        return boton;
    }


    private JSeparator crearLinea() {

        JSeparator linea = new JSeparator();

        linea.setMaximumSize(
                new Dimension(260, 1));

        linea.setForeground(Color.BLACK);

        return linea;
    }

}
