package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt;
    IStmt deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt;
}
