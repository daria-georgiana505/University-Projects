package model.adt;

import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeapTable extends MyDictionary<Integer, Value>{
    private int lastAddr;

    public MyHeapTable() {
        this.lastAddr = 0;
    }

    private MyHeapTable(int lastAddr) {
        this.lastAddr = lastAddr;
    }

    @Override
    public MyHeapTable clone() {
        MyHeapTable newHeap = new MyHeapTable(this.lastAddr);
        newHeap.dictionary = new HashMap<>();
        for (Map.Entry<Integer, Value> entry : this.dictionary.entrySet()) {
            Integer key = entry.getKey();
            Value value = entry.getValue();
            newHeap.dictionary.put(key, value);
        }
        return newHeap;
    }

    public int allocateNewAddr()
    {
        this.lastAddr += 1;
        return this.lastAddr;
    }
}
