package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.types.Type;

public class SleepStmt implements IStmt{
    int number;

    public SleepStmt(int number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIStack<IStmt> stk = state.getExeStack();
        if(number > 0)
        {
            stk.push(new SleepStmt(number-1));
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "sleep(" +number + ')';
    }
}
