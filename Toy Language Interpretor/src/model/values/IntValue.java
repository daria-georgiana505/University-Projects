package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value{
    int val;
    public IntValue(int v)
    {
        this.val = v;
    }
    public int getVal()
    {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object another)
    {
        if(!(another instanceof IntValue))
            return false;
        return this.val == ((IntValue) another).val;
    }
}
