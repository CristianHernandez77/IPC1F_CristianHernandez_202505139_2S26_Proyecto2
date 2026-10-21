package modelo;

public class NodoEvento {
    Evento dato;
    NodoEvento siguiente;

    public NodoEvento(Evento dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}