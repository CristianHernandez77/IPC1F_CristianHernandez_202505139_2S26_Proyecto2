package modelo;

public class Vehiculo {
    private String placa;
    private boolean socio;

    public Vehiculo(String placa, boolean socio){
        this.placa = placa;
        this.socio = socio;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isSocio(){
        return socio;
    }
}