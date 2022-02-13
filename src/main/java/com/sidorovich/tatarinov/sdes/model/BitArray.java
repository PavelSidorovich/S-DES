package com.sidorovich.tatarinov.sdes.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class BitArray {

    private final boolean[] bits;

    public BitArray(int bitCount) {
        this.bits = new boolean[bitCount];
    }

    public BitArray(boolean[] bits) {
        this.bits = bits;
    }

    public BitArray cat(BitArray bitArray) {
        final boolean[] bits = bitArray.bits;
        final boolean[] output = new boolean[bits.length + this.bits.length];

        System.arraycopy(this.bits, 0, output, 0, this.bits.length);
        System.arraycopy(bits, 0, output, this.bits.length, bits.length);

        return new BitArray(output);
    }

    public void xor(BitArray bitArray) {
        boolean[] bits = bitArray.getBits();

        for (int i = 0; i < this.bits.length; i++) {
            this.bits[i] = this.bits[i] ^ bits[i];
        }
    }

    public void set(int bitIndex, boolean value) {
        bits[bitIndex] = value;
    }

    public BitArray get(int from, int to) {
        return new BitArray(Arrays.copyOfRange(bits, from, to));
    }

    public boolean get(int index) {
        return bits[index];
    }

    public int size() {
        return bits.length;
    }

}
