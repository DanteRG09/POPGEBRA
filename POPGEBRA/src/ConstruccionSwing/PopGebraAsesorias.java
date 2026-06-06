package ConstruccionSwing;

/**
 *
 * @author Dante
 *
 */

import Factories.JComponentOval;
import Factories.BotonOvalado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PopGebraAsesorias extends JFrame {

    public PopGebraAsesorias() {
        setTitle("POPGEBRA");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout());

        // =========================================================================
        // MENÚ LATERAL (DORADO)
        // =========================================================================
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

        // =========================================================================
        // CONTENEDOR PRINCIPAL DERECHO (FONDO OSCURO)
        // =========================================================================
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(25, 25, 25));

        // Insertamos directamente el panel de las asesorías que creamos
        contenido.add(Tablas(), BorderLayout.CENTER);

        // Ensamblaje final de la ventana
        principal.add(menu, BorderLayout.WEST);
        principal.add(contenido, BorderLayout.CENTER);

        add(principal);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new BotonOvalado(texto);
        boton.setMaximumSize(new Dimension(260, 60));
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(214, 165, 52));
        boton.setForeground(Color.BLACK);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        return boton;
    }

    private JSeparator crearLinea() {
        JSeparator linea = new JSeparator();
        linea.setMaximumSize(new Dimension(260, 1));
        linea.setForeground(Color.BLACK);
        return linea;
    }

    // =========================================================================
    // VISTA DE ASESORÍAS (TUS MATRICES OVALADAS)
    // =========================================================================
    public JPanel Tablas() {
        JPanel panelAsesorias = new JPanel(new GridBagLayout());
        panelAsesorias.setBackground(new Color(25, 25, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        // BLOQUE 1: LISTA DE ASESORES
        JComponentOval panelLista = new JComponentOval(30);
        panelLista.setBackground(Color.WHITE);
        panelLista.setLayout(new BorderLayout(10, 10));
        panelLista.setPreferredSize(new Dimension(650, 250));

        JLabel lblListaTitulo = new JLabel("  LISTA DE ASESORES");
        lblListaTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblListaTitulo.setForeground(Color.BLACK);

        JTextField txtBuscar = new JTextField(" BUSCAR ASESOR");
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscar.setPreferredSize(new Dimension(600, 35));
        txtBuscar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        JPanel panelSuperiorLista = new JPanel(new BorderLayout(5, 5));
        panelSuperiorLista.setOpaque(false);
        panelSuperiorLista.add(lblListaTitulo, BorderLayout.NORTH);
        panelSuperiorLista.add(txtBuscar, BorderLayout.CENTER);
        panelLista.add(panelSuperiorLista, BorderLayout.NORTH);

        String[] colsAsesores = {"NOMBRE", "MATERIA", "CARRERA", "HORARIOS", "SEMESTRE"};
        DefaultTableModel modeloAsesores = new DefaultTableModel(colsAsesores, 0);
        for(int i = 1; i <= 5; i++) {
            modeloAsesores.addRow(new Object[]{"Asesor " + i, "Álgebra", "Sistemas", "10:00 - 12:00", i + "°"});
        }
        JTable tablaAsesores = new JTable(modeloAsesores);
        tablaAsesores.setRowHeight(30);
        tablaAsesores.setGridColor(Color.BLACK);
        tablaAsesores.setShowGrid(true);
        JScrollPane scrollAsesores = new JScrollPane(tablaAsesores);
        scrollAsesores.setBorder(BorderFactory.createEmptyBorder());
        panelLista.add(scrollAsesores, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 0.4;
        panelAsesorias.add(panelLista, gbc);

        // BLOQUE 2: DESCRIPCIÓN DE ASESOR
        JComponentOval panelDesc = new JComponentOval(30);
        panelDesc.setBackground(Color.WHITE);
        panelDesc.setLayout(new BorderLayout(10, 10));
        panelDesc.setPreferredSize(new Dimension(350, 250));

        JLabel lblDescTitulo = new JLabel("DESCRIPCIÓN DE ASESOR");
        lblDescTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblDescTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelDesc.add(lblDescTitulo, BorderLayout.NORTH);

        JTextArea txtDesc = new JTextArea(
            "LOREM IPSUM DOLOR SIT AMET, CONSECTETUR ADIPISCING ELIT. " +
            "NUNC VULPUTATE ELEIFEND MI VENENATIS TRISTIQUE. UT VITAE COMMODO LEO."
        );
        txtDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        
        JPanel wrapperDesc = new JPanel(new BorderLayout());
        wrapperDesc.setOpaque(false);
        wrapperDesc.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        wrapperDesc.add(txtDesc, BorderLayout.CENTER);
        panelDesc.add(wrapperDesc, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 0.4;
        panelAsesorias.add(panelDesc, gbc);

        // BLOQUE 3: HORARIO COMPLETO
        JComponentOval panelHorarioCompleto = new JComponentOval(30);
        panelHorarioCompleto.setBackground(Color.WHITE);
        panelHorarioCompleto.setLayout(new GridBagLayout());
        panelHorarioCompleto.setPreferredSize(new Dimension(1030, 450));
        
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.insets = new Insets(15, 15, 15, 15);
        gbcH.fill = GridBagConstraints.BOTH;

        JPanel panelGridHorario = new JPanel(new BorderLayout(10, 10));
        panelGridHorario.setOpaque(false);
        
        JLabel lblNombreAsesor = new JLabel("NOMBRE ASESOR");
        lblNombreAsesor.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel lblDetallesAsesor = new JLabel("MATERIA  |  CARRERA  |  SEMESTRE");
        lblDetallesAsesor.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel panelInfoAsesor = new JPanel(new GridLayout(2, 1));
        panelInfoAsesor.setOpaque(false);
        panelInfoAsesor.add(lblNombreAsesor);
        panelInfoAsesor.add(lblDetallesAsesor);
        panelGridHorario.add(panelInfoAsesor, BorderLayout.NORTH);

        String[] colsHoras = {"Día", "6 AM", "8 AM", "10 AM", "12 PM", "2 PM", "4 PM", "6 PM", "8 PM"};
        DefaultTableModel modeloHorario = new DefaultTableModel(colsHoras, 0);
        String[] dias = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : dias) {
            modeloHorario.addRow(new Object[]{dia, "[ ]", "[X]", "[X]", "[ ]", "[X]", "[ ]", "[X]", "[ ]"});
        }
        JTable tablaHorario = new JTable(modeloHorario);
        tablaHorario.setRowHeight(35);
        JScrollPane scrollHorario = new JScrollPane(tablaHorario);
        panelGridHorario.add(scrollHorario, BorderLayout.CENTER);

        gbcH.gridx = 0;
        gbcH.gridy = 0;
        gbcH.weightx = 0.7;
        gbcH.weighty = 1.0;
        panelHorarioCompleto.add(panelGridHorario, gbcH);

        JPanel panelControlesDerecha = new JPanel(new GridBagLayout());
        panelControlesDerecha.setOpaque(false);
        GridBagConstraints gbcC = new GridBagConstraints();
        gbcC.insets = new Insets(10, 10, 10, 10);
        gbcC.fill = GridBagConstraints.HORIZONTAL;
        gbcC.gridx = 0;

        JLabel lblPuntuacion = new JLabel("PUNTUACIÓN DEL ASESOR");
        lblPuntuacion.setFont(new Font("Arial", Font.BOLD, 14));
        gbcC.gridy = 0;
        panelControlesDerecha.add(lblPuntuacion, gbcC);

        JLabel lblEstrellas = new JLabel("★★★★★ (5/5)");
        lblEstrellas.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstrellas.setForeground(new Color(214, 165, 52));
        gbcC.gridy = 1;
        panelControlesDerecha.add(lblEstrellas, gbcC);

        JTextArea txtMaterial = new JTextArea("Material recomendado:\n• Apuntes de clase\n• Ejercicios resueltos");
        txtMaterial.setBackground(new Color(245, 245, 245));
        txtMaterial.setEditable(false);
        gbcC.gridy = 2;
        panelControlesDerecha.add(txtMaterial, gbcC);

        JButton btnInscribir = new BotonOvalado("INSCRIBIR");
        btnInscribir.setFont(new Font("Arial", Font.BOLD, 16));
        btnInscribir.setBackground(new Color(60, 80, 160)); // Azul sólido del blueprint
        btnInscribir.setForeground(Color.WHITE);
        btnInscribir.setPreferredSize(new Dimension(150, 45));

        gbcC.gridy = 3;
        gbcC.insets = new Insets(30, 10, 10, 10);
        panelControlesDerecha.add(btnInscribir, gbcC);

        gbcH.gridx = 1;
        gbcH.gridy = 0;
        gbcH.weightx = 0.3;
        gbcH.weighty = 1.0;
        panelHorarioCompleto.add(panelControlesDerecha, gbcH);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        panelAsesorias.add(panelHorarioCompleto, gbc);

        return panelAsesorias;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PopGebraAsesorias().setVisible(true);
        });
    }
}
