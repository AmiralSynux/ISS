package ui.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class MyStage extends Stage {
    protected void initialise(){
        try {
            FXMLLoader loader = getLoader();
            Pane pane = loader.load();
            initController(loader);
            this.setTitle("BugTracking");
//            this.setResizable(false);
            this.setScene(new Scene(pane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initController(FXMLLoader loader);

    protected abstract String getPath();

    private FXMLLoader getLoader(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(getPath()));
        return loader;
    }
}
