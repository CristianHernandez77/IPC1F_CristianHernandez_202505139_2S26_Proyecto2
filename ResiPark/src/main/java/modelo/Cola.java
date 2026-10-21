package modelo;

// Antes:
// public class ColaEntrada {
public class Cola {
    private Nodo frente;
    private Nodo ultimo;
    private int tamano;

    // Antes:
    // public ColaEntrada(){
    public Cola(){
        frente = null;
        ultimo = null;
        tamano = 0;
    }

    //Agregar al final de la lista y despertar las garitas que esperan
    public synchronized void encolar(Vehiculo v){
        Nodo nuevo = new Nodo(v);
        if(frente == null){
            frente = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
        tamano++;
        notifyAll();
    }

    // Sacar del frente. Si está vacía,la garita (hilo) se duerme con wait()
    public synchronized Vehiculo desencolar(){
        while(frente == null){
            try{
                wait();

            }catch(InterruptedException e){
                return null;
            }
        }

        Vehiculo v = frente.dato;
        frente = frente.siguiente;
        if(frente == null){
            ultimo = null;
        }
        tamano--;
        return v;
    }

    // Recorrer la lista nodo para mostrarla en la interfaz
    public synchronized String listar(){
        String texto = "";
        Nodo actual = frente;
        int posicion = 1;
        while(actual != null){
            texto += posicion + " | " + actual.dato.getPlaca() + "\n";
            actual = actual.siguiente;
            posicion++;
        }
        return texto;
    }

    public synchronized int getTamano(){
        return tamano;
    }

}