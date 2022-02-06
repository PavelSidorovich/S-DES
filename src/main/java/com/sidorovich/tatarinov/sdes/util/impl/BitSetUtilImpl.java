package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.model.Pair;
import com.sidorovich.tatarinov.sdes.util.BitSetUtil;
import com.sidorovich.tatarinov.sdes.util.CyclicShift;
import lombok.AllArgsConstructor;

import java.util.BitSet;

@AllArgsConstructor
public class BitSetUtilImpl implements BitSetUtil {

    private final CyclicShift cyclicShift;

    @Override
    public BitSet replace(BitSet bitSet, int[] positions) {
        final BitSet output = new BitSet(positions.length);

        for (int i = 0; i < positions.length; i++) {
            output.set(i, bitSet.get(positions[i] - 1));
        }
        return output;
    }

    @Override
    public BitSet shift(BitSet bitSet, int size, int position) {
        return cyclicShift.shift(bitSet, size, position);
    }

    @Override
    public Pair<BitSet, BitSet> split(BitSet bitSet, int totalSize) {
        final BitSet part1 = new BitSet(totalSize);
        final BitSet part2 = new BitSet(totalSize);

        for (int i = 0; i < totalSize / 2; i++) {
            part1.set(i, bitSet.get(i));
        }
        for (int i = totalSize / 2; i < totalSize; i++) {
            part2.set(i - totalSize / 2, bitSet.get(i));
        }
        return new Pair<>(part1, part2);
    }

    @Override
    public BitSet concat(Pair<BitSet, BitSet> bitSetPair, int totalSize) {
        final BitSet output = new BitSet(totalSize);
        final BitSet part1 = bitSetPair.getObject1();
        final BitSet part2 = bitSetPair.getObject2();

        for (int i = 0; i < totalSize / 2; i++) {
            output.set(i, part1.get(i));
        }
        for (int i = totalSize / 2; i < totalSize; i++) {
            output.set(i, part2.get(i - totalSize / 2));
        }
        return output;
    }

    @Override
    public BitSet changeParts(BitSet bitSet, int totalSize) {
        Pair<BitSet, BitSet> bitSetPair = split(bitSet, totalSize);

        return concat(new Pair<>(bitSetPair.getObject2(), bitSetPair.getObject1()), totalSize);
    }

}
