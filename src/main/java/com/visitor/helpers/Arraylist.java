/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.helpers;

import com.google.protobuf.ByteString;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author pseudo
 */
public class Arraylist<T> extends ArrayList<T> {

    public Arraylist() {
        super();
    }

    public Arraylist(T value) {
        add(value);
    }

    public Arraylist(List<T> values) {
        super(values);
    }

    public Arraylist(T[] values) {
        super(Arrays.asList(values));
    }

    public Arraylist<T> putIn(T value) {
        add(value);
        return this;
    }

    public Arraylist<T> putAllIn(Arraylist<T> values) {
        addAll(values);
        return this;
    }

    public Arraylist<T> putIn(int index, T value) {
        add(index, value);
        return this;
    }

    public Arraylist<T> removeFrom(T value) {
        remove(value);
        return this;
    }

    public Arraylist<T> removeFrom(int index) {
        remove(index);
        return this;
    }

    public void forEachInOrder(Consumer<? super T> c) {
        for (T t : this) {
            c.accept(t);
        }
    }

    public boolean hasOne(Predicate<? super T> predicate) {
        for (T t : this) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    public void add(T... ts) {
        super.addAll(Arrays.asList(ts));
    }

    public Arraylist<T> addAll(int index, T[] array) {
        super.addAll(index, Arrays.asList(array));
        return this;
    }

    public <R> List<? extends R> transform(Function<? super T, ? extends R> f) {
        return parallelStream().map(f).collect(Collectors.toList());
    }

    public List<String> transformToStringList() {
        return parallelStream().map(Objects::toString).collect(Collectors.toList());
    }

    public T getOrDefault(int index, T defaultValue) {
        return (index > -1 && index < size())? get(index):defaultValue;
    }

}
