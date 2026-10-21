package modelo;

public class Vehiculo {
    private String placa;
    private boolean socio;
    //--------------------------------------------------------------------------------
    private EstadoVehiculo estado;
    //--------------------------------------------------------------------------------

    public Vehiculo(String placa, boolean socio){
        this.placa = placa;
        this.socio = socio;
        //--------------------------------------------------------------------------------
        // Todo vehículo empieza fuera del residencial
        this.estado = EstadoVehiculo.FUERA;
        //--------------------------------------------------------------------------------
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isSocio(){
        return socio;
    }

    //--------------------------------------------------------------------------------
    // synchronized: el estado lo cambian las garitas (sus hilos) y la interfaz (hilo de Swing)
    public synchronized EstadoVehiculo getEstado(){
        return estado;
    }

    public synchronized void setEstado(EstadoVehiculo estado){
        this.estado = estado;
    }

    // Cambia el estado SOLO si el estado actual es el esperado.
    // Revisar y cambiar en un mismo método synchronized evita que dos acciones
    // vean "ESTACIONADO" al mismo tiempo y las dos lo manden a la cola de salida.
    public synchronized boolean cambiarEstado(EstadoVehiculo esperado, EstadoVehiculo nuevo){
        if(estado != esperado){
            return false;
        }
        estado = nuevo;
        return true;
    }
    //--------------------------------------------------------------------------------
}