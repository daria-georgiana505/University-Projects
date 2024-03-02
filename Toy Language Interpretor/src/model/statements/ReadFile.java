package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    Exp exp;
    String var_name;

    public ReadFile(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public String toString() {
        return "readFile(" +exp + ", " + var_name + ')';
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        if (!(symTbl.containsKey(var_name)))
            throw new ExceptionStmt(var_name+" is not in SymTable");
        Value value = symTbl.get(var_name);
        if(!(value.getType().equals(new IntType())))
            throw new ExceptionStmt(value+" is not an int type");
        value = exp.eval(symTbl, state.getHeapTable());
        if(!(value.getType().equals(new StringType())))
            throw new ExceptionStmt(value+" is not a StringType");
        StringValue fileName = (StringValue) value;
        if(!(fileTbl.containsKey(fileName)))
            throw new ExceptionStmt(fileName+" not in FileTable");
        BufferedReader br = fileTbl.get(fileName);
        try
        {
            String line = br.readLine();
            if(line == null)
                line = "0";
            symTbl.put(var_name, new IntValue(Integer.parseInt(line)));
        }
        catch (IOException ioException)
        {
            throw new ExceptionStmt("cannot read from file "+fileName);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), new String(var_name));
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new ExceptionStmt("The expression in ReadFile has not the type string");
    }
}
