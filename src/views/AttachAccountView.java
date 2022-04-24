package views;

import abstracts.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AttachAccountView extends View {

    private Parent view;
    private ToggleGroup tg;
    private RadioButton fromEmployee, customAccount;
    private TextField usernamet;
    private PasswordField passwordt;
    private ComboBox level;
    private Button create;

    public AttachAccountView() {
        view = createView();
    }

    public Parent createView() {
        tg = new ToggleGroup();
        fromEmployee = new RadioButton("Create account from employee");
        fromEmployee.setSelected(true);
        fromEmployee.setToggleGroup(tg);

        customAccount = new RadioButton("Create a custom account");
        customAccount.setToggleGroup(tg);
        GridPane gp = new GridPane();
        usernamet = new TextField();
        usernamet.getStyleClass().add("textfield");
        usernamet.setDisable(true);
        passwordt = new PasswordField();
        passwordt.getStyleClass().add("textfield");
        passwordt.setDisable(true);
        Text username = new Text("Username:");
        username.getStyleClass().add("textlabel");
        gp.add(username, 0, 0, 1,1);
        gp.add(usernamet,1,0,1,1);
        Text password = new Text("Password:");
        password.getStyleClass().add("textlabel");
        gp.add(password,0,1,1,1);
        gp.add(passwordt, 1,1,1,1);
        gp.setVgap(7);
        gp.setHgap(10);

        level = new ComboBox();

        create = new Button("Create");
        create.getStyleClass().add("button");

        VBox v1 = new VBox();
        v1.getChildren().addAll(fromEmployee);

        VBox v2 = new VBox();
        v2.getChildren().addAll(customAccount, gp);

        VBox vBox = new VBox();
        vBox.getStyleClass().add("container");
        vBox.getChildren().addAll(fromEmployee, customAccount, gp, level, create);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER_LEFT);
        return vBox;
    }

    public ToggleGroup getTg() {
        return tg;
    }

    public RadioButton getFromEmployee() {
        return fromEmployee;
    }

    public RadioButton getCustomAccount() {
        return customAccount;
    }

    public TextField getUsernamet() {
        return usernamet;
    }

    public PasswordField getPasswordt() {
        return passwordt;
    }

    public ComboBox getLevel() {
        return level;
    }

    public Button getCreate() {
        return create;
    }

    @Override
    public Parent getView() {
        return view;
    }
}
