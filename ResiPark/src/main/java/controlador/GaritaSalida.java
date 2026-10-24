package controlador;

import modelo.Cola;
import modelo.Espacio;
import modelo.Evento;
import modelo.Parqueo;
import modelo.Pila;
import modelo.Vehiculo;
import modelo.Visitante;
import controlador.Administrador;

// Hilo de la garita de salida: consume vehículos de la cola de salida
public class GaritaSalida extends Thread {
    private Cola cola;
    private Parqueo parqueo;
    private ControladorSalida controlador;

    public GaritaSalida(Cola cola, Parqueo parqueo, ControladorSalida controlador){
        this.cola = cola;
        this.parqueo = parqueo;
        this.controlador = controlador;
        setDaemon(true);
    }

    @Override
    public void run(){
        while(true){
            // Si la cola está vacía, el hilo se duerme dentro de desencolar() con wait()
            Vehiculo v = cola.desencolar();
            if(v == null){
                break;
            }
            controlador.actualizarCola();
            controlador.actualizarGarita("Atendiendo: " + v.getPlaca());

            // Tiempo de atención (el enunciado pide entre 1 y 3 segundos)
            try{
                Thread.sleep(1000 + (int) (Math.random() * 2000));
            }catch(InterruptedException e){
                break;
            }

            // El espacio se libera hasta que la garita lo atiende, NO cuando entra a la cola
            Espacio espacio = parqueo.liberar(v);
            if(espacio == null){
                controlador.actualizarGarita("ERROR: " + v.getPlaca() + " no tenía espacio");
            } else {
                controlador.liberarEspacio(espacio);

                Visitante visitante = Administrador.getInstance().getListaVisitantes().eliminarPorPlaca(v.getPlaca());
                if(visitante != null){
                    Pila.global.push(new Evento("VISITANTE_ELIMINADO", "Visitante "+ visitante.getNombre() + " salio del residencial", "Garita de salida"));
                }
                controlador.actualizarGarita(v.getPlaca() + " salió de " + espacio.getId());
                Pila.global.push(new Evento("VEHICULO_SALIO", "Placa " + v.getPlaca() + " liberó " + espacio.getId(), "Garita Salida"));
            }
        }
    }
}