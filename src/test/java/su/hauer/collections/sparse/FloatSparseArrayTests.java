package su.hauer.collections.sparse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FloatSparseArrayTests {

    @Test
    @DisplayName("Test FloatSparseArray construction from 2 arrays")
    public void testFloatSparseArrayConstructionFromArrays() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        float[] values = new float[]{1f, 2f, 3f, 4f, 5f};

        var fsa = new FloatSparseArray(indices, values, 256);

        assertEquals(256, fsa.length());
        assertEquals(5, fsa.nnz());

        // length is ok with provided indices
        assertDoesNotThrow(() -> new FloatSparseArray(indices, values, 122));
        assertThrows(IllegalArgumentException.class, () -> new FloatSparseArray(indices, values, 121));
        assertThrows(IllegalArgumentException.class, () -> new FloatSparseArray(indices, values, 88));

        // empty arrays
        int[] emptyIndices = new int[]{};
        float[] emptyValues = new float[]{};
        assertThrows(IllegalArgumentException.class, () -> new FloatSparseArray(emptyIndices, emptyValues, 256));

        // differently sized arrays
        int[] indices2 = new int[]{0, 7, 77, 99};
        float[] values2 = new float[]{1f, 2f, 3f, 4f, 5f};
        assertThrows(IllegalArgumentException.class, () -> new FloatSparseArray(indices2, values2, 256));
    }

    @Test
    @DisplayName("Test FloatSparseArray correctness")
    public void testFloatSparseArrayGet() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        float[] values = new float[]{1f, 2f, 3f, 4f, 5f};
        Map<Integer, Float> map = new HashMap<>();
        for (var i = 0; i < indices.length; i++) {
            map.put(indices[i], values[i]);
        }

        var fsa = new FloatSparseArray(indices, values, 256);

        for (var i = 0; i < 256; i++) {
            if (map.containsKey(i)) {
                assertEquals(map.get(i), fsa.get(i));
            } else {
                assertEquals(0f, fsa.get(i));
            }
        }

    }
}
