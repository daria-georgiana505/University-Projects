package model.adt;

import exceptions.ExceptionADT;
import model.values.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    Map<K,V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public boolean containsKey(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public List<V> values() {
        List<V> vals = new ArrayList<V>(this.dictionary.values());
        return vals;
    }

    @Override
    public V get(K key) throws ExceptionADT {
        if(dictionary.containsKey(key)) {
            return this.dictionary.get(key);
        }
        else
        {
            throw new ExceptionADT("Key does not exist");
        }
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public List<K> keys() {
        List<K> keys = new ArrayList<K>(this.dictionary.keySet());
        return keys;
    }

    @Override
    public void put(K key, V val){
        this.dictionary.put(key, val);
    }

    @Override
    public void remove(K key) {
        this.dictionary.remove(key);
    }

    @Override
    public int size() {
        return this.dictionary.size();
    }

    @Override
    public MyIDictionary<K, V> clone() {
        MyIDictionary<K, V> newDictionary = new MyDictionary<>();
        for (Map.Entry<K, V> entry : this.dictionary.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            newDictionary.put(key, value);
        }
        return newDictionary;
    }

    @Override
    public void setContent(Map<K, V> map) {
        this.dictionary = map;
    }

    @Override
    public Map<K, V> getContent() {
        return this.dictionary;
    }

    @Override
    public String toString() {
        String return_as_string = "";
        for(K elem:this.dictionary.keySet())
        {
            return_as_string += elem.toString()+" --> "+this.dictionary.get(elem)+"\n";
        }
        return return_as_string;
    }
}
