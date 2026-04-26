import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structures.HashTable;

public class HashTableTest {

    private HashTable<String, Integer> table;

    /**
     * setupStage1:
     * Tabla hash con tres entradas: "nivel1"=100, "nivel2"=200, "nivel3"=300.
     */
    private void setupStage1() {
        table = new HashTable<>(10);
        table.put("nivel1", 100);
        table.put("nivel2", 200);
        table.put("nivel3", 300);
    }

    /**
     * setupStage2:
     * Tabla hash vacía, sin ninguna entrada.
     */
    private void setupStage2() {
        table = new HashTable<>(10);
    }

    /**
     * Verifica que put almacena el valor correctamente
     * y que put con clave existente actualiza el valor sin duplicar entradas.
     */
    @Test
    public void testPut_InsertaYActualiza() {

        // Arrange
        setupStage1();

        // Act
        table.put("nivel1", 999);

        // Assert
        Assertions.assertEquals(999, table.get("nivel1"));
        Assertions.assertEquals(3, table.size());
    }

    /**
     * Verifica que remove elimina la entrada correcta,
     * retorna su valor y la clave deja de existir en la tabla.
     */
    @Test
    public void testRemove_EliminaEntrada() {

        // Arrange
        setupStage1();

        // Act
        Integer eliminado = table.remove("nivel2");

        // Assert
        Assertions.assertEquals(200, eliminado);
        Assertions.assertFalse(table.containsKey("nivel2"));
        Assertions.assertEquals(2, table.size());
    }

    /**
     * Verifica que get retorna null para claves inexistentes
     * y que isEmpty retorna true en una tabla vacía.
     */
    @Test
    public void testGet_ClaveInexistente() {

        // Arrange
        setupStage2();

        // Act
        Integer resultado = table.get("noExiste");

        // Assert
        Assertions.assertNull(resultado);
        Assertions.assertTrue(table.isEmpty());
    }
}