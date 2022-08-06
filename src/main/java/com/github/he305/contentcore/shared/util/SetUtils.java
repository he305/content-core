package com.github.he305.contentcore.shared.util;

import java.util.Set;
import java.util.stream.Collectors;

public class SetUtils {
    private SetUtils() {
    }

    public static <T> Set<T> findUniqueInFirstSet(Set<T> firstSet, Set<T> secondSet) {
        return firstSet.stream().filter(element -> !secondSet.contains(element)).collect(Collectors.toSet());
    }
}
