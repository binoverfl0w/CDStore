package controllers;

import abstracts.View;
import db.Database;
import interfaces.ControllerInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import managers.GUIManager;
import models.Account;
import models.Employee;
import utility.Logger;
import utility.Utilities;
import views.*;

import javax.xml.crypto.Data;
import java.io.File;
import java.security.Guard;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddEmployeeController implements ControllerInterface<ManageEmployeeView> {

    private Employee newEmp;
    private Account acc;
    private Stage createAccountDialog;
    private Stage stage, accountDialog;
    private Boolean detachAccount = false;

    public AddEmployeeController(ManageEmployeeView view, Stage stage) {
        this.stage = stage;
        setView(view);
    }

    @Override
    public void setView(ManageEmployeeView view) {
        view.getPlus().setDisable(true);
        final String[] imgpath = {null};
        view.getLoad().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

                //Show open file dialog
                File file = fileChooser.showOpenDialog(null);

                if (file != null) {
                    imgpath[0] = file.toURI().toString();
                    Image image = new Image(imgpath[0]);
                    view.getProfile().setImage(image);
                }
            }
        });

        view.getBirthday().setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                view.getBirthday().setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });


        view.getMinus().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                view.createAccountField(null);
                detachAccount = true;
            }
        });

        view.getPlus().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AttachAccountView aav = new AttachAccountView();
                AttachAccountController aac = new AttachAccountController(aav, newEmp);

                aac.getCreatedAccount().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean val) {
                        if (val) {
                            acc = aac.getAccount();
                            if (acc != null) {
                                view.createAccountField(acc);
                                if (aav.getFromEmployee().isSelected()) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Success!");
                                    alert.setHeaderText("Credentials generated from employee");
                                    alert.setContentText("Copy the password as this is the only time you will see it.\nUsername: " + acc.getUsername() + "\nPassword: " + aac.getPassword() + "\nMake sure to click save.");
                                    alert.showAndWait();
                                }
                                if (aav.getCustomAccount().isSelected()) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Success!");
                                    alert.setHeaderText("An account was generated for this employee");
                                    alert.setContentText("Make sure to click save.");
                                    alert.showAndWait();
                                }
                                createAccountDialog.close();
                            }
                        }
                    }
                });

                createAccountDialog = new Stage();
                createAccountDialog.getIcons().add(new Image("images/icon.png"));
                createAccountDialog.initModality(Modality.APPLICATION_MODAL);
                Scene createAccScene = new Scene(aav.getView(), 500, 500);
                createAccScene.getStylesheets().addAll("css/attachaccount.css","css/sharedstyle.css");
                createAccountDialog.setScene(createAccScene);
                createAccountDialog.show();
            }
        });

        view.getFromExisting().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AccountsView av = new AccountsView();
                av.getBottombar().setVisible(false);
                av.getUsername().setCellValueFactory(new PropertyValueFactory<>("username"));
                av.getPassword().setCellValueFactory(new PropertyValueFactory<>("password"));
                av.getLevel().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
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
                av.getEmployee().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> cellDataFeatures) {
                        Employee emp = Utilities.findEmployeeById((cellDataFeatures.getValue().getEmployeeID()));
                        if (emp == null) {
                            return new ReadOnlyObjectWrapper<>("None");
                        }
                        return new ReadOnlyObjectWrapper<>(emp.getName() + " " + emp.getSurname());
                    }
                });
                av.getTable().setItems(FXCollections.observableList(Database.Accounts.getObjects()));
                BooleanProperty gotAccount = new SimpleBooleanProperty(false);
                accountDialog = new Stage();
                accountDialog.getIcons().add(new Image("images/icon.png"));
                Scene accountDialogScene = new Scene(av.getView());
                accountDialogScene.getStylesheets().addAll("css/accounts.css", "css/sharedstyle.css");
                accountDialog.setScene(accountDialogScene);
                accountDialog.show();
                av.getTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            if (mouseEvent.getClickCount() == 2) {
                                acc = av.getTable().getSelectionModel().getSelectedItem();
                                if (acc != null) {
                                    gotAccount.setValue(Boolean.TRUE);
                                    accountDialog.close();
                                }
                            }
                        }
                    }
                });
                gotAccount.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean val) {
                        if (val) {
                            view.createAccountField(acc);
                        }
                    }
                });
            }
        });

        view.getSave().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleSave(view, imgpath);
            }
        });

        view.getSaveandclose().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (handleSave(view, imgpath))
                    stage.close();
            }
        });
    }

    private boolean handleSave(ManageEmployeeView view, String imgpath[]) {
        String name = view.getName().getText();
        String surname = view.getSurname().getText();
        String email = view.getEmail().getText();
        String phone =  view.getPhone().getText();
        String salary = view.getSalary().getText();
        LocalDate birthday = view.getBirthday().getValue();
        if (Utilities.validateEmployee(name, surname, email, phone, salary)) {
            Logger.log("Added employee: " + name + ".");
            if (newEmp == null) {
                newEmp = new Employee(name, surname, email, Long.parseLong(phone), Double.parseDouble(salary), birthday);
            } else {
                newEmp.setName(name);
                newEmp.setSurname(surname);
                newEmp.setEmail(email);
                newEmp.setPhone(Long.parseLong(phone));
                newEmp.setSalary(Double.parseDouble(salary));
            }
            if (imgpath[0] != null) {
                newEmp.setImgpath(imgpath[0]);
            } else {
                newEmp.setImgpath("res/images/employee.png");
            }
            if (acc != null) {
                if (Utilities.findAccountByUsername(acc.getUsername()) == null)
                    Database.Accounts.addObject(acc);
                newEmp.detachAccount();
                newEmp.setAccount(acc);
            }
            if (detachAccount && acc == null) {
                newEmp.detachAccount();
            }
            if (Utilities.findEmployeeById(newEmp.getId()) == null)
                Database.Employees.addObject(newEmp);
            Database.Employees.save();
            Database.Accounts.save();
            view.getPlus().setDisable(false);
            GUIManager.refresh();
            return true;
        }
        return false;
    }
}
