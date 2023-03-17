package su.hauer.collections.sparse;

public class DoubleSparseArray extends SparseArray {

    private final double[] values;

    public DoubleSparseArray(int[] indices, double[] values, int length) {
        super(indices, values.length, length);
        this.values = values.clone();
        populateBuckets(indices);
    }

    public double get(int index) {
        int valueIndex = getValueIndex(index);
        return valueIndex >= 0 ? values[valueIndex] : 0;
    }
}
