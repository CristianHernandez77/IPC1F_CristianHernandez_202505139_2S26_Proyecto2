# Manual de usuario de ResiPark

## 1. Introducción

ResiPark es un sistema para controlar el ingreso, permanencia y salida de vehículos dentro de un residencial.

El sistema permite:

- Registrar residentes.
- Registrar vehículos.
- Registrar visitantes.
- Enviar vehículos a la cola de entrada.
- Asignar espacios de parqueo.
- Solicitar la salida de vehículos.
- Consultar las colas de entrada y salida.
- Consultar el historial de eventos.
- Guardar y recuperar información mediante archivos de texto.

---

## 2. Requisitos para ejecutar el sistema

Para utilizar ResiPark se necesita:

- Tener instalado Java JDK 17 o superior.
- Tener instalado Maven si se ejecuta desde el proyecto.
- Contar con el archivo `.jar` generado, si se ejecuta como aplicación independiente.
- Usar neatbeans o VS code para ejecutar o bien desde el .jar.

---

## 3. Ejecución del programa

### 3.1 Ejecución mediante archivo JAR

Después de generar el proyecto con:

ejecutar:

ResiPark-1.0-SNAPSHOT.jar

![Ejecución del programa](docs/ejecucion-programa.png)

---

## 4. Ventana principal

Al iniciar el programa se muestra la ventana principal de ResiPark.

En esta ventana se encuentran:

- Campo para ingresar la placa de un vehículo.
- Botón para enviar un vehículo a la cola de entrada.
- Botón para enviar vehículos de prueba.
- Botón para gestionar residentes.
- Botón para registrar visitantes.
- Botón para consultar eventos.
- Cuadrícula del parqueo.
- Cola de entrada.
- Cola de salida.
- Estado de las garitas.
- Lista de vehículos estacionados.
- Botón para solicitar salida.

> **Aquí puedes agregar una captura de la ventana principal completa, mostrando el estacionamiento, las colas y los botones principales.**

<!-- Imagen sugerida: docs/ventana-principal.png -->

![Ventana principal de ResiPark](docs/ventana-principal.png)

---

## 5. Interpretación de los colores del parqueo

Los espacios de la cuadrícula utilizan colores para indicar su estado:

- **Verde:** espacio libre.
- **Rojo:** espacio ocupado.

Cada espacio muestra su identificador. Cuando está ocupado, también muestra la placa del vehículo.

Ejemplo:

```text
A1
```

Indica que el espacio `A1` está libre.

```text
A1
ABC123
```

Indica que el vehículo con placa `ABC123` ocupa el espacio `A1`.

> **Aquí puedes agregar una imagen del estacionamiento con espacios verdes y rojos, señalando qué significa cada color.**

<!-- Imagen sugerida: docs/estado-estacionamiento.png -->

![Estados de los espacios del estacionamiento](docs/estado-estacionamiento.png)

---

## 6. Distribución del estacionamiento

El parqueo está dividido en dos áreas.

### 6.1 Área de socios

Está formada por las filas:

```text
A, B y C
```

Cada fila tiene 25 espacios:

```text
A1-A25
B1-B25
C1-C25
```

Esta área contiene 75 espacios.

### 6.2 Área general y visitantes

Está formada por las filas:

```text
E, F, G, H e I
```

Cada fila tiene 15 espacios:

```text
E1-E15
F1-F15
G1-G15
H1-H15
I1-I15
```

Esta área contiene 75 espacios.

La fila `D` no existe.

> **Aquí puedes agregar una captura del estacionamiento donde se distingan las áreas “Socios” y “General/Visitantes”.**

<!-- Imagen sugerida: docs/areas-estacionamiento.png -->

![Áreas del estacionamiento](docs/areas-estacionamiento.png)

---

## 7. Registrar un residente

Para registrar un residente:

1. En la ventana principal, presionar el botón **Gestionar Residentes**.
2. Escribir el ID del residente.
3. Escribir el nombre.
4. Escribir la casa o lote.
5. Marcar la casilla si el residente es socio.
6. Presionar el botón **Registrar**.

Los campos obligatorios son:

- ID.
- Nombre.
- Casa o lote.

El ID debe ser único y no puede repetirse.

> **Aquí puedes agregar una imagen de la ventana de gestión de residentes con los campos llenos antes de presionar “Registrar”.**

<!-- Imagen sugerida: docs/registrar-residente.png -->

![Registrar residente](docs/registrar-residente.png)

### Ejemplo

```text
ID: 202505139
Nombre: Cristian Hernandez
Casa/Lote: Casa 12
Es socio: Sí
```

Después de registrar el residente, este aparece en el área de residentes.

> **Aquí puedes agregar una imagen mostrando el mensaje “Residente registrado correctamente” y la lista actualizada.**

<!-- Imagen sugerida: docs/residente-registrado.png -->

![Residente registrado](docs/residente-registrado.png)

---

## 8. Buscar un residente

Para buscar un residente:

1. Abrir la ventana **Gestionar Residentes**.
2. Escribir el ID en el campo correspondiente.
3. Presionar el botón **Buscar**.

Si el residente existe, el sistema muestra:

- Nombre.
- Casa o lote.
- Condición de socio.
- Vehículos registrados.

Si no existe, se muestra un mensaje indicando que no fue encontrado.

> **Aquí puedes agregar una captura de la búsqueda de un residente mostrando sus datos y sus vehículos.**

<!-- Imagen sugerida: docs/buscar-residente.png -->

![Buscar residente](docs/buscar-residente.png)

---

## 9. Modificar un residente

Para modificar un residente:

1. Buscar el residente mediante su ID.
2. Cambiar el nombre, la casa o la condición de socio.
3. Presionar el botón **Modificar**.

El ID se utiliza como identificador y no debe cambiarse.

No se permite cambiar la condición de socio si alguno de los vehículos del residente está dentro del residencial o en una cola.

> **Aquí puedes agregar una imagen de los datos modificados antes de presionar “Modificar”.**

<!-- Imagen sugerida: docs/modificar-residente.png -->

![Modificar residente](docs/modificar-residente.png)

---

## 10. Eliminar un residente

Para eliminar un residente:

1. Escribir su ID.
2. Presionar el botón **Eliminar**.
3. Confirmar la operación si el sistema lo solicita.

Un residente solamente puede eliminarse cuando todos sus vehículos están en estado:

```text
FUERA
```

Si un vehículo está estacionado, en la cola de entrada o en la cola de salida, el sistema no permite eliminar al residente.

> **Aquí puedes agregar una imagen del mensaje que aparece cuando no se puede eliminar un residente porque tiene un vehículo activo.**

<!-- Imagen sugerida: docs/error-eliminar-residente.png -->

![Error al eliminar residente](docs/error-eliminar-residente.png)

---

## 11. Registrar un vehículo

Para registrar un vehículo:

1. Abrir la ventana **Gestionar Residentes**.
2. Buscar o seleccionar un residente.
3. Escribir la placa.
4. Escribir la marca.
5. Escribir el modelo.
6. Escribir el color.
7. Seleccionar el tipo de vehículo.
8. Presionar **Agregar Vehículo**.

Los tipos disponibles pueden incluir:

- Automóvil.
- Motocicleta.
- Pickup.

Cada residente puede tener como máximo tres vehículos.

> **Aquí puedes agregar una captura de la ventana de residentes con los datos del vehículo antes de registrarlo.**

<!-- Imagen sugerida: docs/registrar-vehiculo.png -->

![Registrar vehículo](docs/registrar-vehiculo.png)

### Ejemplo

```text
Placa: ABC123
Marca: Toyota
Modelo: Corolla
Color: Rojo
Tipo: Automóvil
```

Las placas deben ser únicas en todo el sistema.

Si la placa ya está registrada, el sistema muestra un mensaje de error.

> **Aquí puedes agregar una imagen del mensaje que aparece al intentar registrar una placa duplicada.**

<!-- Imagen sugerida: docs/placa-duplicada.png -->

![Placa duplicada](docs/placa-duplicada.png)

---

## 12. Eliminar un vehículo

Para eliminar un vehículo:

1. Buscar el residente al que pertenece.
2. Escribir la placa del vehículo.
3. Presionar **Eliminar Vehículo**.

El vehículo solamente puede eliminarse si está en estado:

```text
FUERA
```

No se puede eliminar un vehículo que esté:

- En la cola de entrada.
- Estacionado.
- En la cola de salida.

> **Aquí puedes agregar una imagen del vehículo seleccionado y el botón “Eliminar Vehículo”.**

<!-- Imagen sugerida: docs/eliminar-vehiculo.png -->

![Eliminar vehículo](docs/eliminar-vehiculo.png)

---

## 13. Ingresar un vehículo de residente

Para enviar un vehículo registrado a la cola de entrada:

1. Escribir la placa en el campo **Placa** de la ventana principal.
2. Presionar **Enviar a cola**.
3. El sistema valida que la placa pertenezca a un vehículo registrado.
4. El vehículo cambia a estado `EN_COLA_ENTRADA`.
5. Se agrega a la cola de entrada.
6. Una de las dos garitas atiende el vehículo.

El vehículo debe estar en estado:

```text
FUERA
```

Si ya está estacionado o en una cola, el sistema no permite enviarlo nuevamente.

> **Aquí puedes agregar una imagen de la ventana principal con un vehículo en la cola de entrada.**

<!-- Imagen sugerida: docs/vehiculo-cola-entrada.png -->

![Vehículo en cola de entrada](docs/vehiculo-cola-entrada.png)

---

## 14. Asignación de espacios

Cuando una garita atiende un vehículo:

### Para residentes socios

El sistema busca primero en:

```text
A, B y C
```

Si no encuentra espacio libre, busca en:

```text
E, F, G, H e I
```

### Para residentes no socios

El sistema busca únicamente en:

```text
E, F, G, H e I
```

Cuando se asigna un espacio:

- El vehículo cambia a estado `ESTACIONADO`.
- El espacio se pinta de rojo.
- La placa aparece dentro del espacio.
- Se registra un evento.
- El vehículo aparece en la lista de estacionados.

> **Aquí puedes agregar una imagen mostrando un vehículo asignado y el espacio pintado de rojo.**

<!-- Imagen sugerida: docs/vehiculo-estacionado.png -->

![Vehículo estacionado](docs/vehiculo-estacionado.png)

---

## 15. Registrar un visitante

Para registrar un visitante:

1. Presionar el botón **Registrar Visitante**.
2. Escribir el nombre del visitante.
3. Escribir la placa del vehículo.
4. Escribir el ID del residente visitado.
5. Presionar **Enviar a cola de entrada**.

El sistema valida que:

- El nombre no esté vacío.
- La placa no esté vacía.
- El ID del residente no esté vacío.
- El residente visitado exista.
- La placa no esté registrada previamente.

> **Aquí puedes agregar una imagen de la ventana para registrar un visitante con los campos llenos.**

<!-- Imagen sugerida: docs/registrar-visitante.png -->

![Registrar visitante](docs/registrar-visitante.png)

### Ejemplo

```text
Nombre: Miguel Angel
Placa: VIS001
ID residente visitado: 202505139
```

Si el residente visitado no existe, el visitante no puede ser enviado a la cola.

> **Aquí puedes agregar una imagen del mensaje que aparece cuando el ID del residente visitado no existe.**

<!-- Imagen sugerida: docs/visitante-residente-inexistente.png -->

![Residente visitado inexistente](docs/visitante-residente-inexistente.png)

---

## 16. Estacionamiento de visitantes

Los visitantes solamente pueden ocupar espacios del área general:

```text
E, F, G, H e I
```

Nunca pueden ocupar espacios:

```text
A, B o C
```

Cuando el visitante se estaciona:

- Su espacio aparece en rojo.
- Su placa se muestra en la cuadrícula.
- Se registra el evento correspondiente.
- El visitante permanece temporalmente en memoria.

> **Aquí puedes agregar una imagen donde se observe un visitante estacionado en el área general.**

<!-- Imagen sugerida: docs/visitante-estacionado.png -->

![Visitante estacionado en el área general](docs/visitante-estacionado.png)

---

## 17. Visitante rechazado

Un visitante puede ser rechazado cuando no existe espacio disponible en el área general.

Cuando esto ocurre:

- Su estado vuelve a `FUERA`.
- Se elimina de la lista temporal de visitantes.
- No se guarda en los archivos permanentes.
- Se registra un evento de rechazo.
- El vehículo no permanece esperando en la cola.

> **Aquí puedes agregar una imagen del mensaje de visitante rechazado por falta de espacio.**

<!-- Imagen sugerida: docs/visitante-rechazado.png -->

![Visitante rechazado](docs/visitante-rechazado.png)

---

## 18. Consultar la cola de entrada

La cola de entrada muestra los vehículos que están esperando ser atendidos.

La cola funciona bajo el principio FIFO:

```text
First In, First Out
```

Esto significa que el primer vehículo agregado es el primero que debe ser atendido por una garita disponible.

> **Aquí puedes agregar una imagen de la cola de entrada con varios vehículos esperando.**

<!-- Imagen sugerida: docs/cola-entrada.png -->

![Cola de entrada](docs/cola-entrada.png)

---

## 19. Garitas de entrada

El sistema utiliza dos garitas de entrada:

```text
Garita 1
Garita 2
```

Ambas consumen vehículos de la misma cola.

La interfaz muestra cuál vehículo está siendo atendido en cada garita.

Cada garita simula un tiempo de atención de entre 1 y 3 segundos.

> **Aquí puedes agregar una imagen donde se observe el estado de las dos garitas atendiendo vehículos.**

<!-- Imagen sugerida: docs/garitas-entrada.png -->

![Garitas de entrada](docs/garitas-entrada.png)

---

## 20. Solicitar la salida de un vehículo

Para solicitar la salida:

1. Verificar que el vehículo aparezca en la lista **Estacionados**.
2. Seleccionar su placa.
3. Presionar **Solicitar salida**.
4. El vehículo cambia a estado `EN_COLA_SALIDA`.
5. Se agrega a la cola de salida.
6. Su espacio continúa ocupado mientras espera.

Un vehículo que ya solicitó salida no puede volver a solicitarla.

> **Aquí puedes agregar una imagen mostrando la lista de vehículos estacionados y el botón “Solicitar salida”.**

<!-- Imagen sugerida: docs/solicitar-salida.png -->

![Solicitar salida](docs/solicitar-salida.png)

---

## 21. Cola y garita de salida

La cola de salida utiliza el principio FIFO.

La garita de salida atiende los vehículos en el orden en que solicitaron salir.

Cuando la garita atiende un vehículo:

- Se libera el espacio.
- El espacio vuelve a color verde.
- El vehículo cambia a estado `FUERA`.
- Se registra un evento de salida.
- Si era visitante, se elimina de la memoria.

> **Aquí puedes agregar una imagen de la cola de salida y del estado de la garita.**

<!-- Imagen sugerida: docs/cola-salida.png -->

![Cola de salida](docs/cola-salida.png)

---

## 22. Consultar eventos

Para consultar los eventos:

1. Presionar el botón **Ver Eventos**.
2. Se abrirá la ventana del historial.
3. Los eventos se muestran del más reciente al más antiguo.

Entre los eventos registrados se encuentran:

- Vehículo enviado a la cola de entrada.
- Vehículo asignado.
- Vehículo rechazado.
- Visitante registrado.
- Visitante eliminado.
- Vehículo enviado a la cola de salida.
- Vehículo que salió.
- Residente registrado.
- Vehículo registrado.
- Errores de lectura de archivos.

> **Aquí puedes agregar una captura de la ventana “Historial de Eventos”.**

<!-- Imagen sugerida: docs/ventana-eventos.png -->

![Historial de eventos](docs/ventana-eventos.png)

---

## 23. Persistencia de información

El sistema guarda información en archivos de texto.

Los archivos utilizados son:

```text
residentes.txt
vehiculos.txt
eventos.txt
ocupacion.txt
```

### Residentes y vehículos

Los residentes y sus vehículos se cargan automáticamente al iniciar el programa.

### Eventos

El historial de eventos se guarda y se carga nuevamente al iniciar.

### Ocupación

Los vehículos de residentes que estaban estacionados vuelven a aparecer en el mismo espacio al abrir nuevamente el programa.

Los vehículos que estaban en `EN_COLA_SALIDA` se restauran como `ESTACIONADO`.

### Visitantes

Los visitantes son temporales.

No se guardan en:

```text
residentes.txt
vehiculos.txt
ocupacion.txt
```

Al cerrar el programa, los visitantes dejan de existir.

> **Aquí puedes agregar una imagen de los archivos `.txt` generados por el sistema.**

<!-- Imagen sugerida: docs/archivos-persistencia.png -->

![Archivos de persistencia](docs/archivos-persistencia.png)

---

## 24. Cierre del programa

Para cerrar el sistema:

1. Presionar el botón de cerrar de la ventana principal.
2. El sistema guarda los datos antes de finalizar.
3. Se actualizan los archivos de persistencia.
4. El programa termina.

Se recomienda cerrar la aplicación mediante la ventana principal para permitir que se guarden los datos correctamente.

> **Aquí puedes agregar una imagen del momento en que se cierra el sistema o de la consola mostrando que los datos fueron guardados.**

<!-- Imagen sugerida: docs/guardado-cierre.png -->

![Guardado al cerrar](docs/guardado-cierre.png)

---

## 25. Mensajes frecuentes

### “Ingrese una placa”

Aparece cuando se intenta enviar un vehículo sin escribir una placa.

### “El residente visitado no existe”

Aparece cuando se registra un visitante con un ID que no está registrado.

### “La placa ya existe en el sistema”

Aparece cuando se intenta registrar una placa duplicada.

### “El vehículo ya está dentro o en una cola”

Aparece cuando se intenta enviar un vehículo que no está en estado `FUERA`.

### “No hay espacio disponible”

Aparece cuando el área permitida para el vehículo está llena.

### “Seleccione un vehículo estacionado”

Aparece cuando se intenta solicitar una salida sin seleccionar una placa.

### “El vehículo ya solicitó su salida”

Aparece cuando se intenta enviar nuevamente a salida un vehículo que ya está en `EN_COLA_SALIDA`.

---

## 26. Recomendaciones de uso

- Registrar primero a los residentes antes de enviar sus vehículos.
- Verificar que las placas estén escritas correctamente.
- No cerrar el programa mientras se está probando una operación importante, a menos que se quiera probar la persistencia.
- Utilizar el botón de visitantes solamente para personas que no son residentes.
- Consultar el historial de eventos para verificar las operaciones realizadas.
- Revisar los archivos de texto cuando se necesite comprobar la persistencia.

---

## 27. Prueba completa recomendada

Para comprobar el funcionamiento completo:

1. Registrar un residente socio.
2. Registrar un residente no socio.
3. Registrar vehículos para ambos residentes.
4. Registrar un visitante.
5. Enviar un vehículo socio.
6. Enviar un vehículo no socio.
7. Enviar el visitante.
8. Verificar la asignación de espacios.
9. Consultar los eventos.
10. Solicitar la salida de un vehículo residente.
11. Solicitar la salida del visitante.
12. Verificar que los espacios se liberen.
13. Cerrar el sistema.
14. Revisar los archivos de persistencia.
15. Abrir nuevamente el sistema.
16. Confirmar que los vehículos residentes vuelvan a sus espacios.
17. Confirmar que el visitante no sea restaurado.

> **Aquí puedes agregar una imagen final con la prueba completa funcionando: residentes, visitantes, vehículos estacionados y eventos.**

<!-- Imagen sugerida: docs/prueba-final.png -->

![Prueba final del sistema](docs/prueba-final.png)

---

## 28. Solución de problemas

### El programa no inicia

Verificar:

- Que Java esté instalado.
- Que la versión de Java sea compatible.
- Que se haya ejecutado Maven desde la carpeta correcta.
- Que el proyecto compile correctamente.

Comando:

```bash
mvn clean compile
```

### Los datos no aparecen al reiniciar

Verificar:

- Que los archivos `.txt` existan.
- Que se haya cerrado el programa desde la ventana principal.
- Que los archivos tengan el formato correcto.
- Que no existan campos inválidos.

### Un espacio no vuelve a aparecer ocupado

Verificar:

- Que el vehículo sea residente.
- Que la placa exista en `vehiculos.txt`.
- Que la ocupación exista en `ocupacion.txt`.
- Que el identificador del espacio sea correcto.

### No se muestran eventos

Verificar:

- Que exista `eventos.txt`.
- Que el archivo tenga cuatro campos separados por `|`.
- Que la pila de eventos se cargue al iniciar el programa.

---

## 29. Conclusión

ResiPark permite administrar el ciclo completo de residentes, vehículos y visitantes dentro de un residencial.

El usuario puede:

```text
Registrar personas
→ Registrar vehículos
→ Enviar vehículos a la cola
→ Estacionarlos
→ Solicitar su salida
→ Liberar espacios
→ Consultar eventos
→ Recuperar información al reiniciar
```

Los visitantes se manejan de forma temporal y solamente pueden utilizar el área general del estacionamiento.