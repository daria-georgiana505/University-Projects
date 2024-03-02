package model;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import javafx.util.Pair;
import model.adt.*;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyHeapTable heapTable;
    MyISemaphoreTable semaphoreTable;

    public int id;
    private static AtomicInteger lastId = new AtomicInteger(0);

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIDictionary<StringValue, BufferedReader> filetbl, MyHeapTable heaptbl,  MyISemaphoreTable semtbl, IStmt prg)
    {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = filetbl;
        this.heapTable = heaptbl;
        this.semaphoreTable = semtbl;
        this.originalProgram = prg.deepCopy();
        this.id = getNewPrgStateId();
        stk.push(prg);
    }

    public MyIStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return this.symTable;
    }

    public MyIList<Value> getOut() {
        return this.out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyHeapTable getHeapTable() {
        return this.heapTable;
    }
    public MyISemaphoreTable getSemaphoreTable()
    {
        return this.semaphoreTable;
    }

    public IStmt getOriginalProgram() {
        return this.originalProgram;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setHeapTable(MyHeapTable heapTable) {
        this.heapTable = heapTable;
    }
    public void setSemaphoreTable(MyISemaphoreTable semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public Boolean isNotCompleted()
    {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws ExceptionExp, ExceptionADT, ExceptionStmt {
        if(exeStack.isEmpty())
        {
            throw new ExceptionADT("ProgramState stack is empty");
        }
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public static synchronized int getNewPrgStateId()
    {
        return lastId.incrementAndGet();
    }

    @Override
    public String toString() {
        return  "-------------------------\n"+
                "ID = " + this.id + "\n" +
                "ExeStack:\n" + this.exeStack +
                "SymTable:\n" + this.symTable +
                "Out:\n" + this.out +
                "FileTable:\n"+this.fileTable+
                "HeapTable:\n"+this.heapTable+
                "SemaphoreTable:\n"+this.semaphoreTable+
                "-------------------------\n";
    }
}
