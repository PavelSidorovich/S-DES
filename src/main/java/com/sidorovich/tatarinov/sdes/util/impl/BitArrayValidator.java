package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.util.Validator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BitArrayValidator implements Validator {

    private static final String BIT_SET_REGEX = "^[01]{%d}$";

    private final int bitCount;

    @Override
    public String getRegex() {
        return String.format(BIT_SET_REGEX, bitCount);
    }

}
