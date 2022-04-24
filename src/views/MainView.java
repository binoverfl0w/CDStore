package views;

import abstracts.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import models.Account;
import utility.Session;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainView extends View {

    private Parent view;
    private VBox createBill, manageEmployees, manageAccounts, manageStock, editProfile, checkActivity;
    private Button logOut;

    public MainView() {
        view = createView();
    }

    public Parent createView() {
        Parent view;
        if (Session.currentSession.account.getLevel() == Account.Level.ADMINISTRATOR) {
            view = createAdministratorView();
        } else if (Session.currentSession.account.getLevel() == Account.Level.MANAGER) {
            view = createManagerView();
        } else {
            view = createCachierView();
        }
        return view;
    }

    private Parent createAdministratorView() {
        HBox titleHbox = new HBox();
        titleHbox.getStyleClass().add("container");
        Text title = new Text("Control Panel");
        title.getStyleClass().add("h1");
        titleHbox.getChildren().add(title);
        createBill = createCustomButton("res/images/bill.png","Create bill");
        manageEmployees = createCustomButton("res/images/employees.png","Manage employees");
        manageAccounts = createCustomButton("res/images/accounts.png","Manage accounts");
        manageStock = createCustomButton("res/images/stock.png","Manage stock");
        checkActivity = createCustomButton("res/images/activity.png","Check activity");
        HBox hBox = new HBox();
        hBox.getChildren().add(createBill);
        hBox.getChildren().add(manageStock);
        hBox.getChildren().add(manageEmployees);
        hBox.getChildren().add(manageAccounts);
        hBox.getChildren().add(checkActivity);
        hBox.setAlignment(Pos.CENTER);
        HBox bottomBar = new HBox();
        logOut = new Button("Log out");
        logOut.getStyleClass().add("button");
        bottomBar.getChildren().add(logOut);
        bottomBar.getStyleClass().add("container");
        VBox vBox = new VBox();
        vBox.getStyleClass().add("container");
        vBox.getChildren().addAll(titleHbox, hBox, bottomBar);
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(hBox, Priority.ALWAYS);
        return vBox;
    };

    private Parent createManagerView() {
        HBox titleHbox = new HBox();
        titleHbox.getStyleClass().add("container");
        Text title = new Text("Control Panel");
        title.getStyleClass().add("h1");
        titleHbox.getChildren().add(title);
        createBill = createCustomButton("res/images/bill.png","Create bill");
        manageStock = createCustomButton("res/images/stock.png","Manage stock");
        checkActivity = createCustomButton("res/images/activity.png","Check activity");
        HBox hBox = new HBox();
        hBox.getChildren().add(createBill);
        hBox.getChildren().add(manageStock);
        hBox.getChildren().add(checkActivity);
        hBox.setAlignment(Pos.CENTER);
        HBox bottomBar = new HBox();
        logOut = new Button("Log out");
        logOut.getStyleClass().add("button");
        bottomBar.getChildren().add(logOut);
        bottomBar.getStyleClass().add("container");
        VBox vBox = new VBox();
        vBox.getStyleClass().add("container");
        vBox.getChildren().addAll(titleHbox, hBox, bottomBar);
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(hBox, Priority.ALWAYS);
        return vBox;
    }

    private Parent createCachierView() {
        HBox titleHbox = new HBox();
        titleHbox.getStyleClass().add("container");
        Text title = new Text("Control Panel");
        title.getStyleClass().add("h1");
        titleHbox.getChildren().add(title);
        createBill = createCustomButton("res/images/bill.png","Create bill");
        HBox hBox = new HBox();
        hBox.getChildren().add(createBill);
        hBox.setAlignment(Pos.CENTER);
        HBox bottomBar = new HBox();
        logOut = new Button("Log out");
        logOut.getStyleClass().add("button");
        bottomBar.getChildren().add(logOut);
        bottomBar.getStyleClass().add("container");
        VBox vBox = new VBox();
        vBox.getStyleClass().add("container");
        vBox.getChildren().addAll(titleHbox, hBox, bottomBar);
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(hBox, Priority.ALWAYS);
        return vBox;
    }

    private VBox createCustomButton(String res, String label) {
        VBox vBox = new VBox();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(res);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image icon = new Image(fis);
        ImageView icon_v = new ImageView(icon);
        icon_v.getStyleClass().add("customButtonIcon");
        icon_v.setPreserveRatio(true);
        icon_v.setFitHeight(90);
        VBox iconContainer = new VBox();
        iconContainer.getChildren().add(icon_v);
        iconContainer.getStyleClass().add("customButtonIconContainer");
        Text employees_l = new Text(label);
        employees_l.getStyleClass().add("customButtonLabel");
        vBox.getChildren().addAll(iconContainer, employees_l);
        vBox.getStyleClass().add("customButton");
        vBox.setAlignment(Pos.CENTER);
        vBox.setShape(new Circle(200));
        VBox.setMargin(vBox, new Insets(15));
        vBox.prefWidthProperty().bind(vBox.heightProperty());
        return vBox;
    }

    public Button getLogOut() {
        return logOut;
    }

    public VBox getCreateBill() {
        return createBill;
    }

    public VBox getManageEmployees() {
        return manageEmployees;
    }

    public VBox getManageAccounts() { return manageAccounts; }

    public VBox getManageStock() {
        return manageStock;
    }

    public VBox getEditProfile() {
        return editProfile;
    }

    public VBox getCheckActivity() {
        return checkActivity;
    }

    public Parent getView() {
        return view;
    }
}
