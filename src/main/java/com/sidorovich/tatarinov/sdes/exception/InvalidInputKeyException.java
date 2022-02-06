package com.sidorovich.tatarinov.sdes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidInputKeyException extends RuntimeException {

    private final String key;

}
