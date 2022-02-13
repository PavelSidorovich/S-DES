package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.model.BitArray;
import com.sidorovich.tatarinov.sdes.model.Pair;
import com.sidorovich.tatarinov.sdes.util.BitSetUtil;
import com.sidorovich.tatarinov.sdes.util.CyclicShift;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BitSetUtilImpl implements BitSetUtil {

    private final CyclicShift cyclicShift;

    @Override
    public BitArray replace(BitArray bitSet, int[] positions) {
        final BitArray output = new BitArray(positions.length);

        for (int i = 0; i < positions.length; i++) {
            output.set(i, bitSet.get(positions[i] - 1));
        }
        return output;
    }

    @Override
    public BitArray shift(BitArray bitArray, int position) {
        return cyclicShift.shift(bitArray, position);
    }

    @Override
    public Pair<BitArray, BitArray> split(BitArray bitSet) {
        final int size = bitSet.size();
        final BitArray part1 = bitSet.get(0, size / 2);
        final BitArray part2 = bitSet.get(size / 2, size);

        return new Pair<>(part1, part2);
    }

    @Override
    public BitArray changeParts(BitArray bitArray, int totalSize) {
        Pair<BitArray, BitArray> bitArrayPair = split(bitArray);

        return bitArrayPair.getObject2().cat(bitArrayPair.getObject1());
    }

}
