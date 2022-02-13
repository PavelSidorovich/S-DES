package com.sidorovich.tatarinov.sdes.util;

import com.sidorovich.tatarinov.sdes.exception.InvalidInputKeyException;
import com.sidorovich.tatarinov.sdes.model.BitArray;

public interface KeyGenerator {

    BitArray generateKey1(BitArray inputKey) throws InvalidInputKeyException;

    BitArray generateKey2(BitArray inputKey) throws InvalidInputKeyException;

}
