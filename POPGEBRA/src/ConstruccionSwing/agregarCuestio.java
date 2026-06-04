package ConstruccionSwing;

/*@author Gabriel Ordoñes
 */

import Factories.JComponentOval;
import Factories.BotonOvalado;

import javax.swing.*;
import java.awt.*;

public class agregarCuestio extends JFrame{
    
    public agregarCuestio(){
    
        setTitle ("POPGEBRA");
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

        // =========================================================================
        // CONTENEDOR PRINCIPAL DERECHO (FONDO OSCURO)
        // =========================================================================
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(new Color(25, 25, 25));


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

    
    
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new agregarCuestio().setVisible(true);
        });
    }
}