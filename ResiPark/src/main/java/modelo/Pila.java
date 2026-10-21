package modelo;

public class Pila {
    private NodoEvento tope;
    private int tamano;

    public Pila() {
        tope = null;
        tamano = 0;
    }

    // synchronized para que las garitas no choquen al guardar eventos
    public synchronized void push(Evento e) {
        NodoEvento nuevo = new NodoEvento(e);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    public synchronized Evento pop() {
        if (tope == null) return null;
        Evento e = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return e;
    }

    // Muestra los eventos del más reciente al más antiguo
    public synchronized String listarInverso() {
        String texto = "";
        NodoEvento actual = tope;
        while (actual != null) {
            texto += actual.dato.toString() + "\n";
            actual = actual.siguiente;
        }
        return texto;
    }

    public synchronized int getTamano() {
        return tamano;
    }
}