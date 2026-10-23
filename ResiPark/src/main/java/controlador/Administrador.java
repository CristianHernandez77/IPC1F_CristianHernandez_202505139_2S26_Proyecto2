package controlador;

import modelo.ListaDoble;
import modelo.Vehiculo;
import modelo.Residente;
import persistencia.Archivo;

public class Administrador {
    private static Administrador instancia;
    private ListaDoble listaResidentes;

    private Administrador() {
        listaResidentes = new ListaDoble();
    }

    public static  Administrador getInstance() {
    if (instancia == null) {
        instancia = new Administrador();
    }
    return instancia;
    }

    public ListaDoble getListaResidentes() {
        return listaResidentes;
    }

    public Vehiculo buscarVehiculoGlobal(String placa){
        return null;
    }

    public void guardarDatos() {
        Archivo.guardarTodo(listaResidentes);
    }
}

