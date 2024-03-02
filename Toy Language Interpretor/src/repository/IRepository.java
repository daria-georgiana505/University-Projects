package repository;

import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void addProgramState(PrgState prgState);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prglist);
    void logPrgStateExec(PrgState prgState) throws IOException;
}
