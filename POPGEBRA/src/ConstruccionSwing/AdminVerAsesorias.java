package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.AsesoriasDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminVerAsesorias extends JPanel {

    private final DefaultTableModel modeloAsesorias;
    private final JTable tablaAsesorias;

    public AdminVerAsesorias() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel titulo = new JLabel("Listado de asesorías activas", SwingConstants.LEFT);
        titulo.setFont(new Font("Courier New", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        modeloAsesorias = new DefaultTableModel(new String[]{"Id", "Nombre", "Materia", "Periodo", "Cupo", "Inicio", "Fin", "Activa"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaAsesorias = new JTable(modeloAsesorias);
        tablaAsesorias.setRowHeight(26);
        JScrollPane scroll = new JScrollPane(tablaAsesorias);
        scroll.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(titulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        cargarAsesorias();
    }

    private void cargarAsesorias() {
        modeloAsesorias.setRowCount(0);
        AsesoriasDAO dao = new AsesoriasDAO();
        List<String[]> asesorias = dao.getAllAsesorias();
        for (String[] asesoria : asesorias) {
            modeloAsesorias.addRow(asesoria);
        }
    }
}
