package com.sidorovich.tatarinov.sdes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Pair<T, F> {

    private final T object1;
    private final F object2;

}
