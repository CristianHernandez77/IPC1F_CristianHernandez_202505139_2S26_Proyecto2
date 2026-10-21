package controlador;

import javax.swing.SwingUtilities;

import modelo.Cola;
import modelo.Espacio;
import modelo.EstadoVehiculo;
import modelo.Evento;
import modelo.Parqueo;
import modelo.Pila;
import modelo.Vehiculo;
import vista.VentanaPrincipal;

public class ControladorSalida {
    private Cola cola;
    private Parqueo parqueo;
    private VentanaPrincipal vista;

    // Recibe el parqueo del controlador de entrada: los dos trabajan sobre las mismas listas
    public ControladorSalida(VentanaPrincipal vista, Parqueo parqueo){
        this.vista = vista;
        this.parqueo = parqueo;
        this.cola = new Cola();   // Misma clase Cola, otra instancia

        vista.setAccionSalida(e -> solicitarSalida());
    }

    public void solicitarSalida(){
        String placa = vista.getPlacaSalida();
        if(placa == null){
            vista.mostrarMensaje("Seleccione un vehículo estacionado");
            return;
        }

        Vehiculo v = parqueo.buscarVehiculo(placa);
        if(v == null){
            vista.mostrarMensaje("El vehículo " + placa + " ya no está en el parqueo");
            return;
        }

        // Solo un vehículo ESTACIONADO puede pedir salida. Revisar y cambiar el estado
        // en un solo paso evita que el mismo vehículo entre dos veces a la cola.
        if(!v.cambiarEstado(EstadoVehiculo.ESTACIONADO, EstadoVehiculo.EN_COLA_SALIDA)){
            vista.mostrarMensaje("El vehículo " + placa + " ya solicitó su salida");
            return;
        }

        // Sigue ocupando su espacio mientras espera en la cola
        cola.encolar(v);
        actualizarCola();
        actualizarEstacionados();
    }

    public void iniciarGarita(){
        new GaritaSalida(cola, parqueo, this).start();
    }

    // Los métodos de abajo los llama el hilo de la garita: la interfaz se toca con invokeLater
    public void actualizarCola(){
        SwingUtilities.invokeLater(() -> vista.mostrarColaSalida(cola.listar()));
    }

    public void actualizarGarita(String texto){
        SwingUtilities.invokeLater(() -> vista.mostrarGaritaSalida(texto));
    }

    public void liberarEspacio(Espacio espacio){
        if (espacio != null) {
             String placa = espacio.getVehiculo() != null ? espacio.getVehiculo().getPlaca() : "Desconocida";
             Pila.global.push(new Evento("VEHICULO_SALIO", "Placa " + placa + " liberó " + espacio.getId(), "Garita Salida"));
             String id = espacio.getId();
            SwingUtilities.invokeLater(() -> vista.pintarEspacio(id, null));
        }   
        
    }

    public void actualizarEstacionados(){
        SwingUtilities.invokeLater(() -> vista.mostrarEstacionados(parqueo.getPlacasEstacionadas()));
    }
}