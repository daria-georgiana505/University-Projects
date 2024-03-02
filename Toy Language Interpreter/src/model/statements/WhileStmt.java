package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.Exp;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIStack<IStmt> stk = state.getExeStack();
        Value val = exp.eval(state.getSymTable(), state.getHeapTable());
        if(!(val instanceof BoolValue))
            throw new ExceptionStmt("Value is not of BoolType");
        BoolValue bool_val = (BoolValue)val;
        if(bool_val.getVal())
        {
            stk.push(this);
            stk.push(this.stmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new ExceptionStmt("The condition of WHILE has not the type bool");
    }

    @Override
    public String toString() {
        return "WHILE(" + exp + ") {\n"+stmt + "\n}";
    }
}
