package com.sidorovich.tatarinov.sdes.util;

import com.sidorovich.tatarinov.sdes.exception.InvalidInputKeyException;

import java.util.BitSet;

public interface KeyGenerator {

    BitSet generateKey1(BitSet inputKey) throws InvalidInputKeyException;

    BitSet generateKey2(BitSet inputKey) throws InvalidInputKeyException;

}
