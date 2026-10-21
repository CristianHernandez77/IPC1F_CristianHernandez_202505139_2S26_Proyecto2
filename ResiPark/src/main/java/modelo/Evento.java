package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {
    private String fechaHora;
    private String tipo;
    private String descripcion;
    private String garita;

    public Evento(String tipo, String descripcion, String garita) {
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.garita = garita;
    }

    @Override
    public String toString() {
        String g = (garita == null || garita.isEmpty()) ? "" : " | " + garita;
        return fechaHora + " | " + tipo + " | " + descripcion + g;
    }
}