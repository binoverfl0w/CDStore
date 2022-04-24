package views;

import abstracts.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.lang.ref.PhantomReference;

public class LoginView extends View {

    private TextField usernamet;
    private TextField passwordt;
    private Button login;
    private CheckBox rememberMe;

    Parent view;

    public LoginView() {
        view = createView();
    }

    public VBox createView() {
        VBox vBox = new VBox();
        ImageView imgView = new ImageView(new Image("images/splashscreen.png"));
        imgView.getStyleClass().add("logo");
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(140);
        HBox hBox = new HBox();
        hBox.getChildren().add(imgView);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20,0,20,0));
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(createLoginPanel());
        vBox.getChildren().add(createLoginBar());
        vBox.getStyleClass().add("container");
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private GridPane createLoginPanel() {
        Text usernamel = new Text("Username: ");
        usernamel.getStyleClass().add("textlabel");
        usernamet = new TextField();
        Text passwordl = new Text("Password: ");
        passwordl.getStyleClass().add("textlabel");
        passwordt = new PasswordField();
        passwordt.getStyleClass().add("textfield");
        usernamet.getStyleClass().add("textfield");
        rememberMe = new CheckBox();
        rememberMe.getStyleClass().add("checkbox");
        Text lb = new Text("Remember me");
        lb.getStyleClass().add("textlabel");
        HBox hb = new HBox();
        hb.getChildren().addAll(rememberMe, lb);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.setSpacing(5);
        GridPane gp = new GridPane();
        gp.add(usernamel, 0, 0);
        gp.add(usernamet, 1, 0);
        gp.add(passwordl, 0, 1);
        gp.add(passwordt, 1, 1);
        gp.add(hb,1,2);
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(10);
        gp.setVgap(15);
        VBox.setVgrow(gp, Priority.ALWAYS);
        return gp;
    }

    private HBox createLoginBar() {
        login = new Button("Login");
        login.getStyleClass().add("button");
        HBox loginbar = new HBox();
        loginbar.getChildren().add(login);
        loginbar.setAlignment(Pos.BOTTOM_CENTER);
        loginbar.getStyleClass().add("loginbar");
        return loginbar;
    }

    public Parent getView() {
        return view;
    }

    public Button getButton() {
        return login;
    }

    public TextField getUsername() {
        return usernamet;
    }

    public TextField getPassword() {
        return passwordt;
    }

    public CheckBox getRememberMe() { return rememberMe; }
}
