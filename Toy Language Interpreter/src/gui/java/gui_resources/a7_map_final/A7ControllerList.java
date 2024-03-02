package gui_resources.a7_map_final;

import cli.commands.RunExample;
import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import gui.ListMenu;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class A7ControllerList {
    ListMenu menu;

    @FXML
    private ListView<String> listViewO;

    @FXML
    private Label errorLabel;

    private  IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));

    private  IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
            new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                            new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), "*"), "+")),
                            new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"),
                                    new ValueExp(new IntValue(1)), "+")), new PrintStmt(new VarExp("b"))))));

    private IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),
                    new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

    private IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
            new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(
            new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
            "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"),
            "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));

    private IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
            new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(3))), new CompStmt(new AssignStmt("b",
                    new ValueExp(new IntValue(5))), new IfStmt(new RelExp(new VarExp("a"), new VarExp("b"), ">="),
                    new PrintStmt(new ValueExp(new StringValue("is greater or equal!"))), new PrintStmt(new ValueExp(new
                    StringValue("is less..."))))))));

    private IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
            new ValueExp(new IntValue(10))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new New("a", new VarExp("v")), new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))),
                    new PrintStmt(new ArithExp(new ReadHeap(new ReadHeap(new VarExp("a"))), new ValueExp(new IntValue(5)), "+")))))));

    private IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
            new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v"))), new CompStmt(
            new WriteHeap("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp(new ReadHeap(
            new VarExp("v")), new ValueExp(new IntValue(5)), "+"))))));

    private IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
            new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new New("a", new VarExp("v")), new CompStmt(new New("v", new ValueExp(
                    new IntValue(30))), new PrintStmt(new ReadHeap(new ReadHeap(new VarExp("a")))))))));

    private IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(
            new IntValue(4))), new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
            new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(new VarExp("v"),
                    new ValueExp(new IntValue(1)), "-")))), new PrintStmt(new VarExp("v")))));

    private IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",
            new ValueExp(new IntValue(20))), new CompStmt(new New("v", new ValueExp(new IntValue(50))),
            new CompStmt(new New("v", new ValueExp(new IntValue(100))), new PrintStmt(new ReadHeap(new VarExp("v")))))));

    private IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(
            new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new New("a",
            new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new WriteHeap("a", new ValueExp(
            new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(
            new VarExp("v")), new PrintStmt(new ReadHeap(new VarExp("a"))))))), new CompStmt(new PrintStmt(
            new VarExp("v")), new PrintStmt(new ReadHeap(new VarExp("a")))))))));

    private IStmt ex12 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("b", new BoolType()), new CompStmt(
            new VarDeclStmt("c", new BoolType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(false))), new CompStmt(new AssignStmt("b",
            new ValueExp(new BoolValue(true))), new CompStmt(new AssignStmt("c", new ValueExp(new BoolValue(true))), new CompStmt(new VarDeclStmt("r",
            new BoolType()), new CompStmt(new AssignStmt("r", new LogicExp(new VarExp("a"), new LogicExp(new VarExp("b"), new VarExp("c"), "and"), "or")),
            new PrintStmt(new VarExp("r"))))))))));

    private IStmt ex13 = new CompStmt(new NopStmt(), new CompStmt(new VarDeclStmt("x", new IntType()),
            new CompStmt(new AssignStmt("x", new ArithExp(new ValueExp(new IntValue(5)), new ValueExp(new IntValue(0)), "/")),
                    new PrintStmt(new VarExp("x")))));

    private IStmt ex14 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
            new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt(
            new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"),
            "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFile(new VarExp("varf"),
            "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));

    private IStmt ex15 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(
            new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new New("a",
            new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new WriteHeap("a", new ValueExp(
            new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(
            new VarExp("v")), new PrintStmt(new ReadHeap(new VarExp("a"))))))), new CompStmt(new AssignStmt("v",
            new ArithExp(new ValueExp(new IntValue(5)), new ValueExp(new IntValue(0)), "/")), new CompStmt(new PrintStmt(
            new VarExp("v")), new PrintStmt(new ReadHeap(new VarExp("a"))))))))));

    private IStmt ex16 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(
        new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new New("a",
        new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new WriteHeap("a", new ValueExp(
        new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(
        new VarExp("v")), new PrintStmt(new ReadHeap(new VarExp("a"))))))), new CompStmt(new PrintStmt(
        new VarExp("v")), new CompStmt(new PrintStmt(new ReadHeap(new VarExp("a"))), new CompStmt(new PrintStmt(
                new ValueExp(new IntValue(1))), new CompStmt(new PrintStmt(new ValueExp(new IntValue(2))),
        new PrintStmt(new ValueExp(new IntValue(3))))))))))));

    private IStmt ex17 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
            new CompStmt(new VarDeclStmt("c", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                    new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))), new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                            new CompStmt(new SwitchStmt(new ArithExp(new VarExp("a"), new ValueExp(new IntValue(10)), "*"),
                                    new ArithExp(new VarExp("b"), new VarExp("c"), "*"), new CompStmt(new PrintStmt(new VarExp("a")),
                                    new PrintStmt(new VarExp("b"))), new ValueExp(new IntValue(10)), new CompStmt(new PrintStmt(new ValueExp(
                                            new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))), new PrintStmt(new ValueExp(
                                                    new IntValue(300)))), new PrintStmt(new ValueExp(new IntValue(300))))))))));

    private IStmt ex18 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
            new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), "-")),
                    new CompStmt(new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), "-")), new PrintStmt(new VarExp("v"))))),
                    new CompStmt(new SleepStmt(10), new PrintStmt(new ArithExp(new VarExp("v"), new ValueExp(new IntValue(10)), "*"))))));

    private IStmt ex19 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("cnt", new IntType()),
            new CompStmt(new New("v1", new ValueExp(new IntValue(1))), new CompStmt(new createSemaphore("cnt", new ReadHeap(
                    new VarExp("v1"))), new CompStmt(new ForkStmt(new CompStmt(new acquire("cnt"), new CompStmt(new WriteHeap("v1",
                    new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(10)), "*")), new CompStmt(new PrintStmt(
                    new ReadHeap(new VarExp("v1"))), new release("cnt"))))), new CompStmt(new ForkStmt(new CompStmt(new acquire("cnt"),
                    new CompStmt(new WriteHeap("v1", new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(10)), "*")),
                            new CompStmt(new WriteHeap("v1", new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(2)), "*")),
                                    new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v1"))), new release("cnt")))))), new CompStmt(new acquire("cnt"),
                    new CompStmt(new PrintStmt(new ArithExp(new ReadHeap(new VarExp("v1")), new ValueExp(new IntValue(1)), "-")), new release("cnt")))))))));

    @FXML
    public void initialize() {
        this.menu = new ListMenu();

        this.menu.addCommand(new RunExample("1", ex1.toString(), ex1));
        this.menu.addCommand(new RunExample("2", ex2.toString(), ex2));
        this.menu.addCommand(new RunExample("3", ex3.toString(), ex3));
        this.menu.addCommand(new RunExample("4", ex4.toString(), ex4));
        this.menu.addCommand(new RunExample("5", ex5.toString(), ex5));
        this.menu.addCommand(new RunExample("6", ex6.toString(), ex6));
        this.menu.addCommand(new RunExample("7", ex7.toString(), ex7));
        this.menu.addCommand(new RunExample("8", ex8.toString(), ex8));
        this.menu.addCommand(new RunExample("9", ex9.toString(), ex9));
        this.menu.addCommand(new RunExample("10", ex10.toString(), ex10));
        this.menu.addCommand(new RunExample("11", ex11.toString(), ex11));
        this.menu.addCommand(new RunExample("12", ex12.toString(), ex12));
        this.menu.addCommand(new RunExample("13", ex13.toString(), ex13));
        this.menu.addCommand(new RunExample("14", ex14.toString(), ex14));
        this.menu.addCommand(new RunExample("15", ex15.toString(), ex15));
        this.menu.addCommand(new RunExample("16", ex16.toString(), ex16));
        this.menu.addCommand(new RunExample("17", ex17.toString(), ex17));
        this.menu.addCommand(new RunExample("18", ex18.toString(), ex18));
        this.menu.addCommand(new RunExample("19", ex19.toString(), ex19));

        try {
            this.menu.runTypeCheck();
            listViewO.setItems(this.menu.returnMenu());
        }
        catch (ExceptionExp | ExceptionADT | ExceptionStmt e) {
            errorLabel.setText("TypeCheck Error: " + e.getMessage());
        }
    }

    @FXML
    public void openSelectedStmt() throws IOException {
        if (!this.listViewO.getItems().isEmpty())
        {
            FXMLLoader mainPageLoader = new FXMLLoader(A7Application.class.getResource("A7ViewMainWindow.fxml"));
            Parent mainPageRoot = mainPageLoader.load();

            A7ControllerMainWindow mainWindowController = mainPageLoader.getController();
            int posList = listViewO.getSelectionModel().getSelectedIndex();
            String fileName = String.format("log%d.txt", posList + 1);
            mainWindowController.setStmtFilename(this.menu.getStmtAtIndex(posList), fileName);

            Scene mainPageScene = new Scene(mainPageRoot, 1480, 740);
            Stage mainPageStage = new Stage();
            mainPageStage.initModality(Modality.WINDOW_MODAL);
            mainPageStage.setTitle("A7_MAIN");
            mainPageStage.setScene(mainPageScene);
            mainPageStage.setResizable(false);
            mainPageStage.show();
        }
    }
}