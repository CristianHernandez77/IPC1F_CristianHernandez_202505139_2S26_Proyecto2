package modelo;

public class Parqueo {
    private ListaCircularEspacios socios;
    private ListaCircularEspacios general;

    public Parqueo(){
        socios = new ListaCircularEspacios();
        general = new ListaCircularEspacios();

        // Area de socios: filas A,B,C con 25 espacios (A1,...A25,B1,...B25...)
        String[] filasSocios = {"A","B","C"};
        for(int f = 0; f < filasSocios.length; f++){
            for(int n = 1; n <= 25; n++){
                socios.agregar(new Espacio(filasSocios[f] + n, "SOCIO"));
            }
        }

        String [] filasGeneral = {"E","F","G","H","I"};
        for(int f = 0; f < filasGeneral.length; f++){
            for(int n = 1; n <= 15; n++){
                general.agregar(new Espacio(filasGeneral[f] + n, "GENERAL"));
            }
        }
    }

    public Espacio asignar(Vehiculo v){
        if(v.isSocio()){
            Espacio espacio = socios.asignar(v);
            if(espacio != null){
                return espacio;
            }
        }
        return general.asignar(v);
    }

    public String[] getIdsSocios(){
        return socios.getIds();
    }

    public String[] getIdsGeneral(){
        return general.getIds();
    }

    //Elegir la lista según la letra : A-C es el área de socios, el resto es general
    public int contarEnFila(char fila, boolean soloOcupados){
        if(fila >='A' && fila <= 'C'){
            return socios.contar(fila, soloOcupados);
        }
        return general.contar(fila, soloOcupados);
    }

    //--------------------------------------------------------------------------------
    // Libera el espacio del vehículo. Un socio pudo quedar en el área general
    // (si la de socios estaba llena), por eso se busca igual que en asignar().
    public Espacio liberar(Vehiculo v){
        if(v.isSocio()){
            Espacio espacio = socios.liberar(v);
            if(espacio != null){
                return espacio;
            }
        }
        return general.liberar(v);
    }

    // Busca en las dos áreas el vehículo estacionado con esa placa
    public Vehiculo buscarVehiculo(String placa){
        Vehiculo v = socios.buscarPorPlaca(placa);
        if(v != null){
            return v;
        }
        return general.buscarPorPlaca(placa);
    }

    // Junta las placas de las dos áreas en un solo arreglo para la vista
    public String[] getPlacasEstacionadas(){
        String[] deSocios = socios.getPlacasEstacionadas();
        String[] deGeneral = general.getPlacasEstacionadas();

        String[] todas = new String[deSocios.length + deGeneral.length];
        for(int i = 0; i < deSocios.length; i++){
            todas[i] = deSocios[i];
        }
        for(int j = 0; j < deGeneral.length; j++){
            todas[deSocios.length + j] = deGeneral[j];
        }
        return todas;
    }

    public synchronized Espacio buscarEspacio(String id) {
        Espacio espacio = socios.buscarPorId(id);

        if (espacio != null) {
            return espacio;
        }

        return general.buscarPorId(id);
    }

    public synchronized boolean restaurarVehiculo(
            Vehiculo vehiculo,
            String idEspacio,
            EstadoVehiculo estado
    ) {
        Espacio espacio = buscarEspacio(idEspacio);

        if (espacio == null || !espacio.isLibre()) {
            return false;
        }

        espacio.ocupar(vehiculo);
        vehiculo.setEstado(estado);

        return true;
    }

    public synchronized String obtenerOcupacion() {
        return socios.obtenerOcupacion()
                + general.obtenerOcupacion();
    }

    public synchronized Espacio buscarEspacioDeVehiculo(
            Vehiculo vehiculo
    ) {
        Espacio espacio =
                socios.buscarEspacioDeVehiculo(vehiculo);

        if (espacio != null) {
            return espacio;
        }

        return general.buscarEspacioDeVehiculo(vehiculo);
    }

    public synchronized String[] getPlacasOcupadas() {
        String[] placasSocios =
                socios.getPlacasOcupadas();

        String[] placasGeneral =
                general.getPlacasOcupadas();

        String[] todas = new String[
                placasSocios.length + placasGeneral.length
        ];

        for (int i = 0; i < placasSocios.length; i++) {
            todas[i] = placasSocios[i];
        }

        for (int i = 0; i < placasGeneral.length; i++) {
            todas[placasSocios.length + i] =
                    placasGeneral[i];
        }

        return todas;
    }
    //--------------------------------------------------------------------------------
}