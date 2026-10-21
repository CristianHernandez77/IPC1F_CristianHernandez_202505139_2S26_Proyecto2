

package resipark;

import javax.swing.SwingUtilities;

import controlador.ControladorEntrada;
import controlador.ControladorSalida;
import vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            VentanaPrincipal vista = new VentanaPrincipal();
            ControladorEntrada controlador = new ControladorEntrada(vista);
            controlador.iniciarGaritas();
            //--------------------------------------------------------------------------------
            ControladorSalida controladorSalida = new ControladorSalida(vista, controlador.getParqueo());
            controladorSalida.iniciarGarita();
            //--------------------------------------------------------------------------------
            vista.setVisible(true);
        });
    }
}