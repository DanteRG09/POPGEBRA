package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.ComboItem;
import ConstruccionSwing.CrearCuestionario;
import ConstruccionSwing.AgregarPregunta;
import ConstruccionSwing.CuestionariosPanel;
import ConstruccionSwing.InicioSesion.Login;
import ConstruccionSwing.InicioSesion.MateriaDAO;
import ConstruccionSwing.InicioSesion.MateriasImpartidasDAO;
import ConstruccionSwing.InicioSesion.AsesoriasDAO;
import Factories.BotonOvalado;
import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AsesorHome extends JFrame {
    private final String matricula;
    private final TamañosColoresPosicion valores = new TamañosColoresPosicion();
    private final JPanel contentPanel;
    private final CardLayout contentLayout;

    private DefaultTableModel modeloAsignadas;
    private JTable tablaAsignadas;
    private DefaultListModel<ComboItem> disponiblesModel;
    private JList<ComboItem> listaDisponibles;
    private JLabel lblResumen;
    private CrearAsesoria panelCrearAsesoria;
    
    private DefaultTableModel modeloAsesorias;
    private JTable tablaAsesorias;
    private CuestionariosPanel cuestionariosPanel;

    public AsesorHome(String matricula) {
        super("Panel asesor");
        this.matricula = matricula;

        setSize(valores.getTamañoVentana(0), valores.getTamañoVentana(1));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = buildHeader();
        JPanel sidebar = buildSidebar();
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(Color.decode(valores.getColorFondo()));
        buildContentArea(contentPanel);

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        getContentPane().setBackground(Color.decode(valores.getColorFondo()));

        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.decode(valores.getColorPanel()));
        header.setPreferredSize(new Dimension(getWidth(), 90));

        JLabel lblTitle = new JLabel("Asesor - Panel de control");
        lblTitle.setFont(new Font("Courier New", Font.BOLD, 28));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));

        lblResumen = new JLabel("Bienvenido, " + matricula);
        lblResumen.setFont(new Font("Courier New", Font.PLAIN, 18));
        lblResumen.setForeground(Color.BLACK);
        lblResumen.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 30));
        lblResumen.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(lblResumen, BorderLayout.EAST);

        return header;
    }

    private JPanel buildSidebar() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(280, getHeight()));
        panel.setBackground(Color.decode(valores.getColorPanel()));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblMenu = new JLabel("Menú");
        lblMenu.setFont(new Font("Courier New", Font.BOLD, 28));
        lblMenu.setForeground(Color.BLACK);
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMenu.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JComponentOvalBtn btnInicio = new JComponentOvalBtn(30);
        btnInicio.setText("Inicio");
        btnInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInicio.setMaximumSize(new Dimension(240, 50));
        btnInicio.setBackground(new Color(243, 180, 45));
        btnInicio.setForeground(Color.BLACK);
        btnInicio.addActionListener(e -> showCard("home"));

        JComponentOvalBtn btnMaterias = new JComponentOvalBtn(30);
        btnMaterias.setText("Mis materias");
        btnMaterias.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMaterias.setMaximumSize(new Dimension(240, 50));
        btnMaterias.setBackground(new Color(243, 180, 45));
        btnMaterias.setForeground(Color.BLACK);
        btnMaterias.addActionListener(e -> {
            showCard("materias");
            cargarMaterias();
        });

        JComponentOvalBtn btnCrearAsesoria = new JComponentOvalBtn(30);
        btnCrearAsesoria.setText("Crear asesoría");
        btnCrearAsesoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCrearAsesoria.setMaximumSize(new Dimension(240, 50));
        btnCrearAsesoria.setBackground(new Color(100, 180, 240));
        btnCrearAsesoria.setForeground(Color.WHITE);
        btnCrearAsesoria.addActionListener(e -> showCard("crearAsesoria"));

        JComponentOvalBtn btnMisAsesorias = new JComponentOvalBtn(30);
        btnMisAsesorias.setText("Mis asesorías");
        btnMisAsesorias.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMisAsesorias.setMaximumSize(new Dimension(240, 50));
        btnMisAsesorias.setBackground(new Color(100, 200, 100));
        btnMisAsesorias.setForeground(Color.WHITE);
        btnMisAsesorias.addActionListener(e -> {
            showCard("asesorias");
            cargarAsesorias();
        });

        JComponentOvalBtn btnCrearCuestionario = new JComponentOvalBtn(30);
        btnCrearCuestionario.setText("Cuestionarios");
        btnCrearCuestionario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCrearCuestionario.setMaximumSize(new Dimension(240, 50));
        btnCrearCuestionario.setBackground(new Color(100, 180, 240));
        btnCrearCuestionario.setForeground(Color.WHITE);
        btnCrearCuestionario.addActionListener(e -> showCard("cuestionarios"));

        JComponentOvalBtn btnAgregarPregunta = new JComponentOvalBtn(30);
        btnAgregarPregunta.setText("Agregar pregunta");
        btnAgregarPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregarPregunta.setMaximumSize(new Dimension(240, 50));
        btnAgregarPregunta.setBackground(new Color(100, 200, 100));
        btnAgregarPregunta.setForeground(Color.WHITE);
        btnAgregarPregunta.addActionListener(e -> showCard("agregarPregunta"));

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
        panel.add(btnMaterias);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCrearAsesoria);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCrearCuestionario);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnAgregarPregunta);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnMisAsesorias);
        panel.add(Box.createVerticalGlue());
        panel.add(btnCerrar);
        panel.add(Box.createVerticalStrut(30));

        return panel;
    }

    private void buildContentArea(JPanel content) {
        content.setBackground(Color.decode(valores.getColorFondo()));

        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setOpaque(false);
        JLabel lblWelcome = new JLabel("Bienvenido, " + matricula, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Courier New", Font.BOLD, 36));
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));

        JLabel lblInfo = new JLabel("Aquí puedes ver tus materias asignadas, agregar nuevas y crear asesorías.", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Courier New", Font.PLAIN, 20));
        lblInfo.setForeground(Color.BLACK);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        homePanel.add(lblWelcome, BorderLayout.NORTH);
        homePanel.add(lblInfo, BorderLayout.CENTER);

        JPanel materiasPanel = buildMateriasPanel();
        JPanel asesoriasPanel = buildAsesoriasPanel();
        panelCrearAsesoria = new CrearAsesoria(() -> showCard("materias"), matricula);
        cuestionariosPanel = new CuestionariosPanel(matricula);
        JPanel crearCuestionarioPanel = new JPanel(new BorderLayout());
        crearCuestionarioPanel.setOpaque(false);
        JPanel ccTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ccTop.setOpaque(false);
        ccTop.add(new JLabel("Crear cuestionario"));
        JButton abrirCrear = new JButton("Abrir formulario");
        abrirCrear.addActionListener(e -> CrearCuestionario.showDialog(this, matricula));
        ccTop.add(abrirCrear);
        crearCuestionarioPanel.add(ccTop, BorderLayout.NORTH);

        JPanel agregarPreguntaPanel = new JPanel(new BorderLayout());
        agregarPreguntaPanel.setOpaque(false);
        JPanel apTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        apTop.setOpaque(false);
        apTop.add(new JLabel("Agregar pregunta"));
        JButton abrirAgregar = new JButton("Abrir formulario");
        abrirAgregar.addActionListener(e -> {
            boolean saved = AgregarPregunta.showDialog(this, matricula);
            if (saved && cuestionariosPanel != null) {
                cuestionariosPanel.reloadData();
            }
        });
        apTop.add(abrirAgregar);
        agregarPreguntaPanel.add(apTop, BorderLayout.NORTH);

        content.add(homePanel, "home");
        content.add(materiasPanel, "materias");
        content.add(asesoriasPanel, "asesorias");
        content.add(panelCrearAsesoria, "crearAsesoria");
        content.add(cuestionariosPanel, "cuestionarios");
        content.add(crearCuestionarioPanel, "crearCuestionario");
        content.add(agregarPreguntaPanel, "agregarPregunta");
    }

    private JPanel buildMateriasPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Mis materias asignadas", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Courier New", Font.BOLD, 28));
        lblTitulo.setForeground(Color.BLACK);
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        JComponentOval panelTabla = new JComponentOval(30);
        panelTabla.setBackground(Color.decode(valores.getColorPanel()));
        panelTabla.setLayout(new BorderLayout(15, 15));
        panelTabla.setPreferredSize(new Dimension(760, 400));

        modeloAsignadas = new DefaultTableModel(new String[]{"IdAsignacion", "Materia"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAsignadas = new JTable(modeloAsignadas);
        tablaAsignadas.setRowHeight(30);
        tablaAsignadas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaAsignadas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaAsignadas.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollAsignadas = new JScrollPane(tablaAsignadas);
        scrollAsignadas.setPreferredSize(new Dimension(720, 300));

        panelTabla.add(new JLabel("Materias asignadas"), BorderLayout.NORTH);
        panelTabla.add(scrollAsignadas, BorderLayout.CENTER);

        JComponentOval panelDisponibles = new JComponentOval(30);
        panelDisponibles.setBackground(Color.decode(valores.getColorPanel()));
        panelDisponibles.setLayout(new BorderLayout(15, 15));
        panelDisponibles.setPreferredSize(new Dimension(380, 400));

        disponiblesModel = new DefaultListModel<>();
        listaDisponibles = new JList<>(disponiblesModel);
        listaDisponibles.setFont(new Font("Courier New", Font.PLAIN, 16));
        listaDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollDisponibles = new JScrollPane(listaDisponibles);
        scrollDisponibles.setPreferredSize(new Dimension(340, 300));

        panelDisponibles.add(new JLabel("Materias disponibles"), BorderLayout.NORTH);
        panelDisponibles.add(scrollDisponibles, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        JComponentOvalBtn btnAgregar = new JComponentOvalBtn(30);
        btnAgregar.setText("Agregar materia");
        btnAgregar.setPreferredSize(new Dimension(240, 50));
        btnAgregar.setMaximumSize(new Dimension(240, 50));
        btnAgregar.setBackground(new Color(243, 180, 45));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregar.addActionListener(e -> agregarMateria());

        JComponentOvalBtn btnEliminar = new JComponentOvalBtn(30);
        btnEliminar.setText("Eliminar materia");
        btnEliminar.setPreferredSize(new Dimension(240, 50));
        btnEliminar.setMaximumSize(new Dimension(240, 50));
        btnEliminar.setBackground(new Color(220, 80, 80));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEliminar.addActionListener(e -> eliminarMateriaAsignada());

        panelBotones.add(btnAgregar);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(btnEliminar);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.65;
        gbc.weighty = 1.0;
        center.add(panelTabla, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.35;
        center.add(panelDisponibles, gbc);

        panel.add(center, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.EAST);

        cargarMaterias();

        return panel;
    }

    private void showCard(String cardName) {
        contentLayout.show(contentPanel, cardName);
    }

    private void cargarMaterias() {
        modeloAsignadas.setRowCount(0);
        List<ComboItem> asignadas = new MateriasImpartidasDAO().getMateriasByAsesor(matricula);
        Set<Integer> asignadasIds = new HashSet<>();

        for (ComboItem materia : asignadas) {
            modeloAsignadas.addRow(new Object[]{materia.getKey(), materia.getLabel()});
            asignadasIds.add(Integer.parseInt(materia.getKey()));
        }

        disponiblesModel.clear();
        List<Integer> materiaIdsAsignadas = new MateriasImpartidasDAO().getMateriaIdsByAsesor(matricula);
        for (ComboItem materia : new MateriaDAO().getAllMaterias()) {
            int idMateria = Integer.parseInt(materia.getKey());
            if (!materiaIdsAsignadas.contains(idMateria)) {
                disponiblesModel.addElement(materia);
            }
        }

        lblResumen.setText("Asesor: " + matricula + " • Materias asignadas: " + asignadas.size());
    }

    private void agregarMateria() {
        ComboItem materia = listaDisponibles.getSelectedValue();
        if (materia == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una materia disponible para agregar.",
                    "Materia no seleccionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean asignada = new MateriasImpartidasDAO().assignMateria(Integer.parseInt(materia.getKey()), matricula);
        if (asignada) {
            cargarMaterias();
        }
    }

    private void eliminarMateriaAsignada() {
        int fila = tablaAsignadas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una materia asignada para eliminar.",
                    "Materia no seleccionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idAsignacion = Integer.parseInt(modeloAsignadas.getValueAt(fila, 0).toString());
        if (new MateriasImpartidasDAO().deleteAssignment(idAsignacion)) {
            cargarMaterias();
        }
    }

    private JPanel buildAsesoriasPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Mis asesorías activas", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Courier New", Font.BOLD, 28));
        lblTitulo.setForeground(Color.BLACK);
        panel.add(lblTitulo, BorderLayout.NORTH);

        JComponentOval panelTabla = new JComponentOval(30);
        panelTabla.setBackground(Color.decode(valores.getColorPanel()));
        panelTabla.setLayout(new BorderLayout(15, 15));
        panelTabla.setPreferredSize(new Dimension(1100, 500));

        modeloAsesorias = new DefaultTableModel(
                new String[]{"Id", "Nombre", "Materia", "Período", "Cupo", "Inicio", "Fin"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAsesorias = new JTable(modeloAsesorias);
        tablaAsesorias.setRowHeight(30);
        tablaAsesorias.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaAsesorias.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaAsesorias.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablaAsesorias.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaAsesorias.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaAsesorias.getColumnModel().getColumn(5).setPreferredWidth(120);
        tablaAsesorias.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scrollAsesorias = new JScrollPane(tablaAsesorias);
        scrollAsesorias.setPreferredSize(new Dimension(1050, 420));

        panelTabla.add(scrollAsesorias, BorderLayout.CENTER);
        panel.add(panelTabla, BorderLayout.CENTER);

        cargarAsesorias();

        return panel;
    }

    private void cargarAsesorias() {
        modeloAsesorias.setRowCount(0);
        List<String[]> asesorias = new AsesoriasDAO().getAsesoriasActivasByAsesor(matricula);
        
        for (String[] asesoria : asesorias) {
            modeloAsesorias.addRow(asesoria);
        }
    }
}
