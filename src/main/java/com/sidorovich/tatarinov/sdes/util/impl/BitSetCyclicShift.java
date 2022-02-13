package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.model.BitArray;
import com.sidorovich.tatarinov.sdes.util.CyclicShift;

public class BitSetCyclicShift implements CyclicShift {

    @Override
    public BitArray shift(BitArray bitArray, int position) {
        final int middle = bitArray.size() / 2;

        BitArray firstPart = shiftPart(bitArray.get(0, middle), position);
        BitArray secondPart = shiftPart(bitArray.get(middle, bitArray.size()), position);

        return firstPart.cat(secondPart);
    }

    private BitArray shiftPart(BitArray bitArray, int position) {
        final int bitSize = bitArray.size();
        final BitArray output = new BitArray(bitSize);

        for (int i = 0; i < bitSize; i++) {
            output.set(Math.abs(i + bitSize - position % bitSize) % (bitSize), bitArray.get(i));
        }
        return output;
    }

}
