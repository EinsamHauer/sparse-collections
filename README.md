sparse-collections: faster than array
==================================================

This is an implementation of sparse arrays in Java. This data structure allows random access by 
index which is more compact and much faster than plain array in case of heavily sparse arrays.

### motivation

There are several use cases where one would need a fast array-like data structure with random access 
by index:
- Linear algebra sparse vector representation. Basically when one repeatedly calculates, say, inner product one of the vectors may only need to be iterated while the second one will need to be accessed by index. There are not many choices here - plain arrays or dictionary implementations.
- Dictionary-like data structure where the keys are integers. Usual implementations are pretty efficient in terms of memory but require somewhat heavier computation and _only_ amortized $O^{*}(1)$ get complexity. On the other hand arrays offer extremely fast get operation at the cost of huge memory inefficiency.
- etc

### faster than array

This data structure here is a solution to the above problems. It relies on two features of modern CPUs:
- Large **L2/L3** CPU cache allowing to leverage cache locality
- Availability of fast **popcnt** instruction on modern CPUs

Here is the result of a quick benchmark of this data structure VS plain float array. 
The setup is:
- An array is $0.1$ sparse meaning that ony 10% of values are not zeroes
- We vary the size of the array represented on X-axis in logarithmic scale
- We do random index lookup and measure operation per second

The benchmark was done on CPU with 
- L2 cache: 10 MiB
- L3 cache: 24 MiB

Long story short here is the graph:

![The benchmark](/docs/benchmark.png)

_Note: The rhombus marks the last array size which fits into L2 cache, triangle - the last array size which fits into L3._

### NB!

So far these data structures are read-only. It is possible to implement _set-_ and _delete-like_ operations. The problem is that both these operations will be $O(n)$.  This may be still be useful in some cases though.
Still the primary goal is to have fast lookup. 

### NNB!

If for some reason you'd like to use this somewhere and need it Maven repos just ping me here.