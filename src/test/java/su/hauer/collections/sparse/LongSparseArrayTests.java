package su.hauer.collections.sparse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrei Ivanov
 */
public class LongSparseArrayTests {

    @Test
    @DisplayName("Test LongSparseArray construction from 2 arrays")
    public void testLongSparseArrayConstructionFromArrays() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        long[] values = new long[]{1L, 2L, 3L, 4L, 5L};

        var fsa = new LongSparseArray(indices, values, 256);

        assertEquals(256, fsa.length());
        assertEquals(5, fsa.nnz());

        // length is ok with provided indices
        assertDoesNotThrow(() -> new LongSparseArray(indices, values, 122));
        assertThrows(IllegalArgumentException.class, () -> new LongSparseArray(indices, values, 121));
        assertThrows(IllegalArgumentException.class, () -> new LongSparseArray(indices, values, 88));

        // empty arrays
        int[] emptyIndices = new int[]{};
        long[] emptyValues = new long[]{};
        assertThrows(IllegalArgumentException.class, () -> new LongSparseArray(emptyIndices, emptyValues, 256));

        // differently sized arrays
        int[] indices2 = new int[]{0, 7, 77, 99};
        long[] values2 = new long[]{1L, 2L, 3L, 4L, 5L};
        assertThrows(IllegalArgumentException.class, () -> new LongSparseArray(indices2, values2, 256));
    }

    @Test
    @DisplayName("Test LongSparseArray correctness")
    public void testLongSparseArrayGet() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        long[] values = new long[]{1L, 2L, 3L, 4L, 5L};
        Map<Integer, Long> map = new HashMap<>();
        for (var i = 0; i < indices.length; i++) {
            map.put(indices[i], values[i]);
        }

        var fsa = new LongSparseArray(indices, values, 256);

        for (var i = 0; i < 256; i++) {
            if (map.containsKey(i)) {
                assertEquals(map.get(i), fsa.get(i));
            } else {
                assertEquals(0f, fsa.get(i));
            }
        }

    }
}
