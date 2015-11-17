package it.geosolutions.vibi.utils;

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
}
