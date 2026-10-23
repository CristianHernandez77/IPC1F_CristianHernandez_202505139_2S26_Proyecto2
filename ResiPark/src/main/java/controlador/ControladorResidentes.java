package controlador;

import modelo.*;

public class ControladorResidentes {
    private ListaDoble listaResidentes;
    private Residente residenteActual; // Para saber a quién le estamos agregando vehículos

    public ControladorResidentes() {
        this.listaResidentes = Administrador.getInstance().getListaResidentes();
        this.residenteActual = null;
    }

    public String registrarResidente(String id, String nombre, String casa, boolean esSocio) {
        if (id.isEmpty() || nombre.isEmpty() || casa.isEmpty()) {
            return "Error: Todos los campos son obligatorios.";
        }
        if (id.contains("|") || nombre.contains("|") || casa.contains("|")) {
            return "Error: No se permite el carácter '|' en los campos.";
        }

        Residente r = new Residente(id, nombre, casa, esSocio);
        if (listaResidentes.agregar(r)) {
            return "Residente registrado exitosamente.";
        } else {
            return "Error: El ID ya existe.";
        }
    }

    public Residente buscarResidente(String id) {
        residenteActual = listaResidentes.buscarPorId(id);
        return residenteActual;
    }

    public String modificarResidente(String id, String nuevoNombre, String nuevaCasa, boolean nuevoSocio) {
        Residente r = listaResidentes.buscarPorId(id);
        if (r == null) {
            return "Error: Residente no encontrado.";
        }
        // Validación: no cambiar socio si tiene vehículos fuera
        if (r.isEsSocio() != nuevoSocio) {
            if (!todosVehiculosFuera(r)) {
                return "Error: No se puede cambiar la condición de socio porque tiene vehículos activos.";
            }
        }
        r.setNombre(nuevoNombre);
        r.setCasa(nuevaCasa);
        r.setEsSocio(nuevoSocio);
        return "Residente modificado correctamente.";
    }

    public String eliminarResidente(String id) {
        Residente r = listaResidentes.buscarPorId(id);
        if (r == null) {
            return "Error: Residente no encontrado.";
        }
        if (!todosVehiculosFuera(r)) {
            return "Error: No se puede eliminar. El residente tiene vehículos estacionados o en cola.";
        }
        listaResidentes.eliminar(id);
        if (residenteActual != null && residenteActual.getId().equals(id)) {
            residenteActual = null;
        }
        return "Residente eliminado correctamente.";
    }

    public String listarResidentes() {
        return listaResidentes.listar();
    }

    // --- Métodos para Vehículos ---

    public String agregarVehiculo(String placa, String marca, String modelo, String color, String tipo) {
        if (residenteActual == null) {
            return "Error: Primero busque un residente.";
        }
        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || color.isEmpty()) {
            return "Error: Todos los campos del vehículo son obligatorios.";
        }
        if (placa.contains("|")) {
            return "Error: La placa no puede contener '|'.";
        }

        // Validación de placa única en el residente actual
        if (residenteActual.getVehiculos().buscarPorPlaca(placa) != null) {
            return "Error: El residente ya tiene un vehículo con esa placa.";
        }

        Vehiculo v = new Vehiculo(placa, marca, modelo, color, tipo, residenteActual.getId(), residenteActual.isEsSocio());
        if (residenteActual.getVehiculos().agregar(v)) {
            return "Vehículo agregado exitosamente.";
        } else {
            return "Error: El residente ya tiene 3 vehículos (máximo permitido).";
        }
    }

    public String eliminarVehiculo(String placa) {
        if (residenteActual == null) {
            return "Error: Primero busque un residente.";
        }
        Vehiculo v = residenteActual.getVehiculos().buscarPorPlaca(placa);
        if (v == null) {
            return "Error: Vehículo no encontrado.";
        }
        if (v.getEstado() != EstadoVehiculo.FUERA) {
            return "Error: No se puede eliminar un vehículo que está en cola o estacionado.";
        }
        residenteActual.getVehiculos().eliminar(placa);
        return "Vehículo eliminado correctamente.";
    }

    public String listarVehiculos() {
        if (residenteActual == null) {
            return "No hay residente seleccionado.";
        }
        return residenteActual.getVehiculos().listar();
    }

    private boolean todosVehiculosFuera(Residente r) {
        // Recorremos la lista simple de vehículos
        //String listaStr = r.getVehiculos().listar();
        // Aquí haremos una validación real iterando, pero por ahora lo dejamos simple:
        // Si no hay vehículos, devuelve true
        if (r.getVehiculos().getTamano() == 0) return true;
        
        // Como no tenemos un getter del nodo en ListaSimple, haremos la validación real más adelante.
        // Por ahora, asumiremos false para evitar borrados peligrosos.
        return false; 
    }
}