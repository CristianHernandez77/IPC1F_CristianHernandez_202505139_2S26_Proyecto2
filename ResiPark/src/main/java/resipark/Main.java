

package resipark;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controlador.Administrador;
import controlador.ControladorEntrada;
import controlador.ControladorSalida;
import vista.VentanaPrincipal;
import modelo.Pila;
//import vista.VentanaEventos;
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent;
public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            Pila historial = new Pila();
            VentanaPrincipal vista = new VentanaPrincipal();
            ControladorEntrada controladorEntrada = new ControladorEntrada(vista, historial);
            controladorEntrada.iniciarGaritas();
            //--------------------------------------------------------------------------------
            ControladorSalida controladorSalida = new ControladorSalida(vista, controladorEntrada.getParqueo());
            controladorSalida.iniciarGarita();
            //--------------------------------------------------------------------------------
            vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            vista.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Guardamos todos los datos antes de salir
                    Administrador.getInstance().guardarDatos();
                    System.out.println("Datos guardados. Cerrando programa...");
                    System.exit(0);
                }
            });
            vista.setVisible(true);

        });
    }
}