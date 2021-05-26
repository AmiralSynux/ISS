package ui.controllers;

import domain.Bug;
import domain.Programmer;
import service.IService;
import ui.UIException;

public class ProgrammerWorkingOnController extends BugTableController{
    private IService service;
    private Programmer user;
    public void initialise(IService service, Programmer user){
        this.service = service;
        service.attach(this);
        this.user = user;
        populateTable();
    }
    @Override
    protected void refreshItems() {
        items.clear();
        service.getBugs(this.user).forEach(items::add);
    }

    public void markAsSolved() {
        Bug selected = bugTable.getSelectionModel().getSelectedItem();
        if(selected==null){
            showError(new UIException("Please select a bug!"));
            return;
        }
        try{
            service.markAsSolved(selected,this.user);
            show("Successfully marked!");
        }catch (Exception e){showError(e);}
    }

    @Override
    public void update() {
        refreshItems();
    }
}
