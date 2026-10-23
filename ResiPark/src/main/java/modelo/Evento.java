package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {

    private String fechaHora;
    private String tipo;
    private String descripcion;
    private String garita;

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Evento(String tipo, String descripcion, String garita) {
        this.fechaHora = LocalDateTime.now().format(FORMATO);
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.garita = garita;
    }
    
    // Constructor utilizado al cargar eventos desde eventos.txt
    public Evento(String fechaHora, String tipo, String descripcion, String garita) {
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.garita = garita;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getGarita() {
        return garita;
    }

    // Formato para guardar en eventos.txt
    public String serializar() {
        return limpiar(fechaHora) + "|" +
               limpiar(tipo) + "|" +
               limpiar(descripcion) + "|" +
               limpiar(garita);
    }

    private String limpiar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("|", "/");
    }

    @Override
    public String toString() {
        String g = "";

        if (garita != null && !garita.isEmpty()) {
            g = " | " + garita;
        }

        return fechaHora + " | " +
               tipo + " | " +
               descripcion + g;
    }
}