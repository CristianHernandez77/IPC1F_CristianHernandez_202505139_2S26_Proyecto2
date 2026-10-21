package modelo;

public class Residente {
    private String id;
    private String nombre;
    private String casa;
    private boolean esSocio;
    private ListaSimple vehiculos; // Lista simple propia del residente

    public Residente(String id, String nombre, String casa, boolean esSocio) {
        this.id = id;
        this.nombre = nombre;
        this.casa = casa;
        this.esSocio = esSocio;
        this.vehiculos = new ListaSimple();
    }

    // Getters y Setters
    public String getId() { return id; }
    // El ID no se modifica después de creado, por eso no hay setId

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCasa() { return casa; }
    public void setCasa(String casa) { this.casa = casa; }

    public boolean isEsSocio() { return esSocio; }
    public void setEsSocio(boolean esSocio) { this.esSocio = esSocio; }

    public ListaSimple getVehiculos() { return vehiculos; }
}