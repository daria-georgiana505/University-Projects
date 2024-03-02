package model.types;

import model.values.RefValue;
import model.values.Value;

public class RefType implements Type{
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).inner);
        else
            return false;
    }

    @Override
    public Value getDefaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(this.inner);
    }

    @Override
    public String toString() {
        return "Ref(" +inner.toString()+")";
    }

    public Type getInner() {
        return inner;
    }
}
