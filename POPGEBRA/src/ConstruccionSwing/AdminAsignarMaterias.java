package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.AsesorDAO;
import ConstruccionSwing.InicioSesion.ComboItem;
import ConstruccionSwing.InicioSesion.MateriaDAO;
import ConstruccionSwing.InicioSesion.MateriasImpartidasDAO;
import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;
import GlobalConfig.Texto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminAsignarMaterias extends JPanel {
    private TamañosColoresPosicion valores = new TamañosColoresPosicion();
    private Texto Fuente = new Texto();
    private final Runnable onBack;

    private JComboBox<ComboItem> CBXAsesor;
    private JList<ComboItem> listMaterias;
    private DefaultListModel<ComboItem> materiasModel;
    private JList<ComboItem> listAsignadas;
    private DefaultListModel<ComboItem> asignadasModel;

    public AdminAsignarMaterias(Runnable onBack) {
        this.onBack = onBack;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        setOpaque(false);

        JComponentOval panel = new JComponentOval(60);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        Dimension panelSize = valores.getPosicionDelPanelRegistro();
        panel.setPreferredSize(new Dimension(panelSize.width, 700));
        panel.setBackground(Color.decode(valores.getColorPanel()));

        JLabel titulo = new JLabel("Asignar materias a asesor", SwingConstants.CENTER);
        titulo.setFont(new Font("Courier New", Font.BOLD, 36));
        titulo.setForeground(Color.BLACK);

        JLabel lblAsesor = new JLabel("Selecciona un asesor:");
        lblAsesor.setFont(new Font("Courier New", Font.PLAIN, 18));
        lblAsesor.setForeground(Color.BLACK);

        CBXAsesor = new JComboBox<>();
        CBXAsesor.setPreferredSize(new Dimension(350, 40));
        CBXAsesor.setFont(new Font("Courier New", Font.PLAIN, 16));

        JLabel lblMaterias = new JLabel("Materias disponibles:");
        lblMaterias.setFont(new Font("Courier New", Font.PLAIN, 18));
        lblMaterias.setForeground(Color.BLACK);

        materiasModel = new DefaultListModel<>();
        listMaterias = new JList<>(materiasModel);
        listMaterias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listMaterias.setFont(new Font("Courier New", Font.PLAIN, 16));
        JScrollPane materiasScroll = new JScrollPane(listMaterias);
        materiasScroll.setPreferredSize(new Dimension(350, 140));

        JLabel lblAsignadas = new JLabel("Materias ya asignadas:");
        lblAsignadas.setFont(new Font("Courier New", Font.PLAIN, 18));
        lblAsignadas.setForeground(Color.BLACK);

        asignadasModel = new DefaultListModel<>();
        listAsignadas = new JList<>(asignadasModel);
        listAsignadas.setFont(new Font("Courier New", Font.PLAIN, 16));
        JScrollPane asignadasScroll = new JScrollPane(listAsignadas);
        asignadasScroll.setPreferredSize(new Dimension(350, 140));

        JComponentOvalBtn btnAsignar = new JComponentOvalBtn(30);
        btnAsignar.setText("Asignar materias");
        btnAsignar.setPreferredSize(new Dimension(260, 50));
        btnAsignar.setBackground(new Color(243, 180, 45));
        btnAsignar.setForeground(Color.BLACK);

        JComponentOvalBtn btnEliminar = new JComponentOvalBtn(30);
        btnEliminar.setText("Eliminar materia");
        btnEliminar.setPreferredSize(new Dimension(260, 50));
        btnEliminar.setBackground(new Color(220, 80, 80));
        btnEliminar.setForeground(Color.BLACK);

        JComponentOvalBtn btnVolver = new JComponentOvalBtn(30);
        btnVolver.setText("Regresar");
        btnVolver.setPreferredSize(new Dimension(260, 50));
        btnVolver.setBackground(new Color(180, 80, 80));
        btnVolver.setForeground(Color.BLACK);

        panel.add(titulo);
        panel.add(lblAsesor);
        panel.add(CBXAsesor);
        panel.add(lblMaterias);
        panel.add(materiasScroll);
        panel.add(lblAsignadas);
        panel.add(asignadasScroll);
        panel.add(btnAsignar);
        panel.add(btnEliminar);
        panel.add(btnVolver);

        setBackground(Color.decode(valores.getColorFondo()));
        add(panel);

        btnAsignar.addActionListener(e -> asignarMaterias());
        btnEliminar.addActionListener(e -> eliminarAsignacion());
        btnVolver.addActionListener(e -> onBack.run());
        CBXAsesor.addActionListener(e -> cargarMateriasAsignadas());

        cargarAsesores();
        cargarMaterias();
    }

    private void cargarAsesores() {
        CBXAsesor.removeAllItems();
        for (ComboItem asesor : new AsesorDAO().getAllAsesores()) {
            CBXAsesor.addItem(asesor);
        }
        if (CBXAsesor.getItemCount() > 0) {
            CBXAsesor.setSelectedIndex(0);
            cargarMateriasAsignadas();
        }
    }

    private void cargarMaterias() {
        materiasModel.clear();
        for (ComboItem materia : new MateriaDAO().getAllMaterias()) {
            materiasModel.addElement(materia);
        }
    }

    private void cargarMateriasAsignadas() {
        asignadasModel.clear();
        ComboItem asesor = (ComboItem) CBXAsesor.getSelectedItem();
        if (asesor == null) {
            return;
        }
        for (ComboItem materia : new MateriasImpartidasDAO().getMateriasByAsesor(asesor.getKey())) {
            asignadasModel.addElement(materia);
        }
    }

    private void asignarMaterias() {
        ComboItem asesor = (ComboItem) CBXAsesor.getSelectedItem();
        if (asesor == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un asesor primero.",
                    "Asesor no seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<ComboItem> seleccionadas = listMaterias.getSelectedValuesList();
        if (seleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona al menos una materia.",
                    "Materias no seleccionadas",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean anyAssigned = false;
        for (ComboItem materia : seleccionadas) {
            boolean assigned = new MateriasImpartidasDAO().assignMateria(Integer.parseInt(materia.getKey()), asesor.getKey());
            anyAssigned = anyAssigned || assigned;
        }

        if (anyAssigned) {
            cargarMateriasAsignadas();
            listMaterias.clearSelection();
        }
    }

    private void eliminarAsignacion() {
        ComboItem asesor = (ComboItem) CBXAsesor.getSelectedItem();
        if (asesor == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un asesor primero.",
                    "Asesor no seleccionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        ComboItem seleccionada = listAsignadas.getSelectedValue();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una materia asignada para eliminar.",
                    "Materia no seleccionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMateriaImpartida = Integer.parseInt(seleccionada.getKey());
        if (new MateriasImpartidasDAO().deleteAssignment(idMateriaImpartida)) {
            cargarMateriasAsignadas();
            cargarMaterias();
        }
    }

}
