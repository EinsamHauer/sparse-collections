package su.hauer.collections.sparse.utils;

public class ArrayUtils {

    public static int max(int[] values) {
        int result = -1;
        for (var i : values) result = Math.max(i, result);
        return result;
    }
}
