package cli.commands;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;

public abstract class Command {
    private String key, description;
    public Command(String key, String description) { this.key = key; this.description = description;}
    public abstract void execute(String fileName);
    public String getKey(){return key;}
    public String getDescription(){return description;}
    public abstract void runTypeCheck() throws ExceptionExp, ExceptionADT, ExceptionStmt;
}
