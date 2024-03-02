package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value{
    boolean val;
    public BoolValue(boolean v)
    {
        this.val = v;
    }
    public boolean getVal()
    {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public boolean equals(Object another)
    {
        if(!(another instanceof BoolValue))
            return false;
        return this.val == ((BoolValue) another).val;
    }
}
