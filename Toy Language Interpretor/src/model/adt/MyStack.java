package model.adt;

import exceptions.ExceptionADT;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    private MyStack(Stack<T> stack) {
        this.stack = stack;
    }

    @Override
    public T pop() throws ExceptionADT {

        if(stack.isEmpty()) {
            throw new ExceptionADT("Stack is empty");
        }
        else
            return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public T peek() throws ExceptionADT{
        if(stack.isEmpty()) {
            throw new ExceptionADT("Stack is empty");
        }
        else
            return this.stack.peek();
    }

    @Override
    public List<T> getReverse() {
        List<T> auxList = Arrays.asList((T[])stack.toArray());
        Collections.reverse(auxList);
        return auxList;
    }

    @Override
    public MyIStack<T> clone() {
        //        return (MyIStack<T>) this.stack.clone();
        return new MyStack<T>((Stack<T>) this.stack.clone());
    }

    @Override
    public String toString() {
        String return_as_string = "";
        for(T elem:this.getReverse())
        {
            return_as_string += elem.toString()+"\n";
        }
        return return_as_string;
    }

    //TODO: toStringAsList method
    @Override
    public List<String> toStringAsList() {
        List<String> stringList = new ArrayList<>();
        for(T elem:this.getReverse())
        {
            stringList.add(elem.toString());
        }
        return stringList;
    }

}
