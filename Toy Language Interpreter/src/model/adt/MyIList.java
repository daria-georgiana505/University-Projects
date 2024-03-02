package model.adt;

import exceptions.ExceptionADT;

import java.util.List;

public interface MyIList<T> {
    void add(T elem);
    int size();
    boolean isEmpty();
    boolean contains(T elem);
    void remove(T elem);
    T get (int index) throws ExceptionADT;
    void set(int index, T elem) throws ExceptionADT;
    void add(int index, T elem) throws ExceptionADT;
    int indexOf(T elem);
    MyIList<T> clone();

    public List<String> toStringAsList();
}
