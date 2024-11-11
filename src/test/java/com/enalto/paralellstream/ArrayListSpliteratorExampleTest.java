package com.enalto.paralellstream;

import com.enalto.domain.checkout.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValueTest() {
        // scenario
        int size = 10_000_000;
        ArrayList<Integer> integerArrayList = DataSet.generateArrayList(size);

        //execution
        arrayListSpliteratorExample.multiplyEachValue(integerArrayList, 2);

        //verification

        Assertions.assertEquals(size, integerArrayList.size());

    }
}