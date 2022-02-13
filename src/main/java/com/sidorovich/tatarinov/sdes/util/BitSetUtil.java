package com.sidorovich.tatarinov.sdes.util;

import com.sidorovich.tatarinov.sdes.model.BitArray;
import com.sidorovich.tatarinov.sdes.model.Pair;

public interface BitSetUtil {

    BitArray replace(BitArray bitSet, int[] positions);

    BitArray shift(BitArray inputKey, int position);

    Pair<BitArray, BitArray> split(BitArray input);

    BitArray changeParts(BitArray bitSet, int totalSize);

}
