package com.sidorovich.tatarinov.sdes.service;

import com.sidorovich.tatarinov.sdes.model.BitArray;

public interface Decipher {

    BitArray decipher(BitArray bitSet, BitArray key1, BitArray key2);

}
