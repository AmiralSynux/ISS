package ui.controllers;

import domain.Bug;
import domain.BugStatus;
import domain.Programmer;
import domain.Tester;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.IService;
import ui.UIException;
import ui.stages.LoginStage;

public class TesterStageController extends BugTableController{
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label welcomeLabel;
    private IService service;
    private Stage stage;
    private Tester user;

    public void initialise(IService service, Tester user, Stage stage){
        this.service = service;
        this.stage = stage;
        this.user = user;
        service.attach(this);
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        this.stage.setOnCloseRequest((e)->service.shutdown());
        populateTable();
    }

    protected void refreshItems(){
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

    public void registerNewBug() {
        try {
            if(!confirmation("Are you sure you want to register the new bug?"))return;
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

    private void modifyStatus(BugStatus status){
        Bug selected = bugTable.getSelectionModel().getSelectedItem();
        if(selected==null){
            showError(new UIException("Please select a bug!"));
            return;
        }
        try{
            service.modifyStatus(selected,status,this.user);
            show("Successfully marked as " + status + "!");
        }catch (Exception e){showError(e);}
    }

    public void solveBug() {
        if(!confirmation("Are you sure you want to mark the bug as solved?"))return;
        modifyStatus(BugStatus.SOLVED);
    }

    public void unsolveBug() {
        if(!confirmation("Are you sure you want to mark the bug as unsolved?"))return;
        modifyStatus(BugStatus.UNSOLVED);
    }

    @Override
    public void update() {
        refreshItems();
    }
}
