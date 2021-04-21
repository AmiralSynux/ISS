package ui;

import Domain.User;
import Service.IService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    }

    public void login() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = service.login(username,password);
            showWindow(user);
            this.stage.close();
        }catch (Exception e){
            showError(e);
        }

    }

    private void showWindow(User user) {
        boolean smth=false;
        if(smth){

        }
        else{

        }
    }
}
