package ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

public abstract class AbstractController {
    protected void showError(Exception error){
        error.printStackTrace();
        System.out.println(error.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR,error.getMessage(), ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setHeaderText(null);
        alert.show();
    }
    protected void show(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,message,ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setHeaderText(null);
        alert.show();
    }

}
