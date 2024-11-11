package com.enalto.paralellstream;

import com.enalto.domain.checkout.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    private final LinkedListSpliteratorExample linkedListSpliteratorExample =
            new LinkedListSpliteratorExample();

    @Test
    void multiplyEachValue() {
        // scenario
        int size = 1_000_000;
        LinkedList<Integer> integerLinkedList = DataSet.generateLinkedList(size);

        //execution
        linkedListSpliteratorExample.multiplyEachValue(integerLinkedList, 2, false);

        //verification

        Assertions.assertEquals(size, integerLinkedList.size());

    }

    @Test
    void multiplyEachValueParallel() {
        // scenario
        int size = 1_000_000;
        LinkedList<Integer> integerLinkedList = DataSet.generateLinkedList(size);

        //execution
        linkedListSpliteratorExample.multiplyEachValue(integerLinkedList, 2, true);

        //verification

        Assertions.assertEquals(size, integerLinkedList.size());

    }

}