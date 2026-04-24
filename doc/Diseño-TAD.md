# Diseño de Tipos Abstractos de Datos (TAD)
## Proyecto: Sokoban

---

## Tabla de contenido

1. [TAD Pila (Stack)](#1-tad-pila-stack)
2. [TAD Cola (Queue)](#2-tad-cola-queue)
3. [TAD Cola de Prioridad (Priority Queue)](#3-tad-cola-de-prioridad-priority-queue)
4. [TAD Tabla Hash (Hash Table)](#4-tad-tabla-hash-hash-table)

---

## 1. TAD Pila (Stack)

### Descripción
Estructura lineal de tipo LIFO (Last In, First Out). En el contexto de Sokoban almacena estados completos del tablero (`GameState`) para implementar la función **Undo**, permitiendo al jugador regresar exactamente un movimiento atrás.

### Representación visual

```
┌─────────────────────────────┐
│         TAD Pila            │
│                             │
│  ┌───────────────────────┐  │
│  │  estado_N   ← top()   │  │ 
│  ├───────────────────────┤  │
│  │  estado_N-1           │  │
│  ├───────────────────────┤  │
│  │  estado_N-2           │  │
│  ├───────────────────────┤  │
│  │  ...                  │  │
│  ├───────────────────────┤  │
│  │  estado_1  ← fondo    │  │
│  └───────────────────────┘  │
│                             │
│  GameState = posJugador     │
│           + posiciones[]    │
└─────────────────────────────┘
```

> `{ inv: size >= 0 }`

### Operaciones Primitivas

| Operación    | Signatura                         | Descripción                              |
|-------------|-----------------------------------|------------------------------------------|
| `createStack` | → Stack                          | Crea una pila vacía                       |
| `push`       | Stack × GameState → Stack        | Inserta un estado en el tope de la pila  |
| `pop`        | Stack → Stack                    | Elimina el estado del tope               |
| `top`        | Stack → GameState                | Retorna el estado del tope sin eliminarlo|
| `isEmpty`    | Stack → Boolean                  | Verifica si la pila está vacía           |
| `size`       | Stack → Integer                  | Retorna la cantidad de elementos         |

### Precondiciones y Postcondiciones

- **Pre `pop` / `top`:** `¬isEmpty(s)`
- **Post `push`:** `top(push(s, e)) = e`
- **Post `pop`:** `push(pop(s), top(s)) = s` *(el estado se preserva)*

### Uso en el proyecto

- Función **Undo**: cada vez que el jugador realiza un movimiento, se hace `push(estadoActual)`. Al presionar Undo, se hace `pop()` y se restaura `top()`.
- Implementada con **lista enlazada propia** (no `java.util.Stack`).



---

## 2. TAD Cola (Queue)

### Descripción
Estructura lineal de tipo FIFO (First In, First Out). En Sokoban funciona como **input buffer**: guarda los comandos de teclado del jugador en orden de llegada, evitando perder entradas cuando el usuario presiona teclas rápidamente.

### Representación visual

```
┌──────────────────────────────────────────────┐
│               TAD Cola                       │
│                                              │
│  enqueue →  [UP][DOWN][LEFT][RIGHT][...] → dequeue  │
│              ↑                         ↑    │
│            front()                   rear   │
│                                              │
│  Command ∈ { UP, DOWN, LEFT, RIGHT }         │
└──────────────────────────────────────────────┘
```

> `{ inv: size >= 0 }`

### Operaciones Primitivas

| Operación     | Signatura                         | Descripción                                    |
|--------------|-----------------------------------|------------------------------------------------|
| `createQueue` | → Queue                          | Crea una cola vacía                             |
| `enqueue`    | Queue × Command → Queue          | Inserta un comando al final de la cola          |
| `dequeue`    | Queue → Queue                    | Elimina el comando del frente de la cola        |
| `front`      | Queue → Command                  | Retorna el comando del frente sin eliminarlo    |
| `isEmpty`    | Queue → Boolean                  | Verifica si la cola está vacía                  |
| `size`       | Queue → Integer                  | Retorna la cantidad de comandos en espera       |

### Precondiciones y Postcondiciones

- **Pre `dequeue` / `front`:** `¬isEmpty(q)`
- **Post `enqueue`:** `¬isEmpty(enqueue(q, c))`
- **Post `dequeue`:** si `size(q) = 1`, entonces `isEmpty(dequeue(q))`

### Uso en el proyecto

- **Input buffer**: los comandos del teclado se insertan con `enqueue` y el motor del juego los consume con `dequeue` en cada ciclo de actualización.
- También usada en el algoritmo **BFS** para explorar estados del tablero (escenario 3).
- Implementada con **lista enlazada propia** (no `java.util.LinkedList`).


---

## 3. TAD Cola de Prioridad (Priority Queue)

### Descripción
Estructura que organiza elementos según una prioridad definida. En Sokoban implementa el **leaderboard por nivel**: mantiene los mejores K resultados de partidas (menor número de movimientos = mayor prioridad), actualizándose eficientemente cuando llegan nuevas partidas.

### Representación visual

```
┌──────────────────────────────────────────┐
│        TAD Cola de Prioridad             │
│                                          │
│  #1  │  12 mov · 5 push · 0:42  │ ←peek() │
│  #2  │  15 mov · 7 push · 1:03  │         │
│  #3  │  18 mov · 9 push · 1:21  │         │
│  ...                                     │
│  #K  │  peor resultado en top-K │         │
│                                          │
│  Score = { moves, pushes, time, player } │
│  Prioridad: menor moves → mayor prio     │
└──────────────────────────────────────────┘
```

> `{ inv: size <= K  ∧  prio(peek()) <= prio(todos los demás) }`

### Operaciones Primitivas

| Operación      | Signatura                          | Descripción                                          |
|---------------|-------------------------------------|------------------------------------------------------|
| `createPQ`    | → PQueue                           | Crea una cola de prioridad vacía                      |
| `insert`      | PQueue × Score → PQueue            | Inserta un resultado manteniendo el orden de prioridad|
| `extractMin`  | PQueue → Score                     | Extrae y retorna el elemento de menor prioridad (peor)|
| `peek`        | PQueue → Score                     | Retorna el mejor resultado sin eliminarlo             |
| `isEmpty`     | PQueue → Boolean                   | Verifica si la cola está vacía                        |
| `size`        | PQueue → Integer                   | Retorna la cantidad de resultados almacenados         |

### Precondiciones y Postcondiciones

- **Pre `extractMin` / `peek`:** `¬isEmpty(pq)`
- **Post `insert` (cuando `size = K`):** si `prio(nuevo) < prio(peor)`, se reemplaza el peor
- **Invariante estricto:** `prio(peek()) ≤ prio(x)` para todo `x` en la cola

### Uso en el proyecto

- **Leaderboard**: al finalizar cada nivel se llama `insert(nuevoScore)`. Si `size > K`, se llama `extractMin()` para descartar el peor resultado.
- Criterio de prioridad: `moves ASC`, desempate por `pushes ASC`, luego por `time ASC`.
- Implementada con **heap mínimo** sobre lista enlazada propia.



---

## 4. TAD Tabla Hash (Hash Table)

### Descripción
Estructura que mapea claves a valores mediante una función hash. En Sokoban implementa la **tabla de transposición**: almacena los estados del tablero ya visitados durante la búsqueda (BFS/DFS), evitando explorar ciclos en el grafo de estados y reduciendo drásticamente el tiempo de búsqueda.

### Representación visual

```
┌──────────────────────────────────────────┐
│           TAD Tabla Hash                 │
│                                          │
│  hash(state) ↓                           │
│                                          │
│  Índice │ Bucket (lista encadenada)       │
│  ─────────────────────────────────       │
│    0    │  —                             │
│    1    │  estado_A → estado_D           │
│    2    │  estado_C                      │
│    3    │  —                             │
│    4    │  estado_B                      │
│   ...   │  ...                           │
│                                          │
│  State = hash(posJugador + posCajas[])   │
└──────────────────────────────────────────┘
```

> `{ inv: ∀ k: contains(t, k)  ⇒  get(t, k) ≠ null }`

### Operaciones Primitivas

| Operación      | Signatura                                  | Descripción                                      |
|---------------|---------------------------------------------|--------------------------------------------------|
| `createTable` | → HashTable                                | Crea una tabla hash vacía                         |
| `put`         | HashTable × State × Boolean → HashTable    | Inserta o actualiza un estado en la tabla         |
| `get`         | HashTable × State → Boolean                | Retorna el valor asociado a un estado             |
| `contains`    | HashTable × State → Boolean                | Verifica si un estado ya fue visitado             |
| `remove`      | HashTable × State → HashTable              | Elimina un estado de la tabla                     |
| `size`        | HashTable → Integer                        | Retorna la cantidad de estados almacenados        |

### Precondiciones y Postcondiciones

- **Pre `get` / `remove`:** `contains(t, key)`
- **Post `put`:** `contains(put(t, k, v), k) = true`
- **Post `remove`:** `contains(remove(t, k), k) = false`


### Uso en el proyecto

- **Tabla de transposición** en BFS y DFS: antes de encolar un estado, se verifica `contains(visitados, estado)`. Si ya fue visitado, se descarta.
- Clave: representación canónica del estado = `(row_jugador, col_jugador, [(row_caja1, col_caja1), ..., (row_cajaN, col_cajaN)])` con las cajas ordenadas.


---

## Resumen de TADs del proyecto

| TAD              | Uso en Sokoban                        | 
|-----------------|---------------------------------------|
| Pila            | Función Undo                          |
| Cola            | Input buffer de teclado / BFS         |
| Cola Prioridad  | Leaderboard top-K por nivel           |
| Tabla Hash      | Tabla de transposición (BFS/DFS)      |
