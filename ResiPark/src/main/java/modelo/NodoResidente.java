package modelo;

public class NodoResidente {
    Residente dato;
    NodoResidente siguiente;
    NodoResidente anterior; // Para la lista doble

    public NodoResidente(Residente dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}