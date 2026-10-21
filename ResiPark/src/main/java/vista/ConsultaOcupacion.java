package vista;

public interface ConsultaOcupacion {

    // Cantidad de espacios ocupados en la fila indicada (A,B,C,E,F,G,H o I)
    int ocupadosEnFila(char fila);

    //Cantidad total de espacios de la fila indicada (25 para A-C y 15 para E-I)
    int totalEnFila(char fila);
}