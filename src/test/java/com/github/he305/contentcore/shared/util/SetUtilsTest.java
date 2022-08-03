package com.github.he305.contentcore.shared.util;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetUtilsTest {

    @Test
    void findUniqueInFirstSet_valid() {
        Set<Integer> expected = Set.of(1, 2);

        Set<Integer> firstSet = Set.of(1, 2, 3, 4);
        Set<Integer> secondSet = Set.of(3, 4, 5, 6);

        Set<Integer> actual = SetUtils.findUniqueInFirstSet(firstSet, secondSet);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void findUniqueInFirstSet_wholeUnique() {
        Set<Integer> expected = Set.of(1, 2, 3, 4);
        Set<Integer> firstSet = Set.of(1, 2, 3, 4);
        Set<Integer> secondSet = Set.of(5, 6, 7, 8);

        Set<Integer> actual = SetUtils.findUniqueInFirstSet(firstSet, secondSet);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void findUniqueInFirstSet_noUniques() {
        Set<Integer> firstSet = Set.of(1, 2, 3, 4);
        Set<Integer> secondSet = Set.of(1, 2, 3, 4);

        Set<Integer> actual = SetUtils.findUniqueInFirstSet(firstSet, secondSet);
        assertTrue(actual.isEmpty());
    }


}