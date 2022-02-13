package com.sidorovich.tatarinov.sdes.service;

import com.sidorovich.tatarinov.sdes.model.BitArray;

import java.util.BitSet;

public interface Cipher {

    BitArray cipher(BitArray bitSet, BitArray key1, BitArray key2);

}
