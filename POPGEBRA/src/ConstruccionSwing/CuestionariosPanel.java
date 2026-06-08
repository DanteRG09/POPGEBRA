package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.ComboItem;
import ConstruccionSwing.InicioSesion.CuestionarioDAO;
import ConstruccionSwing.InicioSesion.CuestionarioAsesoriaDAO;
import ConstruccionSwing.InicioSesion.PreguntasDAO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CuestionariosPanel extends JPanel {

    private final String matricula;
    private final DefaultTableModel modeloAsesorias;
    private final JTable tablaAsesorias;
    private final DefaultTableModel modeloCuestionarios;
    private final JTable tablaCuestionarios;
    private final DefaultTableModel modeloPreguntas;
    private final JTable tablaPreguntas;
    private final JLabel lblAsesoriaInfo;
    private final JLabel lblCuestionarioInfo;

    public CuestionariosPanel(String matricula) {
        this.matricula = matricula;
        setLayout(new BorderLayout(10, 10));
        setOpaque(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Cuestionarios", SwingConstants.LEFT);
        title.setFont(new Font("Courier New", Font.BOLD, 32));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        modeloAsesorias = new DefaultTableModel(new String[]{"Id Asesoría", "Asesoría", "Materia", "Id Cuestionario", "Cuestionario asignado"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaAsesorias = new JTable(modeloAsesorias);
        tablaAsesorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAsesorias.setRowHeight(26);
        JScrollPane scrollAsesorias = new JScrollPane(tablaAsesorias);

        modeloCuestionarios = new DefaultTableModel(new String[]{"Id Cuestionario", "Nombre", "Fecha"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCuestionarios = new JTable(modeloCuestionarios);
        tablaCuestionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCuestionarios.setRowHeight(26);
        JScrollPane scrollCuestionarios = new JScrollPane(tablaCuestionarios);

        modeloPreguntas = new DefaultTableModel(new String[]{"Id Pregunta", "Número", "Pregunta", "Tipo"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPreguntas = new JTable(modeloPreguntas);
        tablaPreguntas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPreguntas.setRowHeight(26);
        JScrollPane scrollPreguntas = new JScrollPane(tablaPreguntas);

        lblAsesoriaInfo = new JLabel("Seleccione una asesoría activa para ver o crear cuestionarios.");
        lblCuestionarioInfo = new JLabel("Seleccione un cuestionario para ver sus preguntas.");

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.setOpaque(false);
        topPanel.add(lblAsesoriaInfo, BorderLayout.NORTH);
        topPanel.add(scrollAsesorias, BorderLayout.CENTER);

        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topActions.setOpaque(false);
        JButton btnCrearCuestionario = new JButton("Crear cuestionario en asesoría seleccionada");
        JButton btnAsignarExistente = new JButton("Asignar cuestionario existente a asesoría");
        JButton btnRefrescar = new JButton("Refrescar");
        topActions.add(btnCrearCuestionario);
        topActions.add(btnAsignarExistente);
        topActions.add(btnRefrescar);
        topPanel.add(topActions, BorderLayout.SOUTH);

        JPanel middlePanel = new JPanel(new BorderLayout(8, 8));
        middlePanel.setOpaque(false);
        middlePanel.add(lblCuestionarioInfo, BorderLayout.NORTH);
        middlePanel.add(scrollCuestionarios, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.setOpaque(false);
        bottomPanel.add(new JLabel("Preguntas del cuestionario seleccionado:"), BorderLayout.NORTH);
        bottomPanel.add(scrollPreguntas, BorderLayout.CENTER);

        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, middlePanel);
        splitVertical.setResizeWeight(0.45);
        splitVertical.setBorder(null);
        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitVertical, bottomPanel);
        mainSplit.setResizeWeight(0.65);
        mainSplit.setBorder(null);
        add(mainSplit, BorderLayout.CENTER);

        tablaAsesorias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    actualizarCuestionariosPorAsesoria();
                }
            }
        });

        tablaCuestionarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    actualizarPreguntasPorCuestionario();
                }
            }
        });

        tablaPreguntas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mostrarDetallePregunta();
                }
            }
        });

        btnCrearCuestionario.addActionListener(e -> crearCuestionarioParaAsesoriaSeleccionada());
        btnAsignarExistente.addActionListener(e -> asignarCuestionarioExistenteAAsesoriaSeleccionada());
        btnRefrescar.addActionListener(e -> {
            cargarAsesorias();
            cargarCuestionarios();
            modeloPreguntas.setRowCount(0);
        });

        cargarAsesorias();
        cargarCuestionarios();
    }

    private void cargarAsesorias() {
        modeloAsesorias.setRowCount(0);
        CuestionarioAsesoriaDAO dao = new CuestionarioAsesoriaDAO();
        List<String[]> rows = dao.getActiveAsesoriasWithCuestionarios(matricula);
        for (String[] row : rows) {
            modeloAsesorias.addRow(row);
        }
    }

    private void cargarCuestionarios() {
        modeloCuestionarios.setRowCount(0);
        CuestionarioDAO dao = new CuestionarioDAO();
        List<String[]> rows = dao.getAllCuestionariosByAsesor(matricula);
        for (String[] row : rows) {
            modeloCuestionarios.addRow(row);
        }
    }

    private void actualizarCuestionariosPorAsesoria() {
        int fila = tablaAsesorias.getSelectedRow();
        if (fila < 0) {
            lblAsesoriaInfo.setText("Seleccione una asesoría activa para ver o crear cuestionarios.");
            return;
        }
        String asesoria = tablaAsesorias.getValueAt(fila, 1).toString();
        String materia = tablaAsesorias.getValueAt(fila, 2).toString();
        String cuestionario = tablaAsesorias.getValueAt(fila, 4) != null ? tablaAsesorias.getValueAt(fila, 4).toString() : "(ninguno)";
        lblAsesoriaInfo.setText("Asesoría seleccionada: " + asesoria + " - Materia: " + materia + " - Cuestionario asignado: " + cuestionario);
        lblCuestionarioInfo.setText("Lista general de cuestionarios. Seleccione uno para ver sus preguntas.");
    }

    private void actualizarPreguntasPorCuestionario() {
        int fila = tablaCuestionarios.getSelectedRow();
        if (fila < 0) {
            modeloPreguntas.setRowCount(0);
            return;
        }
        int idCuestionario = Integer.parseInt(tablaCuestionarios.getValueAt(fila, 0).toString());
        lblCuestionarioInfo.setText("Cuestionario seleccionado: " + tablaCuestionarios.getValueAt(fila, 1));
        PreguntasDAO dao = new PreguntasDAO();
        List<String[]> preguntas = dao.getPreguntasByCuestionario(idCuestionario);
        modeloPreguntas.setRowCount(0);
        for (String[] pregunta : preguntas) {
            String tipo = dao.getTipoPreguntaById(Integer.parseInt(pregunta[3]));
            modeloPreguntas.addRow(new String[]{pregunta[0], pregunta[1], pregunta[2], tipo});
        }
    }

    private void mostrarDetallePregunta() {
        int fila = tablaPreguntas.getSelectedRow();
        if (fila < 0) return;
        int idPregunta = Integer.parseInt(tablaPreguntas.getValueAt(fila, 0).toString());
        String numero = tablaPreguntas.getValueAt(fila, 1).toString();
        String pregunta = tablaPreguntas.getValueAt(fila, 2).toString();
        PreguntasDAO dao = new PreguntasDAO();
        List<String> respuestas = dao.getRespuestasByPregunta(idPregunta);
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Pregunta #").append(numero).append(":\n").append(pregunta).append("\n\nRespuestas:\n");
        for (int i = 0; i < respuestas.size(); i++) {
            mensaje.append(i + 1).append(". ").append(respuestas.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Detalle de pregunta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void crearCuestionarioParaAsesoriaSeleccionada() {
        int fila = tablaAsesorias.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione primero una asesoría activa.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idAsesoria = Integer.parseInt(tablaAsesorias.getValueAt(fila, 0).toString());
        CrearCuestionario.showDialog(SwingUtilities.getWindowAncestor(this), matricula, idAsesoria);
        cargarAsesorias();
        cargarCuestionarios();
    }

    private void asignarCuestionarioExistenteAAsesoriaSeleccionada() {
        int filaAsesoria = tablaAsesorias.getSelectedRow();
        int filaCuestionario = tablaCuestionarios.getSelectedRow();
        if (filaAsesoria < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione primero una asesoría activa.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (filaCuestionario < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un cuestionario existente para asignar.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idAsesoria = Integer.parseInt(tablaAsesorias.getValueAt(filaAsesoria, 0).toString());
        int idCuestionario = Integer.parseInt(tablaCuestionarios.getValueAt(filaCuestionario, 0).toString());

        CuestionarioAsesoriaDAO dao = new CuestionarioAsesoriaDAO();
        if (dao.assignCuestionarioToAsesoria(idCuestionario, idAsesoria)) {
            JOptionPane.showMessageDialog(this,
                    "Cuestionario asignado correctamente a la asesoría.",
                    "Asignación",
                    JOptionPane.INFORMATION_MESSAGE);
            cargarAsesorias();
        }
    }

    public void reloadData() {
        cargarAsesorias();
        cargarCuestionarios();
        modeloPreguntas.setRowCount(0);
    }
}
