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

    public ControladorEntrada(VentanaPrincipal vista, Pila hostorial){
        this.vista = vista;
        // Antes:
        // this.cola = new ColaEntrada();
        this.cola = new Cola();
        this.parqueo = new Parqueo();
        this.historialEventos = Pila.global;
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
        Pila.global.push(new Evento("VEHICULO_EN_COLA_ENTRADA", "Placa" + placa, "Garita de Entrada"));
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
            int numResidente = (contadorPrueba / 3) + 1; 
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
            Pila.global.push(new Evento("VEHICULO_EN_COLA_ENTRADA", "Placa " + placaPrueba, "Garita de Entrada"));
            
            contadorPrueba++;
        }
        actualizarCola();
    }
}

