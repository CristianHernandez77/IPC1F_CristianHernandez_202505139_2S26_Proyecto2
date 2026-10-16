package modelo;

public class ListaCircularEspacios {
    private Espacio primero;
    private Espacio ultimo;
    private Espacio ultimoAsignado;
    private int tamano;

    public ListaCircularEspacios(){
        primero = null;
        ultimo = null;
        ultimoAsignado = null;
        tamano = 0;
    }

    public void agregar(Espacio nuevo){
        if(primero == null){
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }

        ultimo.siguiente = primero;
        tamano++;
    }

    public synchronized Espacio asignar(Vehiculo v){
        if(primero == null) return null;

        Espacio inicio;
        if(ultimoAsignado == null){
            inicio = primero;
        } else {
            inicio = ultimoAsignado.siguiente;
        }

        Espacio actual = inicio;

        do {
            if(actual.isLibre()){
                actual.ocupar(v);
                ultimoAsignado = actual;
                return actual;
            }
            actual = actual.siguiente;
        }while(actual != inicio);
        return null;
    }

    public synchronized String[] getIds(){
        String [] ids = new String[tamano];
        if(primero == null){
            return ids;
        }

        Espacio actual = primero;
        int i = 0;
        do{
            ids[i] = actual.getId();
            i++;
            actual = actual.siguiente;
        } while(actual != primero);
        return ids;
    }
}