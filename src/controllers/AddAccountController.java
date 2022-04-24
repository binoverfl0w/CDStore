package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import managers.GUIManager;
import models.Account;
import models.Log;
import utility.Logger;
import utility.Utilities;
import views.ManageAccountView;

public class AddAccountController implements ControllerInterface<ManageAccountView> {

    private Stage stage;

    public AddAccountController(ManageAccountView view, Stage stage) {
        this.stage = stage;
        setView(view);
    }

    @Override
    public void setView(ManageAccountView view) {
        view.getLevel().getItems().addAll(
                "Cashier",
                "Manager",
                "Administrator"
        );
        view.getLevel().setValue("Cashier");

        view.getSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleSave(view);
            }
        });

        view.getSaveandclose().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (handleSave(view))
                    stage.close();
            }
        });
    }

    private boolean handleSave(ManageAccountView view) {
        String username = view.getUsernamet().getText();
        String password = view.getPasswordt().getText();
        int l = view.getLevel().getSelectionModel().getSelectedIndex()+1;
        if (Utilities.validateAccount(username, password)) {
            Logger.log("Added account: " + username + ".");
            Account acc = new Account(username, password, l);
            Database.Accounts.addObject(acc);
            Database.Accounts.save();
            GUIManager.refresh();
            return true;
        }
        return false;
    }
}
