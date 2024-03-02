package model.adt;

import exceptions.ExceptionADT;
import model.values.Value;

import java.util.List;
import java.util.Map;

public interface MyIDictionary<K, V> {
    boolean containsKey(K key);
    List<V> values();
    V get(K key) throws ExceptionADT; //lookup
    boolean isEmpty();
    List<K> keys();
    void put(K key, V val);
    void remove(K key);
    int size();
    MyIDictionary<K, V> clone();

    void setContent(Map<K, V> map);

    Map<K, V> getContent();
}
