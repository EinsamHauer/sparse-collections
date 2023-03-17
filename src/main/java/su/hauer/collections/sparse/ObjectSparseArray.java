package su.hauer.collections.sparse;

/**
 * @author Andrei Ivanov
 */
public class ObjectSparseArray<E> extends SparseArray {

    private final E[] values;

    public ObjectSparseArray(int[] indices, E[] values, int length) {
        super(indices, values.length, length);
        this.values = values.clone();
        populateBuckets(indices);
    }

    public E get(int index) {
        int valueIndex = getValueIndex(index);
        return valueIndex >= 0 ? values[valueIndex] : null;
    }
}
