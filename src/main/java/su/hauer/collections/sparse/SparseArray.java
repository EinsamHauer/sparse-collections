package su.hauer.collections.sparse;

import su.hauer.collections.sparse.utils.ArrayUtils;

public abstract class SparseArray {

    protected long[] buckets;
    protected int[] counters;

    protected int nnz;
    protected int length;

    protected SparseArray(int[] indices, int valuesLength, int length) {
        if (indices.length != valuesLength) throw new IllegalArgumentException("Indices and values arrays must be of the same size");
        if (indices.length + valuesLength < 1) throw new IllegalArgumentException("Indices and values arrays must not be empty");

        // check if length makes sense
        if (ArrayUtils.max(indices) >= length) throw new IllegalArgumentException("Length is greater than max index");

        createBuckets(length);
    }

    public int length() {
        return length;
    }

    public int nnz() {
        return nnz;
    }

    protected void setBit(int index) {
        int bucket = index >> 6;
        int offset = index & 63;
        buckets[bucket] |= 1L << offset;
    }

    /**
     *
     * @param index index in the array
     * @return index in the underlying values array or -1 if the corresponding bit is not set
     */
    protected int getValueIndex(int index) {
        if (index >= length) throw new ArrayIndexOutOfBoundsException(index);

        int bucket = index >> 6;
        int offset = index & 63;

        if ((buckets[bucket] & (1L << offset)) > 0) {
            return counters[bucket] + Long.bitCount(buckets[bucket] << (63 - offset)) - 1;
        }

        return -1;
    }

    protected void createBuckets(int length) {
        this.length = length;
        int bucketsLength = (int) Math.ceil(length / 64.);

        buckets = new long[bucketsLength];
        counters = new int[bucketsLength];
    }

    protected void populateBuckets(int[] indices) {
        this.nnz = indices.length;

        for (var index : indices) setBit(index);

        counters[0] = 0;
        for (var i = 1; i < buckets.length; i++) {
            counters[i] = counters[i - 1] + Long.bitCount(buckets[i - 1]);
        }
    }

}
