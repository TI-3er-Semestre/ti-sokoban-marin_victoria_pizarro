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
