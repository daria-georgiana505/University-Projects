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

public class New implements IStmt{
    private String var_name;
    private Exp exp;

    public New(String var_name, Exp exp) {
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
        Value var_evaluated = exp.eval(symTable, heapTable);
        Type locationType = ((RefValue)var_value).getLocationType();
        if(!(locationType.equals(var_evaluated.getType())))
            throw new ExceptionStmt(var_name+" is not of "+var_evaluated.getType());
        Integer new_alloc_pos = heapTable.allocateNewAddr();
        heapTable.put(new_alloc_pos, var_evaluated);
        symTable.put(var_name, new RefValue(new_alloc_pos, locationType));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new New(new String(var_name), exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typevar = typeEnv.get(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new ExceptionStmt("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "NEW(" +var_name + ", " +exp + ')';
    }
}
