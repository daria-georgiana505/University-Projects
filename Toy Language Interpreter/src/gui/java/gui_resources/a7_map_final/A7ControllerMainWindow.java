package gui_resources.a7_map_final;

import cli.commands.Command;
import controller.Controller;
import exceptions.ExceptionADT;
import exceptions.ExceptionExp;
import exceptions.ExceptionStmt;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class A7ControllerMainWindow {
    private Controller controller;
    private PrgState selectedPrgState;
    private int selectedPositionListPrgState;

    @FXML
    private TextField nrPrgStatesTextField;

    @FXML
    private TableView<Pair<String, Value>> symTableView;

    @FXML
    private TableColumn<Pair<String, Value>, String> symTableViewColumnVarName;

    @FXML
    private TableColumn<Pair<String, Value>, String> symTableViewColumnValue;

    @FXML
    private ListView<String> outListView;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapTableViewColumnAddress;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> heapTableViewColumnValue;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private ListView<Integer> prgStateIdListView;

    @FXML
    private TextArea runtimeErrorTextArea;

    @FXML
    private Button runOneStepButton;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, String>>> semTableView;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, Integer> semTableIndexColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, Integer> semTableValueColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, String> semTableListColumn;

    public void setSelectedPrgState()
    {
        if (!this.prgStateIdListView.getItems().isEmpty())
        {
            int posSelected = prgStateIdListView.getSelectionModel().getSelectedIndex();
            this.selectedPrgState = this.controller.getPrgStates().get(posSelected);
            this.selectedPositionListPrgState = posSelected;

            populateAll();
        }
    }

    public void populatePrgStates()
    {
        prgStateIdListView.getItems().clear(); //TODO: check if we need to clear
        nrPrgStatesTextField.clear();

        List<Integer> listPrgStateId = controller.getPrgStates().stream().map(ps -> ps.id).collect(Collectors.toList());
        prgStateIdListView.setItems(FXCollections.observableList(listPrgStateId));
        nrPrgStatesTextField.setText(String.valueOf(listPrgStateId.size()));

        int findSelectedIdPrg = prgStateIdListView.getItems().indexOf(this.selectedPrgState.id);
        if (findSelectedIdPrg >= 0)
            prgStateIdListView.getSelectionModel().select(this.selectedPositionListPrgState);
        else {
            if (!prgStateIdListView.getItems().isEmpty())
            {
                prgStateIdListView.getSelectionModel().select(0);
                int idFirstPrg = this.prgStateIdListView.getItems().get(0);
                this.selectedPrgState = this.controller.getPrgStates().stream().filter(ps -> ps.id == idFirstPrg).findFirst().orElse(this.controller.getPrgStates().get(0));
                this.selectedPositionListPrgState = 0;
            }
        }
    }

    public void populateSymTable()
    {
        if(selectedPrgState != null)
        {
            symTableView.getItems().clear();
            List<Pair<String, Value>> symTableAsList = new ArrayList<>();
            Map<String, Value> symTable = selectedPrgState.getSymTable().getContent();

            for (Map.Entry<String, Value> entry : symTable.entrySet()) {
                String key = entry.getKey();
                Value value = entry.getValue();
                symTableAsList.add(new Pair<>(key, value));
            }

            symTableView.setItems(FXCollections.observableList(symTableAsList));
        }
    }

    public void populateOutput()
    {
        if(!this.controller.getPrgStates().isEmpty())
        {
            outListView.getItems().clear();
            MyIList<Value> output = this.controller.getPrgStates().get(0).getOut();
            outListView.setItems(FXCollections.observableList(output.toStringAsList()));
        }
    }

    public void populateHeapTable()
    {
        if(!this.controller.getPrgStates().isEmpty())
        {
            heapTableView.getItems().clear();
            List<Pair<Integer, Value>> heapTableAsList = new ArrayList<>();
            Map<Integer, Value> heapTable = this.controller.getPrgStates().get(0).getHeapTable().getContent();

            for (Map.Entry<Integer, Value> entry : heapTable.entrySet()) {
                Integer key = entry.getKey();
                Value value = entry.getValue();
                heapTableAsList.add(new Pair<>(key, value));
            }

            heapTableView.setItems(FXCollections.observableList(heapTableAsList));
        }
    }

    public void populateFileTable()
    {
        if(!this.controller.getPrgStates().isEmpty()) {
            fileTableListView.getItems().clear();
            List<String> fileList = this.controller.getPrgStates().get(0).getFileTable().keys().stream().map(StringValue::toString).collect(Collectors.toList());
            fileTableListView.setItems(FXCollections.observableList(fileList));
        }
    }

    public void populateExeStack()
    {
        exeStackListView.getItems().clear();

        List<String> exeStack;
        if(selectedPrgState != null)
        {
            exeStack = selectedPrgState.getExeStack().toStringAsList();
        }
        else
        {
            exeStack = new ArrayList<>(); //TODO: Check if this is necessary
        }
        exeStackListView.setItems(FXCollections.observableList(exeStack));
    }

    public void populateSemaphoreTable()
    {
        if(!this.controller.getPrgStates().isEmpty()) {
            semTableView.getItems().clear();

            List<Pair<Integer, Pair<Integer, String>>> semTableAsList = new ArrayList<>();
            Map<Integer, Pair<Integer, List<Integer>>> semaphoreTable = this.controller.getPrgStates().get(0).getSemaphoreTable().getContent();

            for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : semaphoreTable.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue().getKey();
                List<Integer> listVal = entry.getValue().getValue();
                String listValuesString = listVal.stream().map(Object::toString).collect(Collectors.joining("\n"));
                semTableAsList.add(new Pair<>(key, new Pair<>(value, listValuesString)));
            }

            semTableView.setItems(FXCollections.observableList(semTableAsList));
        }
    }

    public void populateAll()
    {
        populatePrgStates();
        populateExeStack();
        populateSymTable();
        populateOutput();
        populateHeapTable();
        populateFileTable();
        populateSemaphoreTable();
    }

    public void setStmtFilename(IStmt stmt, String fileName)
    {
        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> filetbl = new MyDictionary<>();
        MyHeapTable heaptbl = new MyHeapTable();
        MyISemaphoreTable semaphoreTable = new MySemaphoreTable();
        PrgState crtPrgState = new PrgState(stk, symtbl, out, filetbl, heaptbl, semaphoreTable, stmt);

        IRepository repo = new Repository(fileName);
        repo.addProgramState(crtPrgState);
        this.controller = new Controller(repo);

        symTableViewColumnVarName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        symTableViewColumnValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        heapTableViewColumnAddress.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableViewColumnValue.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        semTableIndexColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        semTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getKey()));
        semTableListColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(String.join("\n", p.getValue().getValue().getValue())));

        if(!this.controller.getPrgStates().isEmpty()) {
            this.selectedPrgState = this.controller.getPrgStates().get(0);
            this.selectedPositionListPrgState = 0;

            populatePrgStates();
            populateExeStack();
        }
        else
            runOneStepButton.setDisable(true);
    }

    public void execOneStep()
    {
        try {
            this.controller.oneStep();

            if(this.controller.getPrgStates().isEmpty())
                runOneStepButton.setDisable(true);
            populateAll();
        }
        catch (ExceptionExp | ExceptionStmt | ExceptionADT | IOException | RuntimeException | InterruptedException e) {
            runtimeErrorTextArea.setText(e.getMessage());
            runOneStepButton.setDisable(true);
        }
    }
}
