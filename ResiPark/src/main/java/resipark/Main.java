

package resipark;

import javax.swing.SwingUtilities;

import controlador.ControladorEntrada;
import controlador.ControladorSalida;
import vista.VentanaPrincipal;
import modelo.Pila;
import vista.VentanaEventos;
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
            vista.setVisible(true);
        });
    }
}