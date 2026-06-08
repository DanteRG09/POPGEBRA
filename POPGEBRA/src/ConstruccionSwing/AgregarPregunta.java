package ConstruccionSwing;

import ConstruccionSwing.InicioSesion.ComboItem;
import ConstruccionSwing.InicioSesion.CuestionarioDAO;
import ConstruccionSwing.InicioSesion.PreguntasDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AgregarPregunta extends JDialog {

    private final JComboBox<ComboItem> cbCuestionarios = new JComboBox<>();
    private final JTextField numeroField = new JTextField(4);
    private final JTextArea preguntaArea = new JTextArea(4, 30);
    private final JTextField tipoField = new JTextField(4);
    private final JTextField[] respuestaFields = new JTextField[4];
    private final JRadioButton[] radio = new JRadioButton[4];
    private final Integer defaultCuestionarioId;
    private boolean saved = false;

    public AgregarPregunta(Frame owner, String matricula) {
        this(owner, matricula, null);
    }

    public AgregarPregunta(Frame owner, String matricula, Integer defaultCuestionarioId) {
        super(owner, "Agregar Pregunta", true);
        this.defaultCuestionarioId = defaultCuestionarioId;
        init(matricula);
    }

    private void init(String matricula) {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Cuestionario:"));
        top.add(cbCuestionarios);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; center.add(new JLabel("Número:"), c);
        c.gridx = 1; center.add(numeroField, c);

        c.gridx = 0; c.gridy = 1; center.add(new JLabel("Tipo (id):"), c);
        c.gridx = 1; tipoField.setText("1"); center.add(tipoField, c);

        c.gridx = 0; c.gridy = 2; center.add(new JLabel("Pregunta:"), c);
        c.gridx = 1; JScrollPane sp = new JScrollPane(preguntaArea); center.add(sp, c);

        ButtonGroup bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            c.gridx = 0; c.gridy = 3 + i; radio[i] = new JRadioButton("Correcta"); bg.add(radio[i]); center.add(radio[i], c);
            c.gridx = 1; respuestaFields[i] = new JTextField(30); center.add(respuestaFields[i], c);
        }

        add(center, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        JButton agregarBtn = new JButton("Agregar");
        JButton cancelarBtn = new JButton("Cancelar");
        actions.add(agregarBtn); actions.add(cancelarBtn);
        add(actions, BorderLayout.SOUTH);

        cancelarBtn.addActionListener(a -> dispose());
        agregarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPregunta();
            }
        });

        pack();
        setLocationRelativeTo(getOwner());

        loadCuestionarios(matricula);
    }

    private void loadCuestionarios(String matricula) {
        CuestionarioDAO dao = new CuestionarioDAO();
        List<ComboItem> items = dao.getCuestionariosByAsesor(matricula);
        DefaultComboBoxModel<ComboItem> model = new DefaultComboBoxModel<>();
        int defaultIndex = -1;
        for (int i = 0; i < items.size(); i++) {
            ComboItem it = items.get(i);
            model.addElement(it);
            if (defaultCuestionarioId != null && String.valueOf(defaultCuestionarioId).equals(it.getKey())) {
                defaultIndex = i;
            }
        }
        cbCuestionarios.setModel(model);
        if (defaultIndex >= 0) {
            cbCuestionarios.setSelectedIndex(defaultIndex);
        }
    }

    private void agregarPregunta() {
        ComboItem selected = (ComboItem) cbCuestionarios.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cuestionario.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idCuestionario;
        try { idCuestionario = Integer.parseInt(selected.getKey()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Id de cuestionario inválido.", "Error", JOptionPane.ERROR_MESSAGE); return; }

        int numero;
        try { numero = Integer.parseInt(numeroField.getText().trim()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Número inválido.", "Validación", JOptionPane.WARNING_MESSAGE); return; }

        int idTipo;
        try { idTipo = Integer.parseInt(tipoField.getText().trim()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Tipo inválido.", "Validación", JOptionPane.WARNING_MESSAGE); return; }

        String texto = preguntaArea.getText().trim();
        if (texto.isEmpty()) { JOptionPane.showMessageDialog(this, "Escriba la pregunta.", "Validación", JOptionPane.WARNING_MESSAGE); return; }

        List<String> respuestas = new ArrayList<>();
        int idxCorrecta = -1;
        for (int i = 0; i < 4; i++) {
            String r = respuestaFields[i].getText().trim();
            if (r.isEmpty()) { JOptionPane.showMessageDialog(this, "Complete todas las respuestas.", "Validación", JOptionPane.WARNING_MESSAGE); return; }
            respuestas.add(r);
            if (radio[i].isSelected()) idxCorrecta = i;
        }
        if (idxCorrecta == -1) { JOptionPane.showMessageDialog(this, "Seleccione la respuesta correcta.", "Validación", JOptionPane.WARNING_MESSAGE); return; }

        PreguntasDAO dao = new PreguntasDAO();
        int id = dao.createPreguntaWithRespuestas(idCuestionario, numero, texto, idTipo, respuestas, idxCorrecta);
        if (id > 0) {
            saved = true;
            dispose();
        }
    }

    public static boolean showDialog(Frame owner, String matricula) {
        AgregarPregunta d = new AgregarPregunta(owner, matricula);
        d.setVisible(true);
        return d.saved;
    }

    public static boolean showDialog(Frame owner, String matricula, Integer defaultCuestionarioId) {
        AgregarPregunta d = new AgregarPregunta(owner, matricula, defaultCuestionarioId);
        d.setVisible(true);
        return d.saved;
    }
}
