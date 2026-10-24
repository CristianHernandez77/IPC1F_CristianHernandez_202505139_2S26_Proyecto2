package controlador;

import javax.swing.SwingUtilities;

// Antes:
// import modelo.ColaEntrada;
import modelo.Cola;
import modelo.Espacio;
//--------------------------------------------------------------------------------
import modelo.EstadoVehiculo;
import modelo.Evento;
//--------------------------------------------------------------------------------
import modelo.Parqueo;
import modelo.Pila;
import modelo.Residente;
import modelo.Vehiculo;
import modelo.Visitante;
import persistencia.Archivo;
//------------
import vista.ConsultaOcupacion;
//------------
import vista.VentanaPrincipal;

public class ControladorEntrada {
    // Antes:
    // private ColaEntrada cola;
    private Cola cola;
    private Parqueo parqueo;
    private VentanaPrincipal vista;
    private Pila historialEventos;
    private int contadorPrueba = 1;

    public ControladorEntrada(VentanaPrincipal vista, Pila historial){
        this.vista = vista;
        // Antes:
        // this.cola = new ColaEntrada();
        this.cola = new Cola();
        this.parqueo = new Parqueo();
        this.historialEventos = historial;
        //------------
        // antes: vista.construirArea("Socios (A-C(", parqueo.getIdsSocios(), 25);
        vista.construirArea("Socios (A-C)", parqueo.getIdsSocios(), 25);
        //------------
        vista.construirArea("General / Visitantes (E-I)", parqueo.getIdsGeneral(), 15);

        //------------
        // El adaptador traduce las preguntas del panel a las listas circulares
        vista.agregarPanelOcupacion(new ConsultaOcupacion() {
            @Override
            public int ocupadosEnFila(char fila){
                return parqueo.contarEnFila(fila, true);
            }

            @Override
            public int totalEnFila(char fila){
                return parqueo.contarEnFila(fila, false);
            }
        });
        //------------

        vista.setAccionEnviar(e->enviarVehiculo());
        //------------
        // antes: vista.setAccionEnviar(e->enviarPrueba());
        vista.setAccionPrueba(e->enviarPrueba());
        //------------
    }

    public void enviarVehiculo(){
        String placa = vista.getPlaca().toUpperCase();
        if(placa.isEmpty()){
            vista.mostrarMensaje("Ingrese una placa");
            return;
        }
        // Antes:
        // cola.encolar(new Vehiculo(placa, vista.isSocio()));
        //--------------------------------------------------------------------------------
        // El estado se cambia ANTES de encolar: una garita podría tomarlo en cuanto entra
        Vehiculo v = Administrador.getInstance().getListaResidentes().buscarVehiculoGlobal(placa);
        
        if(v == null){
            vista.mostrarMensaje("ERROR: La placa " + placa + " no pertenece a ningún residente registrado");
            return;
        }

        if(v.getEstado() != EstadoVehiculo.FUERA){
            vista.mostrarMensaje("ERROR: EL VEHICULO "+ placa + " ya esta dentro o en una cola");
            return;
        }
        v.setEstado(EstadoVehiculo.EN_COLA_ENTRADA);
        cola.encolar(v);
        //--------------------------------------------------------------------------------
        // registrar evento
        historialEventos.push(new Evento("VEHICULO_EN_COLA_ENTRADA", "Placa" + placa, "Garita de Entrada"));
        vista.limpiarPlaca();
        actualizarCola();
    }

    public void iniciarGaritas(){
        new Garita(1, cola, parqueo,this).start();
        new Garita(2,cola,parqueo,this).start();
    }

    //--------------------------------------------------------------------------------
    // La salida debe usar ESTE mismo parqueo, si no, no encontraría los vehículos
    public Parqueo getParqueo(){
        return parqueo;
    }
    //--------------------------------------------------------------------------------

    public void actualizarCola(){
        SwingUtilities.invokeLater(()-> vista.mostrarCola(cola.listar()));
    }

    public void actualizarGarita(int numero, String texto){
        SwingUtilities.invokeLater(()-> vista.mostrarGarita(numero, texto));
    }

    public void actualizarEspacio(Espacio espacio){
        String id = espacio.getId();
        String placa = espacio.getVehiculo().getPlaca();
        SwingUtilities.invokeLater(()->vista.pintarEspacio(id, placa));
        //--------------------------------------------------------------------------------
        // El vehículo recién estacionado ya puede solicitar su salida
        SwingUtilities.invokeLater(()->vista.mostrarEstacionados(parqueo.getPlacasEstacionadas()));
        //--------------------------------------------------------------------------------
    }

    private void enviarPrueba(){
        for(int i = 0; i < 10; i++){
            int numResidente = ((contadorPrueba -1)/3) + 1; 
            String idResidente = "PRUEBA" + numResidente;
            Residente r = Administrador.getInstance().getListaResidentes().buscarPorId(idResidente);
            if (r == null) {
                r = new Residente(idResidente, "Residente Prueba " + numResidente, "Casa P" + numResidente, true);
                Administrador.getInstance().getListaResidentes().agregar(r);
            }

            String placaPrueba = "T" + contadorPrueba;
            Vehiculo v = new Vehiculo(placaPrueba, "Marca", "Modelo", "Color", "Automóvil", idResidente, true);
            r.getVehiculos().agregar(v);
            v.setEstado(EstadoVehiculo.EN_COLA_ENTRADA);
            cola.encolar(v);
            historialEventos.push(new Evento("VEHICULO_EN_COLA_ENTRADA", "Placa " + placaPrueba, "Garita de Entrada"));
            
            contadorPrueba++;
        }
        actualizarCola();
    }

    public void restaurarOcupacion() {
    Archivo.cargarOcupacion(
            parqueo,
            Administrador.getInstance().getListaResidentes()
    );

     String[] placas = parqueo.getPlacasOcupadas();

    for (int i = 0; i < placas.length; i++) {
        Vehiculo vehiculo = parqueo.buscarVehiculo(placas[i]);

        if (vehiculo != null) {
            Espacio espacio =
                    parqueo.buscarEspacioDeVehiculo(vehiculo);

            if (espacio != null) {
                vista.pintarEspacio(
                        espacio.getId(),
                        vehiculo.getPlaca()
                );
            }
        }
    }

    vista.mostrarEstacionados(parqueo.getPlacasEstacionadas());
    }

    public boolean esVisitante(Vehiculo vehiculo) {
    return Administrador.getInstance().getListaVisitantes().contienePlaca(vehiculo.getPlaca());
    }

    public Espacio asignarVehiculo(Vehiculo vehiculo) {
    if (esVisitante(vehiculo)) {
        return parqueo.asignarVisitante(vehiculo);
    }

    return parqueo.asignar(vehiculo);
    }

    public void eliminarVisitante(String placa) {
    Visitante visitante = Administrador.getInstance().getListaVisitantes().eliminarPorPlaca(placa);

        if (visitante != null) {
            historialEventos.push(new Evento("VISITANTE_RECHAZADO","Visitante con placa " + placa + " fue rechazado y descartado","Garita de Entrada"));
        }
    }
    
    public String registrarVisitante(String nombre,String placa,String idResidente) {
    nombre = nombre.trim();
    placa = placa.trim().toUpperCase();
    idResidente = idResidente.trim();

    if (nombre.isEmpty()|| placa.isEmpty()|| idResidente.isEmpty()) {
        return "Complete todos los campos del visitante.";
    }

    if (nombre.contains("|")|| placa.contains("|")|| idResidente.contains("|")) {

        return "Los campos no pueden contener el carácter |.";
    }

    Residente residente = Administrador.getInstance().getListaResidentes().buscarPorId(idResidente);

        if (residente == null) {
            historialEventos.push(new Evento("VISITANTE_RECHAZADO","El residente " + idResidente+ " no existe","Garita de Entrada"
            ));

            return "El residente visitado no existe.";
        }

    Administrador administrador =
            Administrador.getInstance();

        if (administrador.placaExiste(placa)) {
            historialEventos.push(new Evento(
                "VISITANTE_RECHAZADO","La placa " + placa+ " ya existe en el sistema","Garita de Entrada"
            ));

            return "La placa ya existe en el sistema.";
        }

    Visitante visitante = new Visitante(nombre, placa, idResidente);

    boolean agregado = administrador.getListaVisitantes().agregar(visitante);

    if (!agregado) {
            return "No se pudo registrar el visitante.";
        }

        Vehiculo vehiculo = visitante.getVehiculo();
        vehiculo.setEstado(
            EstadoVehiculo.EN_COLA_ENTRADA
        );

        cola.encolar(vehiculo);

        historialEventos.push(new Evento(
            "VISITANTE_REGISTRADO",visitante.toString(),"Garita de Entrada"
        ));

        historialEventos.push(new Evento(
            "VEHICULO_EN_COLA_ENTRADA","Placa " + placa + " de visitante","Garita de Entrada"
        ));

        actualizarCola();

        return "Visitante enviado correctamente a la cola.";
    }

    public void registrarEvento(Evento evento) {
    if (evento != null) {
        historialEventos.push(evento);
    }
    }
}

