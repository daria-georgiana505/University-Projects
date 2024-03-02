package model.adt;

import exceptions.ExceptionADT;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MySemaphoreTable implements MyISemaphoreTable{
    Map<Integer, Pair<Integer, List<Integer>>> dictionary;
    private int lastAddr;

    public MySemaphoreTable() {
        this.dictionary = new HashMap<>();
        this.lastAddr = 0;
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> lookup(Integer key) throws ExceptionADT {
        if (dictionary.containsKey(key)) {
            return this.dictionary.get(key);
        } else {
            throw new ExceptionADT("Key does not exist");
        }
    }

    @Override
    public synchronized void put(Integer key, Pair<Integer, List<Integer>> val) {
            this.dictionary.put(key, val);
    }

    @Override
    public synchronized MyISemaphoreTable clone() {
        MyISemaphoreTable newDictionary = new MySemaphoreTable();
        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : this.dictionary.entrySet()) {
            Integer key = entry.getKey();
            Pair<Integer, List<Integer>> value = entry.getValue();
            newDictionary.put(key, value);
        }
        return newDictionary;
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, List<Integer>>> map) {
            this.dictionary = map;
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.dictionary;
    }

    @Override
    public synchronized int allocateNewAddr()
    {
        this.lastAddr += 1;
        return this.lastAddr;
    }

    @Override
    public String toString() {
        String return_as_string = "";
        for(Integer key:this.dictionary.keySet())
        {
            String value_string = this.dictionary.get(key).getKey()+ ", [" + String.join(", ", this.dictionary.get(key).getValue().stream().map(Objects::toString).collect(Collectors.toList())) + ']';
            return_as_string += key.toString()+" --> "+ value_string +"\n";
        }
        return return_as_string;
    }
}
