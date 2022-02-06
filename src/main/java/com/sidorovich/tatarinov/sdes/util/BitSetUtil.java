package com.sidorovich.tatarinov.sdes.util;

import com.sidorovich.tatarinov.sdes.model.Pair;

import java.util.BitSet;

public interface BitSetUtil {

    BitSet replace(BitSet bitSet, int[] positions);

    BitSet shift(BitSet inputKey, int size, int position);

    Pair<BitSet, BitSet> split(BitSet input, int totalSize);

    BitSet concat(Pair<BitSet, BitSet> bitSetPair, int totalSize);

    BitSet changeParts(BitSet bitSet, int totalSize);

}
