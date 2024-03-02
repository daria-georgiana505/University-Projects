package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyISemaphoreTable;
import model.adt.MyIStack;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.List;

public class release implements IStmt{
    String var;

    public release(String var) {
        this.var = var;
    }

    @Override
    public synchronized PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyISemaphoreTable semTbl = state.getSemaphoreTable();
        if(!symTbl.containsKey(var) || !symTbl.get(var).getType().equals(new IntType()))
        {
            throw new ExceptionStmt("release error: variable not in symTable or is not an integer");
        }
        else
        {
            int foundIndex = ((IntValue)symTbl.get(var)).getVal();
            if (semTbl.containsKey(foundIndex)) {
                Pair<Integer, List<Integer>> value = semTbl.lookup(foundIndex);
                List<Integer> List1 = value.getValue();
                int N1 = value.getKey();
                if (List1.contains(state.id)) {
                    List1.remove(Integer.valueOf(state.id));
                    semTbl.put(foundIndex, new Pair<>(N1, List1));
                }
            } else
                throw new ExceptionStmt("release error: index not in semaphoreTable");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new release(new String(var));
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        if(typeEnv.get(var).equals(new IntType()))
        {
            return typeEnv;
        }
        else
            throw new ExceptionStmt("release error: variable not of type int");
    }

    @Override
    public String toString() {
        return "release(" + var + ')';
    }
}
