package ui;

import javafx.fxml.FXMLLoader;

public class LoaderFactory {
    private LoaderFactory(){}
    private static final LoaderFactory instance = new LoaderFactory();
    public static LoaderFactory getInstance(){
        return instance;
    }
    public FXMLLoader getLoader(SceneName scene){
        return switch (scene) {
            case Login -> loginLoader();
            case ProgrammerMainScene -> programmerMainSceneLoader();
            case TesterMainScene ->testerMainSceneLoader();
        };
    }

    private FXMLLoader programmerMainSceneLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ProgrammerMainScreen.fxml"));
        return loader;
    }

    private FXMLLoader testerMainSceneLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/TesterMainScreen.fxml"));
        return loader;
    }

    private FXMLLoader loginLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/TesterMainScreen.fxml"));
        return loader;
    }
}
