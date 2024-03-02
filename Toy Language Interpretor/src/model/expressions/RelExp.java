package model.expressions;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import model.adt.MyHeapTable;
import model.adt.MyIDictionary;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelExp implements Exp{
    Exp e1;
    Exp e2;
    String op;

    public RelExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyHeapTable heap) throws ExceptionExp, ExceptionADT {
        Value v1;
        Value v2;
        v1=e1.eval(tbl, heap);
        if(v1.getType().equals(new IntType()))
        {
            v2=e2.eval(tbl, heap);
            if(v2.getType().equals(new IntType()))
            {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1=i1.getVal();
                n2=i2.getVal();
                return switch (op) {
                    case "<" -> new BoolValue(n1 < n2);
                    case "<=" -> new BoolValue(n1 <= n2);
                    case ">" -> new BoolValue(n1 > n2);
                    case ">=" -> new BoolValue(n1 >= n2);
                    case "==" -> new BoolValue(n1 == n2);
                    case "!=" -> new BoolValue(n1 != n2);
                    default -> throw new ExceptionExp("operand does not exist");
                };
            }
            else
                throw new ExceptionExp("second operand is not an integer");
        }
        else
            throw new ExceptionExp("first operand is not an integer");

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
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            }
            else throw new ExceptionExp("RelExp: second operand is not an integer");
        }
        else throw new ExceptionExp("RelExp: first operand is not an integer");
    }

    @Override
    public String toString() {
        switch (op) {
            case "<" -> {
                return e1 + " < " + e2;
            }
            case "<=" -> {
                return e1 + " <= " + e2;
            }
            case ">" -> {
                return e1 + " > " + e2;
            }
            case ">=" -> {
                return e1 + " >= " + e2;
            }
            case "==" -> {
                return e1 + " == " + e2;
            }
            case "!=" -> {
                return e1 + " != " + e2;
            }
            default -> {
                return "operation not found";
            }
        }
    }
}
