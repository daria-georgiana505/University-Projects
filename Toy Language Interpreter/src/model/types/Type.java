package model.types;

import model.values.Value;

public interface Type {
    boolean equals(Object another);
    Value getDefaultValue();
    Type deepCopy();
}
