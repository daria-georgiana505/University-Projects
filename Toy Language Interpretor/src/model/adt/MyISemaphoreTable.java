package model.adt;

import exceptions.ExceptionADT;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface MyISemaphoreTable {
    boolean containsKey(Integer key);
    Pair<Integer, List<Integer>> lookup(Integer key) throws  ExceptionADT;
    void put(Integer key, Pair<Integer, List<Integer>> val);
    MyISemaphoreTable clone();
    void setContent(Map<Integer, Pair<Integer, List<Integer>>> map);
    int allocateNewAddr();
    Map<Integer, Pair<Integer, List<Integer>>> getContent();
}
