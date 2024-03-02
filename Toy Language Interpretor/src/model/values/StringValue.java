package model.values;

import model.types.StringType;
import model.types.Type;

import java.util.Objects;

public class StringValue implements Value{
    String val;

    public StringValue(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return this.val;
    }

    public String getVal()
    {
        return this.val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(Object another)
    {
        if(!(another instanceof StringValue))
            return false;
        return this.val.equals(((StringValue) another).val);
    }
}
