package com.sidorovich.tatarinov.sdes.util;

import com.sidorovich.tatarinov.sdes.model.BitArray;

public interface CyclicShift {

    BitArray shift(BitArray inputKey, int position);

}
