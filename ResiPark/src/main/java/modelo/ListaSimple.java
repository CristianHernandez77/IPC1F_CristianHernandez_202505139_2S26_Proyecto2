package modelo;

public class ListaSimple {
    private NodoVehiculo primero;
    private int tamano;

    public ListaSimple() {
        primero = null;
        tamano = 0;
    }

    public synchronized boolean agregar(Vehiculo v) {
        // Validar que no tenga más de 3 vehículos
        if (tamano >= 3) {
            System.out.println("Un residente no puede tener más de 3 vehículos.");
            return false;
        }
        
        NodoVehiculo nuevo = new NodoVehiculo(v);
        if (primero == null) {
            primero = nuevo;
        } else {
            NodoVehiculo actual = primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamano++;
        return true;
    }

    public synchronized Vehiculo buscarPorPlaca(String placa) {
        NodoVehiculo actual = primero;
        while (actual != null) {
            if (actual.dato.getPlaca().equals(placa)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public synchronized boolean eliminar(String placa) {
        if (primero == null) return false;

        // Si es el primero
        if (primero.dato.getPlaca().equals(placa)) {
            primero = primero.siguiente;
            tamano--;
            return true;
        }

        NodoVehiculo actual = primero;
        while (actual.siguiente != null) {
            if (actual.siguiente.dato.getPlaca().equals(placa)) {
                actual.siguiente = actual.siguiente.siguiente;
                tamano--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public synchronized String listar() {
        String texto = "";
        NodoVehiculo actual = primero;
        while (actual != null) {
            texto += actual.dato.toString() + "\n";
            actual = actual.siguiente;
        }
        return texto;
    }

    public synchronized int getTamano() {
        return tamano;
    }

    public NodoVehiculo getPrimero() {
    return primero;
    }

    public synchronized boolean todosEstanFuera() {
    NodoVehiculo actual = primero;

    while (actual != null) {
        if (actual.dato.getEstado() != EstadoVehiculo.FUERA) {
            return false;
        }

        actual = actual.siguiente;
    }

    return true;
    }
}