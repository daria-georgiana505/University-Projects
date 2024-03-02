package model.statements;
import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.Exp;
import model.values.Value;
import model.types.Type;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return id+" = "+exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.containsKey(id))
        {
            Value val = exp.eval(symTbl, state.getHeapTable());
            Type typId = (symTbl.get(id)).getType();
            if((val.getType()).equals(typId))
            {
                symTbl.put(id, val);
            }
            else
                throw new ExceptionStmt("Type of variable "+id+" and type of expression do not match");
        }
        else
            throw new ExceptionStmt("Variable "+id+" is not declared");
//        return state;
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(new String(id), exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typevar = typeEnv.get(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new ExceptionStmt("Assignment: right hand side and left hand side have different types ");
    }
}
