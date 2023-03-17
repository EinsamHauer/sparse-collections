package su.hauer.collections.sparse;

/**
 * @author Andrei Ivanov
 */
public class LongSparseArray extends SparseArray {

    private final long[] values;

    public LongSparseArray(int[] indices, long[] values, int length) {
        super(indices, values.length, length);
        this.values = values.clone();
        populateBuckets(indices);
    }

    public long get(int index) {
        int valueIndex = getValueIndex(index);
        return valueIndex >= 0 ? values[valueIndex] : 0L;
    }
}
