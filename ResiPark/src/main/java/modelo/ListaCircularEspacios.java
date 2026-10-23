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

    // Cuenta los espacios de una fila recorriendo la lista una sola vuelta.
    // Si los soloOcupados es true  uenta únicamente los ocupuados; si es false, todos
    // Es synchronized porque las garitas modifican la lista desde otros hilos
    public synchronized int contar(char fila, boolean soloOcupados){
        if(primero == null ){
            return 0;
        }

        int cantidad = 0;
        Espacio actual = primero;
        do {
            if(actual.getId().charAt(0) == fila && (!soloOcupados || !actual.isLibre())){
                cantidad ++;
            }
            actual = actual.siguiente;
        }while(actual != primero);
        return cantidad;
    }

    //--------------------------------------------------------------------------------
    // Busca el espacio donde está el vehículo y lo libera. Recorre la lista una sola vuelta.
    // Es synchronized con el MISMO candado que asignar(): una garita de entrada y la de
    // salida nunca modifican esta lista al mismo tiempo.
    public synchronized Espacio liberar(Vehiculo v){
        if(primero == null){
            return null;
        }

        Espacio actual = primero;
        do {
            if(actual.getVehiculo() == v){
                actual.liberar();
                return actual;
            }
            actual = actual.siguiente;
        } while(actual != primero);
        return null;
    }

    // Busca un vehículo estacionado por su placa (null si no está en esta lista)
    public synchronized Vehiculo buscarPorPlaca(String placa){
        if(primero == null){
            return null;
        }

        Espacio actual = primero;
        do {
            if(!actual.isLibre() && actual.getVehiculo().getPlaca().equals(placa)){
                return actual.getVehiculo();
            }
            actual = actual.siguiente;
        } while(actual != primero);
        return null;
    }

    // Placas de los vehículos que pueden pedir salida (estado ESTACIONADO).
    // Igual que getIds(), el arreglo solo sirve para entregarle los datos a la vista.
    // Primera vuelta: contar. Segunda vuelta: llenar el arreglo.
    public synchronized String[] getPlacasEstacionadas(){
        if(primero == null){
            return new String[0];
        }

        int cantidad = 0;
        Espacio actual = primero;
        do {
            if(estaEstacionado(actual)){
                cantidad++;
            }
            actual = actual.siguiente;
        } while(actual != primero);

        String[] placas = new String[cantidad];
        int i = 0;
        actual = primero;
        do {
            if(estaEstacionado(actual)){
                placas[i] = actual.getVehiculo().getPlaca();
                i++;
            }
            actual = actual.siguiente;
        } while(actual != primero);
        return placas;
    }

    // Un vehículo en EN_COLA_SALIDA sigue ocupando su espacio, pero ya no puede pedir salida otra vez
    private boolean estaEstacionado(Espacio espacio){
        return !espacio.isLibre() && espacio.getVehiculo().getEstado() == EstadoVehiculo.ESTACIONADO;
    }
    //--------------------------------------------------------------------------------
    public synchronized Espacio buscarPorId(String id) {
    if (primero == null) {
        return null;
    }

    Espacio actual = primero;

    do {
        if (actual.getId().equalsIgnoreCase(id)) {
            return actual;
        }

        actual = actual.siguiente;
    } while (actual != primero);

    return null;
    }

    public synchronized String obtenerOcupacion() {
    String texto = "";

    if (primero == null) {
        return texto;
    }

    Espacio actual = primero;

    do {
        if (!actual.isLibre()) {
            Vehiculo vehiculo = actual.getVehiculo();
            EstadoVehiculo estado = vehiculo.getEstado();
             if (estado == EstadoVehiculo.ESTACIONADO ||
                estado == EstadoVehiculo.EN_COLA_SALIDA) {

                texto += vehiculo.getPlaca() + "|" +
                         estado.name() + "|" +
                         actual.getId() + "\n";
            }
        }

        actual = actual.siguiente;
    } while (actual != primero);

    return texto;
    }

    public synchronized Espacio buscarEspacioDeVehiculo(
        Vehiculo vehiculo
) {
    if (primero == null) {
        return null;
    }

    Espacio actual = primero;

    do {
        if (actual.getVehiculo() == vehiculo) {
            return actual;
        }

        actual = actual.siguiente;
    } while (actual != primero);

    return null;
    }

    public synchronized String[] getPlacasOcupadas() {
    if (primero == null) {
        return new String[0];
    }

    int cantidad = 0;
    Espacio actual = primero;

    do {
        if (!actual.isLibre()) {
            cantidad++;
        }

        actual = actual.siguiente;
    } while (actual != primero);

    String[] placas = new String[cantidad];
    int i = 0;
    actual = primero;

    do {
        if (!actual.isLibre()) {
            placas[i] = actual.getVehiculo().getPlaca();
            i++;
        }

        actual = actual.siguiente;
    } while (actual != primero);

    return placas;
    }
}