package repository;

import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> prgStateList;
    String logFilePath;

    public Repository(String logFilePath) {
        this.prgStateList = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void addProgramState(PrgState prgState) {
        prgStateList.add(prgState);
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgStateList;
    }

    @Override
    public void setPrgList(List<PrgState> prglist) {
        this.prgStateList = prglist;
    }


    @Override
    public void logPrgStateExec(PrgState prgState) throws IOException { // MyException, IOException {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(prgState.toString());
        logFile.close();
    }

    @Override
    public String toString() {
        return "Repository{" +
                "prgStateList=" + prgStateList +
                '}';
    }
}
