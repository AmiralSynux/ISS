package ui.controllers;

import domain.Bug;
import domain.BugStatus;
import domain.Programmer;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.IObserver;

import java.util.Set;

public abstract class BugTableController extends AbstractController implements IObserver {
    @FXML
    protected TableView<Bug> bugTable;
    @FXML
    private TableColumn<Bug, String> nameColumn;
    @FXML
    private TableColumn<Bug, String> descriptionColumn;
    @FXML
    private TableColumn<Bug, String> programmersColumn;
    @FXML
    private TableColumn<Bug, BugStatus> stateColumn;
    protected ObservableList<Bug> items=null;
    protected void populateTable(){
        if(items==null)
            initTable();
        refreshItems();
    }
    private void initTable(){
        items = FXCollections.observableArrayList();
        nameColumn.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getName()));
        programmersColumn.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(getString(x.getValue().getProgrammers())));
        descriptionColumn.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getDescription()));
        stateColumn.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getStatus()));
        bugTable.setItems(items);
    }
    private String getString(Set<Programmer> programmers) {
        StringBuilder str = new StringBuilder();
        for(Programmer p : programmers)
            str.append(p.getUsername()).append(", ");
        return str.toString();
    }
    protected abstract void refreshItems();
}
