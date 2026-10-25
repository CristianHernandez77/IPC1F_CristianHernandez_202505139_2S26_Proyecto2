#  RESIPARK

## IPC1 - Proyecto 2

**Universidad de San Carlos de Guatemala**  
**Facultad de Ingeniería**  
**Escuela de Ingeniería en Ciencias y Sistemas**  
**Introducción a la Programación y Computación 1**

**Autor:** Cristian Hernández  
**Carné:** 202505139  
**Fecha:** 24 de Octubre 2026

---
- **Repositorio:** `CristianHernandez77/IPC1F_CristianHernandez_202505139_2S26_Proyecto2`

## Tecnologías utilizadas

- Java.
- Java Swing.
- Maven.
- Git y GitHub.
- Archivos de texto para persistencia.


## Requisitos

Para ejecutar el proyecto se necesita:

- JDK 17 o superior.
- Maven.
- Sistema operativo compatible con Java Swing.
- Git, opcionalmente, para clonar el repositorio.


## Ejecución del proyecto

Desde la carpeta `ResiPark`, ejecutar:

```bash
mvn clean compile
```

Para ejecutar la aplicación:

```bash
mvn exec:java
```

También se puede ejecutar la clase principal:

```text
resipark.Main
```

## Archivo JAR

La ejecución del archivo JAR puede realizarse con:

```bash
java -jar target/ResiPark-1.0-SNAPSHOT.jar
```

## Distribución del parqueo

El parqueo tiene 150 espacios distribuidos de la siguiente forma:

### Área de socios

- Filas: `A`, `B` y `C`.
- 25 espacios por fila.
- 75 espacios en total.
- Identificadores:

```text
A1-A25
B1-B25
C1-C25
```

### Área general y visitantes

- Filas: `E`, `F`, `G`, `H` e `I`.
- 15 espacios por fila.
- 75 espacios en total.
- Identificadores:

```text
E1-E15
F1-F15
G1-G15
H1-H15
I1-I15
```

La fila `D` no existe.

## Estados de un vehículo

El sistema utiliza los siguientes estados:

```text
FUERA
EN_COLA_ENTRADA
ESTACIONADO
EN_COLA_SALIDA
```

Un vehículo no puede estar simultáneamente en dos colas o en dos espacios.