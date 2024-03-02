package model.expressions;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class ReadHeap implements Exp{
    private Exp exp;

    public ReadHeap(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapTable heap) throws ExceptionExp, ExceptionADT {
        Value val = exp.eval(tbl, heap);
        if(!(val instanceof RefValue))
            throw new ExceptionExp(val+" is not of RefType");
        RefValue refval = (RefValue)val;

        return heap.get(refval.getAddress());
    }

    @Override
    public Exp deepCopy() {
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT {
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        }
        else
            throw new ExceptionExp("the ReadHeap argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "rH(" + exp + ')';
    }
}
