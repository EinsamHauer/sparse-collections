package su.hauer.collections.sparse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrei Ivanov
 */
public class DoubleSparseArrayTests {

    @Test
    @DisplayName("Test DoubleSparseArray construction from 2 arrays")
    public void testDoubleSparseArrayConstructionFromArrays() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        double[] values = new double[]{1., 2., 3., 4., 5.};

        var fsa = new DoubleSparseArray(indices, values, 256);

        assertEquals(256, fsa.length());
        assertEquals(5, fsa.nnz());

        // length is ok with provided indices
        assertDoesNotThrow(() -> new DoubleSparseArray(indices, values, 122));
        assertThrows(IllegalArgumentException.class, () -> new DoubleSparseArray(indices, values, 121));
        assertThrows(IllegalArgumentException.class, () -> new DoubleSparseArray(indices, values, 88));

        // empty arrays
        int[] emptyIndices = new int[]{};
        double[] emptyValues = new double[]{};
        assertThrows(IllegalArgumentException.class, () -> new DoubleSparseArray(emptyIndices, emptyValues, 256));

        // differently sized arrays
        int[] indices2 = new int[]{0, 7, 77, 99};
        double[] values2 = new double[]{1., 2., 3., 4., 5.};
        assertThrows(IllegalArgumentException.class, () -> new DoubleSparseArray(indices2, values2, 256));
    }

    @Test
    @DisplayName("Test DoubleSparseArray correctness")
    public void testDoubleSparseArrayGet() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        double[] values = new double[]{1., 2., 3., 4., 5.};
        Map<Integer, Double> map = new HashMap<>();
        for (var i = 0; i < indices.length; i++) {
            map.put(indices[i], values[i]);
        }

        var fsa = new DoubleSparseArray(indices, values, 256);

        for (var i = 0; i < 256; i++) {
            if (map.containsKey(i)) {
                assertEquals(map.get(i), fsa.get(i));
            } else {
                assertEquals(0f, fsa.get(i));
            }
        }

    }
}
