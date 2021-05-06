package ui.stages;

import javafx.fxml.FXMLLoader;
import service.IService;
import ui.controllers.LoginController;

public class LoginStage extends MyStage {

    private final IService service;
    public LoginStage(IService service){
        this.service = service;
        super.initialise();
    }

    @Override
    protected void initController(FXMLLoader loader) {
        LoginController controller = loader.getController();
        controller.initialise(service,this);
    }

    @Override
    protected String getPath() {
        return "/views/Login.fxml";
    }
}
