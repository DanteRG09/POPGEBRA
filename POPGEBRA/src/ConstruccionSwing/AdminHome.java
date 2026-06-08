package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.Login;
import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;
import GlobalConfig.Texto;
import ConstruccionSwing.AdminVerAsesorias;

import javax.swing.*;
import java.awt.*;

public class AdminHome extends JFrame {
    private TamañosColoresPosicion valores = new TamañosColoresPosicion();
    private Texto Fuente = new Texto();

    private JPanel sidebar;
    private JPanel contentArea;
    private CardLayout contentLayout;
    private boolean sidebarVisible = true;

    public AdminHome() {
        super("Panel administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(valores.getTamañoVentana(0), valores.getTamañoVentana(1));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = buildHeader();
        sidebar = buildSidebar();
        contentArea = buildContentArea();

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentArea, BorderLayout.CENTER);

        getContentPane().setBackground(Color.decode(valores.getColorFondo()));
        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 90));
        header.setBackground(Color.decode(valores.getColorPanel()));

        JButton btnHamburger = new JButton("☰");
        btnHamburger.setFont(new Font("Courier New", Font.BOLD, 24));
        btnHamburger.setBackground(Color.decode(valores.getColorPanel()));
        btnHamburger.setForeground(Color.BLACK);
        btnHamburger.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnHamburger.setFocusPainted(false);
        btnHamburger.addActionListener(e -> toggleSidebar());

        JLabel lblHeader = new JLabel("Administrador - Panel de control");
        lblHeader.setFont(new Font("Courier New", Font.BOLD, 28));
        lblHeader.setForeground(Color.BLACK);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));

        header.add(btnHamburger, BorderLayout.WEST);
        header.add(lblHeader, BorderLayout.CENTER);

        return header;
    }

    private JPanel buildSidebar() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(280, getHeight()));
        panel.setBackground(Color.decode(valores.getColorPanel()));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblMenu = new JLabel("Menú");
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMenu.setFont(new Font("Courier New", Font.BOLD, 28));
        lblMenu.setForeground(Color.BLACK);
        lblMenu.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JComponentOvalBtn btnInicio = new JComponentOvalBtn(30);
        btnInicio.setText("Inicio");
        btnInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInicio.setMaximumSize(new Dimension(240, 50));
        btnInicio.setBackground(new Color(243, 180, 45));
        btnInicio.setForeground(Color.BLACK);
        btnInicio.addActionListener(e -> showCard("home"));

        JComponentOvalBtn btnAsignarMaterias = new JComponentOvalBtn(30);
        btnAsignarMaterias.setText("Asignar materias");
        btnAsignarMaterias.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAsignarMaterias.setMaximumSize(new Dimension(240, 50));
        btnAsignarMaterias.setBackground(new Color(243, 180, 45));
        btnAsignarMaterias.setForeground(Color.BLACK);
        btnAsignarMaterias.addActionListener(e -> showCard("asignar"));

        JComponentOvalBtn btnAgregarAsesorias = new JComponentOvalBtn(30);
        btnAgregarAsesorias.setText("Crear asesoría");
        btnAgregarAsesorias.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregarAsesorias.setMaximumSize(new Dimension(240, 50));
        btnAgregarAsesorias.setBackground(new Color(100, 180, 240));
        btnAgregarAsesorias.setForeground(Color.WHITE);
        btnAgregarAsesorias.addActionListener(e -> showCard("agregarAsesorias"));

        JComponentOvalBtn btnVerAsesorias = new JComponentOvalBtn(30);
        btnVerAsesorias.setText("Ver asesorías");
        btnVerAsesorias.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVerAsesorias.setMaximumSize(new Dimension(240, 50));
        btnVerAsesorias.setBackground(new Color(100, 200, 100));
        btnVerAsesorias.setForeground(Color.WHITE);
        btnVerAsesorias.addActionListener(e -> showCard("verAsesorias"));

        JComponentOvalBtn btnCerrar = new JComponentOvalBtn(30);
        btnCerrar.setText("Cerrar sesión");
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.setMaximumSize(new Dimension(240, 50));
        btnCerrar.setBackground(new Color(220, 80, 80));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.addActionListener(e -> {
            dispose();
            new Login().show();
        });

        panel.add(lblMenu);
        panel.add(btnInicio);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnAsignarMaterias);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnAgregarAsesorias);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnVerAsesorias);
        panel.add(Box.createVerticalGlue());
        panel.add(btnCerrar);
        panel.add(Box.createVerticalStrut(30));

        return panel;
    }

    private JPanel buildContentArea() {
        contentLayout = new CardLayout();
        JPanel panel = new JPanel(contentLayout);
        panel.setBackground(Color.decode(valores.getColorFondo()));

        JPanel homePanel = new JPanel();
        homePanel.setBackground(Color.decode(valores.getColorFondo()));
        homePanel.setLayout(new BorderLayout());

        JLabel lblBienvenido = new JLabel("Bienvenido Admin", SwingConstants.LEFT);
        lblBienvenido.setFont(new Font("Courier New", Font.BOLD, 32));
        lblBienvenido.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JLabel lblDescripcion = new JLabel("Selecciona una opción del menú para comenzar.", SwingConstants.LEFT);
        lblDescripcion.setFont(new Font("Courier New", Font.PLAIN, 20));
        lblDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));

        homePanel.add(lblBienvenido, BorderLayout.NORTH);
        homePanel.add(lblDescripcion, BorderLayout.CENTER);

        panel.add(homePanel, "home");
        panel.add(new AdminAsignarMaterias(() -> showCard("home")), "asignar");
        panel.add(new AdminAgregarAsesorias(() -> showCard("home")), "agregarAsesorias");
        panel.add(new AdminVerAsesorias(), "verAsesorias");

        return panel;
    }

    private void showCard(String cardName) {
        contentLayout.show(contentArea, cardName);
    }

    private void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        sidebar.setVisible(sidebarVisible);
        revalidate();
        repaint();
    }
}
