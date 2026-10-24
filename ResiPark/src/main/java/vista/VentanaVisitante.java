package vista;

import controlador.ControladorEntrada;

import javax.swing.*;
import java.awt.*;

public class VentanaVisitante extends JFrame {

    private JTextField txtNombre;
    private JTextField txtPlaca;
    private JTextField txtIdResidente;
    private JButton btnEnviar;

    private ControladorEntrada controlador;

    public VentanaVisitante(
            ControladorEntrada controlador
    ) {
        this.controlador = controlador;

        setTitle("Registrar Visitante");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formulario =new JPanel(new GridLayout(4, 2, 5, 5));

        formulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formulario.add(txtNombre);

        formulario.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        formulario.add(txtPlaca);

        formulario.add(new JLabel("ID residente visitado:"));

        txtIdResidente = new JTextField();
        formulario.add(txtIdResidente);

        btnEnviar = new JButton("Enviar a cola de entrada");

        formulario.add(new JLabel());
        formulario.add(btnEnviar);

        add(formulario, BorderLayout.CENTER);

        btnEnviar.addActionListener(e -> registrar());
    }

    private void registrar() {
        String mensaje =
                controlador.registrarVisitante(txtNombre.getText(),txtPlaca.getText(),txtIdResidente.getText());

        JOptionPane.showMessageDialog(this,mensaje);

        if (mensaje.startsWith("Visitante enviado")) {
            txtNombre.setText("");txtPlaca.setText("");txtIdResidente.setText("");
        }
    }
}