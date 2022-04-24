package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import managers.GUIManager;
import models.Account;
import utility.Session;
import utility.Utilities;
import views.ManageAccountView;

import javax.xml.crypto.Data;

public class ManageAccountController implements ControllerInterface<ManageAccountView> {

    private Stage stage;
    private Account acc;

    public ManageAccountController(ManageAccountView view, Account acc, Stage stage) {
        this.stage = stage;
        this.acc = acc;
        setView(view);
    }

    @Override
    public void setView(ManageAccountView view) {
        view.getLevel().getItems().addAll(
                "Cashier",
                "Manager",
                "Administrator"
        );
        if (acc.getLevel() == Account.Level.ADMINISTRATOR)
            view.getLevel().setValue("Administrator");
        else if (acc.getLevel() == Account.Level.MANAGER)
            view.getLevel().setValue("Manager");
        else
            view.getLevel().setValue("Cashier");

        view.getUsernamet().setText(acc.getUsername());
        view.getUsernamet().setDisable(true);

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
        if (Utilities.validateAccount(username, password, true, password.length() == 0)) {
            if (password.length() > 0)
                acc.setPassword(password);
            int prevLevel = acc.getLevel();
            acc.setLevel(l);
            Database.Accounts.save();
            GUIManager.refresh();
            if (prevLevel != l && Session.currentSession.account.getUsername().equals(acc.getUsername())) {
                Database.deleteSession();
                stage.close();
                if (GUIManager.stage != null)
                    GUIManager.stage.close();
                if (GUIManager.accountsStage != null)
                    GUIManager.accountsStage.close();
                GUIManager.main = null;
                GUIManager.displayLoginView();
            }
            return true;
        }
        return false;
    }
}
