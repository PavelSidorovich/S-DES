package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.util.BitSetUtil;
import com.sidorovich.tatarinov.sdes.util.KeyGenerator;
import lombok.AllArgsConstructor;

import java.util.BitSet;

@AllArgsConstructor
public class KeyGeneratorImpl implements KeyGenerator {

    private static final int[] RULE_10 = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
    private static final int[] RULE_8 = { 6, 3, 7, 4, 8, 5, 10, 9 };

    private static final int KEY_SIZE = 10;
    private static final int SHIFT_KEY_ONE = 1;
    private static final int SHIFT_KEY_TWO = 3;

    private final BitSetUtil bitSetUtil;

    @Override
    public BitSet generateKey1(BitSet inputKey) {
        return generateKey(inputKey, SHIFT_KEY_ONE);
    }

    @Override
    public BitSet generateKey2(BitSet inputKey) {
        return generateKey(inputKey, SHIFT_KEY_TWO);
    }

    private BitSet generateKey(BitSet bitSet, int shift) {
        BitSet output = bitSetUtil.replace(bitSet, RULE_10);

        output = bitSetUtil.shift(output, KEY_SIZE, shift);
        output = bitSetUtil.replace(output, RULE_8);

        return output;
    }

}
