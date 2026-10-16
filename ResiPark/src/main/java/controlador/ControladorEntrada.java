package controlador;

import javax.swing.SwingUtilities;

import modelo.ColaEntrada;
import modelo.Espacio;
import modelo.Parqueo;
import modelo.Vehiculo;
import vista.VentanaPrincipal;

public class ControladorEntrada {
    private ColaEntrada cola;
    private Parqueo parqueo;
    private VentanaPrincipal vista;
    private int contadorPrueba = 1;

    public ControladorEntrada(VentanaPrincipal vista){
        this.vista = vista;
        this.cola = new ColaEntrada();
        this.parqueo = new Parqueo();

        vista.construirArea("Socios (A-C(", parqueo.getIdsSocios(), 25);
        vista.construirArea("General / Visitantes (E-I)", parqueo.getIdsGeneral(), 15);
        vista.setAccionEnviar(e->enviarVehiculo());
        vista.setAccionEnviar(e->enviarPrueba());
    }

    public void enviarVehiculo(){
        String placa = vista.getPlaca();
        if(placa.isEmpty()){
            vista.mostrarMensaje("Ingrese una placa");
            return;
        }
        cola.encolar(new Vehiculo(placa, vista.isSocio()));
        vista.limpiarPlaca();
        actualizarCola();
    }

    public void iniciarGaritas(){
        new Garita(1, cola, parqueo,this).start();
        new Garita(2,cola,parqueo,this).start();
    }

    public void actualizarCola(){
        SwingUtilities.invokeLater(()-> vista.mostrarCola(cola.listar()));
    }

    public void actualizarGarita(int numero, String texto){
        SwingUtilities.invokeLater(()-> vista.mostrarGarita(numero, texto));
    }

    public void actualizarEspacio(Espacio espacio){
        String id = espacio.getId();
        String placa = espacio.getVehiculo().getPlaca();
        SwingUtilities.invokeLater(()->vista.pintarEspacio(id, placa));
    }

    private void enviarPrueba(){
        for(int i = 0; i < 10; i++){
            cola.encolar(new Vehiculo("T" + contadorPrueba, vista.isSocio()));
            contadorPrueba++;
        }
    }
}