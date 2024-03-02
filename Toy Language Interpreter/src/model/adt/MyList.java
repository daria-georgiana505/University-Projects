package model.adt;

import exceptions.ExceptionADT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyList<T> implements MyIList<T> {
    List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T elem) {
        this.list.add(elem);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(T elem) {
        return this.list.contains(elem);
    }

    @Override
    public void remove(T elem) {
        this.list.remove(elem);
    }

    @Override
    public T get (int index) throws ExceptionADT {
        try {
            return this.list.get(index);
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new ExceptionADT("Index out of bounds");
        }
    }

    @Override
    public void set(int index, T elem) throws ExceptionADT{
        try {
            this.list.set(index, elem);
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new ExceptionADT("Index out of bounds");
        }
    }

    @Override
    public void add(int index, T elem) throws ExceptionADT{
        try {
            this.list.add(index, elem);
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new ExceptionADT("Index out of bounds");
        }
    }

    @Override
    public int indexOf(T elem) {
        return this.list.indexOf(elem);
    }

    @Override
    public MyIList<T> clone() {
//        return (MyIList<T>) new ArrayList<T>(this.list);
        MyIList<T> newList = new MyList<>();
        for (T entry : this.list) {
            newList.add(entry);
        }
        return newList;
    }

    @Override
    public String toString() {
        String return_as_string = "";
        for(T elem:this.list)
        {
            return_as_string += elem.toString()+"\n";
        }
        return return_as_string;
    }

    @Override
    public List<String> toStringAsList()
    {
        List<String> stringList = new ArrayList<>();
        for(T elem:this.list)
        {
            stringList.add(elem.toString());
        }
        return stringList;
    }
}
