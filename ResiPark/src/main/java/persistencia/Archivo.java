package persistencia;

import modelo.*;
import java.io.*;
import java.util.ArrayList;

public class Archivo {

    private static final String RUTA_RESIDENTES = "residentes.txt";
    private static final String RUTA_VEHICULOS = "vehiculos.txt";

    // guardar
    public static void guardarTodo(ListaDoble residentes) {
        try (PrintWriter pwRes = new PrintWriter(new FileWriter(RUTA_RESIDENTES));
             PrintWriter pwVeh = new PrintWriter(new FileWriter(RUTA_VEHICULOS))) {

            NodoResidente actual = residentes.getPrimero();
            while (actual != null) {
                Residente r = actual.dato;
                
                //  Guardar Residente
                String socio = r.isEsSocio() ? "S" : "N";
                pwRes.println(r.getId() + "|" + r.getNombre() + "|" + r.getCasa() + "|" + socio);

                //  Guardar sus vehículos
                NodoVehiculo actualVeh = r.getVehiculos().getPrimero();
                while (actualVeh != null) {
                    Vehiculo v = actualVeh.dato;
                    pwVeh.println(v.getPlaca() + "|" + v.getMarca() + "|" + v.getModelo() + "|" + 
                                  v.getColor() + "|" + v.getTipo() + "|" + r.getId());
                    actualVeh = actualVeh.siguiente;
                }
                
                actual = actual.siguiente;
            }
            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar archivos: " + e.getMessage());
        }
    }

    // cargar
    public static ListaDoble cargarTodo() {
        ListaDoble residentes = new ListaDoble();

        //  Cargar Residentes
        File fRes = new File(RUTA_RESIDENTES);
        if (fRes.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fRes))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split("\\|");
                    if (partes.length == 4) {
                        boolean esSocio = partes[3].equals("S");
                        Residente r = new Residente(partes[0], partes[1], partes[2], esSocio);
                        residentes.agregar(r);
                    } else {
                        Pila.global.push(new Evento("ERROR_ARCHIVO", "Línea inválida en residentes.txt", null));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer residentes: " + e.getMessage());
            }
        }

        // Cargar Vehículos
        File fVeh = new File(RUTA_VEHICULOS);
        if (fVeh.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fVeh))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split("\\|");
                    if (partes.length == 6) {
                        String placa = partes[0];
                        String marca = partes[1];
                        String modelo = partes[2];
                        String color = partes[3];
                        String tipo = partes[4];
                        String idResidente = partes[5];

                        Residente r = residentes.buscarPorId(idResidente);
                        if (r != null) {
                            // Validar que no supere los 3 vehículos
                            if (r.getVehiculos().getTamano() < 3) {
                                Vehiculo v = new Vehiculo(placa, marca, modelo, color, tipo, idResidente, r.isEsSocio());
                                r.getVehiculos().agregar(v);
                            } else {
                                Pila.global.push(new Evento("ERROR_ARCHIVO", "El residente " + idResidente + " superó el límite de vehículos", null));
                            }
                        } else {
                            Pila.global.push(new Evento("ERROR_ARCHIVO", "Vehículo " + placa + " apunta a un residente inexistente", null));
                        }
                    } else {
                        Pila.global.push(new Evento("ERROR_ARCHIVO", "Línea inválida en vehiculos.txt", null));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer vehículos: " + e.getMessage());
            }
        }

        return residentes;
    }
}