package controlador;

import modelo.ListaDoble;
import modelo.Parqueo;
import modelo.Pila;
import modelo.Vehiculo;
import persistencia.Archivo;

public class Administrador {

    private static Administrador instancia;
    private ListaDoble listaResidentes;

    private Administrador() {
        listaResidentes = Archivo.cargarTodo();
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

    public Vehiculo buscarVehiculoGlobal(String placa) {
        return listaResidentes.buscarVehiculoGlobal(placa);
    }

    public void guardarDatos(Parqueo parqueo) {
        Archivo.guardarTodo(listaResidentes, parqueo);
    }
}