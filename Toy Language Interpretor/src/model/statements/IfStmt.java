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

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "IF("+exp+") {\n"+thenS+"\n}\nELSE {\n"+elseS+"\n}";
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIStack<IStmt> stk = state.getExeStack();
        Value val = exp.eval(state.getSymTable(), state.getHeapTable());
        if(val.getType().equals(new BoolType()))
        {
            BoolValue cond = (BoolValue)val;
            if(cond.getVal())
            {
                stk.push(thenS);
            }
            else
                stk.push(elseS);
        }
        else
            throw new ExceptionStmt("Conditional expression is not a boolean");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new ExceptionStmt("The condition of IF has not the type bool");
    }
}
