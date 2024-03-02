package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.types.BoolType;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    Exp exp;

    public CloseRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "closeRFile(" +exp+')';
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Value value = exp.eval(state.getSymTable(), state.getHeapTable());
        if(!(value.getType().equals(new StringType())))
            throw new ExceptionStmt(value+" is not a StringType");
        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        if (!fileTbl.containsKey(fileName))
            throw new ExceptionStmt(fileName+" is not in SymTable");
        BufferedReader br = fileTbl.get(fileName);
        try
        {
            br.close();
        }
        catch (IOException ioException)
        {
            throw new ExceptionStmt("error when closing "+fileName);
        }
        fileTbl.remove(fileName);
//        return state;
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new ExceptionStmt("The expression in CloseRFile has not the type string");
    }
}
