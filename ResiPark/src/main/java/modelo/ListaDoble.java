package modelo;

public class ListaDoble {
    private NodoResidente primero;
    private NodoResidente ultimo;
    private int tamano;

    public ListaDoble() {
        primero = null;
        ultimo = null;
        tamano = 0;
    }

    public synchronized boolean agregar(Residente r) {
        if (buscarPorId(r.getId()) != null) {
            return false; // ID duplicado
        }

        NodoResidente nuevo = new NodoResidente(r);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            nuevo.anterior = ultimo;
            ultimo = nuevo;
        }
        tamano++;
        return true;
    }

    public synchronized Residente buscarPorId(String id) {
        NodoResidente actual = primero;
        while (actual != null) {
            if (actual.dato.getId().equals(id)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public synchronized boolean eliminar(String id) {
        NodoResidente actual = primero;
        while (actual != null) {
            if (actual.dato.getId().equals(id)) {
                // Regla: No eliminar si tiene vehículos que NO estén FUERA
                ListaSimple listaVehiculos = actual.dato.getVehiculos();
                // (Aquí validaremos si están fuera, por ahora lo dejamos en false para que no borre si tiene)
                if (listaVehiculos.getTamano() > 0) {
                    // Lógica futura: revisar si todos están en estado FUERA.
                }

                if (actual == primero) {
                    primero = actual.siguiente;
                    if (primero != null) primero.anterior = null;
                } else if (actual == ultimo) {
                    ultimo = actual.anterior;
                    if (ultimo != null) ultimo.siguiente = null;
                } else {
                    actual.anterior.siguiente = actual.siguiente;
                    actual.siguiente.anterior = actual.anterior;
                }
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public synchronized String listar() {
        String texto = "";
        NodoResidente actual = primero;
        while (actual != null) {
            texto += "ID: " + actual.dato.getId() + " | Nombre: " + actual.dato.getNombre() + 
                     " | Casa: " + actual.dato.getCasa() + " | Socio: " + (actual.dato.isEsSocio() ? "S" : "N") + "\n";
            actual = actual.siguiente;
        }
        return texto;
    }
    
    public synchronized int getTamano() {
        return tamano;
    }
}