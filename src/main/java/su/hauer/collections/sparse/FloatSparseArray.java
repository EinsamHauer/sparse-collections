package su.hauer.collections.sparse;

public class FloatSparseArray extends SparseArray {

    private final float[] values;

    public FloatSparseArray(int[] indices, float[] values, int length) {
        super(indices, values.length, length);
        this.values = values.clone();
        populateBuckets(indices);
    }

    public float get(int index) {
        int valueIndex = getValueIndex(index);
        return valueIndex >= 0 ? values[valueIndex] : 0f;
    }
}
