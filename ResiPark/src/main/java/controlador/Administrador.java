package controlador;

import modelo.ListaDoble;
import modelo.ListaVisitantes;
import modelo.Parqueo;
import modelo.Pila;
import modelo.Vehiculo;
import modelo.Visitante;
import persistencia.Archivo;

public class Administrador {

    private static Administrador instancia;

    private ListaDoble listaResidentes;
    private ListaVisitantes listaVisitantes;

    private Administrador() {
        listaResidentes = Archivo.cargarTodo();
        listaVisitantes = new ListaVisitantes();

        Archivo.cargarEventos(Pila.global);
    }

    public static synchronized Administrador getInstance() {
        if (instancia == null) {
            instancia = new Administrador();
        }

        return instancia;
    }

    public ListaDoble getListaResidentes() {
        return listaResidentes;
    }

    public ListaVisitantes getListaVisitantes() {
        return listaVisitantes;
    }

    public Vehiculo buscarVehiculoGlobal(String placa) {
        Vehiculo vehiculo =
                listaResidentes.buscarVehiculoGlobal(placa);

        if (vehiculo != null) {
            return vehiculo;
        }

        Visitante visitante =
                listaVisitantes.buscarPorPlaca(placa);

        if (visitante != null) {
            return visitante.getVehiculo();
        }

        return null;
    }

    public boolean placaExiste(String placa) {
        return buscarVehiculoGlobal(placa) != null;
    }

    public void guardarDatos(Parqueo parqueo) {

        Archivo.guardarTodo(listaResidentes, parqueo);
    }
}