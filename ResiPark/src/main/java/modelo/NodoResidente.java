package modelo;

public class NodoResidente {
    public Residente dato;
    public NodoResidente siguiente;
    public NodoResidente anterior; // Para la lista doble

    public NodoResidente(Residente dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}