package views;

import abstracts.View;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Account;
import models.Employee;
import utility.Utilities;

import java.util.Collections;
import java.util.List;

public class AccountsView extends View {

    private Parent view;
    private TableView<Account> tv;
    private Button edit, add, delete;
    private HBox bottombar;
    private TableColumn username, password, level, employee;

    public AccountsView() {
        view = createView();
    }

    public Parent createView() {
        tv = new TableView<>();
        username = new TableColumn("Username");
        username.setMinWidth(100);
        password = new TableColumn("Password");
        password.setMinWidth(200);
        level = new TableColumn("Level");
        level.setMinWidth(15);
        employee = new TableColumn<Account,Button>("Employee");
        tv.getColumns().addAll(username, password, level,employee);
        bottombar = new HBox();
        edit = new Button("Edit");
        add = new Button("Add");
        delete = new Button("Delete");
        bottombar.getChildren().addAll(add, edit, delete);
        bottombar.setSpacing(10);
        bottombar.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tv, bottombar);
        vBox.getStyleClass().add("container");
        vBox.setSpacing(5);
        return vBox;
    }

    public TableView<Account> getTable() {
        return tv;
    }

    public TableColumn getUsername() {
        return username;
    }

    public TableColumn getPassword() {
        return password;
    }

    public TableColumn getLevel() {
        return level;
    }

    public TableColumn getEmployee() {
        return employee;
    }

    public Button getAdd() {
        return add;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getDelete() {
        return delete;
    }

    public HBox getBottombar() {
        return bottombar;
    }

    public Parent getView() {
        return view;
    }
}
