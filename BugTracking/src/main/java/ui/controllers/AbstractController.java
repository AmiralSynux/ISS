package ui.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.util.Optional;

public abstract class AbstractController {
    protected boolean confirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent())
            return result.get() == ButtonType.OK;
        return false;
    }

    protected void showError(Exception error){
//        error.printStackTrace();
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
