package modelo;

public class NodoVehiculo {
    public Vehiculo dato;
    public NodoVehiculo siguiente;

    public NodoVehiculo(Vehiculo dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}