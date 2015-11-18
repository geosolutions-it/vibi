package it.geosolutions.vibi.utils;

import java.util.HashMap;
import java.util.Map;

public final class Tuple<T1, T2> {

    public T1 first;
    public T2 second;

    private Tuple(T1 firts, T2 second) {
        this.first = firts;
        this.second = second;
    }

    public static <P1, P2> Tuple<P1, P2> tuple(P1 first, P2 second) {
        return new Tuple<>(first, second);
    }

    public static <R1, R2> Map<R1, R2> tuplesToMap(Tuple<R1, R2>... tuples) {
        Map<R1, R2> map = new HashMap<>();
        for (Tuple<R1, R2> tuple : tuples) {
            map.put(tuple.first, tuple.second);
        }
        return map;
    }
}
