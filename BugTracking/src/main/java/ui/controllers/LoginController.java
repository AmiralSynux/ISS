package ui.controllers;

import domain.Programmer;
import domain.Tester;
import domain.User;
import service.IService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.stages.ProgrammerStage;
import ui.stages.TesterStage;

public class LoginController extends AbstractController{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private IService service;
    private Stage stage;
    public void initialise(IService service, Stage stage){
        this.service = service;
        this.stage = stage;
        this.stage.setOnCloseRequest((e)->service.shutdown());
    }

    public void login() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = service.login(username,password);
            showWindow(user);
            this.stage.setOnCloseRequest(null);
            this.stage.close();
        }catch (Exception e){
            showError(e);
        }
    }

    private void showWindow(User user) {
        Stage stage;
        if(user instanceof Programmer){
            stage = new ProgrammerStage(service, (Programmer) user);
        }
        else{
            stage = new TesterStage(service, (Tester) user);
        }
        stage.show();
        this.stage.close();
    }
}
