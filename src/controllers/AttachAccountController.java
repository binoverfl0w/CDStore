package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import managers.GUIManager;
import models.Account;
import models.Employee;
import utility.Utilities;
import views.AccountsView;
import views.AttachAccountView;

public class AttachAccountController implements ControllerInterface<AttachAccountView> {

    private Employee emp;
    private Account acc;
    private String password;
    private BooleanProperty createdAccount = new SimpleBooleanProperty(false);
    
    public AttachAccountController(AttachAccountView view, Employee emp) {
        this.emp = emp;
        setView(view);
    }
    
    @Override
    public void setView(AttachAccountView view) {
        view.getLevel().getItems().addAll(
                "Cashier",
                "Manager",
                "Administrator"
        );
        view.getLevel().setValue("Cashier");

        view.getCustomAccount().selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isSelected) {
                if (isSelected) {
                    view.getUsernamet().setDisable(false);
                    view.getPasswordt().setDisable(false);
                } else {
                    view.getUsernamet().setDisable(true);
                    view.getPasswordt().setDisable(true);
                }
            }
        });

        view.getCreate().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (view.getFromEmployee().isSelected()) {
                    String username = emp.generateUsername();
                    password = emp.generatePassword();
                    if (Utilities.validateAccount(username, password)) {
                        acc = new Account(username, password, view.getLevel().getSelectionModel().getSelectedIndex()+1);
                        createdAccount.setValue(true);
                    }
                } else {
                    if (Utilities.validateAccount(view.getUsernamet().getText(), view.getPasswordt().getText())) {
                        acc = new Account(view.getUsernamet().getText(), view.getPasswordt().getText(), view.getLevel().getSelectionModel().getSelectedIndex()+1);
                        createdAccount.setValue(true);
                    }
                }
            }
        });
    }

    public Account getAccount() {
        return acc;
    }

    public String getPassword() { return password; }

    public BooleanProperty getCreatedAccount() {
        return createdAccount;
    }
}
