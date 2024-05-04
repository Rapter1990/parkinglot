package com.project.parkinglot.model.mapper;

import java.util.Collection;
import java.util.List;

/**
 * Base mapper interface named {@link BaseMapper} for mapping between two objects.
 *
 * @param <S> The source object type.
 * @param <T> The target object type.
 */
public interface BaseMapper<S, T> {

    /**
     * Maps a source object to a target object.
     *
     * @param source The source object to map.
     * @return The mapped target object.
     */
    T map(S source);

    /**
     * Maps a collection of source objects to a list of target objects.
     *
     * @param sources The collection of source objects to map.
     * @return The list of mapped target objects.
     */
    List<T> map(Collection<S> sources);

}
