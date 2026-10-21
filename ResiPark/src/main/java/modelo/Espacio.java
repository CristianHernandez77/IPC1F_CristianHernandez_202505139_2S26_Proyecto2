package modelo;

public class Espacio {
    private String id;
    private String tipo;
    private Vehiculo vehiculo;
    Espacio siguiente;

    public Espacio(String id, String tipo){
        this.id = id;
        this.tipo = tipo;
        this.vehiculo = null;
        this.siguiente = null;
    }

    public String getId(){
        return id;
    }

    public String getTipo(){
        return tipo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public boolean isLibre(){
        return vehiculo == null;
    }

    public void ocupar(Vehiculo v){
        this.vehiculo = v;
        //--------------------------------------------------------------------------------
        // Ocupar un espacio y quedar ESTACIONADO pasan juntos, dentro del synchronized de la lista
        v.setEstado(EstadoVehiculo.ESTACIONADO);
        //--------------------------------------------------------------------------------
    }

    public void liberar(){
        //--------------------------------------------------------------------------------
        // El vehículo que sale vuelve a quedar FUERA
        if(vehiculo != null){
            vehiculo.setEstado(EstadoVehiculo.FUERA);
        }
        //--------------------------------------------------------------------------------
        this.vehiculo = null;
    }
}