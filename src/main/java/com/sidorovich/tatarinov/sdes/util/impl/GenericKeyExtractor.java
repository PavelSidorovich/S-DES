package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.util.BitSetUtil;
import com.sidorovich.tatarinov.sdes.util.KeyGenerator;
import lombok.AllArgsConstructor;

import java.util.BitSet;

@AllArgsConstructor
public abstract class GenericKeyExtractor implements KeyGenerator {

    private static final int[] RULE_10 = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
    private static final int[] RULE_8 = { 6, 3, 7, 4, 8, 5, 10, 9 };

    private static final int KEY_SIZE = 10;

    private final BitSetUtil bitSetUtil;

    @Override
    public BitSet generateKey1(BitSet inputKey) {
        BitSet output = bitSetUtil.replace(inputKey, RULE_10);

        output = bitSetUtil.shift(output, KEY_SIZE, getShift());
        output = bitSetUtil.replace(output, RULE_8);

        return output;
    }

    protected abstract int getShift();

}
