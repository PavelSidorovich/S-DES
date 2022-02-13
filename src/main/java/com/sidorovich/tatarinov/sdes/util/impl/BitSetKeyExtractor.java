package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.exception.InvalidInputKeyException;
import com.sidorovich.tatarinov.sdes.model.BitArray;
import com.sidorovich.tatarinov.sdes.util.KeyExtractor;
import com.sidorovich.tatarinov.sdes.util.Validator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BitSetKeyExtractor implements KeyExtractor {

    private final Validator validator;

    @Override
    public BitArray extract(String inputKey) throws InvalidInputKeyException {
        BitArray bitArray = new BitArray(inputKey.length());

        if (!validator.validate(inputKey)) {
            throw new InvalidInputKeyException(inputKey);
        }
        for (int i = 0; i < inputKey.length(); i++) {
            bitArray.set(i, inputKey.charAt(i) == '1');
        }
        return bitArray;
    }

}
