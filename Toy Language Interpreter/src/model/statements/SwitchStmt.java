package model.statements;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.Exp;
import model.expressions.RelExp;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class SwitchStmt implements IStmt{
    Exp exp;
    Exp exp1;
    IStmt stmt1;
    Exp exp2;
    IStmt stmt2;
    IStmt stmt3;

    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        MyIStack<IStmt> stk = state.getExeStack();
        IfStmt ifStmt = new IfStmt(new RelExp(exp, exp1, "=="), stmt1, new IfStmt(new RelExp(exp, exp2, "=="), stmt2, stmt3));
        stk.push(ifStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws ExceptionExp, ExceptionADT, ExceptionStmt {
        Type typeExp=exp.typecheck(typeEnv);
        Type typeExp1=exp1.typecheck(typeEnv);
        Type typeExp2=exp2.typecheck(typeEnv);
        if(typeExp.equals(typeExp1) && typeExp.equals(typeExp2))
        {
            stmt1.typecheck(typeEnv.clone());
            stmt2.typecheck(typeEnv.clone());
            stmt3.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new ExceptionStmt("Switch error: the expressions don't have the same type");
    }

    @Override
    public String toString() {
        return "(switch(" +exp +")\n"+
                "  (case (" + exp1 +"): "+ stmt1 +")\n"+
                "  (case (" + exp2 +"): "+ stmt2 +")\n"+
                "  (default: "+ stmt3 +"))";
    }
}
