package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class WriteHeap implements IStmt{
    private String var_name;
    private Exp exp;

    public WriteHeap(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyHeapTable heapTable = state.getHeapTable();

        if(!symTable.containsKey(var_name))
            throw new ExceptionStmt(var_name+" is not in SymTable");
        Value var_value = symTable.get(var_name);
        if(!(var_value.getType() instanceof RefType))
            throw new ExceptionStmt(var_name+" is not of RefType");
        RefValue refValue = (RefValue) var_value;
        if(!heapTable.containsKey(refValue.getAddress()))
            throw new ExceptionStmt("RefValue is not in HeapTable");
        Value var_eval = exp.eval(symTable, heapTable);
        if(!var_eval.getType().equals(refValue.getLocationType()))
            throw new ExceptionStmt("Evaluated expression value not of "+refValue.getLocationType());
        heapTable.put(refValue.getAddress(), var_eval);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeap(new String(var_name), exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        if (typeEnv.get(var_name).equals(new RefType(exp.typecheck(typeEnv))))
            return typeEnv;
        else throw new ExceptionStmt("WriteHeap: variable not of RefType");
    }

    @Override
    public String toString() {
        return "wH(" + var_name + ", " + exp + ')';
    }
}
