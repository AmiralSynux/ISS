package ui.stages;

import domain.Programmer;
import javafx.fxml.FXMLLoader;
import service.IService;
import ui.controllers.ProgrammerStageController;

public class ProgrammerStage extends MyStage{
    private final IService service;
    private final Programmer user;
    public ProgrammerStage(IService service, Programmer programmer){
        this.service = service;
        user = programmer;
        super.initialise();
    }

    @Override
    protected void initController(FXMLLoader loader) {
        ProgrammerStageController controller = loader.getController();
        controller.initialise(service,user,this);
    }

    @Override
    protected String getPath() {
        return "/views/ProgrammerMainScreen.fxml";
    }
}
