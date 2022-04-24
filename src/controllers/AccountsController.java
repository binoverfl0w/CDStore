package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import managers.GUIManager;
import models.Account;
import models.Employee;
import utility.Session;
import utility.Utilities;
import views.AccountsView;

import javax.xml.crypto.Data;

public class AccountsController implements ControllerInterface<AccountsView> {

    private final static ObservableList<Account> accounts = FXCollections.observableList(Database.Accounts.getObjects());

    public AccountsController(AccountsView view) {
        setView(view);
    }

    @Override
    public void setView(AccountsView view) {
        view.getUsername().setCellValueFactory(new PropertyValueFactory<>("username"));
        view.getPassword().setCellValueFactory(new PropertyValueFactory<>("password"));
        view.getLevel().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> cellDataFeatures) {
                if (cellDataFeatures.getValue().getLevel() == Account.Level.ADMINISTRATOR) {
                    return new ReadOnlyObjectWrapper<String>("Administrator");
                } else if (cellDataFeatures.getValue().getLevel() == Account.Level.MANAGER) {
                    return new ReadOnlyObjectWrapper<String>("Manager");
                } else if (cellDataFeatures.getValue().getLevel() == Account.Level.CASHIER) {
                    return new ReadOnlyObjectWrapper<String>("Cashier");
                }
                return new ReadOnlyObjectWrapper<>("None");
            }
        });
        view.getEmployee().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Button>, ObservableValue<Button>>() {
            @Override
            public ObservableValue<Button> call(TableColumn.CellDataFeatures<Account, Button> accountButtonCellDataFeatures) {
                Employee emp = Utilities.findEmployeeById((accountButtonCellDataFeatures.getValue().getEmployeeID()));
                Button btn = new Button();
                if (emp == null) {
                    btn.setText("None");
                    btn.setDisable(true);
                    return new ReadOnlyObjectWrapper<>(btn);
                }
                btn.setText(emp.getName() + " " + emp.getSurname());
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        GUIManager.displayEmployeeManager(emp);
                    }
                });
                return new ReadOnlyObjectWrapper<>(btn);
            }
        });
        view.getTable().setItems(accounts);
        view.getTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Account acc = view.getTable().getSelectionModel().getSelectedItem();
                    }
                }
            }
        });

        view.getAdd().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GUIManager.displayAddAccountView();
            }
        });

        view.getTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Account acc = view.getTable().getSelectionModel().getSelectedItem();
                        if (acc != null) {
                            GUIManager.displayAccountManager(acc);
                        }
                    }
                }
            }
        });

        view.getEdit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Account acc = view.getTable().getSelectionModel().getSelectedItem();
                if (acc != null) {
                    GUIManager.displayAccountManager(acc);
                }
            }
        });

        view.getDelete().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Account acc = view.getTable().getSelectionModel().getSelectedItem();
                if (acc != null) {
                    Database.Accounts.getObjects().remove(acc);
                    Database.Accounts.save();
                    GUIManager.refresh();
                    if (acc.getUsername() == Session.currentSession.account.getUsername()) {
                        if (GUIManager.accountsStage != null)
                            GUIManager.accountsStage.close();
                        if (GUIManager.stage != null)
                            GUIManager.stage.close();
                        GUIManager.displayLoginView();
                    }
                }
            }
        });
    }
}
