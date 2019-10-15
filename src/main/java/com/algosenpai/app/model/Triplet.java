package com.algosenpai.app.model;

public class Triplet<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    /**
     * Initializes container to store a triplet.
     * @param first first item in triplet.
     * @param second second item in triplet.
     * @param third third item in triplet.
     */
    public Triplet(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }
}
