package views;

import abstracts.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ManageAccountView extends View {

    private Parent view;
    private TextField usernamet;
    private PasswordField passwordt;
    private ComboBox level;
    private Button save, saveandclose;

    public ManageAccountView() {
        view = createView();
    }

    @Override
    public Parent createView() {
        usernamet = new TextField();
        usernamet.getStyleClass().add("textfield");
        passwordt = new PasswordField();
        passwordt.getStyleClass().add("textfield");
        level = new ComboBox();
        GridPane gp = new GridPane();
        Text u = new Text("Username:");
        u.getStyleClass().add("textlabel");
        gp.add(u, 0,0,1,1);
        gp.add(usernamet,1,0,1,1);
        Text p = new Text("Password:");
        p.getStyleClass().add("textlabel");
        gp.add(p,0,1,1,1);
        gp.add(passwordt,1,1,1,1);
        Text pos = new Text("Position:");
        pos.getStyleClass().add("textlabel");
        gp.add(pos,0,2,1,1);
        gp.add(level,1,2,1,1);
        gp.setHgap(10);
        gp.setVgap(20);

        save = new Button("Save");
        saveandclose = new Button("Save and close");
        HBox saveBar = new HBox();
        saveBar.setAlignment(Pos.CENTER);
        saveBar.setSpacing(10);
        saveBar.getChildren().addAll(save, saveandclose);

        VBox vBox = new VBox();
        vBox.setSpacing(25);
        vBox.getChildren().addAll(gp,saveBar);
        vBox.getStyleClass().add("container");

        return vBox;
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

    public Button getSave() {
        return save;
    }

    public Button getSaveandclose() {
        return saveandclose;
    }

    @Override
    public Parent getView() {
        return view;
    }
}
