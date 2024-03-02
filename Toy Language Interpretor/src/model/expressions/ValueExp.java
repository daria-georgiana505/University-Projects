package model.expressions;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapTable heap) throws ExceptionExp, ExceptionADT {
        return this.e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
