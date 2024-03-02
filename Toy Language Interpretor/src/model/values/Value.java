package model.values;

import model.types.Type;

public interface Value {
    Type getType();
    Value deepCopy();
    boolean equals(Object another);
}
