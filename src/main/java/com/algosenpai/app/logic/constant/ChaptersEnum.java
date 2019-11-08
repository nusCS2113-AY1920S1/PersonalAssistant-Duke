package com.algosenpai.app.logic.constant;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ChaptersEnum {
    sorting,
    linkedlist,
    bitmask;

    public static HashSet<String> getChapters() {
        return Stream.of(ChaptersEnum.values()).map(ChaptersEnum::name).collect(Collectors.toCollection(HashSet::new));
    }
}
