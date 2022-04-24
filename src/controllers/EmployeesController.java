package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managers.GUIManager;
import models.Employee;
import utility.Session;
import views.EmployeesView;

import javax.swing.text.TableView;

public class EmployeesController implements ControllerInterface<EmployeesView> {


    private final ObservableList<Employee> employees = FXCollections.observableList(Database.Employees.getObjects());

    public EmployeesController(EmployeesView view) {
        setView(view);
    }

    @Override
    public void setView(EmployeesView view) {
        view.getName().setCellValueFactory(new PropertyValueFactory<>("name"));
        view.getSurname().setCellValueFactory(new PropertyValueFactory<>("surname"));
        view.getEmail().setCellValueFactory(new PropertyValueFactory<>("email"));
        view.getPhone().setCellValueFactory(new PropertyValueFactory<>("phone"));
        view.getBirthday().setCellValueFactory(new PropertyValueFactory<>("birthday"));
        view.getSalary().setCellValueFactory(new PropertyValueFactory<>("salary"));
        view.getTable().setItems(employees);
        view.getTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Employee emp = view.getTable().getSelectionModel().getSelectedItem();
                        if (emp != null)
                            GUIManager.displayEmployeeManager(emp);
                    }
                }
            }
        });
        view.getEdit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Employee emp = view.getTable().getSelectionModel().getSelectedItem();
                if (emp != null)
                    GUIManager.displayEmployeeManager(emp);
            }
        });

        view.getAdd().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GUIManager.displayAddEmployeeView();
            }
        });

        view.getDelete().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Employee emp = view.getTable().getSelectionModel().getSelectedItem();
                if (emp != null) {
                    emp.detachAccount();
                    Database.Employees.getObjects().remove(emp);
                    Database.Employees.save();
                    GUIManager.refresh();
                }
            }
        });

        view.getDeletewacc().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Employee emp = view.getTable().getSelectionModel().getSelectedItem();
                boolean goToLogin = false;
                if (emp != null) {
                    if (emp.getAccount() != null) {
                        Database.Accounts.getObjects().remove(emp.getAccount());
                        if (emp.getAccount().getUsername() == Session.currentSession.account.getUsername()) {
                            goToLogin = true;
                        }
                    }
                    emp.detachAccount();
                    Database.Accounts.save();
                    Database.Employees.getObjects().remove(emp);
                    Database.Employees.save();
                    GUIManager.refresh();
                    if (goToLogin) {
                        if (GUIManager.employeesStage != null)
                            GUIManager.employeesStage.close();
                        if (GUIManager.stage != null)
                            GUIManager.stage.close();
                        GUIManager.displayLoginView();
                    }
                }
            }
        });

    }
}
