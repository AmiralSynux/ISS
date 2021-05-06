package ui.controllers;

import domain.Programmer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.IService;
import ui.stages.LoginStage;

public class ProgrammerStageController extends AbstractController{
    @FXML private Label welcomeLabel;
    private IService service;
    private Stage stage;
    private Programmer user;
    public void initialise(IService service, Programmer user, Stage stage){
        this.service = service;
        this.stage = stage;
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        this.stage.setOnCloseRequest((e)->service.shutdown());
    }

    public void logOut() {
        Stage stage = new LoginStage(service);
        stage.show();
        this.stage.setOnCloseRequest(null);
        this.stage.close();
    }
}
