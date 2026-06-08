package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.AsesoriasDAO;
import ConstruccionSwing.InicioSesion.ComboItem;
import ConstruccionSwing.InicioSesion.HorariosDAO;
import ConstruccionSwing.InicioSesion.MateriasImpartidasDAO;
import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CrearAsesoria extends JPanel {
    private final Runnable onBack;
    private final String matriculaAsesor;
    private final TamañosColoresPosicion valores = new TamañosColoresPosicion();

    private JComboBox<ComboItem> cmbMaterias;
    private JTextField txtNombre;
    private JTextField txtPeriodo;
    private JSpinner spnCupo;
    private JSpinner spnFechaIni;
    private JSpinner spnFechaFin;
    private JCheckBox chkActiva;
    private JCheckBox chkMultiHorario;
    
    private JComboBox<String> cmbDia;
    private JSpinner spnHoraIni;
    private JSpinner spnHoraFin;
    private DefaultTableModel modeloHorarios;
    private JTable tablaHorarios;

    public CrearAsesoria(Runnable onBack, String matriculaAsesor) {
        this.onBack = onBack;
        this.matriculaAsesor = matriculaAsesor;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        setOpaque(false);

        JComponentOval panel = new JComponentOval(60);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(800, 900));
        panel.setBackground(Color.decode(valores.getColorPanel()));

        JLabel titulo = new JLabel("Crear Nueva Asesoría");
        titulo.setFont(new Font("Courier New", Font.BOLD, 32));
        titulo.setForeground(Color.BLACK);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        panel.add(buildDatosBasicos());
        panel.add(Box.createVerticalStrut(15));
        panel.add(buildHorarios());
        panel.add(Box.createVerticalStrut(15));
        panel.add(buildBotones());
        panel.add(Box.createVerticalStrut(20));

        setBackground(Color.decode(valores.getColorFondo()));
        add(panel);
    }

    private JPanel buildDatosBasicos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Courier New", Font.PLAIN, 16);
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10));

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblMateria = new JLabel("Materia asignada:");
        lblMateria.setFont(labelFont);
        panel.add(lblMateria, gbc);

        gbc.gridx = 1;
        cmbMaterias = new JComboBox<>();
        cmbMaterias.setFont(labelFont);
        cmbMaterias.setPreferredSize(new Dimension(300, 35));
        cargarMaterias();
        panel.add(cmbMaterias, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        panel.add(lblNombre, gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setFont(labelFont);
        txtNombre.setBorder(border);
        txtNombre.setPreferredSize(new Dimension(300, 35));
        panel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblPeriodo = new JLabel("Período (ej: 2026-1):");
        lblPeriodo.setFont(labelFont);
        panel.add(lblPeriodo, gbc);

        gbc.gridx = 1;
        txtPeriodo = new JTextField(20);
        txtPeriodo.setFont(labelFont);
        txtPeriodo.setBorder(border);
        txtPeriodo.setPreferredSize(new Dimension(300, 35));
        panel.add(txtPeriodo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblCupo = new JLabel("Cupo disponible:");
        lblCupo.setFont(labelFont);
        panel.add(lblCupo, gbc);

        gbc.gridx = 1;
        spnCupo = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        spnCupo.setPreferredSize(new Dimension(300, 35));
        panel.add(spnCupo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblFechaIni = new JLabel("Fecha inicio (YYYY-MM-DD):");
        lblFechaIni.setFont(labelFont);
        panel.add(lblFechaIni, gbc);

        gbc.gridx = 1;
        spnFechaIni = new JSpinner(new SpinnerDateModel());
        spnFechaIni.setEditor(new JSpinner.DateEditor(spnFechaIni, "yyyy-MM-dd"));
        spnFechaIni.setPreferredSize(new Dimension(300, 35));
        panel.add(spnFechaIni, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblFechaFin = new JLabel("Fecha fin (YYYY-MM-DD):");
        lblFechaFin.setFont(labelFont);
        panel.add(lblFechaFin, gbc);

        gbc.gridx = 1;
        spnFechaFin = new JSpinner(new SpinnerDateModel());
        spnFechaFin.setEditor(new JSpinner.DateEditor(spnFechaFin, "yyyy-MM-dd"));
        spnFechaFin.setPreferredSize(new Dimension(300, 35));
        panel.add(spnFechaFin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        chkActiva = new JCheckBox("Asesoría activa", true);
        chkActiva.setFont(labelFont);
        chkActiva.setOpaque(false);
        panel.add(chkActiva, gbc);

        gbc.gridx = 1;
        chkMultiHorario = new JCheckBox("Múltiples horarios", false);
        chkMultiHorario.setFont(labelFont);
        chkMultiHorario.setOpaque(false);
        panel.add(chkMultiHorario, gbc);

        return panel;
    }

    private JPanel buildHorarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblHorarios = new JLabel("Horarios:");
        lblHorarios.setFont(new Font("Courier New", Font.BOLD, 20));
        lblHorarios.setForeground(Color.BLACK);

        JPanel inputHorarios = new JPanel(new GridBagLayout());
        inputHorarios.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        Font labelFont = new Font("Courier New", Font.PLAIN, 14);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblDia = new JLabel("Día:");
        lblDia.setFont(labelFont);
        inputHorarios.add(lblDia, gbc);

        gbc.gridx = 1;
        cmbDia = new JComboBox<>(new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});
        cmbDia.setPreferredSize(new Dimension(120, 30));
        inputHorarios.add(cmbDia, gbc);

        gbc.gridx = 2;
        JLabel lblHoraIni = new JLabel("Hora inicio:");
        lblHoraIni.setFont(labelFont);
        inputHorarios.add(lblHoraIni, gbc);

        gbc.gridx = 3;
        spnHoraIni = new JSpinner(new SpinnerDateModel());
        spnHoraIni.setEditor(new JSpinner.DateEditor(spnHoraIni, "HH:mm"));
        spnHoraIni.setPreferredSize(new Dimension(100, 30));
        inputHorarios.add(spnHoraIni, gbc);

        gbc.gridx = 4;
        JLabel lblHoraFin = new JLabel("Hora fin:");
        lblHoraFin.setFont(labelFont);
        inputHorarios.add(lblHoraFin, gbc);

        gbc.gridx = 5;
        spnHoraFin = new JSpinner(new SpinnerDateModel());
        spnHoraFin.setEditor(new JSpinner.DateEditor(spnHoraFin, "HH:mm"));
        spnHoraFin.setPreferredSize(new Dimension(100, 30));
        inputHorarios.add(spnHoraFin, gbc);

        gbc.gridx = 6;
        JComponentOvalBtn btnAgregar = new JComponentOvalBtn(20);
        btnAgregar.setText("Agregar");
        btnAgregar.setPreferredSize(new Dimension(100, 30));
        btnAgregar.setBackground(new Color(243, 180, 45));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.addActionListener(e -> agregarHorario());
        inputHorarios.add(btnAgregar, gbc);

        modeloHorarios = new DefaultTableModel(new String[]{"Día", "Hora inicio", "Hora fin"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaHorarios = new JTable(modeloHorarios);
        tablaHorarios.setRowHeight(25);
        JScrollPane scrollHorarios = new JScrollPane(tablaHorarios);
        scrollHorarios.setPreferredSize(new Dimension(750, 120));

        panel.add(lblHorarios, BorderLayout.NORTH);
        panel.add(inputHorarios, BorderLayout.SOUTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.add(scrollHorarios, BorderLayout.CENTER);

        JComponentOvalBtn btnEliminar = new JComponentOvalBtn(20);
        btnEliminar.setText("Eliminar horario");
        btnEliminar.setPreferredSize(new Dimension(150, 30));
        btnEliminar.setBackground(new Color(220, 80, 80));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.addActionListener(e -> eliminarHorarioSeleccionado());
        tablePanel.add(btnEliminar, BorderLayout.SOUTH);

        JPanel fullPanel = new JPanel(new BorderLayout(0, 10));
        fullPanel.setOpaque(false);
        fullPanel.add(panel, BorderLayout.NORTH);
        fullPanel.add(tablePanel, BorderLayout.CENTER);

        return fullPanel;
    }

    private JPanel buildBotones() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComponentOvalBtn btnGuardar = new JComponentOvalBtn(30);
        btnGuardar.setText("Guardar Asesoría");
        btnGuardar.setPreferredSize(new Dimension(200, 50));
        btnGuardar.setBackground(new Color(60, 120, 200));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> guardarAsesoria());

        JComponentOvalBtn btnCancelar = new JComponentOvalBtn(30);
        btnCancelar.setText("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(200, 50));
        btnCancelar.setBackground(new Color(200, 100, 100));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> onBack.run());

        panel.add(btnGuardar);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(btnCancelar);

        return panel;
    }

    private void cargarMaterias() {
        cmbMaterias.removeAllItems();
        MateriasImpartidasDAO dao = new MateriasImpartidasDAO();
        List<ComboItem> materias = dao.getMateriasByAsesor(matriculaAsesor);
        for (ComboItem materia : materias) {
            cmbMaterias.addItem(materia);
        }
    }

    private void agregarHorario() {
        String dia = (String) cmbDia.getSelectedItem();
        java.util.Date horaIniObj = (java.util.Date) spnHoraIni.getValue();
        java.util.Date horaFinObj = (java.util.Date) spnHoraFin.getValue();

        LocalTime horaIni = LocalTime.of(horaIniObj.getHours(), horaIniObj.getMinutes());
        LocalTime horaFin = LocalTime.of(horaFinObj.getHours(), horaFinObj.getMinutes());

        if (horaFin.isBefore(horaIni) || horaFin.equals(horaIni)) {
            JOptionPane.showMessageDialog(this,
                    "La hora de fin debe ser mayor que la hora de inicio.",
                    "Horario inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloHorarios.addRow(new Object[]{dia, horaIni, horaFin});
    }

    private void eliminarHorarioSeleccionado() {
        int fila = tablaHorarios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un horario para eliminar.",
                    "Horario no seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        modeloHorarios.removeRow(fila);
    }

    private void guardarAsesoria() {
        String nombre = txtNombre.getText().trim();
        String periodo = txtPeriodo.getText().trim();
        int cupo = (Integer) spnCupo.getValue();
        java.util.Date fechaIniObj = (java.util.Date) spnFechaIni.getValue();
        java.util.Date fechaFinObj = (java.util.Date) spnFechaFin.getValue();
        boolean activa = chkActiva.isSelected();
        boolean multiHorario = chkMultiHorario.isSelected();

        if (nombre.isEmpty() || periodo.isEmpty() || cmbMaterias.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor completa todos los campos obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloHorarios.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor agrega al menos un horario.",
                    "Sin horarios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        ComboItem materiaSeleccionada = (ComboItem) cmbMaterias.getSelectedItem();
        int idMateriaImpartida = Integer.parseInt(materiaSeleccionada.getKey());

        Date fechaIni = new Date(fechaIniObj.getTime());
        Date fechaFin = new Date(fechaFinObj.getTime());

        AsesoriasDAO asesoriasDAO = new AsesoriasDAO();
        int idAsesoria = asesoriasDAO.createAsesoria(nombre, periodo, cupo, fechaIni, fechaFin,
                activa, multiHorario, idMateriaImpartida);

        if (idAsesoria > 0) {
            HorariosDAO horariosDAO = new HorariosDAO();

            for (int i = 0; i < modeloHorarios.getRowCount(); i++) {
                String dia = modeloHorarios.getValueAt(i, 0).toString();
                LocalTime horaIni = (LocalTime) modeloHorarios.getValueAt(i, 1);
                LocalTime horaFin = (LocalTime) modeloHorarios.getValueAt(i, 2);

                Time timeIni = Time.valueOf(horaIni);
                Time timeFin = Time.valueOf(horaFin);

                horariosDAO.addHorario(timeIni, timeFin, dia, idAsesoria);
            }

            JOptionPane.showMessageDialog(this,
                    "Asesoría y horarios creados correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            onBack.run();
        }
    }

    private int getLastAsesoria() {
        return new AsesoriasDAO().getNextAsesoriId() - 1;
    }
}
