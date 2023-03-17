package su.hauer.collections.sparse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectSparseArrayTests {

    @Test
    @DisplayName("Test ObjectSparseArray construction from 2 arrays")
    public void testObjectSparseArrayConstructionFromArrays() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        String[] values = new String[]{"1", "2", "3", "4", "5"};

        var fsa = new ObjectSparseArray<>(indices, values, 256);

        assertEquals(256, fsa.length());
        assertEquals(5, fsa.nnz());

        // length is ok with provided indices
        assertDoesNotThrow(() -> new ObjectSparseArray<>(indices, values, 122));
        assertThrows(IllegalArgumentException.class, () -> new ObjectSparseArray<>(indices, values, 121));
        assertThrows(IllegalArgumentException.class, () -> new ObjectSparseArray<>(indices, values, 88));

        // empty arrays
        int[] emptyIndices = new int[]{};
        String[] emptyValues = new String[]{};
        assertThrows(IllegalArgumentException.class, () -> new ObjectSparseArray<>(emptyIndices, emptyValues, 256));

        // differently sized arrays
        int[] indices2 = new int[]{0, 7, 77, 99};
        String[] values2 = new String[]{"1", "2", "3", "4", "5"};
        assertThrows(IllegalArgumentException.class, () -> new ObjectSparseArray<>(indices2, values2, 256));
    }

    @Test
    @DisplayName("Test ObjectSparseArray correctness")
    public void testObjectSparseArrayGet() {
        int[] indices = new int[]{0, 7, 77, 99, 121};
        String[] values = new String[]{"1", "2", "3", "4", "5"};
        Map<Integer, String> map = new HashMap<>();
        for (var i = 0; i < indices.length; i++) {
            map.put(indices[i], values[i]);
        }

        var fsa = new ObjectSparseArray<>(indices, values, 256);

        for (var i = 0; i < 256; i++) {
            assertEquals(map.getOrDefault(i, null), fsa.get(i));
        }

    }
}
