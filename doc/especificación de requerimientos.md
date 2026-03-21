#  Análisis del problema

##  Información general
| Campo                         | Descripción |
|-------------------------------|------------|
| Cliente                       |     empresa desarrolladora de software       |
| Usuario                       |       jugador, estudiantes y usuario registrado     |
| Contexto del problema         |           se necesita desarrollar una aplicacion de escritorio en fx que implemente el juego de sokoban con interfaz grafica, niveles cargados desde JSON, registro de jugadores, segumiento de partidas, implementacion de estrucutras y algoritmos, aplicacion desarrollada bajo principios de software|
| Requerimientos funcionales    | - RF1 - Registrar usuario <br> - RF2 -  cargar niveles desde json <br> - RF3 - guardar partidas <br> - RF4 - Permitir el movimiento del jugador <br> - RF5 - validar movimeintos <br> - RF6 - empujar cajas solo si hay espacios <br> - RF7 - detectar condicion de victoria <br> - RF8 -  mostrar interfaz grafica del juego <br> - RF9 - mostrar un menú principal al usuario <br> - RF10 -  jugar en tres niveles diferentes <br> - RF11 -  pantalla de estadísticas del jugador <br> - RF12 - proporcionar un manual de usuario accesible <br> - RF13 -  mostrar la cantidad de movimeinto relizados <br> - RF14 -  la cantidad de empujes de caja <br> - RF15 -  mostrar el tiempo transcurrido de la partida <br> - RF16 -  Sistema de ranking por nivel <br> - RF17 -  deshacer movimientos <br> - RF18 - Registar entradas del usuario <br> - RF19 - Implementar solucion automatica <br> - RF20 - Mostrar animacion de solucion automatica <br> - RF21 -  mostrar una pantalla de victoria al usuario |
| Requerimientos no funcionales | - RNF1 - Descripción <br> - RNF2 - Descripción <br> - RNF3 - Descripción |
| Requerimientos de proceso     | - RP1 - Descripción <br> - RP2 - Descripción <br> - RP3 - Descripción |

---

#  Requerimiento Funcional RF1 registrar usuario

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Registrar usuario |
| Resumen                |Permitir el registro de un usurario mediante un formulario que incluya nombre, correo electronico, username, avatar y nivel de experiencia |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
| nombre completo     |String             |    no vacio                       |
| correo electronico              | String            |formato vaido de email                           |
| username              |   String          |  unico no vacio                         |
| avatar              |         imagen/stirng    |    opcional o valido                       |
|      nivel de experiencia         |     String        |              principiante, avanzado o experto             |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|        usuario registrado      |       objeto      |    datos del usuario     |
|        mensaje de confirmacio      |       String      |    texto     |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|       el usuario queda registrado y puede ingresar al juego                    |

#  Requerimiento Funcional RF2 Cargar niveles desde JSON

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - cargar niveles desde JSON |
| Resumen                | Permitir la carga de niveles del juego en formato JSON donde se guardaran elementos |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
| archivos JSON              |    Archivo         |      Formato JSON valido                     |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|---------------|------------|-----------|
| bien cargado      |     objeto        |    Estructura del tablero     |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                    el nivel es cargado correctamente       |


#  Requerimiento Funcional RF3 Guardar partidas 

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Guardar partidas |
| Resumen                | Permitir guardar el estado actual de la partida |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|          Estado de la partida     | Objeto            | valido y completo                          |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|     Archivo de guardado         |   Archivo          |     Serializable    |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|       la partida queda almacenada y puede ser cargada posteriormente                    |

#  Requerimiento Funcional RF4 Movimiento del jugador

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF4 - Movimiento del jugador|
| Resumen                | Permitir al jugador desplazarse en cuatros direcciones dentro del tablero |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|   Direccion            |      String       | arriba, abajo, izquierda y derecha                          |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|       nueva psocicion del jugador       |    coordenada         |      (X,y)   |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|        el jugador de posicion en el tablero si el movimiento es valido                   |

#  Requerimiento Funcional RF5 Validar movimientos

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF5 - validar movimeintos |
| Resumen                | Validar que los movimientos del jugador cumplan las reglas del juego. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|   Posicion actual            |       coordenada      |               (x,y) valida            |
|         direccion      |       String      |                 valida          |
|       Esatdo del tablero        |    Objeto         |             valido              |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|        Movimiento valido      | boolean            | true/false        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|          solo permite el movimiento si cumple las reglas del juego                 |

#  Requerimiento Funcional RF6

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre|RF6  empujar cajas|
| Resumen                | Permitir empujar cajas unicamente si la posicion sguiente esta libre |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|  posicion del jugador            |      coordenada       |    (x,y) valida                       |
|           direccion    |   String          |              arriba,abajo,izquierda,derecha             |
|   Estado del tablero            |       objeto      |          valido                 |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|      nueva posicion de caja       | coordenada            |      (x,y)   |
|     movimiento realizado         | Boolean            | true/false        |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|       la caja se mueve si la posicion esta libre, de lo contrario no lo hara |                    |

#  Requerimiento Funcional RF7 detectar condicion de victoria

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF7 - Detectar condicion de victoria |
| Resumen                | Detectar cuando todas las cajas han sido ubicados en ru respectiva posiciones de objetivos |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|          Estado del tablero     |       objeto      |      valido                     |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|    Estado de victoria          |   boolean          |    true/false     |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|      el sistema identifica correctamente si el nivel ha sido completado                     |

#  Requerimiento Funcional RF8 Mostar interfaz grafica del juego

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF8 - Mostrar interfaz grafica |
| Resumen                | Mostrar representacion visual del juego mediante una interfaz grafica 2d. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|      Estado del juego         |      objeto       |               valido            |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|     interfaz renderizada         |        visual     |  pantalla 2d       |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                 el juego se muestra correctamente en pantalla con todos sus elementos visuales          |

#  Requerimiento Funcional RF9 Mostrar menu principal

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Mostrar menu principal |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|  incio del sistema             |     evento        |       validp                    |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|    Menu principal          |  visual           |  pantalla       |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|      el usuario puede acceder a las diferentes opciones del sustema desde el menu principal                     |

#  Requerimiento Funcional RF10 jugar en 3 niveles diferentes

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Niveles de juego|
| Resumen                | permitir al usuario jugar en 3 niveles de dificultad, basico, avanzado y experto |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|       Seleccion de nivel        |     String        |          Basico,avanzado,experto                 |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|        nivel cargado      | objeto            | tablero del nivel        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|             El sistema carga el nivel seleccionado y permite iniciar la partida correspondiente              |

#  Requerimiento Funcional RF11

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF12

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF13

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF14

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF15

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF16

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF17

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF18

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF19

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF20

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |

#  Requerimiento Funcional RF21

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                | Descripción clara, precisa, concisa, verificable y completa del requerimiento. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|               |             |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|              |             |         |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|                           |
