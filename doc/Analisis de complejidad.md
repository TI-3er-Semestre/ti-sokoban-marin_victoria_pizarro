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

### 2 `pop()` 
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
 ### 3 `peek()`
 ### Complejidad Temporal O(1)
