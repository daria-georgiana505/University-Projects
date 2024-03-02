package cli.commands;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;

public class ExitCommand extends Command{
    public ExitCommand(String key, String desc){
        super(key, desc);
    }

    @Override
    public void execute(String fileName) {
        System.exit(0);
    }

    @Override
    public void runTypeCheck()  throws ExceptionExp, ExceptionADT, ExceptionStmt {}
}
