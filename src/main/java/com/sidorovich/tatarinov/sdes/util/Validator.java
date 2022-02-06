package com.sidorovich.tatarinov.sdes.util;

import java.util.regex.Pattern;

public interface Validator {

    String getRegex();

    default boolean validate(String input) {
        return Pattern.compile(getRegex()).matcher(input).matches();
    }

}
