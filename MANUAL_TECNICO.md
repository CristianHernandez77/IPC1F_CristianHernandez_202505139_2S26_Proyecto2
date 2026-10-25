# Manual técnico de ResiPark

## 1. Información general

**Nombre del proyecto:** ResiPark  
**Curso:** IPC1 - Introducción a la Programación y Computación 1   
**Repositorio:** `CristianHernandez77/IPC1F_CristianHernandez_202505139_2S26_Proyecto2`


---

## 2. Objetivos del sistema

### 2.1 Objetivo general

Desarrollar una aplicación de escritorio en Java que simule el control de vehículos dentro de un residencial utilizando estructuras de datos enlazadas implementadas manualmente.

### 2.2 Objetivos específicos

- Administrar residentes y sus vehículos.
- Controlar el ingreso de vehículos mediante una cola FIFO.
- Procesar vehículos mediante dos garitas de entrada.
- Controlar la salida mediante una cola FIFO y una garita.
- Asignar espacios según el tipo de usuario.
- Registrar eventos utilizando una pila.
- Persistir información en archivos de texto.
- Restaurar la ocupación del parqueo al reiniciar.
- Utilizar hilos y sincronización para simular la atención simultánea.

---

## 3. Modelo de datos

### 3.1 Residente

La clase `Residente` representa a una persona que vive en el residencial.

Sus datos principales son:

- ID.
- Nombre.
- Casa o lote.
- Indicador de socio.
- Lista de vehículos.

Cada residente puede tener entre cero y tres vehículos.

### 3.2 Vehículo

La clase `Vehiculo` representa un vehículo registrado o temporal.

Sus datos incluyen:

- Placa.
- Marca.
- Modelo.
- Color.
- Tipo.
- ID del residente.
- Indicador de socio.
- Estado actual.

### 3.3 Visitante

La clase `Visitante` representa a una persona que visita a un residente.

Sus datos son:

- Nombre.
- Placa.
- ID del residente visitado.
- Vehículo temporal asociado.

Los visitantes se almacenan solamente en memoria durante la ejecución del programa. No se guardan como residentes ni como vehículos permanentes.

---

## 4. Distribución del parqueo

El parqueo se representa mediante dos listas circulares:

```text
Área de socios:
A1-A25
B1-B25
C1-C25

Área general:
E1-E15
F1-F15
G1-G15
H1-H15
I1-I15
```

La capacidad total es:

```text
Área de socios: 75 espacios
Área general: 75 espacios
Total: 150 espacios
```

La fila `D` no existe.

### 4.1 Búsqueda circular

La búsqueda comienza desde el espacio posterior al último espacio asignado. Esto permite distribuir los vehículos a lo largo de la lista y evitar comenzar siempre desde el primer espacio.

---

## 5. Lista doble de residentes

La clase `ListaDoble` almacena residentes mediante nodos con referencias:

```text
anterior ← nodo residente → siguiente
```

Cada `NodoResidente` contiene:

- Un objeto `Residente`.
- Referencia al nodo siguiente.
- Referencia al nodo anterior.

### 5.1 Operaciones

- Agregar residente.
- Buscar residente por ID.
- Eliminar residente.
- Recorrer residentes.
- Buscar una placa globalmente.
- Listar residentes.

### 5.2 Validaciones

- El ID debe ser único.
- El ID no puede estar vacío.
- No se permite el carácter `|`.
- No se puede eliminar un residente si tiene vehículos activos.
- No se puede modificar la condición de socio si tiene vehículos dentro del residencial.

---

## 6. Lista simple de vehículos

Cada residente posee una lista simple de vehículos.

La estructura es:

```text
Vehículo 1 → Vehículo 2 → Vehículo 3 → null
```

Cada `NodoVehiculo` contiene:

- Un objeto `Vehiculo`.
- Referencia al siguiente nodo.

### 6.1 Operaciones

- Agregar vehículo.
- Buscar por placa.
- Eliminar por placa.
- Recorrer vehículos.
- Contar vehículos.
- Verificar que todos estén en estado `FUERA`.

### 6.2 Restricciones

Cada residente puede tener como máximo tres vehículos.

Además, las placas deben ser únicas en todo el sistema.

---

## 7. Hilos y sincronización

El sistema utiliza:

- Dos hilos para las garitas de entrada.
- Un hilo para la garita de salida.

Cada garita extiende la clase `Thread`

---

## 8. Persistencia

El sistema utiliza cuatro archivos principales:

```text
residentes.txt
vehiculos.txt
eventos.txt
ocupacion.txt
```

### 8.1 Archivo `residentes.txt`

Formato:

```text
ID|NOMBRE|CASA|SOCIO
```

### 8.2 Archivo `vehiculos.txt`

Formato:

```text
PLACA|MARCA|MODELO|COLOR|TIPO|ID_RESIDENTE
```

### 8.3 Archivo `eventos.txt`

Formato:

```text
FECHA_HORA|TIPO|DESCRIPCION|GARITA
```

### 8.4 Archivo `ocupacion.txt`

Formato:

```text
PLACA|ESTADO|ESPACIO
```

### 8.5 Inicio del programa

Al iniciar:

1. Se cargan los residentes.
2. Se cargan los vehículos.
3. Se cargan los eventos.
4. Se crea el parqueo.
5. Se restaura la ocupación de los vehículos residentes.
6. Se pintan los espacios ocupados.
7. Se inicia la simulación.

Si un vehículo estaba en `EN_COLA_SALIDA` al cerrar, se restaura como `ESTACIONADO`, porque la cola de salida es temporal y no se reconstruye automáticamente.

### 8.6 Cierre del programa

Al cerrar:

1. Se guardan los residentes.
2. Se guardan los vehículos.
3. Se guarda la ocupación de vehículos residentes.
4. Se guardan los eventos.
5. Se descartan los visitantes temporales.

---

## 9. Interfaz gráfica

La aplicación utiliza Java Swing.

### 9.1 Ventana principal

Contiene:

- Campo de placa.
- Botón para enviar a cola.
- Botón para enviar vehículos de prueba.
- Botón de gestión de residentes.
- Botón para ver eventos.
- Botón para registrar visitantes.
- Cuadrícula del parqueo.
- Cola de entrada.
- Cola de salida.
- Estado de las garitas.
- Selector de vehículos estacionados.
- Botón para solicitar salida.

### 9.2 Colores del parqueo

```text
Verde: espacio libre
Rojo: espacio ocupado
```

### 9.3 Ventana de residentes

Permite:

- Registrar residentes.
- Buscar residentes.
- Modificar residentes.
- Eliminar residentes.
- Agregar vehículos.
- Eliminar vehículos.
- Mostrar vehículos asociados.

### 9.4 Ventana de visitantes

Permite ingresar:

- Nombre.
- Placa.
- ID del residente visitado.

### 9.5 Ventana de eventos

Muestra la pila de eventos del más reciente al más antiguo.

---

## 10. Validaciones implementadas

El sistema debe validar:

- Campos obligatorios.
- IDs únicos.
- Placas únicas.
- Máximo tres vehículos por residente.
- Residentes existentes para visitantes.
- Residentes no eliminables con vehículos activos.
- Visitantes sin acceso al área de socios.
- Vehículos que no pueden estar en dos estados simultáneamente.
- Vehículos que no pueden solicitar dos veces la salida.
- Estados válidos en archivos.
- Líneas inválidas en archivos.
- Campos que no contengan el carácter `|`.

---
