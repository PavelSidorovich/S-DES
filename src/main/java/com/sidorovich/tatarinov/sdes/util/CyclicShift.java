package com.sidorovich.tatarinov.sdes.util;

import java.util.BitSet;

public interface CyclicShift {

    BitSet shift(BitSet inputKey, int size, int position);

}
