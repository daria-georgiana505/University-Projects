package cli;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import cli.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){ commands=new HashMap<>(); }
    public void addCommand(Command c){
        commands.put(c.getKey(),c);
    }
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("%4s)\n%s\n", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public void runTypeCheck() throws ExceptionExp, ExceptionADT, ExceptionStmt
    {

        for (Map.Entry<String, Command> entry : this.commands.entrySet())
        {
            Command com = entry.getValue();
            com.runTypeCheck();
        }
    }


    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue; }

            String filename = "";

            if (!key.equals("0")) {
                System.out.printf("\nFilename: ");
                filename = scanner.nextLine();
            }

            com.execute(filename);
        }
    }
}
