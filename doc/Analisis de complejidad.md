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
