package su.hauer.collections.sparse;

/**
 * @author Andrei Ivanov
 */
public class IntSparseArray extends SparseArray {

    private final int[] values;

    public IntSparseArray(int[] indices, int[] values, int length) {
        super(indices, values.length, length);
        this.values = values.clone();
        populateBuckets(indices);
    }

    public int get(int index) {
        int valueIndex = getValueIndex(index);
        return valueIndex >= 0 ? values[valueIndex] : 0;
    }
}
