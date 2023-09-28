package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingUtil {
    public static List<Long> toIds(Collection<? extends Named> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream().map(Named::getId).collect(Collectors.toList());
    }

    public static <T, V> List<T> toList(List<V> source, Function<V, T> transformer) {
        if (source == null) {
            return new ArrayList<>();
        }
        return source.stream().distinct().map(transformer).collect(Collectors.toList());
    }
}
