package modelo;

public class ListaVisitantes {
    private NodoVisitante primero;
    private NodoVisitante ultimo;
    private int tamano;

    public ListaVisitantes() {
        primero = null;
        ultimo = null;
        tamano = 0;
    }
    
    public synchronized boolean agregar(Visitante visitante){
        if(visitante == null){
            return false;
        }

        if(buscarPorPlaca(visitante.getPlaca()) != null){
            return false;
        }
        
        NodoVisitante nuevo = new NodoVisitante(visitante);

        if(primero == null){
            primero = nuevo;
            ultimo = nuevo;
        }else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }

        tamano++;
        return true;
    }

    public synchronized Visitante buscarPorPlaca(String placa){
        NodoVisitante actual = primero;
        while (actual != null) {
            if(actual.dato.getPlaca().equalsIgnoreCase(placa)){
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public synchronized Visitante eliminarPorPlaca(String placa){
        NodoVisitante anterior = null;
        NodoVisitante actual = primero;
        
        while (actual != null) {
            if(actual.dato.getPlaca().equalsIgnoreCase(placa)){
                if(anterior == null){
                    primero = actual.siguiente;
                }else {
                    anterior.siguiente = actual.siguiente;
                }

                if(actual == ultimo){
                    ultimo = anterior;
                }

                tamano--;
                return actual.dato;
            }

            anterior = actual;
            actual = actual.siguiente;
        }
        return null;
    }

    public synchronized boolean contienePlaca(String placa){
        return buscarPorPlaca(placa) != null;
    }

    public synchronized String listar(){
        String texto =  "";
        NodoVisitante actual = primero;

        while (actual != null) {
            texto += actual.dato.toString() + "\n";
            actual = actual.siguiente;
        }

        return texto;
    }

    public synchronized int getTamano(){
        return tamano;
    }
}
