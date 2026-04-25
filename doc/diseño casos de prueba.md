# Diseño de Casos de Prueba - Sokoban

## Configuración de los Escenarios

| Nombre | Clase | Escenario |
|---|---|---|
| setupStage1 | GameTest | Tablero 5x5 con bordes de muro, jugador ubicado en (2,2) y las cuatro celdas adyacentes libres (sin cajas ni objetivos). |
| setupStage2 | GameTest | Tablero 5x5 con bordes de muro, jugador en (2,2) y un muro en la celda superior (2,1). |
| setupStage3 | GameTest | Tablero 5x5 con bordes de muro, jugador en (2,2), una caja en (3,2) y la celda (4,2) libre. |
| setupStage4 | GameTest | Tablero 5x5 con bordes de muro, jugador en (2,2), una caja en (3,2) y un muro en (4,2). |
| setupStage5 | GameTest | Tablero 5x5 con bordes de muro, jugador en (2,2), una caja en (3,2) y otra caja en (4,2). |
| setupStage6 | GameTest | Nivel donde todas las cajas están ubicadas sobre celdas objetivo. |
| setupStage7 | GameTest | Nivel donde al menos una caja no está sobre una celda objetivo. |
| setupStage8 | BoardTest | Tablero con distintas celdas: una libre en (2,2), un muro en (0,0) y una celda ocupada por una caja en (3,2). |
| setupStage9 | CellTest | Celdas individuales de tipo FLOOR, GOAL y WALL, sin presencia de cajas. |
| setupStage10 | LevelTest | Nivel con al menos una caja fuera de su objetivo correspondiente. |
| setupStage11 | LevelTest | Nivel donde todas las cajas coinciden con las posiciones objetivo. |
| setupStage12 | PlayerTest | Jugador inicializado en una posición conocida (2,2). |
| setupStage13 | PositionTest | Dos posiciones: una igual (2,2) vs (2,2) y otra diferente (2,2) vs (3,2). |
| setupStage14 | GameStatsTest | Estadísticas inicializadas en cero (movimientos y empujes) y luego modificadas. |
| setupStage15 | PriorityQueueTest | Cola vacía con capacidad 5. |
| setupStage16 | PriorityQueueTest | Cola con tres resultados insertados: 10 movimientos, 5 movimientos y 8 movimientos. |
| setupStage17 | PriorityQueueTest | Cola llena (capacidad 3) con resultados de 5, 8 y 12 movimientos. |
| setupStage18 | PriorityQueueTest | Cola llena (capacidad 3) con resultados de 5, 7 y 10 movimientos. |
| setupStage19 | GameResultTest | GameResult con usuario "juan123", nivel 1, 10 movimientos, 3 empujes y 120 segundos. |
| setupStage20 | LeaderboardTest | Leaderboard vacío para el nivel 1. |
| setupStage21 | LeaderboardTest | Leaderboard con 3 resultados ya agregados: 10, 15 y 20 movimientos. |
| setupStage22 | LeaderboardTest | Leaderboard lleno (top 5) con resultados de 5, 7, 8, 9 y 10 movimientos. |
| setupStage23  | QueueTest      | Cola con tres elementos encolados en orden: 10, 20, 30. El frente de la cola es 10. |
| setupStage24  | QueueTest      | Cola vacía, sin ningún elemento. |
| setupStage25  | HashTableTest  | Tabla hash con tres entradas: "nivel1"=100, "nivel2"=200, "nivel3"=300. Capacidad 10. |
| setupStage26  | HashTableTest  | Tabla hash vacía con capacidad 10, sin ninguna entrada. |


---

## Diseño de Casos de Prueba

### Objetivo de la Prueba:
Validar que el jugador pueda moverse correctamente a celdas libres y que los movimientos inválidos sean rechazados.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Game | movePlayer(direction : String) | setupStage1 | "UP" | El jugador se mueve de (2,2) a (2,1) y se incrementa el contador de movimientos. |
| Game | movePlayer(direction : String) | setupStage1 | "DOWN" | El jugador se mueve de (2,2) a (2,3) y se incrementa el contador de movimientos. |
| Game | movePlayer(direction : String) | setupStage1 | "LEFT" | El jugador se mueve de (2,2) a (1,2) y se incrementa el contador de movimientos. |
| Game | movePlayer(direction : String) | setupStage1 | "RIGHT" | El jugador se mueve de (2,2) a (3,2) y se incrementa el contador de movimientos. |
| Game | movePlayer(direction : String) | setupStage2 | "UP" | El jugador permanece en (2,2) porque la celda destino es un muro y no se incrementa el contador de movimientos. |
| Game | movePlayer(direction : String) | setupStage4 | "RIGHT" | El jugador permanece en (2,2) porque la caja no puede moverse (muro detrás) y no se modifica el estado. |
| Game | movePlayer(direction : String) | setupStage5 | "RIGHT" | El jugador permanece en (2,2) porque la caja está bloqueada por otra caja. |

---

### Objetivo de la Prueba:
Validar que el empuje de cajas solo ocurra cuando la celda siguiente esté libre.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Game | movePlayer(direction : String) | setupStage3 | "RIGHT" | El jugador se mueve a (3,2) y la caja se desplaza de (3,2) a (4,2). |
| Game | pushBox(box : Box, direction : String) | setupStage3 | caja, "RIGHT" | La caja se mueve correctamente a la celda libre siguiente. |
| Game | pushBox(box : Box, direction : String) | setupStage4 | caja, "RIGHT" | La caja no se mueve porque la celda destino es un muro. |
| Game | pushBox(box : Box, direction : String) | setupStage5 | caja, "RIGHT" | La caja no se mueve porque la celda destino está ocupada por otra caja. |

---

### Objetivo de la Prueba:
Validar que el tablero identifique correctamente celdas libres y bloqueadas.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Board | isFree(x : int, y : int) | setupStage8 | (2,2) | Retorna true porque la celda está libre. |
| Board | isFree(x : int, y : int) | setupStage8 | (0,0) | Retorna false porque la celda es un muro. |
| Board | isFree(x : int, y : int) | setupStage8 | (3,2) | Retorna false porque la celda contiene una caja. |

---

### Objetivo de la Prueba:
Validar que las celdas identifiquen correctamente si son caminables o metas.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Cell | isWalkable() | setupStage9 | No aplica | Retorna true para celdas FLOOR y GOAL. |
| Cell | isWalkable() | setupStage9 | No aplica | Retorna false para celdas WALL. |
| Cell | isGoal() | setupStage9 | No aplica | Retorna true cuando la celda es GOAL. |
| Cell | isGoal() | setupStage9 | No aplica | Retorna false cuando la celda es FLOOR. |

---

### Objetivo de la Prueba:
Validar que la condición de victoria se detecte correctamente.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Game | checkWin() | setupStage6 | No aplica | Retorna true porque todas las cajas están en objetivos. |
| Game | checkWin() | setupStage7 | No aplica | Retorna false porque al menos una caja no está en objetivo. |
| Game | updateGame() | setupStage6 | No aplica | El juego cambia su estado a finalizado. |
| Game | updateGame() | setupStage7 | No aplica | El juego continúa sin finalizar. |
| Level | isCompleted() | setupStage11 | No aplica | Retorna true porque el nivel está completo. |
| Level | isCompleted() | setupStage10 | No aplica | Retorna false porque el nivel no está completo. |

---

### Objetivo de la Prueba:
Validar la correcta gestión de la posición del jugador.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Player | getPosition() | setupStage12 | No aplica | Retorna la posición actual del jugador (2,2). |
| Player | setPosition(pos : Position) | setupStage12 | (3,2) | La posición del jugador cambia correctamente a (3,2). |

---

### Objetivo de la Prueba:
Validar la comparación de posiciones.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Position | isSamePosition(other) | setupStage13 | posición (2,2) vs (2,2) | Retorna true porque ambas posiciones son iguales. |
| Position | isSamePosition(other) | setupStage13 | posición (2,2) vs (3,2) | Retorna false porque las posiciones son diferentes. |

---

### Objetivo de la Prueba:
Validar la gestión de posición de las cajas.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Box | getPosition() | setupStage3 | No aplica | Retorna la posición actual de la caja. |
| Box | setPosition(pos : Position) | setupStage3 | (4,2) | La posición de la caja cambia correctamente a (4,2). |

---

### Objetivo de la Prueba:
Validar el registro de estadísticas del juego.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| GameStats | addMove() | setupStage14 | No aplica | El contador de movimientos aumenta en 1. |
| GameStats | addPush() | setupStage14 | No aplica | El contador de empujes aumenta en 1. |
| GameStats | reset() | setupStage14 | No aplica | Los contadores se reinician a cero. |

### Objetivo: Validar que `insert()` agrega correctamente y mantiene la propiedad del Min-Heap.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| PriorityQueue | insert() | setupStage15 | GameResult con 10 movimientos | cantidadActual=1, peekMin() retorna el resultado insertado |
| PriorityQueue | insert() | setupStage16 | GameResult con 3 movimientos | cantidadActual=4, peekMin() retorna el resultado de 3 movimientos |
| PriorityQueue | insert() | setupStage17 | GameResult con 6 movimientos | cantidadActual sigue en 3, el resultado se ignora silenciosamente |

---

### Objetivo: Validar que `extractMin()` extrae el mejor resultado y reorganiza el heap correctamente.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| PriorityQueue | extractMin() | setupStage16 | — | Retorna el resultado de 5 movimientos, cantidadActual=2 |
| PriorityQueue | extractMin() | setupStage15 | — | Retorna null, la cola sigue vacía |
| PriorityQueue | extractMin() | setupStage16 | Extraer los 3 elementos uno a uno | Salen en orden: 5, 8, 10 movimientos. Cola vacía al final |

---

### Objetivo: Validar que `tryAdd()` solo permite entrar al top-K resultados que lo merezcan.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| PriorityQueue | tryAdd() | setupStage15 | GameResult con 10 movimientos | cantidadActual=1, resultado insertado directamente |
| PriorityQueue | tryAdd() | setupStage18 | GameResult con 3 movimientos | El resultado de 10 mov es reemplazado, cantidadActual sigue en 3 |
| PriorityQueue | tryAdd() | setupStage18 | GameResult con 25 movimientos | La cola no se modifica, cantidadActual sigue en 3 |

---

### Objetivo: Validar que `GameResult` almacena correctamente los datos y los expone bien.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| GameResult | getPlayerName() | setupStage19 | — | Retorna "juan123" |
| GameResult | getMoves() | setupStage19 | — | Retorna 10 |
| GameResult | compareTo() | setupStage19 | GameResult con 5 movimientos | Retorna positivo porque 10 > 5 |

---

### Objetivo: Validar que `Leaderboard` gestiona correctamente el top-K de resultados.

| Clase | Método | Escenario | Valores de Entrada | Resultado esperado |
|---|---|---|---|---|
| Leaderboard | addResult() | setupStage20 | GameResult con 10 movimientos | getMejoresResultados().getSize()==1 |
| Leaderboard | addResult() | setupStage22 | GameResult con 3 movimientos | El peor resultado es reemplazado, tamaño sigue en 5 |
| Leaderboard | addResult() | setupStage22 | GameResult con 50 movimientos | La cola no se modifica, tamaño sigue en 5 |

---

### Objetivo de la Prueba:
Validar que `enqueue()` agrega elementos correctamente y que `dequeue()` los retorna en orden FIFO.

| Clase  | Método            | Escenario    | Valores de Entrada        | Resultado esperado |
|--------|-------------------|--------------|---------------------------|--------------------|
| Queue  | enqueue(data)     | setupStage24  | 10, luego 20, luego 30    | size()==3, peek()==10 |
| Queue  | dequeue()         | setupStage23  | —                         | Retorna 10, luego 20, luego 30 en ese orden. Cola vacía al final. |
| Queue  | dequeue()         | setupStage24  | —                         | Retorna null. La cola sigue vacía. |

---

### Objetivo de la Prueba:
Validar que `peek()` consulta el frente sin modificar la cola.

| Clase  | Método    | Escenario    | Valores de Entrada | Resultado esperado |
|--------|-----------|--------------|--------------------|--------------------|
| Queue  | peek()    | setupStage23  | —                  | Retorna 10 y size() sigue siendo 3. |
| Queue  | peek()    | setupStage24  | —                  | Retorna null. |

---

### Objetivo de la Prueba:
Validar que `clear()` vacía completamente la cola.

| Clase  | Método   | Escenario    | Valores de Entrada | Resultado esperado |
|--------|----------|--------------|--------------------|--------------------|
| Queue  | clear()  | setupStage23  | —                  | isEmpty()==true, size()==0, peek()==null. |

---

### Objetivo de la Prueba:
Validar que `put()` inserta correctamente y actualiza el valor cuando la clave ya existe.

| Clase      | Método           | Escenario    | Valores de Entrada   | Resultado esperado |
|------------|------------------|--------------|----------------------|--------------------|
| HashTable  | put(key, value)  | setupStage26  | "nivel1", 100        | size()==1, get("nivel1")==100. |
| HashTable  | put(key, value)  | setupStage25  | "nivel1", 999        | get("nivel1")==999, size() sigue en 3 sin duplicados. |

---

### Objetivo de la Prueba:
Validar que `get()` retorna el valor correcto o null si la clave no existe.

| Clase      | Método     | Escenario    | Valores de Entrada | Resultado esperado |
|------------|------------|--------------|--------------------|--------------------|
| HashTable  | get(key)   | setupStage25  | "nivel2"           | Retorna 200. |
| HashTable  | get(key)   | setupStage25  | "noExiste"         | Retorna null. |
| HashTable  | get(key)   | setupStage26  | "nivel1"           | Retorna null. |

---

### Objetivo de la Prueba:
Validar que `remove()` elimina la entrada correcta y actualiza el tamaño.

| Clase      | Método        | Escenario    | Valores de Entrada | Resultado esperado |
|------------|---------------|--------------|--------------------|--------------------|
| HashTable  | remove(key)   | setupStage25  | "nivel2"           | Retorna 200, containsKey("nivel2")==false, size()==2. |
| HashTable  | remove(key)   | setupStage25  | "noExiste"         | Retorna null, size() sigue en 3. |
| HashTable  | remove(key)   | setupStage26  | "nivel1"           | Retorna null, la tabla sigue vacía. |

---

### Objetivo de la Prueba:
Validar que `containsKey()` detecta correctamente la presencia o ausencia de una clave.

| Clase      | Método              | Escenario    | Valores de Entrada | Resultado esperado |
|------------|---------------------|--------------|--------------------|--------------------|
| HashTable  | containsKey(key)    | setupStage25  | "nivel3"           | Retorna true. |
| HashTable  | containsKey(key)    | setupStage25  | "noExiste"         | Retorna false. |
| HashTable  | containsKey(key)    | setupStage26  | "nivel1"           | Retorna false. |

---

### Objetivo de la Prueba:
Validar que `clear()` vacía completamente la tabla hash.

| Clase      | Método    | Escenario    | Valores de Entrada | Resultado esperado |
|------------|-----------|--------------|--------------------|--------------------|
| HashTable  | clear()   | setupStage25  | —                  | isEmpty()==true, size()==0, get("nivel1")==null. |
