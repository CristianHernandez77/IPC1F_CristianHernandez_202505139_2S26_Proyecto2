package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Pila {

    public static Pila global = new Pila();

    private NodoEvento tope;
    private int tamano;

    public Pila() {
        tope = null;
        tamano = 0;
    }

    public synchronized void push(Evento evento) {
        NodoEvento nuevo = new NodoEvento(evento);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    public synchronized Evento pop() {
        if (tope == null) {
            return null;
        }

        Evento evento = tope.dato;
        tope = tope.siguiente;
        tamano--;

        return evento;
    }

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

   public synchronized void guardarEnArchivo(
        java.io.PrintWriter escritor
    ) {
    guardarDesdeAntiguo(tope, escritor);
    }

    private void guardarDesdeAntiguo(
        NodoEvento nodo,
        java.io.PrintWriter escritor
    ) {
    if (nodo == null) {
        return;
    }

    guardarDesdeAntiguo(nodo.siguiente, escritor);
    escritor.println(nodo.dato.serializar());
    }

    public synchronized void cargarDesdeArchivo(
        java.io.BufferedReader lector
    ) throws java.io.IOException {
    String linea;

    while ((linea = lector.readLine()) != null) {
        if (linea.trim().isEmpty()) {
            continue;
        }

        String[] partes = linea.split("\\|", -1);

        if (partes.length != 4) {
            continue;
        }

        Evento evento = new Evento(
                partes[0],
                partes[1],
                partes[2],
                partes[3]
        );

        push(evento);
        }
    }
}