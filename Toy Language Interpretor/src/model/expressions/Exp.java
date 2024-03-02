package model.expressions;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.types.Type;
import model.values.Value;

public interface Exp {
    //    Value eval(MyIDictionary<String, Value> tbl) throws ExceptionExp, ExceptionADT;
    Value eval(MyIDictionary<String, Value> tbl, MyHeapTable heap) throws ExceptionExp, ExceptionADT;
    Exp deepCopy();
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws ExceptionExp, ExceptionADT;
}
