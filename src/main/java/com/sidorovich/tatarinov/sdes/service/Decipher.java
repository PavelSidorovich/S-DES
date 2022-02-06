package com.sidorovich.tatarinov.sdes.service;

import java.util.BitSet;

public interface Decipher {

    BitSet decipher(BitSet bitSet, BitSet key1, BitSet key2);

}
