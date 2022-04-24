package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import managers.AccountManager;
import models.Account;
import managers.GUIManager;
import utility.DatabaseException;
import utility.Logger;
import utility.Session;
import utility.Utilities;
import views.LoginView;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController implements ControllerInterface<LoginView> {

    public LoginController(LoginView view) {
        setView(view);
    }

    @Override
    public void setView(LoginView view) {
        view.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String username = view.getUsername().getText();
                String password = view.getPassword().getText();
                Boolean rememberMe = view.getRememberMe().isSelected();
                Account attempt = AccountManager.checkAccount(username, password);
                if (attempt != null) {
                    Session.currentSession = new Session(attempt, rememberMe);
                    Logger.log("Successfully logged in!");
                    try {
                        Database.saveSession();
                    } catch (DatabaseException e) {
                        e.show();
                    }
                    GUIManager.stage.close();
                    GUIManager.displayMainView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login failed!");
                    alert.setHeaderText("Wrong credentials!");
                    alert.showAndWait();
                }
            }
        });
    }
}
