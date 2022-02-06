package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.util.CyclicShift;

import java.util.BitSet;

public class BitSetCyclicShift implements CyclicShift {

    @Override
    public BitSet shift(BitSet bitSet, int size, int position) {
        final int middle = size / 2;

        BitSet firstPart = shiftPart(bitSet.get(0, middle), middle, position);
        BitSet secondPart = shiftPart(bitSet.get(middle, size), middle, position);

        return concat(firstPart, secondPart, middle, middle);
    }

    private BitSet shiftPart(BitSet bitSet, int size, int position) {
        final BitSet output = new BitSet(size);

        for (int i = 0; i < size; i++) {
            output.set(Math.abs(i + size - position % size) % (size), bitSet.get(i));
        }
        return output;
    }

    private BitSet concat(BitSet bitSet1, BitSet bitSet2, int size1, int size2) {
        final BitSet output = new BitSet(size1 + size2);

        for (int i = 0; i < size1; i++) {
            output.set(i, bitSet1.get(i));
        }
        for (int i = size1; i < size1 + size2; i++) {
            output.set(i, bitSet2.get(i - size1));
        }
        return output;
    }

}
