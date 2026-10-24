package modelo;

public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String tipo; // "Automóvil", "Motocicleta", "Pickup"
    private String idResidente; // Para saber a quién pertenece (null si es visitante)
    private boolean socio;
    private EstadoVehiculo estado;

    // Constructor para vehículos de residentes
    public Vehiculo(String placa, String marca, String modelo, String color, String tipo, String idResidente, boolean socio) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.tipo = tipo;
        this.idResidente = idResidente;
        this.socio = socio;
        this.estado = EstadoVehiculo.FUERA;
    }
    
    public Vehiculo(String placa, boolean socio) {
        this(placa,"","","","Visitante","",socio);
    }

    // Constructor para visitantes (no tienen ID de residente, se les asigna null)
    public Vehiculo(String placa, String marca, String modelo, String color, String tipo) {
        this(placa, marca, modelo, color, tipo, null, false);
    }

    // Getters y Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getIdResidente() { return idResidente; }
    public void setIdResidente(String idResidente) { this.idResidente = idResidente; }

    public boolean isSocio() { return socio; }
    public void setSocio(boolean socio) { this.socio = socio; }

    // --- Lógica de Estados (synchronized) ---
    public synchronized EstadoVehiculo getEstado() {
        return estado;
    }

    public synchronized void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }

    public synchronized boolean cambiarEstado(EstadoVehiculo esperado, EstadoVehiculo nuevo) {
        if (estado != esperado) {
            return false;
        }
        estado = nuevo;
        return true;
    }

    @Override
    public String toString() {
        return placa + " - " + marca + " " + modelo + " (" + color + ")";
    }
}