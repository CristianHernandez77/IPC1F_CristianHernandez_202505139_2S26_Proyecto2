package modelo;

public class Parqueo {
    private ListaCircularEspacios socios;
    private ListaCircularEspacios general;

    public Parqueo(){
        socios = new ListaCircularEspacios();
        general = new ListaCircularEspacios();

        // Area de socios: filas A,B,C con 25 espacios (A1,...A25,B1,...B25...)
        String[] filasSocios = {"A","B","C"};
        for(int f = 0; f < filasSocios.length; f++){
            for(int n = 1; n <= 25; n++){
                socios.agregar(new Espacio(filasSocios[f] + n, "SOCIO"));
            }
        }

        String [] filasGeneral = {"E","F","G","H","I"};
        for(int f = 0; f < filasGeneral.length; f++){
            for(int n = 1; n <= 15; n++){
                general.agregar(new Espacio(filasGeneral[f] + n, "GENERAL"));
            }
        }
    }

    public Espacio asignar(Vehiculo v){
        if(v.isSocio()){
            Espacio espacio = socios.asignar(v);
            if(espacio != null){
                return espacio;
            }
        }
        return general.asignar(v);
    }

    public String[] getIdsSocios(){
        return socios.getIds();
    }

    public String[] getIdsGeneral(){
        return general.getIds();
    }
}