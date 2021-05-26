package ui.stages;

import domain.Programmer;
import javafx.fxml.FXMLLoader;
import service.IService;
import ui.controllers.ProgrammerStageController;
import ui.controllers.ProgrammerWorkingOnController;

public class WorkingOnStage extends MyStage{

    private final IService service;
    private final Programmer user;
    public WorkingOnStage(IService service, Programmer programmer){
        this.service = service;
        user = programmer;
        super.initialise();
    }

    @Override
    protected void initController(FXMLLoader loader) {
        ProgrammerWorkingOnController controller = loader.getController();
        controller.initialise(service,user);
    }

    @Override
    protected String getPath() {
        return "/views/ProgrammerWorkingOnTab.fxml";
    }
}
