package com.sidorovich.tatarinov.sdes.service.impl;

import com.sidorovich.tatarinov.sdes.exception.FailOnReadException;
import com.sidorovich.tatarinov.sdes.exception.TextFileNotFoundException;
import com.sidorovich.tatarinov.sdes.model.Pair;
import com.sidorovich.tatarinov.sdes.service.Cipher;
import com.sidorovich.tatarinov.sdes.service.FileCipherDecipher;
import com.sidorovich.tatarinov.sdes.util.KeyExtractor;
import com.sidorovich.tatarinov.sdes.util.KeyGenerator;
import com.sidorovich.tatarinov.sdes.util.impl.BitSetCyclicShift;
import com.sidorovich.tatarinov.sdes.util.impl.BitSetKeyExtractor;
import com.sidorovich.tatarinov.sdes.util.impl.BitSetUtilImpl;
import com.sidorovich.tatarinov.sdes.util.impl.BitSetValidator;
import com.sidorovich.tatarinov.sdes.util.impl.KeyGeneratorImpl;
import javafx.scene.control.ProgressIndicator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;

public class FileCipherDecipherImpl implements FileCipherDecipher {

    private static final int BITS_COUNT = 8;
    private static final int BYTE_READ_SIZE = 10 * 1024 ; // 10 bytes

    private final Cipher cipher;
    private final KeyExtractor keyExtractor;
    private final KeyGenerator keyGenerator;

    public FileCipherDecipherImpl(Cipher cipher) {
        this.cipher = cipher;
        this.keyExtractor = new BitSetKeyExtractor(new BitSetValidator(10));
        this.keyGenerator = new KeyGeneratorImpl(new BitSetUtilImpl(new BitSetCyclicShift()));
    }

    @Override
    public void cipher(File input, File output, String key, ProgressIndicator indicator) {
        Pair<BitSet, BitSet> keys = generateKeys(key);

        process(input, output, keys.getObject1(), keys.getObject2(), indicator);
    }

    @Override
    public void decipher(File input, File output, String key, ProgressIndicator indicator) {
        Pair<BitSet, BitSet> keys = generateKeys(key);

        process(input, output, keys.getObject2(), keys.getObject1(), indicator);
    }

    private void process(File input, File output, BitSet key1, BitSet key2, ProgressIndicator indicator) {
        try (InputStream inputStream = new FileInputStream(input);
             OutputStream outputStream = new FileOutputStream(output)) {
            byte[] fileBytes = new byte[BYTE_READ_SIZE];
            final long allFileByteLength = input.length();
            long totalRead = 0;
            int readBytes;
            
            while ((readBytes = inputStream.read(fileBytes, 0, BYTE_READ_SIZE)) > 0) {
                writeBytes(key1, key2, outputStream, fileBytes, readBytes);
                totalRead += readBytes;
                indicator.setProgress((double) totalRead / allFileByteLength);
                fileBytes = new byte[BYTE_READ_SIZE];
            }
        } catch (FileNotFoundException e) {
            throw new TextFileNotFoundException();
        } catch (IOException e) {
            throw new FailOnReadException();
        }
    }

    private void writeBytes(BitSet key1, BitSet key2, OutputStream outputStream, byte[] fileBytes, int readBytes)
            throws IOException {
        final BitSet bitSet = BitSet.valueOf(fileBytes);

        for (int i = 0; i < readBytes; i++) {
            BitSet toCipher = buildCipheredByte(bitSet, i);
            BitSet ciphered = cipher.cipher(toCipher, key1, key2);
            writeCipheredByte(bitSet, i, ciphered);
        }
        outputStream.write(bitSet.toByteArray());
    }

    private void writeCipheredByte(BitSet bitSet, int i, BitSet ciphered) {
        for (int j = 0; j < BITS_COUNT; j++) {
            bitSet.set(j + (i * 8), ciphered.get(j));
        }
    }

    private BitSet buildCipheredByte(BitSet bitSet, int i) {
        BitSet toCipher = new BitSet(BITS_COUNT);
        for (int j = 0; j < BITS_COUNT; j++) {
            toCipher.set(j, bitSet.get(j + (i * 8)));
        }
        return toCipher;
    }

    private Pair<BitSet, BitSet> generateKeys(String key) {
        final BitSet inputKey = keyExtractor.extract(key);
        return new Pair<>(
                keyGenerator.generateKey1(inputKey),
                keyGenerator.generateKey2(inputKey)
        );
    }

}
