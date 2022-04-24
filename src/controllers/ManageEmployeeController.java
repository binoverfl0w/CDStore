package controllers;

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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import managers.GUIManager;
import models.Account;
import models.Employee;
import utility.Utilities;
import views.AccountsView;
import views.AttachAccountView;
import views.EmployeesView;
import views.ManageEmployeeView;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManageEmployeeController implements ControllerInterface<ManageEmployeeView> {

    private Employee emp;
    private Account acc;
    private Stage accountDialog, createAccountDialog, stage;
    private Boolean detachAccount = false;

    public ManageEmployeeController(ManageEmployeeView view, Employee emp, Stage stage) {
        this.stage = stage;
        this.emp = emp;
        setView(view);
    }

    @Override
    public void setView(ManageEmployeeView view) {
        File file = new File(emp.getImgpath());
        if (file.exists()) {
            view.getProfile().setImage(new Image(file.toURI().toString()));
        }
        view.getName().setText(emp.getName());
        view.getSurname().setText(emp.getSurname());
        view.getEmail().setText(emp.getEmail());
        view.getPhone().setText(String.valueOf(emp.getPhone()));
        view.getSalary().setText(String.valueOf(emp.getSalary()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        view.getBirthday().setValue(LocalDate.parse(emp.getBirthday(),formatter));
        if (emp.getAccount() != null) {
            view.createAccountField(emp.getAccount());
        } else {
            view.createAccountField(null);
        }

        view.getMinus().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                view.createAccountField(null);
                detachAccount = true;
            }
        });

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
                    imgpath[0] = file.toURI().getPath();
                    Image image = new Image(file.toURI().toString());
                    view.getProfile().setImage(image);
                }
            }
        });

        view.getPlus().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AttachAccountView aav = new AttachAccountView();
                AttachAccountController aac = new AttachAccountController(aav, emp);

                aac.getCreatedAccount().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean val) {
                        if (val) {
                            acc = aac.getAccount();
                            if (acc != null) {
                                view.createAccountField(acc);
                                if (aav.getFromEmployee().isSelected()) {
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Success!");
                                    alert.setHeaderText("Credentials generated from employee");
                                    alert.setContentText("Copy the password as this is the only time you will see it.\nUsername: " + acc.getUsername() + "\nPassword: " + aac.getPassword() + "\nMake sure to click save.");
                                    alert.showAndWait();
                                }
                                if (aav.getCustomAccount().isSelected()) {
                                    Alert alert = new Alert(AlertType.INFORMATION);
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
                createAccountDialog.setTitle("Create account");
                createAccountDialog.initModality(Modality.APPLICATION_MODAL);
                Scene attachAccount = new Scene(aav.getView());
                attachAccount.getStylesheets().addAll("css/attachaccount.css","css/sharedstyle.css");
                createAccountDialog.setScene(attachAccount);
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
                accountDialog.setTitle("Choose an account");
                Scene accountsView = new Scene(av.getView());
                accountsView.getStylesheets().addAll("css/employees.css", "css/sharedstyle.css");
                accountDialog.setScene(accountsView);
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


        view.getSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleSave(view, imgpath);
            }
        });

        view.getSaveandclose().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (handleSave(view,imgpath))
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
            emp.setName(name);
            emp.setSurname(surname);
            emp.setEmail(email);
            emp.setPhone(Long.parseLong(phone));
            emp.setSalary(Double.parseDouble(salary));
            emp.setBirthday(birthday);
            if (imgpath[0] != null) {
                emp.setImgpath(imgpath[0]);
            } else {
                emp.setImgpath("res/images/employee.png");
            }
            if (acc != null) {
                if (Utilities.findAccountByUsername(acc.getUsername()) == null)
                    Database.Accounts.addObject(acc);
                emp.detachAccount();
                emp.setAccount(acc);
            }
            if (detachAccount && acc == null) {
                emp.detachAccount();
            }
            Database.Employees.save();
            Database.Accounts.save();
            GUIManager.refresh();
            return true;
        }
        return false;
    }
}
