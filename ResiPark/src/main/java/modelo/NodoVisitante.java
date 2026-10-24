package modelo;

public class NodoVisitante {
    public Visitante dato;
    public NodoVisitante siguiente;

    public NodoVisitante(Visitante dato){
        this.dato = dato;
        this.siguiente = null;
    }
}
