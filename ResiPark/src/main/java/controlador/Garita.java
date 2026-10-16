package controlador;

import modelo.ColaEntrada;
import modelo.Espacio;
import modelo.Parqueo;
import modelo.Vehiculo;

public class Garita extends Thread{
    private int numero;
    private ColaEntrada cola;
    private Parqueo parqueo;
    private ControladorEntrada controlador;

    public Garita(int numero, ColaEntrada cola, Parqueo parqueo, ControladorEntrada controlador){
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
                Thread.sleep(5000+(int) (Math.random() * 2000));
            }catch(InterruptedException e){
                break;
            }

            Espacio espacio = parqueo.asignar(v);
            if(espacio == null){
                controlador.actualizarGarita(numero, "RECHAZADO "+v.getPlaca() + " (sin espacio)");
            } else {
                controlador.actualizarEspacio(espacio);
                controlador.actualizarGarita(numero, v.getPlaca() + " -> " + espacio.getId());
            }

            //controlador.actualizarGarita(numero, "libre");
        }
    }

}