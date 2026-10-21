package controlador;

import javax.swing.SwingUtilities;

// Antes:
// import modelo.ColaEntrada;
import modelo.Cola;
import modelo.Espacio;
//--------------------------------------------------------------------------------
import modelo.EstadoVehiculo;
//--------------------------------------------------------------------------------
import modelo.Parqueo;
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
    private int contadorPrueba = 1;

    public ControladorEntrada(VentanaPrincipal vista){
        this.vista = vista;
        // Antes:
        // this.cola = new ColaEntrada();
        this.cola = new Cola();
        this.parqueo = new Parqueo();

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
        String placa = vista.getPlaca();
        if(placa.isEmpty()){
            vista.mostrarMensaje("Ingrese una placa");
            return;
        }
        // Antes:
        // cola.encolar(new Vehiculo(placa, vista.isSocio()));
        //--------------------------------------------------------------------------------
        // El estado se cambia ANTES de encolar: una garita podría tomarlo en cuanto entra
        Vehiculo v = new Vehiculo(placa, vista.isSocio());
        v.setEstado(EstadoVehiculo.EN_COLA_ENTRADA);
        cola.encolar(v);
        //--------------------------------------------------------------------------------
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
            // Antes:
            // cola.encolar(new Vehiculo("T" + contadorPrueba, vista.isSocio()));
            //--------------------------------------------------------------------------------
            Vehiculo v = new Vehiculo("T" + contadorPrueba, vista.isSocio());
            v.setEstado(EstadoVehiculo.EN_COLA_ENTRADA);
            cola.encolar(v);
            //--------------------------------------------------------------------------------
            contadorPrueba++;
        }
        //------------
        actualizarCola();
        //------------
    }
}