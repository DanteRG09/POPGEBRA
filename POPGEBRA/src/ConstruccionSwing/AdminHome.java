package ConstruccionSwing;

import Factories.JComponentOval;
import Factories.JComponentOvalBtn;
import GlobalConfig.TamañosColoresPosicion;
import GlobalConfig.Texto;

import ConstruccionSwing.InicioSesion.Login;
import javax.swing.*;
import java.awt.*;

public class AdminHome extends JFrame {
    private TamañosColoresPosicion valores = new TamañosColoresPosicion();
    private Texto Fuente = new Texto();

    public AdminHome() {
        super("Panel administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(valores.getTamañoVentana(0), valores.getTamañoVentana(1));
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));

        JComponentOval panel = new JComponentOval(60);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        panel.setPreferredSize(valores.getPosicionDelPanelRegistro());
        panel.setBackground(Color.decode(valores.getColorPanel()));

        JLabel titulo = new JLabel("Bienvenido Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Courier New", Font.BOLD, 36));
        titulo.setForeground(Color.BLACK);

        JLabel mensaje = new JLabel("Aquí puedes administrar el sistema.", SwingConstants.CENTER);
        mensaje.setFont(new Font("Courier New", Font.PLAIN, 18));
        mensaje.setForeground(Color.BLACK);

        JComponentOvalBtn btnCerrar = new JComponentOvalBtn(30);
        btnCerrar.setText("Cerrar sesión");
        btnCerrar.setPreferredSize(new Dimension(220, 50));
        btnCerrar.setBackground(new Color(243, 180, 45));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.addActionListener(e -> {
            dispose();
            new InicioSesion.Login().show();
        });

        panel.add(titulo);
        panel.add(mensaje);
        panel.add(btnCerrar);

        getContentPane().setBackground(Color.decode(valores.getColorFondo()));
        add(panel);
    }
}
