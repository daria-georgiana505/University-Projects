package model.expressions;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    String op;

    public LogicExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapTable heap) throws ExceptionExp, ExceptionADT {
        Value v1;
        Value v2;
        v1=e1.eval(tbl, heap);
        if(v1.getType().equals(new BoolType()))
        {
            v2=e2.eval(tbl, heap);
            if(v2.getType().equals(new BoolType()))
            {
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1=i1.getVal();
                n2=i2.getVal();
                return switch (op) {
                    case "and" -> new BoolValue(n1 && n2);
                    case "or" -> new BoolValue(n1 || n2);
                    default -> throw new ExceptionExp("operand does not exist");
                };
            }
            else
                throw new ExceptionExp("second operand is not a boolean");
        }
        else
            throw new ExceptionExp("first operand is not a boolean");

    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else throw new ExceptionExp("LogicExp: second operand is not a boolean");
        }
        else throw new ExceptionExp("LogicExp: first operand is not a boolean");
    }

    @Override
    public String toString() {
        switch (op) {
            case "and" -> {
                return e1 + " AND " + e2;
            }
            case "or" -> {
                return e1 + " OR " + e2;
            }
            default -> {
                return "operation not found";
            }
        }
    }
}
