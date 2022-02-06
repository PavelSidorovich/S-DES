package com.sidorovich.tatarinov.sdes.util;

import com.sidorovich.tatarinov.sdes.exception.InvalidInputKeyException;

import java.util.BitSet;

public interface KeyExtractor {

    BitSet extract(String inputKey) throws InvalidInputKeyException;

}
