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

    private FXMLLoader getLoader(String path){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        return loader;
    }

    private FXMLLoader programmerMainSceneLoader() {
        return getLoader("/views/ProgrammerMainScreen.fxml");
    }

    private FXMLLoader testerMainSceneLoader() {
        return getLoader("/views/TesterMainScreen.fxml");
    }

    private FXMLLoader loginLoader() {
        return getLoader("/views/Login.fxml");
    }
}
