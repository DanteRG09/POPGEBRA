package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.CuestionarioAsesoriaDAO;
import ConstruccionSwing.InicioSesion.CuestionarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class CrearCuestionario extends JDialog {

    private final JTextField nombreField = new JTextField(30);
    private final JTextField fechaField = new JTextField(10);
    private final JTextField matriculaField = new JTextField(15);
    private final Integer linkedAsesoriaId;

    public CrearCuestionario(Frame owner, String defaultMatricula) {
        this(owner, defaultMatricula, null);
    }

    public CrearCuestionario(Frame owner, String defaultMatricula, Integer linkedAsesoriaId) {
        super(owner, "Crear Cuestionario", true);
        this.linkedAsesoriaId = linkedAsesoriaId;
        init(defaultMatricula);
    }

    public CrearCuestionario(Window owner, String defaultMatricula, Integer linkedAsesoriaId) {
        super(owner, "Crear Cuestionario", ModalityType.APPLICATION_MODAL);
        this.linkedAsesoriaId = linkedAsesoriaId;
        init(defaultMatricula);
    }

    private void init(String defaultMatricula) {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Nombre:"), c);
        c.gridx = 1; form.add(nombreField, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Fecha (YYYY-MM-DD):"), c);
        c.gridx = 1; fechaField.setText(java.time.LocalDate.now().toString()); form.add(fechaField, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Matrícula Asesor:"), c);
        c.gridx = 1; matriculaField.setText(defaultMatricula != null ? defaultMatricula : ""); form.add(matriculaField, c);

        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton crearBtn = new JButton("Crear");
        JButton cancelarBtn = new JButton("Cancelar");
        actions.add(crearBtn);
        actions.add(cancelarBtn);
        add(actions, BorderLayout.SOUTH);

        crearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCuestionario();
            }
        });
        cancelarBtn.addActionListener(a -> dispose());

        pack();
        setLocationRelativeTo(getOwner());
    }

    private void crearCuestionario() {
        String nombre = nombreField.getText().trim();
        String fechaTxt = fechaField.getText().trim();
        String matricula = matriculaField.getText().trim();
        if (nombre.isEmpty() || fechaTxt.isEmpty() || matricula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Date fecha = Date.valueOf(fechaTxt);
            CuestionarioDAO dao = new CuestionarioDAO();
            int id = dao.createCuestionario(nombre, fecha, matricula);
            if (id > 0) {
                if (linkedAsesoriaId != null) {
                    CuestionarioAsesoriaDAO relacionDAO = new CuestionarioAsesoriaDAO();
                    if (!relacionDAO.assignCuestionarioToAsesoria(id, linkedAsesoriaId)) {
                        JOptionPane.showMessageDialog(this, "Cuestionario creado pero no se pudo asignar a la asesoría.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
                dispose();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void showDialog(Frame owner, String matricula) {
        CrearCuestionario d = new CrearCuestionario(owner, matricula);
        d.setVisible(true);
    }

    public static void showDialog(Frame owner, String matricula, Integer linkedAsesoriaId) {
        CrearCuestionario d = new CrearCuestionario(owner, matricula, linkedAsesoriaId);
        d.setVisible(true);
    }

    public static void showDialog(Window owner, String matricula, Integer linkedAsesoriaId) {
        CrearCuestionario d = new CrearCuestionario(owner, matricula, linkedAsesoriaId);
        d.setVisible(true);
    }
}
