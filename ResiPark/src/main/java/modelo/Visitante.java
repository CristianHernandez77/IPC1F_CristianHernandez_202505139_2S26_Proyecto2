package modelo;

public class Visitante {
    private String nombre;
    private String placa;
    private String idResidente;
    private Vehiculo vehiculo;


    public Visitante(String nombre, String placa, String idResidente){
        this.nombre = nombre;
        this.placa = placa;
        this.idResidente = idResidente;
        this.vehiculo = new Vehiculo(placa, false);
    }

    public String getNombre(){
        return nombre;
    }

    public String getPlaca() {
        return placa;
    }

    public String getIdResidente() {
        return idResidente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    @Override 
    public String toString(){
        return nombre + " | Placa: " + placa + " | Visita a residente: "+ idResidente;
    }
}
