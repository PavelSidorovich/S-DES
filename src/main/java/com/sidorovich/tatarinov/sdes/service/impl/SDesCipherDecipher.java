package com.sidorovich.tatarinov.sdes.service.impl;

import com.sidorovich.tatarinov.sdes.service.Cipher;
import com.sidorovich.tatarinov.sdes.service.Decipher;
import com.sidorovich.tatarinov.sdes.util.BitSetUtil;
import com.sidorovich.tatarinov.sdes.util.RoundUtil;
import com.sidorovich.tatarinov.sdes.util.impl.BitSetCyclicShift;
import com.sidorovich.tatarinov.sdes.util.impl.BitSetUtilImpl;
import com.sidorovich.tatarinov.sdes.util.impl.RoundUtilImpl;

import java.util.BitSet;

public class SDesCipherDecipher implements Cipher, Decipher {

    private static final int[] RULE_IP = { 2, 6, 3, 1, 4, 8, 5, 7 };
    private static final int[] RULE_IP_1 = { 4, 1, 3, 5, 7, 2, 8, 6 };

    private static final int BIT_COUNT = 8;

    private final BitSetUtil bitSetUtil;
    private final RoundUtil roundUtil;

    public SDesCipherDecipher() {
        this.bitSetUtil = new BitSetUtilImpl(new BitSetCyclicShift());
        this.roundUtil = new RoundUtilImpl(bitSetUtil);
    }

    @Override
    public BitSet cipher(BitSet bitSet, BitSet key1, BitSet key2) {
        BitSet output = bitSetUtil.replace(bitSet, RULE_IP);

        output = roundUtil.round(output, key1);
        output = bitSetUtil.changeParts(output, BIT_COUNT);
        output = roundUtil.round(output, key2);
        output = bitSetUtil.replace(output, RULE_IP_1);

        return output;
    }

    @Override
    public BitSet decipher(BitSet bitSet, BitSet key1, BitSet key2) {
        return cipher(bitSet, key2, key1);
    }

}
