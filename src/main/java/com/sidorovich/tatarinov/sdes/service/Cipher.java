package com.sidorovich.tatarinov.sdes.service;

import java.util.BitSet;

public interface Cipher {

    BitSet cipher(BitSet bitSet, BitSet key1, BitSet key2);

}
