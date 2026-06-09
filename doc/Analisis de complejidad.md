# Análisis de Complejidad — PriorityQueue

## 1. `bajarElemento(int indiceElemento)`

### Complejidad Temporal: O(log n)

El método se llama recursivamente una vez por nivel del heap.
Como el heap es un árbol binario completo, su altura es log₂(n),
por lo tanto en el peor caso el elemento baja desde la raíz
hasta una hoja recorriendo log₂(n) niveles.
En cada nivel se hacen exactamente 2 comparaciones (hijo izquierdo
y hijo derecho), lo cual es una cantidad constante O(1) por nivel.

| Caso           | Comportamiento                                        | Complejidad |
|----------------|-------------------------------------------------------|-------------|
| Mejor caso     | El elemento ya está en su lugar, no baja ningún nivel | O(1)        |
| Caso promedio  | El elemento baja la mitad de los niveles              | O(log n)    |
| Peor caso      | El elemento baja desde la raíz hasta una hoja         | O(log n)    |

### Complejidad Espacial: O(log n)

Cada llamada recursiva apila en el stack con sus variables locales.
Como hay log₂(n) llamadas recursivas apiladas en el peor caso,
el espacio total en el stack es O(log n).

| Tipo      | Variable          | Cantidad de valores atómicos |
|-----------|-------------------|------------------------------|
| Entrada   | indiceElemento    | 1                            |
| Auxiliar  | indiceMenor       | 1                            |
| Auxiliar  | indiceIzquierdo   | 1                            |
| Auxiliar  | indiceDerecho     | 1                            |
| Auxiliar  | guardaTemporal    | 1                            |
| Salida    | ——                | 0                            |

Total de valores atómicos por frame: 5
Frames apilados en el peor caso: log₂(n)
**Espacio total: O(log n)**

---

## 2. `tryAdd(GameResult nuevoResultado)`

### Complejidad Temporal: O(n)

El método tiene estos pasos internos:

| Paso                              | Complejidad     |
|-----------------------------------|-----------------|
| `isFull()`                        | O(1)            |
| `encontrarIndicePeorResultado()`  | O(n/2) = O(n)   |
| `subirElemento()`                 | O(log n)        |
| `bajarElemento()`                 | O(log n)        |

El paso dominante es `encontrarIndicePeorResultado()` que recorre
las hojas del heap (segunda mitad del array), es decir n/2 elementos.

| Caso           | Comportamiento                                       | Complejidad |
|----------------|------------------------------------------------------|-------------|
| Mejor caso     | Cola no llena, se hace `insert()` directo            | O(log n)    |
| Caso promedio  | Cola llena, recorre hojas y reorganiza               | O(n)        |
| Peor caso      | Cola llena, recorre todas las hojas del heap         | O(n)        |

> **Nota práctica:** como n = TOP_K = 5 (constante fija), en esta
> implementación todas las operaciones son efectivamente O(1).
> El análisis O(n) aplica para cualquier valor general de K.

### Complejidad Espacial: O(K)

El array `almacenResultados` de tamaño K domina el espacio.
Las variables locales son constantes independientemente de la entrada.

| Tipo      | Variable             | Cantidad de valores atómicos |
|-----------|----------------------|------------------------------|
| Entrada   | nuevoResultado       | 1                            |
| Auxiliar  | indicePeor           | 1                            |
| Auxiliar  | almacenResultados    | K (tamaño del array)         |
| Auxiliar  | cantidadActual       | 1                            |
| Auxiliar  | capacidadMaxima      | 1                            |
| Salida    | ——                   | 0                            |

Total de valores atómicos: K + 4
**Espacio total: O(K)**

---

## Resumen General

| Método            | Temporal      | Espacial  |
|-------------------|---------------|-----------|
| `bajarElemento()` | O(log n)      | O(log n)  |
| `tryAdd()`        | O(n) / O(1)*  | O(K)      |

*O(1) cuando K es constante (TOP_K = 5)


# Análisis de Complejidad — Queue y HashTable

## 1. `enqueue(E data)`
### Complejidad Temporal: O(1)
El método crea un nodo nuevo y lo enlaza al final de la cola.
Gracias al puntero `rear`, no es necesario recorrer la lista.
Todas las operaciones son de acceso directo y constante.

| Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Mejor caso     | Cola vacía, front y rear apuntan al nuevo nodo         | O(1)        |
| Caso promedio  | Se enlaza el nodo al rear actual                       | O(1)        |
| Peor caso      | Igual al promedio                                      | O(1)        |

### Complejidad Espacial: O(1)
Solo se crea un nodo nuevo por llamada.
No se usan estructuras auxiliares adicionales.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | data       | 1                            |
| Auxiliar  | newNode    | 1                            |
| Salida    | ——         | 0                            |

Total de valores atómicos por frame: 2
Frames apilados en el peor caso: 1
**Espacio total: O(1)**

---

## 2. `dequeue()`
### Complejidad Temporal: O(1)
El método accede directamente al nodo `front`, extrae su dato
y avanza el puntero. No hay recorrido de la lista.

| Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Mejor caso     | Cola vacía, retorna null inmediatamente                | O(1)        |
| Caso promedio  | Extrae el frente y actualiza front                     | O(1)        |
| Peor caso      | Igual al promedio                                      | O(1)        |

### Complejidad Espacial: O(1)
Solo se usan variables locales atómicas.
No se crean estructuras auxiliares.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | ——         | 0                            |
| Auxiliar  | data       | 1                            |
| Salida    | data       | 1                            |

Total de valores atómicos por frame: 2
Frames apilados en el peor caso: 1
**Espacio total: O(1)**

---

## 3. `peek()`
### Complejidad Temporal: O(1)
El método accede directamente al dato del nodo `front`
sin modificar la cola ni recorrer ningún nodo.

| Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Mejor caso     | Cola vacía, retorna null inmediatamente                | O(1)        |
| Caso promedio  | Retorna el dato del nodo front                         | O(1)        |
| Peor caso      | Igual al promedio                                      | O(1)        |

### Complejidad Espacial: O(1)
No se crean nodos ni estructuras auxiliares.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | ——         | 0                            |
| Auxiliar  | ——         | 0                            |
| Salida    | data       | 1                            |

Total de valores atómicos por frame: 1
Frames apilados en el peor caso: 1
**Espacio total: O(1)**

---

## 4. `clear()`
### Complejidad Temporal: O(1)
El método asigna null a `front` y `rear` y reinicia `size`.
No recorre ningún nodo; el recolector de basura libera la memoria.

| Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Mejor caso     | Reasigna tres campos en tiempo constante               | O(1)        |
| Caso promedio  | Igual al mejor caso                                    | O(1)        |
| Peor caso      | Igual al mejor caso                                    | O(1)        |

### Complejidad Espacial: O(1)
No se crean variables auxiliares adicionales.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | ——         | 0                            |
| Auxiliar  | ——         | 0                            |
| Salida    | ——         | 0                            |

Total de valores atómicos por frame: 0
Frames apilados en el peor caso: 1
**Espacio total: O(1)**

---

## Resumen General — Queue

| Método       | Temporal | Espacial |
|--------------|----------|----------|
| `enqueue()`  | O(1)     | O(1)     |
| `dequeue()`  | O(1)     | O(1)     |
| `peek()`     | O(1)     | O(1)     |
| `clear()`    | O(1)     | O(1)     |
| `isEmpty()`  | O(1)     | O(1)     |
| `size()`     | O(1)     | O(1)     |

---
---

# Análisis de Complejidad — HashTable

> Para el análisis se usa **n** = número de entradas almacenadas
> y **c** = `capacity` (tamaño del array de cubetas).
> El factor de carga es α = n/c.

## 1. `put(K key, V value)`
### Complejidad Temporal: O(1) promedio — O(n) peor caso
El método calcula el índice con `getIndex()` en O(1),
luego recorre la cadena de la cubeta correspondiente.
En el caso promedio con buen factor de carga la cadena es corta.
En el peor caso todas las claves colisionan en la misma cubeta,
formando una cadena de longitud n.

| Caso           | Comportamiento                                          | Complejidad      |
|----------------|---------------------------------------------------------|------------------|
| Mejor caso     | La cubeta está vacía, inserción directa                 | O(1)             |
| Caso promedio  | La cadena tiene α ≈ n/c nodos                           | O(1) con buen α  |
| Peor caso      | Todas las claves colisionan en una misma cubeta         | O(n)             |

### Complejidad Espacial: O(1)
Solo se crea un nodo nuevo por llamada.
El array de cubetas ya existe previamente.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | key, value | 2                            |
| Auxiliar  | index      | 1                            |
| Auxiliar  | head       | 1                            |
| Auxiliar  | newNode    | 1                            |
| Salida    | ——         | 0                            |

Total de valores atómicos por frame: 5
Frames apilados en el peor caso: 1
**Espacio total: O(1)** por llamada; O(n) acumulado en la tabla.

---

## 2. `get(K key)`
### Complejidad Temporal: O(1) promedio — O(n) peor caso
El método calcula el índice en O(1) y recorre la cadena de la cubeta.
La estructura es idéntica a `put()`, solo que no inserta ningún nodo.

| Caso           | Comportamiento                                          | Complejidad      |
|----------------|---------------------------------------------------------|------------------|
| Mejor caso     | El nodo buscado es el primero de la cadena              | O(1)             |
| Caso promedio  | Recorre α nodos en promedio                             | O(1) con buen α  |
| Peor caso      | La clave está al final de una cadena de longitud n      | O(n)             |

### Complejidad Espacial: O(1)
No se crean estructuras auxiliares adicionales.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | key        | 1                            |
| Auxiliar  | index      | 1                            |
| Auxiliar  | head       | 1                            |
| Salida    | value      | 1                            |

Total de valores atómicos por frame: 4
Frames apilados en el peor caso: 1
**Espacio total: O(1)**

---

## 3. `remove(K key)`
### Complejidad Temporal: O(1) promedio — O(n) peor caso
El método recorre la cadena igual que `get()`, pero además mantiene
un puntero `prev` para desenlazar el nodo encontrado sin recorrer
la lista nuevamente.

| Caso           | Comportamiento                                          | Complejidad      |
|----------------|---------------------------------------------------------|------------------|
| Mejor caso     | El nodo a eliminar es el primero de la cadena           | O(1)             |
| Caso promedio  | Recorre α nodos en promedio                             | O(1) con buen α  |
| Peor caso      | El nodo está al final de una cadena de longitud n       | O(n)             |

### Complejidad Espacial: O(1)
No se crean estructuras auxiliares adicionales.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | key        | 1                            |
| Auxiliar  | index      | 1                            |
| Auxiliar  | head       | 1                            |
| Auxiliar  | prev       | 1                            |
| Salida    | value      | 1                            |

Total de valores atómicos por frame: 5
Frames apilados en el peor caso: 1
**Espacio total: O(1)**

---

## 4. `containsKey(K key)`
### Complejidad Temporal: O(1) promedio — O(n) peor caso
El método delega completamente en `get()`,
por lo tanto hereda exactamente su complejidad temporal.

| Caso           | Comportamiento                                          | Complejidad      |
|----------------|---------------------------------------------------------|------------------|
| Mejor caso     | El nodo buscado es el primero de la cadena              | O(1)             |
| Caso promedio  | Recorre α nodos en promedio vía get()                   | O(1) con buen α  |
| Peor caso      | La clave está al final de una cadena de longitud n      | O(n)             |

### Complejidad Espacial: O(1)
Igual que `get()`, no se crean estructuras auxiliares.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | key        | 1                            |
| Auxiliar  | ——         | 0                            |
| Salida    | boolean    | 1                            |

Total de valores atómicos por frame: 2
Frames apilados en el peor caso: 1
**Espacio total: O(1)**

---

## 5. `clear()`
### Complejidad Temporal: O(c)
El método reemplaza el array de cubetas con un array nuevo de
tamaño `capacity`. Java inicializa cada posición a null,
lo que implica un recorrido interno de c posiciones.

| Caso           | Comportamiento                                          | Complejidad |
|----------------|---------------------------------------------------------|-------------|
| Mejor caso     | Inicializa c posiciones a null                          | O(c)        |
| Caso promedio  | Igual al mejor caso                                     | O(c)        |
| Peor caso      | Igual al mejor caso                                     | O(c)        |

### Complejidad Espacial: O(c)
Se crea un array nuevo de tamaño `capacity`.

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   | ——         | 0                            |
| Auxiliar  | buckets    | c (nuevo array)              |
| Salida    | ——         | 0                            |

Total de valores atómicos por frame: c
Frames apilados en el peor caso: 1
**Espacio total: O(c)**

---

## Resumen General — HashTable

| Método           | Temporal (promedio) | Temporal (peor caso) | Espacial |
|------------------|---------------------|----------------------|----------|
| `put()`          | O(1)                | O(n)                 | O(1)     |
| `get()`          | O(1)                | O(n)                 | O(1)     |
| `remove()`       | O(1)                | O(n)                 | O(1)     |
| `containsKey()`  | O(1)                | O(n)                 | O(1)     |
| `clear()`        | O(c)                | O(c)                 | O(c)     |
| `isEmpty()`      | O(1)                | O(1)                 | O(1)     |
| `size()`         | O(1)                | O(1)                 | O(1)     |

El peor caso O(n) ocurre cuando todas las claves colisionan en la misma cubeta.
Con una función `hashCode()` bien distribuida y un factor de carga α = n/c < 1,
todas las operaciones de búsqueda e inserción son efectivamente O(1).

# Análisis de Complejidad — Stack

## 1. `push(E data)`
### Complejidad Temporal: O(1)
El metodo crea un nuevo nodo, ajusta la referencia `next` y actualiza `top`


| Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Mejor caso     | inserccion directa                                     | O(1)        |
| Caso promedio  | igual al mejor caso                                     | O(1)        |
| Peor caso      | Igual al mejor caso                                     | O(1)        |

### Complejidad espacial O(1)
se crea un solo nuevo nodo
| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   |  data       | 1                            |
| Auxiliar  | newnode    |                  1              |
| Salida    | ——         | 0                            |

Total por frame 2;
Frames 1;
Espacio tola 1;

## 2 `pop()` 
### Complejidad Temporal: O(1)
El metodo, Berifica si esta vacia, Guarda el dato del nodo top, Mueve el top al siguiente nodo.
No hay recorridos.

| Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Mejor caso     | Pila vacia                                    | O(1)        |
| Caso promedio  | Elimina el Top                                    | O(1)        |
| Peor caso      |  igual                                     | O(1)        |

## complejidad espacial O(1)
no se crean estructuras adiciaonales

| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   |    ——      | 0                            |
| Auxiliar  |  data    |                  1              |
| Salida    | data        | 1                            |

Total: 2 Valores
Espacio total O(1)
 ## 3 `peek()`
 ### Complejidad Temporal O(1)
 Solo accede al dato del nodo en `Top`.
 | Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Todos     | Acceso directo                                    | O(1)        |
### Complejidad Espacial O(1)
No se crea valores adiccionales significativas

## 4 `isEmpty1`

### Complejidad Temporal O(1)
| Caso           |    Complejidad |
|----------------|-------------|
| Todos       | O(1)        |

### Complejidad Espacial O(1)

## 5 'Size()'
### Complejidad Temporal O(1)
Retorna una variable ya almacenada
| Caso           |    Complejidad |
|----------------|-------------|
| Todos       | O(1)        |
### Complejidad Espacial O(1)

## 6 `Display()`
### Complejidad Temporal O(1)
Recorre todos los nodos `top` hasta el final
| Caso           | Comportamiento                                         | Complejidad |
|----------------|--------------------------------------------------------|-------------|
| Mejor caso     | Pila vacia                                    | O(1)        |
| Caso promedio  | Recorre n/2 nodos en promedio                                    | O(n)        |
| Peor caso      |  Recorre todos los nodos                                     | O(n)        |

## Complejidad espacial O(n)
Se construye un `StringBuilder` con todos los elementos
| Tipo      | Variable   | Cantidad de valores atómicos |
|-----------|------------|------------------------------|
| Entrada   |    ——      | 0                            |
| Auxiliar  |     current       |          1                    |
| Auxiliar  |  sb   |                  n             |
| Salida    | String final       | n                           |\
Total proporcional a n
Espacio Total O(n)
## Resumen General — HashTable

| Método           | Temporal |               Espacial |
|------------------|---------------------|----------------------|
| `push()`          | O(1)                 | O(1)     |
| `popp()`          | O(1)                 | O(1)     |
| `peek()`       | O(1)                | O(1)     |
| `isEmpty()`  | O(1)                | O(1)     |
| `Size()`        | O(1)                 | O(1)     |
| `Display()`      | O(n)                | | O(n)     |


# Analisis de Complejidad - BFSAuto
## 1 `solve(GameState estadoInicial)`
### Complejidad Temporal: O(V+E)
El metodo implementa una busqueda en anchura(BFS) sobre el espacion de eestados del juego
para cada estado se generan hasta 4 movimientos posibles(UP, DOWN, LEFT, RIGHT).
Las operaciones principales son:
# Análisis de Complejidad — BFSAuto

> Para el análisis se usa:
>
> * **V** = cantidad de estados alcanzables del espacio de búsqueda.
> * **E** = cantidad de transiciones entre estados.
> * **B** = cantidad de cajas presentes en el nivel.
> * **L** = longitud de la solución encontrada.
> * **W** = ancho del tablero.
> * **H** = alto del tablero.
> * **G** = cantidad de posiciones objetivo (goals).

---

## 1. `solve(GameState estadoInicial)`

### Complejidad Temporal: O(V + E) . B

El método implementa una búsqueda BFS (Breadth First Search).

Cada estado se visita una única vez gracias a la tabla hash
`visitados`.

Para cada estado se generan hasta 4 movimientos posibles: (UP, DOWN, LEF, RIGHT)

Además se verifica si el estado ya fue visitado y se agrega
a la cola únicamente si es nuevo.

En esta versión del algoritmo se aplican podas utilizando
Floyd-Warshall y Dijkstra. Para cada estado generado se
construye un arreglo con las posiciones de las cajas mediante
`boxesToPositionArray()` y posteriormente se calcula una
heurística mediante `computeHeuristic()`.

Estas operaciones dependen del número de cajas B presentes
en el nivel, por lo que cada expansión de estado tiene un
costo adicional O(B)

| Caso          | Comportamiento                                     
| ------------- | -------------------------------------------------- 
| Mejor caso    | El estado inicial ya es solución                   
| Caso promedio | Explora parte del espacio de estados aplicando podas heurisitcas              
| Peor caso     | Explora todos los estados alcanzables del problema

### Complejidad Espacial: O(V)

La cola BFS y la tabla hash de visitados almacenan estados.

| Tipo     | Variable        | Cantidad de valores atómicos |
| -------- | --------------- | ---------------------------- |
| Entrada  | estadoInicial   | 1                            |
| Auxiliar | colaBFS         | V                            |
| Auxiliar | visitados       | V                            |
| Auxiliar | estadoActual    | 1                            |
| Salida   | camino solución | L                            |

Total proporcional a V

**Espacio total: O(V)**

---

## 2. `applyMove(GameState estado, String direction)`

### Complejidad Temporal: O(B)

El método simula un movimiento sobre un estado.

Las operaciones sobre posiciones son constantes.

Sin embargo utiliza:

* `findBoxAt()`
* `buildBoxes()`

ambos recorriendo la lista completa de cajas.

| Caso          | Comportamiento                            | Complejidad |
| ------------- | ----------------------------------------- | ----------- |
| Mejor caso    | Movimiento inválido por pared             | O(1)        |
| Caso promedio | Movimiento normal                         | O(B)        |
| Peor caso     | Empuje de caja y copia completa de estado | O(B)        |

### Complejidad Espacial: O(B)

Se construye una nueva lista de cajas para el estado generado.

| Tipo     | Variable          | Cantidad de valores atómicos |
| -------- | ----------------- | ---------------------------- |
| Entrada  | estado, direction | 2                            |
| Auxiliar | nuevasCajas       | B                            |
| Auxiliar | cajaEnDestino     | 1                            |
| Salida   | nuevo GameState   | B                            |

Total proporcional a B

**Espacio total: O(B)**

---

## 3. `findBoxAt(LinkedList<Box> cajas, int x, int y)`

### Complejidad Temporal: O(B)

Recorre secuencialmente todas las cajas hasta encontrar
la posición buscada.

| Caso          | Comportamiento                         | Complejidad |
| ------------- | -------------------------------------- | ----------- |
| Mejor caso    | Caja encontrada en la primera posición | O(1)        |
| Caso promedio | Caja encontrada en posición intermedia | O(B)        |
| Peor caso     | Caja inexistente o última posición     | O(B)        |

### Complejidad Espacial: O(1)

No crea estructuras auxiliares.

| Tipo     | Variable  | Cantidad de valores atómicos |
| -------- | --------- | ---------------------------- |
| Entrada  | cajas,x,y | 3                            |
| Auxiliar | box       | 1                            |
| Salida   | Box       | 1                            |

Total: 5 valores

**Espacio total: O(1)**

---

## 4. `buildBoxes(...)`

### Complejidad Temporal: O(B)

Recorre todas las cajas para construir una copia del estado.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Una sola caja           | O(1)        |
| Caso promedio | Recorre todas las cajas | O(B)        |
| Peor caso     | Recorre todas las cajas | O(B)        |

### Complejidad Espacial: O(B)

Se crea una nueva LinkedList con todas las cajas.

| Tipo     | Variable      | Cantidad de valores atómicos |
| -------- | ------------- | ---------------------------- |
| Entrada  | cajasActuales | B                            |
| Auxiliar | resultado     | B                            |
| Salida   | nueva lista   | B                            |

Total proporcional a B

**Espacio total: O(B)**

---

## 5. `reconstructPath(GameState estadoFinal)`

### Complejidad Temporal: O(L)

Recorre la cadena de padres desde el estado final
hasta el estado inicial.

Posteriormente invierte el camino usando una pila.

| Caso          | Comportamiento             | Complejidad |
| ------------- | -------------------------- | ----------- |
| Mejor caso    | Solución vacía             | O(1)        |
| Caso promedio | Solución de longitud media | O(L)        |
| Peor caso     | Solución muy larga         | O(L)        |

### Complejidad Espacial: O(L)

Utiliza una pila auxiliar y una lista para almacenar
la solución.

| Tipo     | Variable    | Cantidad de valores atómicos |
| -------- | ----------- | ---------------------------- |
| Entrada  | estadoFinal | 1                            |
| Auxiliar | pilaInversa | L                            |
| Salida   | camino      | L                            |

Total proporcional a L

**Espacio total: O(L)**

---

## 6. `buildInitialState(Game game)`

### Complejidad Temporal: O(B)

Recorre todas las cajas del tablero para construir
el estado inicial.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Una sola caja           | O(1)        |
| Caso promedio | Recorre todas las cajas | O(B)        |
| Peor caso     | Recorre todas las cajas | O(B)        |

### Complejidad Espacial: O(B)

Se crea una copia completa de las cajas existentes.

| Tipo     | Variable          | Cantidad de valores atómicos |
| -------- | ----------------- | ---------------------------- |
| Entrada  | game              | 1                            |
| Auxiliar | cajasIniciales    | B                            |
| Salida   | GameState inicial | B                            |

Total proporcional a B

**Espacio total: O(B)**

---

## 7. `loadGoalPositions()`

### Complejidad Temporal: O(W × H)

El tablero se recorre dos veces:

1. Contar goals.
2. Guardar posiciones.

Cada recorrido inspecciona todas las celdas.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Tablero pequeño         | O(W×H)      |
| Caso promedio | Recorre todo el tablero | O(W×H)      |
| Peor caso     | Recorre todo el tablero | O(W×H)      |

### Complejidad Espacial: O(G)

Se almacena un arreglo con todas las posiciones objetivo.

| Tipo     | Variable         | Cantidad de valores atómicos |
| -------- | ---------------- | ---------------------------- |
| Entrada  | board            | 1                            |
| Auxiliar | goalPositions    | G                            |
| Auxiliar | goalCount        | 1                            |
| Salida   | arreglo de goals | G                            |

Total proporcional a G

**Espacio total: O(G)**

---

# Resumen General — BFSAuto

| Método                | Temporal | Espacial |
| --------------------- | -------- | -------- |
| `solve()`             | O(V + E) · B | O(V)     |
| `applyMove()`         | O(B)     | O(B)     |
| `findBoxAt()`         | O(B)     | O(1)     |
| `buildBoxes()`        | O(B)     | O(B)     |
| `reconstructPath()`   | O(L)     | O(L)     |
| `buildInitialState()` | O(B)     | O(B)     |
| `loadGoalPositions()` | O(W × H) | O(G)     |



# Análisis de Complejidad — DFSAuto

> Para el análisis se usa:
>
> * **V** = cantidad de estados alcanzables del espacio de búsqueda.
> * **E** = cantidad de transiciones entre estados.
> * **B** = cantidad de cajas presentes en el nivel.
> * **D** = profundidad de un estado dentro del árbol de búsqueda.
> * **L** = longitud de la solución encontrada.
> * **W** = ancho del tablero.
> * **H** = alto del tablero.
> * **G** = cantidad de posiciones objetivo (goals).

---

## 1. `solve(GameState initialState)`

### Complejidad Temporal: O((V + E) · (B² + D))

El método implementa una búsqueda en profundidad (DFS)
utilizando una pila.

Cada estado se visita una única vez gracias a la tabla hash
`visited`.

Para cada estado se realizan las siguientes operaciones:

- Obtener la profundidad mediante `getDepth()`.
- Verificar si es estado objetivo.
- Generar hasta 4 movimientos posibles.
- Aplicar podas heurísticas mediante Floyd-Warshall o Dijkstra.

Las heurísticas requieren convertir las cajas a un arreglo
de posiciones mediante boxesToPositionArray().

Además, para cada estado generado se construye una clave única
mediante generateKey(). Este método ordena las posiciones de las
cajas usando ordenamiento burbuja, lo que tiene complejidad O(B²).
Adicionalmente, el método getDepth() recorre la cadena de padres
hasta el estado inicial con costo O(D).
Por esta razón el costo dominante por expansión es O(B² + D),
obteniendo una complejidad total de O((V + E) · (B² + D)).


| Caso          | Comportamiento                        | Complejidad          |
| ------------- | ------------------------------------- | -------------------- |
| Mejor caso    | El estado inicial ya es solución      | O(B + D)             |
| Caso promedio | Explora parte del espacio de estados  | O((V + E) · (B² + D)) |
| Peor caso     | Explora todos los estados alcanzables | O((V + E) · (B² + D)) |

### Complejidad Espacial: O(V)

La pila DFS y la tabla hash de visitados almacenan estados.

| Tipo     | Variable        | Cantidad de valores atómicos |
| -------- | --------------- | ---------------------------- |
| Entrada  | initialState    | 1                            |
| Auxiliar | dfStack         | V                            |
| Auxiliar | visited         | V                            |
| Auxiliar | current         | 1                            |
| Salida   | camino solución | L                            |

Total proporcional a V

**Espacio total: O(V)**

---

## 2. `getDepth(GameState state)`

### Complejidad Temporal: O(D)

Recorre la cadena de estados padre hasta llegar
al estado inicial.

| Caso          | Comportamiento              | Complejidad |
| ------------- | --------------------------- | ----------- |
| Mejor caso    | Estado inicial              | O(1)        |
| Caso promedio | Recorre parte de la cadena  | O(D)        |
| Peor caso     | Recorre toda la profundidad | O(D)        |

### Complejidad Espacial: O(1)

No crea estructuras auxiliares adicionales.

| Tipo     | Variable | Cantidad de valores atómicos |
| -------- | -------- | ---------------------------- |
| Entrada  | state    | 1                            |
| Auxiliar | depth    | 1                            |
| Auxiliar | current  | 1                            |
| Salida   | int      | 1                            |

Total: 4 valores

**Espacio total: O(1)**

---

## 3. `applyMove(GameState state, String direction)`

### Complejidad Temporal: O(B)

El método simula un movimiento sobre un estado.

Las operaciones sobre posiciones son constantes.

Sin embargo utiliza:

* `findBoxAt()`
* `buildBoxes()`

ambos recorriendo la lista completa de cajas.

| Caso          | Comportamiento                             | Complejidad |
| ------------- | ------------------------------------------ | ----------- |
| Mejor caso    | Movimiento inválido por pared              | O(1)        |
| Caso promedio | Movimiento normal                          | O(B)        |
| Peor caso     | Empuje de caja y copia completa del estado | O(B)        |

### Complejidad Espacial: O(B)

Se construye una nueva lista de cajas para el estado generado.

| Tipo     | Variable         | Cantidad de valores atómicos |
| -------- | ---------------- | ---------------------------- |
| Entrada  | state, direction | 2                            |
| Auxiliar | updatedBoxes     | B                            |
| Auxiliar | boxAtTarget      | 1                            |
| Salida   | nuevo GameState  | B                            |

Total proporcional a B

**Espacio total: O(B)**

---

## 4. `findBoxAt(LinkedList<Box> boxes, int x, int y)`

### Complejidad Temporal: O(B)

Recorre secuencialmente todas las cajas hasta encontrar
la posición buscada.

| Caso          | Comportamiento                         | Complejidad |
| ------------- | -------------------------------------- | ----------- |
| Mejor caso    | Caja encontrada en la primera posición | O(1)        |
| Caso promedio | Caja encontrada en posición intermedia | O(B)        |
| Peor caso     | Caja inexistente o última posición     | O(B)        |

### Complejidad Espacial: O(1)

No crea estructuras auxiliares.

| Tipo     | Variable  | Cantidad de valores atómicos |
| -------- | --------- | ---------------------------- |
| Entrada  | boxes,x,y | 3                            |
| Auxiliar | box       | 1                            |
| Salida   | Box       | 1                            |

Total: 5 valores

**Espacio total: O(1)**

---

## 5. `buildBoxes(...)`

### Complejidad Temporal: O(B)

Recorre todas las cajas para construir una copia del estado.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Una sola caja           | O(1)        |
| Caso promedio | Recorre todas las cajas | O(B)        |
| Peor caso     | Recorre todas las cajas | O(B)        |

### Complejidad Espacial: O(B)

Se crea una nueva LinkedList con todas las cajas.

| Tipo     | Variable     | Cantidad de valores atómicos |
| -------- | ------------ | ---------------------------- |
| Entrada  | currentBoxes | B                            |
| Auxiliar | result       | B                            |
| Salida   | nueva lista  | B                            |

Total proporcional a B

**Espacio total: O(B)**

---


## 6. `reconstructPath(GameState finalState)`

### Complejidad Temporal: O(L)

Recorre la cadena de padres desde el estado final
hasta el estado inicial.

Posteriormente invierte el camino utilizando una pila.

| Caso          | Comportamiento             | Complejidad |
| ------------- | -------------------------- | ----------- |
| Mejor caso    | Solución vacía             | O(1)        |
| Caso promedio | Solución de longitud media | O(L)        |
| Peor caso     | Solución larga             | O(L)        |

### Complejidad Espacial: O(L)

Utiliza una pila auxiliar y una lista para almacenar
la solución.

| Tipo     | Variable      | Cantidad de valores atómicos |
| -------- | ------------- | ---------------------------- |
| Entrada  | finalState    | 1                            |
| Auxiliar | reversedStack | L                            |
| Salida   | path          | L                            |

Total proporcional a L

**Espacio total: O(L)**

---

## 7. `boxesToPositionArray(LinkedList<Box> boxes)`

### Complejidad Temporal: O(B)

Recorre todas las cajas y construye un arreglo
de posiciones.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Una caja                | O(1)        |
| Caso promedio | Recorre todas las cajas | O(B)        |
| Peor caso     | Recorre todas las cajas | O(B)        |

### Complejidad Espacial: O(B)

Se crea un arreglo con una posición por cada caja.

| Tipo     | Variable   | Cantidad de valores atómicos |
| -------- | ---------- | ---------------------------- |
| Entrada  | boxes      | B                            |
| Auxiliar | result     | B                            |
| Salida   | Position[] | B                            |

Total proporcional a B

**Espacio total: O(B)**

---

## 8. `buildInitialState(Game game)`

### Complejidad Temporal: O(B)

Recorre todas las cajas del tablero para construir
el estado inicial.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Una sola caja           | O(1)        |
| Caso promedio | Recorre todas las cajas | O(B)        |
| Peor caso     | Recorre todas las cajas | O(B)        |

### Complejidad Espacial: O(B)

Se crea una copia completa de las cajas existentes.

| Tipo     | Variable          | Cantidad de valores atómicos |
| -------- | ----------------- | ---------------------------- |
| Entrada  | game              | 1                            |
| Auxiliar | initialBoxes      | B                            |
| Salida   | GameState inicial | B                            |

Total proporcional a B

**Espacio total: O(B)**

---

## 9. `loadGoalPositions()`

### Complejidad Temporal: O(W × H)

El tablero se recorre dos veces:

1. Contar goals.
2. Guardar posiciones.

Cada recorrido inspecciona todas las celdas.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Tablero pequeño         | O(W × H)    |
| Caso promedio | Recorre todo el tablero | O(W × H)    |
| Peor caso     | Recorre todo el tablero | O(W × H)    |

### Complejidad Espacial: O(G)

Se almacena un arreglo con todas las posiciones objetivo.

| Tipo     | Variable         | Cantidad de valores atómicos |
| -------- | ---------------- | ---------------------------- |
| Entrada  | board            | 1                            |
| Auxiliar | goalPositions    | G                            |
| Auxiliar | goalCount        | 1                            |
| Salida   | arreglo de goals | G                            |

Total proporcional a G

**Espacio total: O(G)**

---

# Resumen General — DFSAuto

| Método                   | Temporal             | Espacial |
| ------------------------ | -------------------- | -------- |
| `solve()`                | O((V + E) · (B² + D)) | O(V)     |
| `getDepth()`             | O(D)                 | O(1)     |
| `applyMove()`            | O(B)                 | O(B)     |
| `findBoxAt()`            | O(B)                 | O(1)     |
| `buildBoxes()`           | O(B)                 | O(B)     |
| `reconstructPath()`      | O(L)                 | O(L)     |
| `boxesToPositionArray()` | O(B)                 | O(B)     |
| `buildInitialState()`    | O(B)                 | O(B)     |
| `loadGoalPositions()`    | O(W × H)             | O(G)     |

# Análisis de Complejidad — FloydWarshall

> Para el análisis se usa:
>
> * **V** = cantidad de nodos del grafo.
> * **W** = ancho del tablero.
> * **H** = alto del tablero.
> * **B** = cantidad de cajas.
> * **G** = cantidad de objetivos.
>
> Donde:
>
> V = W × H

---

## 1. `FloydWarshall(Board board)`

### Complejidad Temporal: O(V³)

El constructor realiza tres etapas:

* Inicializar la matriz de distancias.
* Construir las conexiones entre celdas caminables.
* Ejecutar el algoritmo Floyd-Warshall.

La parte dominante corresponde a los tres ciclos anidados:

```java
for (int k = 0; k < V; k++)
    for (int i = 0; i < V; i++)
        for (int j = 0; j < V; j++)
```

Por lo tanto, el costo total es O(V³).

| Caso          | Comportamiento                  | Complejidad |
| ------------- | ------------------------------- | ----------- |
| Mejor caso    | Tablero pequeño                 | O(V³)       |
| Caso promedio | Ejecuta Floyd-Warshall completo | O(V³)       |
| Peor caso     | Ejecuta Floyd-Warshall completo | O(V³)       |

### Complejidad Espacial: O(V²)

La matriz de distancias almacena una distancia para cada par de nodos.

| Tipo     | Variable | Cantidad de valores atómicos |
| -------- | -------- | ---------------------------- |
| Entrada  | board    | 1                            |
| Auxiliar | dist     | V²                           |
| Auxiliar | rows     | 1                            |
| Auxiliar | cols     | 1                            |
| Salida   | matriz   | V²                           |

Total proporcional a V²

**Espacio total: O(V²)**

---

## 2. `getDistance(Position from, Position to)`

### Complejidad Temporal: O(1)

El método únicamente calcula los índices correspondientes
y consulta una posición de la matriz.

| Caso          | Comportamiento | Complejidad |
| ------------- | -------------- | ----------- |
| Mejor caso    | Acceso directo | O(1)        |
| Caso promedio | Acceso directo | O(1)        |
| Peor caso     | Acceso directo | O(1)        |

### Complejidad Espacial: O(1)

No crea estructuras auxiliares.

| Tipo     | Variable  | Cantidad de valores atómicos |
| -------- | --------- | ---------------------------- |
| Entrada  | from, to  | 2                            |
| Auxiliar | idFrom    | 1                            |
| Auxiliar | idTo      | 1                            |
| Salida   | distancia | 1                            |

Total: 5 valores

**Espacio total: O(1)**

---

## 3. `getDistance(int x1, int y1, int x2, int y2)`

### Complejidad Temporal: O(1)

Realiza operaciones aritméticas simples y consulta
una posición de la matriz.

| Caso          | Comportamiento | Complejidad |
| ------------- | -------------- | ----------- |
| Mejor caso    | Acceso directo | O(1)        |
| Caso promedio | Acceso directo | O(1)        |
| Peor caso     | Acceso directo | O(1)        |

### Complejidad Espacial: O(1)

No crea estructuras auxiliares.

| Tipo     | Variable    | Cantidad de valores atómicos |
| -------- | ----------- | ---------------------------- |
| Entrada  | x1,y1,x2,y2 | 4                            |
| Auxiliar | idFrom      | 1                            |
| Auxiliar | idTo        | 1                            |
| Salida   | distancia   | 1                            |

Total: 7 valores

**Espacio total: O(1)**

---

## 4. `computeHeuristic(Position[] boxPositions, int boxCount, Position[] goalPositions, int goalCount)`

### Complejidad Temporal: O(B × G)

Para cada caja se evalúan todos los objetivos posibles
buscando la distancia mínima.

Las consultas a `getDistance()` son O(1) porque utilizan
la matriz precalculada por Floyd-Warshall.

| Caso          | Comportamiento                      | Complejidad |
| ------------- | ----------------------------------- | ----------- |
| Mejor caso    | Una caja y un objetivo              | O(1)        |
| Caso promedio | Recorre cajas y objetivos           | O(B × G)    |
| Peor caso     | Recorre todas las cajas y objetivos | O(B × G)    |

### Complejidad Espacial: O(1)

Solo utiliza variables auxiliares constantes.

| Tipo     | Variable      | Cantidad de valores atómicos |
| -------- | ------------- | ---------------------------- |
| Entrada  | boxPositions  | B                            |
| Entrada  | goalPositions | G                            |
| Auxiliar | total         | 1                            |
| Auxiliar | minDist       | 1                            |
| Auxiliar | d             | 1                            |
| Salida   | int           | 1                            |

Total constante

**Espacio total: O(1)**

---

## 5. `getDistMatrix()`

### Complejidad Temporal: O(1)

Retorna la referencia de la matriz ya construida.

| Caso          | Comportamiento  | Complejidad |
| ------------- | --------------- | ----------- |
| Mejor caso    | Retorno directo | O(1)        |
| Caso promedio | Retorno directo | O(1)        |
| Peor caso     | Retorno directo | O(1)        |

### Complejidad Espacial: O(1)

No crea estructuras nuevas.

| Tipo    | Variable | Cantidad de valores atómicos |
| ------- | -------- | ---------------------------- |
| Entrada | ——       | 0                            |
| Salida  | dist     | 1                            |

**Espacio total: O(1)**

---

# Resumen General — FloydWarshall

| Método                            | Temporal | Espacial |
| --------------------------------- | -------- | -------- |
| `FloydWarshall()`                 | O(V³)    | O(V²)    |
| `getDistance(Position, Position)` | O(1)     | O(1)     |
| `getDistance(int,int,int,int)`    | O(1)     | O(1)     |
| `computeHeuristic()`              | O(B × G) | O(1)     |
| `getDistMatrix()`                 | O(1)     | O(1)     |

# Análisis de Complejidad — Dijkstra

> Para el análisis se usa:
>
> * **V** = cantidad de nodos del tablero (**V = width × height**).
> * **B** = cantidad de cajas.
> * **G** = cantidad de objetivos.

---

## 1. `processNeighbor(int currentId, int neighborId, int[] dist, boolean[] visited)`

### Complejidad Temporal: O(1)

El método únicamente realiza comparaciones y asignaciones sobre posiciones específicas de los arreglos.

| Caso          | Comportamiento            | Complejidad |
| ------------- | ------------------------- | ----------- |
| Mejor caso    | El vecino ya fue visitado | O(1)        |
| Caso promedio | Actualiza la distancia    | O(1)        |
| Peor caso     | Actualiza la distancia    | O(1)        |

### Complejidad Espacial: O(1)

No crea estructuras auxiliares.

| Tipo     | Variable    | Cantidad de valores atómicos |
| -------- | ----------- | ---------------------------- |
| Entrada  | currentId   | 1                            |
| Entrada  | neighborId  | 1                            |
| Entrada  | dist        | V                            |
| Entrada  | visited     | V                            |
| Auxiliar | newDistance | 1                            |
| Salida   | ——          | 0                            |

Total constante

**Espacio total: O(1)**

---

## 2. `getMinDistanceNode(int[] dist, boolean[] visited)`

### Complejidad Temporal: O(V)

Recorre completamente el arreglo de distancias para encontrar el nodo no visitado con menor valor.

| Caso          | Comportamiento          | Complejidad |
| ------------- | ----------------------- | ----------- |
| Mejor caso    | Recorre todo el arreglo | O(V)        |
| Caso promedio | Recorre todo el arreglo | O(V)        |
| Peor caso     | Recorre todo el arreglo | O(V)        |

### Complejidad Espacial: O(1)

No crea estructuras auxiliares.

| Tipo     | Variable    | Cantidad de valores atómicos |
| -------- | ----------- | ---------------------------- |
| Entrada  | dist        | V                            |
| Entrada  | visited     | V                            |
| Auxiliar | minDistance | 1                            |
| Auxiliar | minNode     | 1                            |
| Salida   | índice      | 1                            |

Total constante

**Espacio total: O(1)**

---

## 3. `shortestDistance(Position start, Position goal)`

### Complejidad Temporal: O(V²)

El método implementa Dijkstra utilizando arreglos simples.

En cada iteración se ejecuta:

```java
getMinDistanceNode(dist, visited)
```

que cuesta O(V).

El ciclo principal puede ejecutarse hasta V veces.

Por tanto:

```text
V × O(V) = O(V²)
```

Las verificaciones de vecinos son constantes porque cada celda tiene como máximo 4 vecinos.

| Caso          | Comportamiento                      | Complejidad |
| ------------- | ----------------------------------- | ----------- |
| Mejor caso    | Origen y destino coinciden          | O(1)        |
| Caso promedio | Explora parte del tablero           | O(V²)       |
| Peor caso     | Explora todos los nodos alcanzables | O(V²)       |

### Complejidad Espacial: O(V)

Se crean dos arreglos de tamaño V:

```java
int[] dist
boolean[] visited
```

| Tipo     | Variable    | Cantidad de valores atómicos |
| -------- | ----------- | ---------------------------- |
| Entrada  | start, goal | 2                            |
| Auxiliar | dist        | V                            |
| Auxiliar | visited     | V                            |
| Auxiliar | current     | 1                            |
| Salida   | distancia   | 1                            |

Total proporcional a V

**Espacio total: O(V)**
---

## 4. `getDistance(Position from, Position to)`

### Complejidad Temporal: O(V²)

El método delega completamente en:

```java
shortestDistance(from, to)
```

Por lo tanto hereda exactamente su complejidad.

| Caso          | Comportamiento                      | Complejidad |
| ------------- | ----------------------------------- | ----------- |
| Mejor caso    | El origen coincide con el destino   | O(1)        |
| Caso promedio | Explora parte del tablero           | O(V²)       |
| Peor caso     | Explora todos los nodos alcanzables | O(V²)       |

### Complejidad Espacial: O(V)

Hereda los arreglos creados por `shortestDistance()`.

| Tipo     | Variable  | Cantidad de valores atómicos |
| -------- | --------- | ---------------------------- |
| Entrada  | from, to  | 2                            |
| Auxiliar | dist      | V                            |
| Auxiliar | visited   | V                            |
| Salida   | distancia | 1                            |

Total proporcional a V

**Espacio total: O(V)**

---

## 5. `getDistance(int x1, int y1, int x2, int y2)`

### Complejidad Temporal: O(V²)

El método crea dos objetos `Position` y delega completamente en `shortestDistance()`.

| Caso          | Comportamiento                      | Complejidad |
| ------------- | ----------------------------------- | ----------- |
| Mejor caso    | El origen coincide con el destino   | O(1)        |
| Caso promedio | Explora parte del tablero           | O(V²)       |
| Peor caso     | Explora todos los nodos alcanzables | O(V²)       |

### Complejidad Espacial: O(V)

Hereda los arreglos creados por `shortestDistance()`.

| Tipo     | Variable         | Cantidad de valores atómicos |
| -------- | ---------------- | ---------------------------- |
| Entrada  | x1,y1,x2,y2      | 4                            |
| Auxiliar | Position origen  | 1                            |
| Auxiliar | Position destino | 1                            |
| Auxiliar | dist             | V                            |
| Auxiliar | visited          | V                            |
| Salida   | distancia        | 1                            |

Total proporcional a V

**Espacio total: O(V)**

---

## 6. `computeHeuristic(Position[] boxPositions, int boxCount, Position[] goalPosition, int goalCount)`

### Complejidad Temporal: O(B × G × V²)

Para cada caja se recorren todos los objetivos.

Para cada combinación caja-objetivo se ejecuta:

```java
shortestDistance(...)
```

que cuesta O(V²).

Por tanto:

```text
B × G × O(V²)
=
O(B × G × V²)
```

| Caso          | Comportamiento                      | Complejidad   |
| ------------- | ----------------------------------- | ------------- |
| Mejor caso    | Una caja y un objetivo              | O(V²)         |
| Caso promedio | Recorre cajas y objetivos           | O(B × G × V²) |
| Peor caso     | Recorre todas las cajas y objetivos | O(B × G × V²) |

### Complejidad Espacial: O(1)

Solo utiliza variables auxiliares constantes.

| Tipo     | Variable     | Cantidad de valores atómicos |
| -------- | ------------ | ---------------------------- |
| Entrada  | boxPositions | B                            |
| Entrada  | goalPosition | G                            |
| Auxiliar | total        | 1                            |
| Auxiliar | minDist      | 1                            |
| Auxiliar | d            | 1                            |
| Salida   | int          | 1                            |

Total constante

**Espacio total: O(1)**

---

# Resumen General — Dijkstra

| Método                            | Temporal      | Espacial |
| --------------------------------- | ------------- | -------- |
| `processNeighbor()`               | O(1)          | O(1)     |
| `getMinDistanceNode()`            | O(V)          | O(1)     |
| `shortestDistance()`              | O(V²)         | O(V)     |
| `getDistance(Position, Position)` | O(V²)         | O(V)     |
| `getDistance(int,int,int,int)`    | O(V²)         | O(V)     |
| `computeHeuristic()`              | O(B × G × V²) | O(1)     |

Donde:

* **V** = cantidad de celdas del tablero (width × height)
* **B** = cantidad de cajas
* **G** = cantidad de objetivos






