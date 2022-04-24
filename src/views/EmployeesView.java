package views;

import abstracts.View;
import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Employee;

public class EmployeesView extends View {

    Parent view;
    private TableView<Employee> tv;
    private TableColumn id, name, surname, email, phone, birthday, salary;
    private Button add, edit, delete, deletewacc;

    public EmployeesView() {
        view = createView();
    }

    public VBox createView() {
        tv = new TableView<>();
        id = new TableColumn("Id");
        id.setMaxWidth(25);
        name = new TableColumn("First name");
        name.setMinWidth(100);
        surname = new TableColumn("Last name");
        surname.setMinWidth(100);
        email = new TableColumn("Email");
        email.setMinWidth(150);
        phone = new TableColumn("Phone");
        phone.setMinWidth(120);
        birthday = new TableColumn("Birthday");
        birthday.setMinWidth(100);
        salary = new TableColumn("Salary ($)");
        salary.setMinWidth(100);
        tv.getColumns().addAll(name,surname,email,phone,birthday,salary);

        HBox hBox = new HBox();
        edit = new Button("Edit");
        edit.getStyleClass().add("button");
        add = new Button("Add");
        add.getStyleClass().add("button");
        delete = new Button("Delete employee");
        delete.getStyleClass().add("button");
        deletewacc = new Button("Delete employee and account");
        deletewacc.getStyleClass().add("button");
        hBox.getChildren().addAll(add, edit, delete, deletewacc);
        hBox.setSpacing(15);
        hBox.getStyleClass().add("container");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tv, hBox);
        vBox.getStyleClass().add("container");
        return vBox;
    }

    public TableView<Employee> getTable() {
        return tv;
    }

    public TableColumn getId() {
        return id;
    }

    public TableColumn getName() {
        return name;
    }

    public TableColumn getSurname() {
        return surname;
    }

    public TableColumn getEmail() {
        return email;
    }

    public TableColumn getPhone() {
        return phone;
    }

    public TableColumn getBirthday() {
        return birthday;
    }

    public TableColumn getSalary() {
        return salary;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getAdd() { return add; }

    public Button getDelete() {
        return delete;
    }

    public Button getDeletewacc() {
        return deletewacc;
    }

    public Parent getView() {
        return view;
    }
}
