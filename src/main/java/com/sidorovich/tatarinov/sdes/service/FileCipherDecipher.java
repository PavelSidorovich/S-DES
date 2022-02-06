package com.sidorovich.tatarinov.sdes.service;

import javafx.scene.control.ProgressIndicator;

import java.io.File;

public interface FileCipherDecipher {

    void cipher(File input, File output, String key, ProgressIndicator indicator);

    void decipher(File input, File output, String key, ProgressIndicator indicator);

}
