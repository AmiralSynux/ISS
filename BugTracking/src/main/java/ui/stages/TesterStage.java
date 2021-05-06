package ui.stages;

import domain.Tester;
import javafx.fxml.FXMLLoader;
import service.IService;
import ui.controllers.TesterStageController;

public class TesterStage extends MyStage{
    private final IService service;
    private final Tester user;

    public TesterStage(IService service, Tester user) {
        this.service = service;
        this.user = user;
        super.initialise();
    }

    @Override
    protected void initController(FXMLLoader loader) {
        TesterStageController controller = loader.getController();
        controller.initialise(service,user,this);
    }

    @Override
    protected String getPath() {
        return "/views/TesterMainScreen.fxml";
    }
}
