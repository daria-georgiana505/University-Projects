package gui;

import cli.commands.Command;
import cli.commands.RunExample;
import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.statements.IStmt;

import java.util.*;

public class ListMenu {
    private Map<String, Command> commands;
    public ListMenu(){ commands=new HashMap<>(); }
    public void addCommand(Command c){
        commands.put(c.getKey(),c);
    }
    public ObservableList<String> returnMenu(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(Command com : commands.values()){
            String line=String.format("%s\n", com.getDescription());
            list.add(line);
        }
        return list;
    }
    //NEW
    public void runTypeCheck() throws ExceptionExp, ExceptionADT, ExceptionStmt
    {
        for (Map.Entry<String, Command> entry : this.commands.entrySet())
        {
            Command com = entry.getValue();
            com.runTypeCheck();
        }

    }

    public IStmt getStmtAtIndex(int i)
    {
        List<Command> tempList = new ArrayList<>(this.commands.values());
        RunExample stmtCommand = (RunExample) tempList.get(i);
        return stmtCommand.getStmt();
    }
}

