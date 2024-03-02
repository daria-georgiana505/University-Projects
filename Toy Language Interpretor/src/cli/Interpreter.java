package cli;

import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import cli.commands.ExitCommand;
import cli.commands.RunExample;

public class Interpreter {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                                new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+")),
                                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"),
                                        new ValueExp(new IntValue(1)), "+")), new PrintStmt(new VarExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),
                        new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
                new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(
                new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"),
                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));

        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(3))), new CompStmt(new AssignStmt("b",
                        new ValueExp(new IntValue(5))), new IfStmt(new RelExp(new VarExp("a"), new VarExp("b"), ">="),
                        new PrintStmt(new ValueExp(new StringValue("is greater or equal!"))), new PrintStmt(new ValueExp(new
                        StringValue("is less..."))))))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
                new ValueExp(new IntValue(10))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new New("a", new VarExp("v")), new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                        new PrintStmt(new ArithExp(new ReadHeap(new ReadHeap(new VarExp("a"))), new ValueExp(new IntValue(5)), "+")))))));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
                new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))), new CompStmt(
                new WriteHeap("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp(new ReadHeap(
                new VarExp("v")), new ValueExp(new IntValue(5)), "+"))))));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
                new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new New("a", new VarExp("v")), new CompStmt(new New("v", new ValueExp(
                        new IntValue(30))), new PrintStmt(new ReadHeap(new ReadHeap(new VarExp("a")))))))));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(
                new IntValue(4))), new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(new VarExp("v"),
                        new ValueExp(new IntValue(1)), "-")))), new PrintStmt(new VarExp("v")))));

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
                new ValueExp(new IntValue(20))), new CompStmt(new New("v", new ValueExp(new IntValue(50))),
                new CompStmt(new New("v", new ValueExp(new IntValue(100))), new PrintStmt(new ReadHeap(new VarExp("v")))))));

        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(
                new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new New("a",
                new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new WriteHeap("a", new ValueExp(
                new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(
                new VarExp("v")), new PrintStmt(new ReadHeap(new VarExp("a"))))))), new CompStmt(new PrintStmt(
                new VarExp("v")), new PrintStmt(new ReadHeap(new VarExp("a")))))))));

        IStmt ex12 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("cnt", new IntType()),
                new CompStmt(new New("v1", new ValueExp(new IntValue(1))), new CompStmt(new createSemaphore("cnt", new ReadHeap(
                        new VarExp("v1"))), new CompStmt(new ForkStmt(new CompStmt(new acquire("cnt"), new CompStmt(new WriteHeap("v1",
                        new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(10)), "*")), new CompStmt(new PrintStmt(
                                new ReadHeap(new VarExp("v1"))), new release("cnt"))))), new CompStmt(new ForkStmt(new CompStmt(new acquire("cnt"),
                        new CompStmt(new WriteHeap("v1", new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(10)), "*")),
                                new CompStmt(new WriteHeap("v1", new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(2)), "*")),
                                        new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v1"))), new release("cnt")))))), new CompStmt(new acquire("cnt"),
                        new CompStmt(new PrintStmt(new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(1)), "-")), new release("cnt")))))))));

//        //error AssignStmt
//        IStmt ex13 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("v", new ValueExp(new BoolValue(true))),
//                        new PrintStmt(new VarExp("v"))));
//
//        //error OpenRFile
//        IStmt ex14 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
//                new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFile(new ValueExp(new IntValue(123))), new CompStmt(
//                new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
//                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"),
//                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
//
//        //error CloseRFile
//        IStmt ex15 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
//                new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(
//                new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
//                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"),
//                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new ValueExp(new IntValue(123)))))))))));
//
//        //error IfStmt
//        IStmt ex16 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
//                new IfStmt(new ValueExp(new IntValue(4)), new PrintStmt(new VarExp("v")), new PrintStmt(new ValueExp(new StringValue("ELSE"))))));
//
//        //error New
//        IStmt ex17 = new CompStmt(new VarDeclStmt("v", new IntType()), new New("v", new ValueExp(new IntValue(20))));
//
//        //error ReadFile
//        IStmt ex18 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
//                new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(
//                new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new ValueExp(new IntValue(123)),
//                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"),
//                "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
//
//        //error WhileStmt
//        IStmt ex19 = new WhileStmt(new ValueExp(new IntValue(4)), new PrintStmt(new ValueExp(new StringValue("ELSE"))));
//
//        //error WriteHeap
//        IStmt ex20 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new WriteHeap("v", new ValueExp(
//                new IntValue(30))), new PrintStmt(new ReadHeap(new VarExp("v")))));
//
//        //error ArithExp
//        IStmt ex21 = new PrintStmt(new ArithExp(new ValueExp(new IntValue(2)), new ValueExp(new BoolValue(true)), "+"));
//
//        //error LogicExp
//        IStmt ex22 = new PrintStmt(new LogicExp(new ValueExp(new IntValue(2)), new ValueExp(new BoolValue(true)), "and"));
//
//        //error RelExp
//        IStmt ex23 = new PrintStmt(new RelExp(new ValueExp(new IntValue(2)), new ValueExp(new BoolValue(true)), "<"));
//
//        //error ReadHeap
//        IStmt ex24 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
//                new ValueExp(new IntValue(20))), new PrintStmt(new ReadHeap(new ValueExp(new IntValue(2))))));

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ex1));
        menu.addCommand(new RunExample("2", ex2.toString(), ex2));
        menu.addCommand(new RunExample("3", ex3.toString(), ex3));
        menu.addCommand(new RunExample("4", ex4.toString(), ex4));
        menu.addCommand(new RunExample("5", ex5.toString(), ex5));
        menu.addCommand(new RunExample("6", ex6.toString(), ex6));
        menu.addCommand(new RunExample("7", ex7.toString(), ex7));
        menu.addCommand(new RunExample("8", ex8.toString(), ex8));
        menu.addCommand(new RunExample("9", ex9.toString(), ex9));
        menu.addCommand(new RunExample("10", ex10.toString(), ex10));
        menu.addCommand(new RunExample("11", ex11.toString(), ex11));
        menu.addCommand(new RunExample("12", ex12.toString(), ex12));

//        menu.addCommand(new RunExample("13", ex13.toString(), ex13));
//        menu.addCommand(new RunExample("14", ex14.toString(), ex14));
//        menu.addCommand(new RunExample("15", ex15.toString(), ex15));
//        menu.addCommand(new RunExample("16", ex16.toString(), ex16));
//        menu.addCommand(new RunExample("17", ex17.toString(), ex17));
//        menu.addCommand(new RunExample("18", ex18.toString(), ex18));
//        menu.addCommand(new RunExample("19", ex19.toString(), ex19));
//        menu.addCommand(new RunExample("20", ex20.toString(), ex20));
//        menu.addCommand(new RunExample("21", ex21.toString(), ex21));
//        menu.addCommand(new RunExample("22", ex22.toString(), ex22));
//        menu.addCommand(new RunExample("23", ex23.toString(), ex23));
//        menu.addCommand(new RunExample("24", ex24.toString(), ex24));

        try {
            menu.runTypeCheck();
            menu.show();
        }
        catch (ExceptionExp | ExceptionADT | ExceptionStmt e) {
            System.out.println(e.getMessage() + "\n");
        }
    }
}
