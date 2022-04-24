package controllers;

import interfaces.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import managers.GUIManager;
import models.Account;
import utility.Session;
import utility.Utilities;
import views.EmployeesView;
import views.MainView;

import java.io.File;

public class MainController implements ControllerInterface<MainView> {
    public MainController(MainView mv) {
        setView(mv);
    }

    @Override
    public void setView(MainView view) {
        if (Session.currentSession.account.getLevel() == Account.Level.ADMINISTRATOR) {
            setAdministratorView(view);
            Utilities.checkStockAvailability();
        } else if (Session.currentSession.account.getLevel() == Account.Level.MANAGER) {
            setManagerView(view);
            Utilities.checkStockAvailability();
        } else {
            setCashierView(view);
        }
        view.getLogOut().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = new File(Utilities.SESSION_PATH);
                file.delete();
                GUIManager.stage.close();
                GUIManager.stage = null;
                GUIManager.main = null;
                GUIManager.displayLoginView();
            }
        });
    }

    public void setAdministratorView(MainView view) {
        view.getCreateBill().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayBillView();
            }
        });

        view.getManageEmployees().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayEmployeesView();
            }
        });

        view.getManageAccounts().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayAccountsView();
            }
        });

        view.getManageStock().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayStockView();
            }
        });

        view.getCheckActivity().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayActivityView();
            }
        });
    }

    public void setManagerView(MainView view) {
        view.getCreateBill().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayBillView();
            }
        });

        view.getManageStock().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayStockView();
            }
        });

        view.getCheckActivity().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayActivityView();
            }
        });
    }

    public void setCashierView(MainView view) {
        view.getCreateBill().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GUIManager.displayBillView();
            }
        });
    }
}
