package com.sidorovich.tatarinov.sdes.util;

import com.sidorovich.tatarinov.sdes.exception.InvalidInputKeyException;
import com.sidorovich.tatarinov.sdes.model.BitArray;

public interface KeyExtractor {

    BitArray extract(String inputKey) throws InvalidInputKeyException;

}
