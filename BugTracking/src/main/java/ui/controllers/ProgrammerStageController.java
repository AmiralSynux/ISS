package ui.controllers;

import domain.Bug;
import domain.Programmer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.IService;
import ui.UIException;
import ui.stages.LoginStage;
import ui.stages.WorkingOnStage;

public class ProgrammerStageController extends BugTableController{

    @FXML
    private TabPane tabPane;
    @FXML private Label welcomeLabel;
    private IService service;
    private Stage stage;
    private Programmer user;
    public void initialise(IService service, Programmer user, Stage stage){
        this.service = service;
        service.attach(this);
        this.stage = stage;
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        this.stage.setOnCloseRequest((e)->service.shutdown());
        populateTable();
        addTab();
    }

    private void addTab() {
        Tab tab = new Tab();
        tab.setText("Working on");
        tab.setContent(new WorkingOnStage(service,user).getScene().getRoot());
        tabPane.getTabs().add(tab);
    }

    @Override
    protected void refreshItems() {
        items.clear();
        service.getAllBugs().forEach(items::add);
    }

    public void logOut() {
        Stage stage = new LoginStage(service);
        stage.show();
        this.service.detach(this);
        this.stage.setOnCloseRequest(null);
        this.stage.close();
    }

    public void registerToBug() {
        Bug selected = bugTable.getSelectionModel().getSelectedItem();
        if(selected==null){
            showError(new UIException("Please select a bug!"));
            return;
        }
        if(!confirmation("Are you sure you want to register to the bug?"))return;
        try{
            service.registerToBug(selected,this.user);
            show("Successfully registered!");
        }catch (Exception e){showError(e);}
    }

    @Override
    public void update() {
        refreshItems();
    }
}
