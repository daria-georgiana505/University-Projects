package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyISemaphoreTable;
import model.expressions.Exp;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;
import java.util.List;

public class createSemaphore implements IStmt{
    private String var;
    private Exp exp1;

    public createSemaphore(String var, Exp exp1) {
        this.var = var;
        this.exp1 = exp1;
    }

    @Override
    public synchronized PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyISemaphoreTable semTbl = state.getSemaphoreTable();
        Value number1 = exp1.eval(symTbl, state.getHeapTable());
        if(number1.getType().equals(new IntType()))
        {
            Integer number1_as_int = ((IntValue)number1).getVal();
            int newFreeLocation = semTbl.allocateNewAddr();
            semTbl.put(newFreeLocation, new Pair<Integer, List<Integer>>(number1_as_int, new ArrayList<>()));

            if (symTbl.containsKey(var) && symTbl.get(var).getType().equals(new IntType())) {
                symTbl.put(this.var, new IntValue(newFreeLocation));
            } else
                throw new ExceptionStmt("createSemaphore error: variable not in symTable or it's not an integer");
        }
        else
            throw new ExceptionStmt("createSemaphore error: expression is not an integer");

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new createSemaphore(new String(var), exp1.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        if(typeEnv.get(var).equals(new IntType()))
        {
            if(exp1.typecheck(typeEnv).equals(new IntType()))
            {
                return typeEnv;
            }
            else
                throw new ExceptionStmt("createSemaphore error: expression not of type int");
        }
        else
            throw new ExceptionStmt("createSemaphore error: variable not of type int");
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + ", " + exp1 +')';
    }
}
