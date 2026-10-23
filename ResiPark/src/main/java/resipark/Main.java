package resipark;

import controlador.Administrador;
import controlador.ControladorEntrada;
import controlador.ControladorSalida;
import modelo.Pila;
import vista.VentanaPrincipal;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Carga los datos antes de iniciar la interfaz
            Administrador administrador =
                    Administrador.getInstance();

            Pila historial = Pila.global;

            VentanaPrincipal vista =
                    new VentanaPrincipal();

            ControladorEntrada controladorEntrada =
                    new ControladorEntrada(vista, Pila.global);
            controladorEntrada.restaurarOcupacion();
            controladorEntrada.iniciarGaritas();

            ControladorSalida controladorSalida =
                    new ControladorSalida(
                            vista,
                            controladorEntrada.getParqueo()
                    );

            controladorSalida.iniciarGarita();

            vista.setDefaultCloseOperation(
                    JFrame.DO_NOTHING_ON_CLOSE
            );

            vista.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Administrador.getInstance().guardarDatos(controladorEntrada.getParqueo());;             
                    System.exit(0);
                }
            });

            vista.setVisible(true);
        });
    }
}