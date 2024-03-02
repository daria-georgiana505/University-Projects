package model.adt;

import exceptions.ExceptionADT;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws ExceptionADT;
    void push(T v);
    boolean isEmpty();
    T peek() throws ExceptionADT;
    List<T> getReverse();
    MyIStack<T> clone();

    List<String> toStringAsList();
}
