package cli.commands;

import controller.Controller;
import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;

public class RunExample extends Command{
    private IStmt stmt;
    public RunExample(String key, String desc, IStmt stmt){
        super(key, desc);
        this.stmt = stmt;
    }

    public IStmt getStmt() {
        return this.stmt;
    }

    @Override
    public void execute(String fileName) {
        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> filetbl = new MyDictionary<>();
        MyHeapTable heaptbl = new MyHeapTable();
        MyISemaphoreTable semaphoreTable = new MySemaphoreTable();
        PrgState crtPrgState = new PrgState(stk, symtbl, out, filetbl, heaptbl, semaphoreTable, this.stmt);

        IRepository repo = new Repository(fileName);
        repo.addProgramState(crtPrgState);
        Controller controller = new Controller(repo);

        try {
            controller.allStep();
        } catch (ExceptionExp | ExceptionStmt | ExceptionADT | IOException | RuntimeException | InterruptedException exception) {
            System.out.println(exception.getMessage() + "\n");
        }
    }

    @Override
    public void runTypeCheck() throws ExceptionExp, ExceptionADT, ExceptionStmt{
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
        this.stmt.typecheck(typeEnv);
    }
}
