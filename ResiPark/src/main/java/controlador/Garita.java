package controlador;

// Antes:
// import modelo.ColaEntrada;
import modelo.Cola;
import modelo.Espacio;
//--------------------------------------------------------------------------------
import modelo.EstadoVehiculo;
//--------------------------------------------------------------------------------
import modelo.Parqueo;
import modelo.Vehiculo;
import modelo.Pila;
import modelo.Evento;

public class Garita extends Thread{
    private int numero;
    // Antes:
    // private ColaEntrada cola;
    private Cola cola;
    private Parqueo parqueo;
    private ControladorEntrada controlador;

    // Antes:
    // public Garita(int numero, ColaEntrada cola, Parqueo parqueo, ControladorEntrada controlador){
    public Garita(int numero, Cola cola, Parqueo parqueo, ControladorEntrada controlador){
        this.numero = numero;
        this.cola = cola;
        this.parqueo = parqueo;
        this.controlador = controlador;
        setDaemon(true);
    }

    @Override
    public void run(){
        while(true){
            Vehiculo v = cola.desencolar();
            if(v == null){
                break;
            }
            controlador.actualizarCola();
            controlador.actualizarGarita(numero, "Atendiendo: " + v.getPlaca());

            try{
                Thread.sleep(1000+(int) (Math.random() * 2000));
            }catch(InterruptedException e){
                break;
            }

            Espacio espacio = controlador.asignarVehiculo(v);
            if(espacio == null){
                //--------------------------------------------------------------------------------
                // Rechazado: no se queda esperando en la cola, vuelve a FUERA
                v.setEstado(EstadoVehiculo.FUERA);
                //--------------------------------------------------------------------------------
                    if (controlador.esVisitante(v)) {
                        controlador.eliminarVisitante(v.getPlaca());
                    }
                controlador.registrarEvento(new Evento("VEHICULO_RECHAZADO", "Placa "+ v.getPlaca()+ " Sin espacio disponible", "Garita "+ numero));
                controlador.actualizarGarita(numero,"RECHAZADO "+ v.getPlaca() + " (Sin espacio)");
            } else {
                controlador.registrarEvento(new Evento("VEHICULO_ASIGNADO", "Placa "+ v.getPlaca() + " asignada a " + espacio.getId(), "Garita "+ numero ));
                controlador.actualizarEspacio(espacio);
                controlador.actualizarGarita(numero, v.getPlaca() + " -> " + espacio.getId());
            //controlador.actualizarGarita(numero, "libre");
            }
        }
    }
}