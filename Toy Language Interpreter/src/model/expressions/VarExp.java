package model.expressions;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapTable heap) throws ExceptionExp, ExceptionADT {
        if(tbl.containsKey(id)) {
            return tbl.get(id);
        }
        else
        {
            throw new ExceptionExp("Variable "+id+" is not defined");
        }
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(new String(id));
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT {
        return typeEnv.get(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
