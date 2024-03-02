package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt{
    Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "openRFile(" +exp+')';
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Value value = exp.eval(state.getSymTable(), state.getHeapTable());
        if(!(value.getType().equals(new StringType())))
            throw new ExceptionStmt(value+" is not a StringType");
        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.containsKey(fileName))
            throw new ExceptionStmt(fileName+" is already opened");
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(fileName.getVal()));
        }
        catch(IOException ioException)
        {
            throw new ExceptionStmt(fileName+" could not be opened");
        }
        fileTable.put(fileName, br);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new ExceptionStmt("The expression in OpenRFile has not the type string");
    }
}
