#  Análisis del problema

##  Información general
| Campo                         | Descripción |
|-------------------------------|------------|
| Cliente                       |     empresa desarrolladora de software       |
| Usuario                       |       jugador, estudiantes y usuario registrado     |
| Contexto del problema         |           se necesita desarrollar una aplicacion de escritorio en fx que implemente el juego de sokoban con interfaz grafica, niveles cargados desde JSON, registro de jugadores, segumiento de partidas, implementacion de estrucutras y algoritmos, aplicacion desarrollada bajo principios de software|
| Requerimientos funcionales    | - RF1 - Registrar usuario <br> - RF2 -  cargar niveles desde json <br> - RF3 - guardar partidas <br> - RF4 - Permitir el movimiento del jugador <br> - RF5 - validar movimeintos <br> - RF6 - empujar cajas solo si hay espacios <br> - RF7 - detectar condicion de victoria <br> - RF8 -  mostrar interfaz grafica del juego <br> - RF9 - mostrar un menú principal al usuario <br> - RF10 -  jugar en tres niveles diferentes <br> - RF11 -  pantalla de estadísticas del jugador <br> - RF12 - proporcionar un manual de usuario accesible <br> - RF13 -  mostrar la cantidad de movimeinto relizados <br> - RF14 -  la cantidad de empujes de caja <br> - RF15 -  mostrar el tiempo transcurrido de la partida <br> - RF16 -  Sistema de ranking por nivel <br> - RF17 -  deshacer movimientos <br> - RF18 - Registar entradas del usuario <br> - RF19 - Implementar solucion automatica <br> - RF20 - Mostrar animacion de solucion automatica <br> - RF21 -  mostrar una pantalla de victoria al usuario |
| Requerimientos no funcionales | - RNF1 - uso de javaFx para la interfaz grafica <br> - RNF2 - uso de JSON para la carga de niveles <br> - RNF3 - uso de la libreria gson para el manejo de JSON <br> - RNF4 - implementacion bajo el patron MVC <br> - RNF5 - uso de programacion orientada a objetos <br> - RFN6 - Implementacion de pruebas unitarias automatizadas <br> - RNF7 -  lsa pruebasdeben ejecutarse independientemnete de la interfaz grafica <br> - RNF8 - Separacion clara de responsabilidades en el diseno del sistema <br> - RNF9 - Persistencia de datos mediante serailizacion <br> - RFN10 - Implementacion de estructuras de datos propias(no usar arraylist ni linkedlist etc <br> - RNF11 El codigo debe ser escrito en ingles <br> - RNF12 - Interfaz usable con interaccion mediante teclado <br> - RNF13 - La aplicacion debe sear de escritorio <br> - RNF14 - Buen rendimiento en la ejecucion del juego <br> - RNF15 - Correcta validacion de reglas del juego sin errores|
| Requerimientos de proceso     | - RP1 - uso de git y github para el control de versiones <br> - RP2 - usp de ramas bajo modelo gitflow  <br> - RP3 - Registro de almenos 15 commits con metricas de calidad <br> - RP4 - Documentacion del proyecto en readme.md <br> - RP5 - Inclusion de carpeta  /doc con artefactos del proyecto <br> - RP6 - Elaboracion de diagrama de clases uml <br> - RP7 - Diseno de casos de pruebas <br> - RP8 - Desarrollo Colaborativo en equipos de 3 personas <br> - RP9 - Analisi de complejidad Temporal y espacial de algortimos <br> - RP10 Desarrollo incremental por entregas segun cronograma |

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

#  Requerimiento Funcional RF11 mostrar estadisticas del jugador

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF1 - Nombre |
| Resumen                |Mostrar estadistica del jugador como movimientos, empujes y tiempo pornivel. |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|        Datos de partida       |   objeto          |        validos y existentes                   |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|      Estadisticas        |      visual       |  pantalla       |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|el usuario puede visualizar sus estadisticas de juego correctamente                           |

#  Requerimiento Funcional RF12 manual de usuario

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF12 - manual de usuario |
| Resumen                | proporcionar un manual que explique el funcionamiento del juego y ss controles |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|     solicitud de usuario          | evento            |                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|       maual       | visual/texto            | pantalla        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|          el usuario accede al manual de usuario desde el sistema                 |

#  Requerimiento Funcional RF13 Mostrar movimientos realizados

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF13 - Mostrar movimientos realizados |
| Resumen                | Mostrar la cantidad de movimeintos realizados durante la partida |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|    contador de movimientos           | entero         |  >= 0                         |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|     movimientos         | entero          |  numero       |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
| el numero de de movimientos se actualiza y se muestra correctamente                           |

#  Requerimiento Funcional RF14

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF14 - mostrar empujes  |
| Resumen                | Mostrar empujes de cajas realizados |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|     contador de empujes          | Entero            | >=0                          |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|      empujes        |    entero         | Numero        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|     el numero de empujes se actualiza y se muestra correctamente                      |

#  Requerimiento Funcional RF15 Mostrar tiempo de partida

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF15 - Mostrar tiempo de partida |
| Resumen                | Mostrar el tiempo transcurrido durante la partida  |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|   tiemppo            |    tiempo/time         |>=0                           |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|     tiempo         |  tiempo           | mm:ss        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|      el tiempo se actuliza en tiempo real|                     |

#  Requerimiento Funcional RF16  sitema de ranking por nivel

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | R61 - Nombre |
| Resumen                | gestionar un ranking que almacene los mejores resultado por nivel segun sus criterios definidos|

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|  resultados de la partida             |  objeto           |      validos                     |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|        ranking      | lista            |   ordenada      |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|     el ranking se actualiza y mantien los mejores resulados por nivel                      |

#  Requerimiento Funcional RF17 deshacer nuevos movimientos

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF17- Deshacer nuevos movimeintos |
| Resumen                | pertmitir al usuario deshacer el ultimo movimiento|

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|      accion de  deshacer         | evento            |   valido                        |
|            historial de estados   |  pila           | no vacia                          |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|     Estado anterior       | objeto            | tablero        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|     el juego retorna al estado anterior del tablero                      |

#  Requerimiento Funcional RF18 registar entradas al usuario

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF18 - Registra entradas |
| Resumen                | registrar las accciones del usurario en una estructura de datos para su procesamiento |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|         entrada del teclaco      |   evento          | valido                          |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
| cola de entradas             |   cola          | secuenci        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|         las entradas del usuario quedan almacenadas en orden de llegada                  |

#  Requerimiento Funcional RF19 solucion autmoatica del nivel

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF19 - Solucion automatica del nivel |
| Resumen                | permitir resolver auntomaticamente un nivel mediante algritmos de busqueda|

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|        estado del tablero       |       objeto      |       valido                    |
|     algoritmo elejido          |     String        |                 BFS DFS otros          |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|    secuencia de solucion          | lista            | movimeintos        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|          se genera una solucion valida para completar el nivel                 |

#  Requerimiento Funcional RF20 animacion de solucion automatica

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | R20- Animacion de solucion |
| Resumen                | Mostrar de forma animada la ejecucion de la soluccion automatica del nivel|

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|        secuencias de movimientos       |  lista           | no vacia                          |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|       animacion        |  vasual            | pantalla        |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|        la solucion se reproduce visualmente de forma continua en juego                   |

#  Requerimiento Funcional RF21

##  Información del requerimiento
| Campo                   | Descripción |
|------------------------|------------|
| Identificador y nombre | RF21 - pantalla de victoria |
| Resumen                | mostrar una pantalla de victoria cuando el jugador complete el nivel |

---

## Entradas
| Nombre entrada | Tipo de dato | Condición valores válidos |
|---------------|-------------|---------------------------|
|    estado de victoria           |  boolean           | true                          |
|               |             |                           |
|               |             |                           |

---

## Salidas
| Nombre salida | Tipo de dato | Formato |
|--------------|-------------|---------|
|    pantalla de victoria          |  visual           |     pantalla     |
|              |             |         |
|              |             |         |

---

## Resultado o Postcondición
| Resultado o Postcondición |
|---------------------------|
|      el usuario es notificado de que se ha completado el nivel                     |
