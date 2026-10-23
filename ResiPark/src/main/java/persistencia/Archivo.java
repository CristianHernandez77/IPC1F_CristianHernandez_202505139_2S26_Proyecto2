package persistencia;

import modelo.EstadoVehiculo;
import modelo.Evento;
import modelo.ListaDoble;
import modelo.NodoResidente;
import modelo.NodoVehiculo;
import modelo.Parqueo;
import modelo.Pila;
import modelo.Residente;
import modelo.Vehiculo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Archivo {

    private static final String RUTA_RESIDENTES = "residentes.txt";
    private static final String RUTA_VEHICULOS = "vehiculos.txt";
    private static final String RUTA_EVENTOS = "eventos.txt";
    private static final String RUTA_OCUPACION = "ocupacion.txt";

    public static void guardarTodo(ListaDoble residentes,Parqueo parqueo) {
        guardarResidentesYVehiculos(residentes);
        guardarOcupacion(parqueo);
        guardarEventos(Pila.global);
    }

    private static void guardarResidentesYVehiculos(ListaDoble residentes) {
        try (
            PrintWriter escritorResidentes =
                    new PrintWriter(new FileWriter(RUTA_RESIDENTES));

            PrintWriter escritorVehiculos =
                    new PrintWriter(new FileWriter(RUTA_VEHICULOS))
        ) {
            NodoResidente actual = residentes.getPrimero();

            while (actual != null) {
                Residente residente = actual.dato;

                String socio = residente.isEsSocio() ? "S" : "N";

                escritorResidentes.println(
                        residente.getId() + "|" +
                        residente.getNombre() + "|" +
                        residente.getCasa() + "|" +
                        socio
                );

                NodoVehiculo actualVehiculo =
                        residente.getVehiculos().getPrimero();

                while (actualVehiculo != null) {
                    Vehiculo vehiculo = actualVehiculo.dato;

                    escritorVehiculos.println(
                            vehiculo.getPlaca() + "|" +
                            vehiculo.getMarca() + "|" +
                            vehiculo.getModelo() + "|" +
                            vehiculo.getColor() + "|" +
                            vehiculo.getTipo() + "|" +
                            residente.getId()
                    );

                    actualVehiculo = actualVehiculo.siguiente;
                }

                actual = actual.siguiente;
            }

            System.out.println("Residentes y vehículos guardados.");

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar residentes y vehículos: "
                    + e.getMessage()
            );
        }
    }

    public static void guardarEventos(Pila pila) {
        try (
            PrintWriter escritor =
                    new PrintWriter(new FileWriter(RUTA_EVENTOS))
        ) {
            pila.guardarEnArchivo(escritor);
            System.out.println("Eventos guardados.");

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar eventos: " + e.getMessage()
            );
        }
    }

    public static void cargarEventos(Pila pila) {
        File archivo = new File(RUTA_EVENTOS);

        if (!archivo.exists()) {
            return;
        }

        try (
            BufferedReader lector =
                    new BufferedReader(new FileReader(archivo))
        ) {
            pila.cargarDesdeArchivo(lector);
            System.out.println("Eventos cargados.");

        } catch (IOException e) {
            System.out.println(
                    "Error al cargar eventos: " + e.getMessage()
            );
        }
    }

    public static ListaDoble cargarTodo() {
        ListaDoble residentes = new ListaDoble();

        cargarResidentes(residentes);
        cargarVehiculos(residentes);

        return residentes;
    }

    private static void cargarResidentes(ListaDoble residentes) {
        File archivo = new File(RUTA_RESIDENTES);

        if (!archivo.exists()) {
            return;
        }

        try (
            BufferedReader lector =
                    new BufferedReader(new FileReader(archivo))
        ) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split("\\|", -1);

                if (partes.length != 4) {
                    Pila.global.push(new Evento(
                            "ERROR_ARCHIVO",
                            "Línea inválida en residentes.txt",
                            ""
                    ));
                    continue;
                }

                if (!partes[3].equals("S") &&
                    !partes[3].equals("N")) {
                    Pila.global.push(new Evento(
                            "ERROR_ARCHIVO",
                            "Socio inválido en residentes.txt",
                            ""
                    ));
                    continue;
                }

                boolean esSocio = partes[3].equals("S");

                Residente residente = new Residente(
                        partes[0],
                        partes[1],
                        partes[2],
                        esSocio
                );

                if (!residentes.agregar(residente)) {
                    Pila.global.push(new Evento(
                            "ERROR_ARCHIVO",
                            "ID de residente duplicado: " + partes[0],
                            ""
                    ));
                }
            }

        } catch (IOException e) {
            Pila.global.push(new Evento(
                    "ERROR_ARCHIVO",
                    "No se pudo leer residentes.txt",
                    ""
            ));
        }
    }

    private static void cargarVehiculos(ListaDoble residentes) {
        File archivo = new File(RUTA_VEHICULOS);

        if (!archivo.exists()) {
            return;
        }

        try (
            BufferedReader lector =
                    new BufferedReader(new FileReader(archivo))
        ) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split("\\|", -1);

                if (partes.length != 6) {
                    Pila.global.push(new Evento(
                            "ERROR_ARCHIVO",
                            "Línea inválida en vehiculos.txt",
                            ""
                    ));
                    continue;
                }

                String placa = partes[0];
                String marca = partes[1];
                String modelo = partes[2];
                String color = partes[3];
                String tipo = partes[4];
                String idResidente = partes[5];

                Residente residente =
                        residentes.buscarPorId(idResidente);

                if (residente == null) {
                    Pila.global.push(new Evento(
                            "ERROR_ARCHIVO",
                            "Residente inexistente para placa " + placa,
                            ""
                    ));
                    continue;
                }

                if (residentes.buscarVehiculoGlobal(placa) != null) {
                    Pila.global.push(new Evento(
                            "ERROR_ARCHIVO",
                            "Placa duplicada: " + placa,
                            ""
                    ));
                    continue;
                }

                if (residente.getVehiculos().getTamano() >= 3) {
                    Pila.global.push(new Evento(
                            "ERROR_ARCHIVO",
                            "Límite de vehículos excedido para "
                            + idResidente,
                            ""
                    ));
                    continue;
                }

                Vehiculo vehiculo = new Vehiculo(
                        placa,
                        marca,
                        modelo,
                        color,
                        tipo,
                        idResidente,
                        residente.isEsSocio()
                );

                residente.getVehiculos().agregar(vehiculo);
            }

        } catch (IOException e) {
            Pila.global.push(new Evento(
                    "ERROR_ARCHIVO",
                    "No se pudo leer vehiculos.txt",
                    ""
            ));
        }
    }

    public static void guardarOcupacion(Parqueo parqueo) {
    try (
        PrintWriter escritor =
                new PrintWriter(new FileWriter(RUTA_OCUPACION))
    ) {
        escritor.print(parqueo.obtenerOcupacion());

        System.out.println("Ocupación del parqueo guardada.");

    } catch (IOException e) {
        System.out.println(
                "Error al guardar ocupación: " + e.getMessage()
        );
    }
    }
    
    public static void cargarOcupacion(
        Parqueo parqueo,
        ListaDoble residentes
) {
    File archivo = new File(RUTA_OCUPACION);

    if (!archivo.exists()) {
        return;
    }

    try (
        BufferedReader lector =
                new BufferedReader(new FileReader(archivo))
    ) {
        String linea;

        while ((linea = lector.readLine()) != null) {
            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] partes = linea.split("\\|", -1);

            if (partes.length != 3) {
                Pila.global.push(new Evento(
                        "ERROR_ARCHIVO",
                        "Línea inválida en ocupacion.txt",
                        ""
                ));
                continue;
            }

            String placa = partes[0];
            String estadoTexto = partes[1];
            String idEspacio = partes[2];

            Vehiculo vehiculo =
                    residentes.buscarVehiculoGlobal(placa);

            if (vehiculo == null) {
                Pila.global.push(new Evento(
                        "ERROR_ARCHIVO",
                        "La placa " + placa
                        + " no existe en vehiculos.txt",
                        ""
                ));
                continue;
            }

            EstadoVehiculo estado;

            try {
                estado = EstadoVehiculo.valueOf(estadoTexto);
            } catch (IllegalArgumentException e) {
                Pila.global.push(new Evento(
                        "ERROR_ARCHIVO",
                        "Estado inválido para la placa " + placa,
                        ""
                ));
                continue;
            }

            if (estado != EstadoVehiculo.ESTACIONADO &&
                estado != EstadoVehiculo.EN_COLA_SALIDA) {
                estado = EstadoVehiculo.ESTACIONADO;
                Pila.global.push(new Evento(
                        "ERROR_ARCHIVO",
                        "Estado no restaurable para la placa " + placa,
                        ""
                ));
                continue;
            }

            boolean restaurado = parqueo.restaurarVehiculo(
                    vehiculo,
                    idEspacio,
                    estado
            );

            if (!restaurado) {
                Pila.global.push(new Evento(
                        "ERROR_ARCHIVO",
                        "No se pudo restaurar "
                        + placa + " en " + idEspacio,
                        ""
                ));
            }
        }

    } catch (IOException e) {
        Pila.global.push(new Evento(
                "ERROR_ARCHIVO",
                "No se pudo leer ocupacion.txt",
                ""
        ));
    }
    }
}