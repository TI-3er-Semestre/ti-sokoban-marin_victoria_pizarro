## **Historias de Usuario**

## Historia de Usuario N° 1

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |   HU-01         |
| Yo como       |       jugador     |
| Quiero        |  registrarme en el sistema usando los datos          |
| Para          |     poder acceder al juego       |

---

## Escenario 1.1

**Escenario: Registro exitoso del jugador**  

| Paso  | Descripción |
|-------|------------|
| Given |       el jugador se encuentra en la pantalla de registro     |
| And   |        todos los campos obligatorios de resgitro estan disponibles    |
| When  |        el jugador ingresa sus datos correctamente y confirma    |
| Then  |     el sistema resgistra el jugador en el sistema       |
| And   |       la vista cambia al menu principal     |

---
## Escenario 1.2

**Escenario: Registro con datos imcompletos**  

| Paso  | Descripción |
|-------|------------|
| Given |       el jugador se encuentra en la pantalla de registro     |
| And   |        por lo menos un campo obligatorio esta vacio    |
| When  |        el jugador intenta registrarse   |
| Then  |     el sistema muestra un mensaje de error     |
| And   |      El registro no se completa    |


## Historia de Usuario N° 2

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |   HU-02         |
| Yo como       |    Jugador        |
| Quiero        |    acceder al menu principal        |
| Para          |    navegar entre las opciones del juego        |

---

## Escenario 2.1

**Escenario: Acceso al menu principal**  

| Paso  | Descripción |
|-------|------------|
| Given |    El jugador ha compplpetado el registro        |
| And   |      no hay una partida activa      |
| When  |       el sistema finaliza el registro     |
| Then  |  se muestra el menu principal          |
| And   |        el jugador visualiza las opciones disponebles    |

---
## Escenario 2.2
**Escenario: Acceso al menu principal con partida activa**  

| Paso  | Descripción |
|-------|------------|
| Given |      El jugador ha iniciado sesion en el sistema      |
| And   |  Existe una partida previamente guardada          |
| When  |   El sistema muestra el menu principal         |
| Then  |       se habilita la opcion de continuar partida     |
| And   |      el jugador puede seleccionar, entre continuar o elegir un nuevo juego      |



## Historia de Usuario N° 3

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |       HU-03     |
| Yo como       |    Jugador        |
| Quiero        |    iniciar el nivel y visualizar el tablero       |
| Para          |       interactuar con el juego     |

---

## Escenario 3.1

**Escenario: Visualizacion del tablero del nivel**  

| Paso  | Descripción |
|-------|------------|
| Given |       el jugador esta en el menu de seleccion de niveles     |
| And   |     selecciona un nivel disponible       |
| When  |      el sistema carga el nivel desde el archivo      |
| Then  |       se muestra el tablero con sus elementos     |
| And   |       los contadores de movimientos, empujes y tiempo son visibles     |

---
## Escenario 3.2 
**Escenario: Error al cargar nivel

| Paso  | Descripción |
|-------|------------|
| Given |       el jugador esta en el menu de seleccion de niveles     |
| And   |    El archivo del nivel esta danado o no disponible       |
| When  |      El jugador intenta iniciar el nivel     |
| Then  |      El sistema muestra un error indicando qu no se puedo cargar el  el nivel    |
| And   |       la vista permanece en la seleccion de niveles     |


## Historia de Usuario N° 4

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |     HU-04       |
| Yo como       |    Jugador        |
| Quiero        |     dexplazarme en el tablero en 4 direcciones        |
| Para          |        avanzar dentro del nivel    |

---

## Escenario 4.1

**Escenario: Movimeinto valido del jugador **  

| Paso  | Descripción |
|-------|------------|
| Given |      El jugador se encuentra en una posicion valida      |
| And   |    Existe una celda libre en la direccion seleccionada        |
| When  |      el jugador presiona una tecla direccion      |
| Then  |  El jugador se mueve a una nueva celda          |
| And   |     El contador de movimientos aumenta a 1       |

---
## Escenario 4.2

**Escenario: Movimiento invalido del jugador **  

| Paso  | Descripción |
|-------|------------|
| Given |    El jugador esta junto a un obstaculo        |
| And   |     no hya una celda libre en la direccion seleccionada       |
| When  |     El jugador intenta moverse       |
| Then  |    el Jugador permanece en la misma posicion        |
| And   |    El contador de movimientos no cambia        |

---

## Historia de Usuario N° 5

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |       HU-05     |
| Yo como       |    Jugador        |
| Quiero        |    Empujar cajas dentro del tablero        |
| Para          |    ubicarlas en las posiciones obejtivo        |

---

## Escenario 5.1

**Escenario:**  

| Paso  | Descripción |
|-------|------------|
| Given |  El jugador esta junto a una caja          |
| And   |   Existe una celda libre detras de la caja         |
| When  |       El jugador intenta mover la caja     |
| Then  |      La caja se dezplaza a la celda libre      |
| And   |    El jugador ocupa la posicion anterior de la caja        |
| And   |    Los contadores de movimiento y empujes aumentan        |

---
## Escenario 5.2

**Escenario: Empuje invalido de la caja **  

| Paso  | Descripción |
|-------|------------|
| Given |  El jugador esta junto a una caja          |
| And   |     no hay espacio libre detras de la caja       |
| When  |    el jugador intenta empujarla        |
| Then  |          La caja no se mueve  |
| And   |      El jugador permanece en su posicion      |
| And   |    Los contadores de empuje y movimiento no cambian       |

---

## Historia de Usuario N° 6

| Campo         | Descripción                                                   |
|---------------|-------------------------------------------------------------- |
| Historia N°   |HU-06                                                          |
| Yo como       |jugador                                                        |
| Quiero        |completar el nivel al ubicar todas las cajas en los objetivos  |
| Para          |avanzar en el juego                                            |

---

## Escenario 6.1

**Escenario: Completar el nivel exitosamente**  

| Paso  | Descripción                                                                                                |
|-------|------------------------------------------------------------------------------------------------------------|
| Given |el jugador se encuentra en un nivel activo con cajas y posiciones objetivo visibles en el tablero           |
| And   |al menos una caja no está posicionada sobre su objetivo                                                     |
| When  |el jugador empuja la última caja pendiente sobre su posición objetivo                                       |
| Then  |el contador de cajas ubicadas pasa a ser igual al total de objetivos del nivel                              |
| And   |la vista cambia a la pantalla de resumen del nivel con el mensaje 'Nivel completado'                        |
| And   |el nivel siguiente aparece desbloqueado en el menú de selección de niveles                                  |

---

## Escenario 6.2

**Escenario: Movimiento que no completa el nivel**  

| Paso  | Descripción                                                                                                |
|-------|------------------------------------------------------------------------------------------------------------|
| Given |el jugador se encuentra en un nivel activo                                                                  |
| And   |al menos una caja no está posicionada sobre su objetivo                                                     |
| When  |el jugador realiza un movimiento que no posiciona la última caja pendiente sobre su objetivo                |
| Then  |la vista del tablero permanece activa sin cambiar de pantalla                                               |
| And   |el contador de cajas ubicadas no alcanza el total de objetivos del nivel                                    |
| And   |el contador de movimientos se incrementa en 1                                                               |

---

## Historia de Usuario N° 7

| Campo         | Descripción                                                      |
|---------------|------------------------------------------------------------------|
| Historia N°   |HU-07                                                             |
| Yo como       |jugador                                                           |
| Quiero        |visualizar mis movimientos, empujes y tiempo durante la partida   |
| Para          |monitorear mi desempeño en tiempo real                            |

---

## Escenario 7.1

**Escenario:Contador de movimientos se actualiza al desplazar el jugador**  

| Paso  | Descripción                                                                           |
|-------|---------------------------------------------------------------------------------------|
| Given |el jugador ha iniciado un nivel y el contador de movimientos marca 0                   |
| And   |el contador de empujes marca 0                                                         |
| When  |el jugador se desplaza a una celda libre sin empujar ninguna caja                      |
| Then  |el contador de movimientos pasa de N a N+1 en la interfaz del juego                    |
| And   |el contador de empujes permanece en el mismo valor que tenía antes del movimiento      |
| And   |el temporizador incrementa su valor en al menos 1 segundo respecto al valor anterior   |  

---

## Escenario 7.2

**Escenario:Contadores se actualizan al empujar una caja**  

| Paso  | Descripción                                                                               |
|-------|-------------------------------------------------------------------------------------------|
| Given |el jugador ha iniciado un nivel y ambos contadores marcan 0                                |
| And   |existe al menos una caja adyacente al jugador con celda libre detrás                       |
| When  |el jugador empuja la caja hacia la celda libre                                             |
| Then  |el contador de movimientos pasa de N a N+1 en la interfaz del juego                        |
| And   |el contador de empujes pasa de M a M+1 en la interfaz del juego                            |
| And   |ambos contadores y el temporizador son visibles en la pantalla principal durante el juego  |  

---

## Escenario 7.3

**Escenario:Movimiento inválido no modifica los contadores**  

| Paso  | Descripción                                                                                |
|-------|--------------------------------------------------------------------------------------------|
| Given |el jugador se encuentra en un nivel activo con contadores en valores mayores a 0            |
| And   |existe una pared adyacente al jugador en la dirección que intenta moverse                   |
| When  |el jugador presiona la tecla de dirección hacia la pared                                    |
| Then  |el contador de movimientos mantiene el mismo valor que tenía antes de la acción             |
| And   |el contador de empujes mantiene el mismo valor que tenía antes de la acción                 |
| And   |el temporizador incrementa su valor en al menos 1 segundo respecto al valor anterior        |  

---

## Historia de Usuario N° 8
  
| Campo         | Descripción                                       |
|---------------|---------------------------------------------------|
| Historia N°   |HU-08                                              |
| Yo como       |jugador                                            |
| Quiero        |deshacer mi último movimiento                      |
| Para          |corregir errores sin reiniciar el nivel completo   |

---

## Escenario 8.1

**Escenario:Deshacer el último movimiento exitosamente**  

| Paso  | Descripción                                                                                      |
|-------|--------------------------------------------------------------------------------------------------|
| Given |el jugador se encuentra en un nivel activo                                                        |
| And   |el jugador ha realizado al menos un movimiento registrado en el historial del nivel               |
| When  |el jugador activa la función de deshacer                                                          |
| Then  |la posición del jugador en el tablero cambia a la celda que ocupaba antes del último movimiento   |
| And   |las cajas afectadas regresan a la celda que ocupaban antes del último movimiento                  |
| And   |el contador de movimientos disminuye en 1 respecto al valor que tenía                             |
| And   |el contador de empujes disminuye en 1 si el último movimiento registrado fue un empuje            |

---

## Escenario 8.2

**Escenario:Intento de deshacer sin movimientos registrados**  

| Paso  | Descripción                                                                                      |
|-------|--------------------------------------------------------------------------------------------------|
| Given |el jugador ha iniciado un nivel                                                                   |
| And   |el historial de movimientos del nivel está vacío (contador en 0)                                  |
| When  |el jugador activa la función de deshacer                                                          |
| Then  |la posición del jugador en el tablero no cambia                                                   |
| And   |el contador de movimientos permanece en 0                                                         |
| And   |el botón de deshacer aparece deshabilitado en la interfaz del juego                               |

---

## Historia de Usuario N° 9

| Campo         | Descripción                                              |
|---------------|----------------------------------------------------------|
| Historia N°   |HU-09                                                     |
| Yo como       |jugador                                                   |
| Quiero        |cargar un nivel del juego desde el menú de selección      |
| Para          |enfrentar diferentes escenarios y desafíos                |

---

## Escenario 9.1

**Escenario:Carga exitosa de un nivel desbloqueado**  

| Paso  | Descripción                                                              |
|-------|--------------------------------------------------------------------------|
| Given |el jugador se encuentra en el menú de selección de niveles                |
| And   |no hay ninguna partida activa en curso                                    |
| When  |el jugador selecciona un nivel marcado como desbloqueado en la lista      |
| Then  |la vista cambia al tablero del nivel seleccionado                         |
| And   |el contador de movimientos en la interfaz marca 0                         |
| And   |el contador de empujes en la interfaz marca 0                             |
| And   |el temporizador en la interfaz marca 00:00 y comienza a incrementarse     |


---

## Escenario 9.2

**Escenario:Seleccionar nivel con partida activa en curso**  

| Paso  | Descripción                                                                                          |
|-------|------------------------------------------------------------------------------------------------------|
| Given |el jugador se encuentra en el menú de selección de niveles                                            |
| And   |existe una partida activa con al menos un movimiento registrado                                       |
| When  |el jugador selecciona un nivel distinto al que está en curso                                          |
| Then  |la vista muestra un cuadro de diálogo con el texto '¿Deseas abandonar la partida actual?'             |
| And   |el tablero activo no cambia hasta que el jugador seleccione una opción en el cuadro                   |
| And   |si el jugador selecciona 'Cancelar', la vista regresa al menú sin modificar la partida activa         |


---

## Historia de Usuario N° 10

| Campo         | Descripción                                                            |
|---------------|------------------------------------------------------------------------|
| Historia N°   |HU-10                                                                   |
| Yo como       |jugador                                                                 |
| Quiero        |que mi progreso se guarde automáticamente al cerrar la aplicación       |
| Para          |no perder el avance realizado en el nivel actual                        |

---

## Escenario 10.1

**Escenario:Guardado automático al cerrar la aplicación con progreso**  

| Paso  | Descripción                                                              |
|-------|--------------------------------------------------------------------------|
| Given |el jugador se encuentra en un nivel activo                                |
| And   |el contador de movimientos registra al menos 1 movimiento realizado       |
| When  |el jugador cierra la aplicación                                           |
| Then  |el sistema almacena en memoria persistente el estado actual del tablero   |
| And   |el sistema almacena el valor actual del contador de movimientos           |
| And   |el sistema almacena el valor actual del contador de empujes               |
| And   |el sistema almacena el valor actual del temporizador en segundos          |

---

## Escenario 10.2

**Escenario:No se genera guardado si no hay movimientos registrados**  

| Paso  | Descripción                                                                        |
|-------|------------------------------------------------------------------------------------|
| Given |el jugador abrió la aplicación y cargó un nivel                                     |
| And   |el contador de movimientos marca 0 (ningún movimiento realizado)                    |
| When  |el jugador cierra la aplicación                                                     |
| Then  |el sistema no escribe ningún archivo de guardado para ese nivel                     |
| And   |al reabrir la aplicación el nivel aparece con el tablero en su estado inicial       |
| And   |el contador de movimientos en la interfaz marca 0                                   |

---


## Historia de Usuario N° 11

| Campo         | Descripción                                                          |
|---------------|----------------------------------------------------------------------|
| Historia N°   |HU-11                                                                 |
| Yo como       |jugador                                                               |
| Quiero        |retomar mi partida desde donde la dejé                                |
| Para          |continuar el nivel sin perder el progreso guardado anteriormente      |

---

## Escenario 11.1

**Escenario:Retomar partida guardada exitosamente**  

| Paso  | Descripción                                                                                        |
|-------|----------------------------------------------------------------------------------------------------|
| Given |el jugador abre la aplicación                                                                       |
| And   |existe un archivo de guardado válido de una sesión anterior                                         |
| When  |el jugador selecciona la opción 'Continuar partida' en el menú principal                            |
| Then  |la vista cambia al tablero del nivel guardado con las cajas en las posiciones almacenadas           |
| And   |el contador de movimientos en la interfaz muestra el valor almacenado en el archivo de guardado     |
| And   |el contador de empujes en la interfaz muestra el valor almacenado en el archivo de guardado         |
| And   |el temporizador inicia desde el valor en segundos almacenado en el archivo de guardado              |

---

## Escenario 11.2

**Escenario:Intento de retomar sin archivo de guardado disponible**  

| Paso  | Descripción                                                                                        |
|-------|----------------------------------------------------------------------------------------------------|
| Given |el jugador abre la aplicación                                                                       |
| And   |no existe ningún archivo de guardado en el sistema para este jugador                                |
| When  |el jugador accede al menú principal del juego                                                       |
| Then  |el botón 'Continuar partida' aparece deshabilitado en el menú principal                             |
| And   |la vista dirige al jugador directamente al menú de selección de niveles                             |

---

## Historia de Usuario N° 12

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |      HU-12      |
| Yo como       |     Jugador       |
| Quiero        |     ver una solución automática del nivel experto     |
| Para          |     entender cómo resolver el juego      |

---

## Escenario 12.1

**Escenario: Intento de visualizar solución sin disponibilidad**  

| Paso  | Descripción |
|-------|------------|
| Given |   el jugador se encuentra en el nivel experto         |
| And   |   no existe una solución automática disponible para ese nivel     |
| When  |   el jugador selecciona la opción para ver solución        |
| Then  |   el sistema muestra un mensaje indicando que la solución no está disponible        |

---
---

## Escenario 12.2

**Escenario: Visualizar solución automática del nivel experto correctamente**  

| Paso  | Descripción |
|-------|------------|
| Given |   el jugador se encuentra en el nivel experto        |
| And   |   existe una solución automática disponible para ese nivel         |
| When  |   el jugador selecciona la opción para ver solución         |
| Then  |   el sistema muestra la solución del nivel experto de forma secuencial         |
| And   |   el jugador puede observar la ejecución de cada movimiento         |

---

## Historia de Usuario N° 13

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |    HU-13        |
| Yo como       |   Jugador         |
| Quiero        |   ver un ranking de mejores resultados          |
| Para          |   comparar mi desempeño         |

---

## Escenario 13.1

**Escenario: Visualizar ranking de mejores resultados correctamente**  

| Paso  | Descripción |
|-------|------------|
| Given |   el jugador ha iniciado sesión en el sistema         |
| And   |   existen resultados registrados de otros jugadores o partidas         |
| When  |   el jugador accede a la pantalla de ranking         |
| Then  |   el sistema muestra una lista ordenada de los mejores resultados         |
| And   |   los resultados se presentan según criterios como menor tiempo, menos movimientos o menos empujes         |

---
---

## Escenario 13.2

**Escenario: Visualizar ranking sin resultados registrados**  

| Paso  | Descripción |
|-------|------------|
| Given |  el jugador ha iniciado sesión en el sistema          |
| And   |  no existen resultados registrados en el sistema          |
| When  |  el jugador accede a la pantalla de ranking          |
| Then  |  el sistema muestra un mensaje indicando que no hay resultados disponibles          |

---

## Historia de Usuario N° 14

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |    HU-14        |
| Yo como       |    Jugador        |
| Quiero        |    consultar mis estadísticas de juego        |
| Para          |    analizar mi progreso        |

---

## Escenario 14.1

**Escenario: Consultar estadísticas de juego correctamente**  

| Paso  | Descripción |
|-------|------------|
| Given |  el jugador ha iniciado sesión en el sistema          |
| And   |  existen estadísticas registradas de sus partidas          |
| When  |  el jugador accede a la pantalla de estadísticas          |
| Then  |  el sistema muestra sus estadísticas de juego          |
| And   |  el jugador visualiza datos como cantidad de movimientos, cantidad de empujes y tiempo por nivel          |

---
---

## Escenario 14.2

**Escenario: Consultar estadísticas sin partidas registradas**  

| Paso  | Descripción |
|-------|------------|
| Given |  el jugador ha iniciado sesión en el sistema          |
| And   |  no existen estadísticas registradas de sus partidas          |
| When  |  el jugador accede a la pantalla de estadísticas          |
| Then  |  el sistema muestra un mensaje indicando que no hay estadísticas disponibles          |

---

## Historia de Usuario N° 15

| Campo         | Descripción |
|---------------|------------|
| Historia N°   |      HU-15      |
| Yo como       |      Jugador     |
| Quiero        |      acceder a un manual de usuario      |
| Para          |      aprender a jugar      |

---

## Escenario 15.1

**Escenario: Acceder al manual de usuario correctamente**  

| Paso  | Descripción |
|-------|------------|
| Given |   el jugador se encuentra en el menú principal del juego         |
| And   |   el manual de usuario está disponible en el sistema         |
| When  |   el jugador selecciona la opción para acceder al manual de usuario        |
| Then  |   el sistema muestra la pantalla del manual de usuario         |
| And   |   el jugador puede visualizar la explicación del juego, cómo jugar y el significado de los indicadores         |

---
---

## Escenario 15.2

**Escenario: Intentar acceder al manual de usuario sin disponibilidad**  

| Paso  | Descripción |
|-------|------------|
| Given |   el jugador se encuentra en el menú principal del juego         |
| And   |   el manual de usuario no está disponible en el sistema         |
| When  |   el jugador selecciona la opción para acceder al manual de usuario         |
| Then  |   el sistema muestra un mensaje indicando que el manual no se encuentra disponible         |

---

