package ui.controllers;

import domain.Bug;
import domain.BugStatus;
import domain.Tester;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.IService;
import ui.UIException;
import ui.stages.LoginStage;

public class TesterStageController extends AbstractController{
    @FXML
    private TableView<Bug> bugTable;
    @FXML
    private TableColumn<Bug,String> nameColumn;
    @FXML
    private TableColumn<Bug, String> descriptionColumn;
    @FXML
    private TableColumn programmersColumn;
    @FXML
    private TableColumn<Bug, BugStatus> stateColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label welcomeLabel;
    private IService service;
    private Stage stage;
    private Tester user;

    private ObservableList<Bug> items;

    public void initialise(IService service, Tester user, Stage stage){
        this.service = service;
        this.stage = stage;
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        this.stage.setOnCloseRequest((e)->service.shutdown());
        initTable();
    }

    private void initTable(){
        items = FXCollections.observableArrayList();
        service.getAllBugs().forEach(items::add);
        nameColumn.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getName()));
        descriptionColumn.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getDescription()));
        stateColumn.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getStatus()));
        bugTable.setItems(items);
    }

    private void refreshItems(){
        items.clear();
        service.getAllBugs().forEach(items::add);
    }

    public void logOut() {
        Stage stage = new LoginStage(service);
        stage.show();
        this.stage.setOnCloseRequest(null);
        this.stage.close();
    }

    public void registerNewBug() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            if(name.length()==0 || description.length() ==0)
                throw new UIException("Please fill in the data!");
            service.registerNewBug(name,description);
            show("Bug registered successfully!");
            nameField.setText("");
            descriptionField.setText("");
            refreshItems();
        }catch (Exception e){
            showError(e);
        }
    }
}
