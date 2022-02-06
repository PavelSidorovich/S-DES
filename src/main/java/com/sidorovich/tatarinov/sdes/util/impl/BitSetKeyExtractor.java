package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.exception.InvalidInputKeyException;
import com.sidorovich.tatarinov.sdes.util.KeyExtractor;
import com.sidorovich.tatarinov.sdes.util.Validator;
import lombok.AllArgsConstructor;

import java.util.BitSet;

@AllArgsConstructor
public class BitSetKeyExtractor implements KeyExtractor {

    private final Validator validator;

    @Override
    public BitSet extract(String inputKey) throws InvalidInputKeyException {
        BitSet bitSet = new BitSet(inputKey.length());

        if (!validator.validate(inputKey)) {
            throw new InvalidInputKeyException(inputKey);
        }
        for (int i = 0; i < inputKey.length(); i++) {
            bitSet.set(i, inputKey.charAt(i) == '1');
        }
        return bitSet;
    }

}
