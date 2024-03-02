package controller;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.PrgState;
import model.adt.MyHeapTable;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public List<PrgState> getPrgStates()
    {
        return this.repo.getPrgList();
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws ExceptionExp, ExceptionADT, ExceptionStmt, IOException, RuntimeException, InterruptedException {
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList). stream()
                . map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                })
                .filter(p -> p!=null)
                .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        repo.setPrgList(prgList);
    }

    public void allStep() throws ExceptionExp, ExceptionADT, ExceptionStmt, IOException, RuntimeException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(!prgList.isEmpty()){
            PrgState prg = prgList.get(0);
            prg.getHeapTable().setContent(
                    safeGarbageCollector(
                            getAllAddresses(prg.getSymTable().getContent().values(), prg.getHeapTable()),
                            prg.getHeapTable().getContent()
                    )
            );

            this.oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public void oneStep() throws ExceptionExp, ExceptionADT, ExceptionStmt, IOException, RuntimeException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        if(!prgList.isEmpty()){
            PrgState prg = prgList.get(0);
            prg.getHeapTable().setContent(
                    safeGarbageCollector(
                            getAllAddresses(prg.getSymTable().getContent().values(), prg.getHeapTable()),
                            prg.getHeapTable().getContent()
                    )
            );

            this.oneStepForAllPrg(prgList);
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    List<Integer> getAllAddresses(Collection<Value> symTableValues, MyHeapTable heapTable){
        ConcurrentLinkedDeque<Integer> symTableAdr = symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

        symTableAdr.stream()
                .forEach(a-> {
                    Value v= heapTable.getContent().get(a);
                    if (v instanceof  RefValue)
                        if (!symTableAdr.contains(((RefValue)v).getAddress()))
                            symTableAdr.add(((RefValue)v).getAddress());});

        return symTableAdr.stream().toList();
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
