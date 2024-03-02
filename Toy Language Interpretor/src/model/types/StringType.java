package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements Type{
    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }
    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "String";
    }
}
