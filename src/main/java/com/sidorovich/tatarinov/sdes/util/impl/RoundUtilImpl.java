package com.sidorovich.tatarinov.sdes.util.impl;

import com.sidorovich.tatarinov.sdes.model.Pair;
import com.sidorovich.tatarinov.sdes.util.BitSetUtil;
import com.sidorovich.tatarinov.sdes.util.KeyExtractor;
import com.sidorovich.tatarinov.sdes.util.RoundUtil;

import java.util.BitSet;

public class RoundUtilImpl implements RoundUtil {

    private static final int[] RULE_EP = { 4, 1, 2, 3, 2, 3, 4, 1 };
    private static final int[] RULE_P_4 = { 2, 4, 3, 1 };
    private static final int[][] S_BOX_1 =
            { { 1, 3, 0, 3 }, { 0, 2, 2, 1 }, { 3, 1, 1, 3 }, { 2, 0, 3, 2 } };
    private static final int[][] S_BOX_2 =
            { { 0, 2, 3, 2 }, { 1, 0, 0, 1 }, { 2, 1, 1, 0 }, { 3, 3, 0, 3 } };

    private static final int BITSET_SIZE = 8;
    private static final int BIT_COUNT = 2;

    private static final String ONE = "1";
    private static final String ZERO = "0";

    private final BitSetUtil bitSetUtil;
    private final KeyExtractor keyExtractor;

    public RoundUtilImpl(BitSetUtil bitSetUtil) {
        this.bitSetUtil = bitSetUtil;
        this.keyExtractor = new BitSetKeyExtractor(new BitSetValidator(BIT_COUNT));
    }

    @Override
    public BitSet round(BitSet source, BitSet key) {
        Pair<BitSet, BitSet> bitSetPair = bitSetUtil.split(source, BITSET_SIZE);
        BitSet rightPart = bitSetUtil.replace(bitSetPair.getObject2(), RULE_EP);
        BitSet xorBitSet = (BitSet) rightPart.clone();

        xorBitSet.xor(key);
        BitSet result = processPartsUsingBoxes(xorBitSet);
        result = bitSetUtil.replace(result, RULE_P_4);
        result.xor(bitSetPair.getObject1());

        return bitSetUtil.concat(new Pair<>(result, bitSetPair.getObject2()), BITSET_SIZE);
    }

    private BitSet processPartsUsingBoxes(BitSet bitSet) {
        Pair<BitSet, BitSet> partsToProcess = bitSetUtil.split(bitSet, BITSET_SIZE);

        BitSet part1 = processUsingBox(partsToProcess.getObject1(), S_BOX_1);
        BitSet part2 = processUsingBox(partsToProcess.getObject2(), S_BOX_2);

        return bitSetUtil.concat(new Pair<>(part1, part2), 4);
    }

    private BitSet processUsingBox(BitSet bitSet, int[][] box) {
        final BitSet rowBitset = new BitSet(BIT_COUNT);
        final BitSet colBitset = new BitSet(BIT_COUNT);

        rowBitset.set(0, bitSet.get(0));
        rowBitset.set(1, bitSet.get(3));
        colBitset.set(0, bitSet.get(1));
        colBitset.set(1, bitSet.get(2));
        Pair<Integer, Integer> indexes = getColAndRowIndexes(colBitset, rowBitset);
        String binaryString = String.format("%2s", Integer.toBinaryString(
                box[indexes.getObject1()][indexes.getObject2()])
        ).replace(' ', '0');

        return keyExtractor.extract(binaryString);
    }

    private Pair<Integer, Integer> getColAndRowIndexes(BitSet colBitSet, BitSet rowBitSet) {
        final int radix = 2;
        StringBuilder colIndexString = new StringBuilder();
        StringBuilder rowIndexString = new StringBuilder();

        for (int i = 0; i < BIT_COUNT; i++) {
            colIndexString.append(colBitSet.get(i)? ONE : ZERO);
            rowIndexString.append(rowBitSet.get(i)? ONE : ZERO);
        }
        int colIndex = Integer.parseInt(colIndexString.toString(), radix);
        int rowIndex = Integer.parseInt(rowIndexString.toString(), radix);

        return new Pair<>(colIndex, rowIndex);
    }

}
