package modelo;

public class Nodo {
    Vehiculo dato;

    Nodo siguiente;

    public Nodo(Vehiculo dato){
        this.dato = dato;
        this.siguiente = null;
    }
}