package modelo;

public class NodoVehiculo {
    Vehiculo dato;
    NodoVehiculo siguiente;

    public NodoVehiculo(Vehiculo dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}