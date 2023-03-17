package su.hauer.collections.sparse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrei Ivanov
 */
public class IntSparseArrayTests {

    @Test
    @DisplayName("Test IntSparseArray construction from 2 arrays")
    public void testIntSparseArrayConstructionFromArrays() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        int[] values = new int[]{1, 2, 3, 4, 5};

        var fsa = new IntSparseArray(indices, values, 256);

        assertEquals(256, fsa.length());
        assertEquals(5, fsa.nnz());

        // length is ok with provided indices
        assertDoesNotThrow(() -> new IntSparseArray(indices, values, 122));
        assertThrows(IllegalArgumentException.class, () -> new IntSparseArray(indices, values, 121));
        assertThrows(IllegalArgumentException.class, () -> new IntSparseArray(indices, values, 88));

        // empty arrays
        int[] emptyIndices = new int[]{};
        int[] emptyValues = new int[]{};
        assertThrows(IllegalArgumentException.class, () -> new IntSparseArray(emptyIndices, emptyValues, 256));

        // differently sized arrays
        int[] indices2 = new int[]{0, 7, 77, 99};
        int[] values2 = new int[]{1, 2, 3, 4, 5};
        assertThrows(IllegalArgumentException.class, () -> new IntSparseArray(indices2, values2, 256));
    }

    @Test
    @DisplayName("Test IntSparseArray correctness")
    public void testIntSparseArrayGet() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        int[] values = new int[]{1, 2, 3, 4, 5};
        Map<Integer, Integer> map = new HashMap<>();
        for (var i = 0; i < indices.length; i++) {
            map.put(indices[i], values[i]);
        }

        var fsa = new IntSparseArray(indices, values, 256);

        for (var i = 0; i < 256; i++) {
            if (map.containsKey(i)) {
                assertEquals(map.get(i), fsa.get(i));
            } else {
                assertEquals(0f, fsa.get(i));
            }
        }

    }
}
