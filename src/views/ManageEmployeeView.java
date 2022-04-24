package views;

import abstracts.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.Account;

import java.time.LocalDate;
import java.util.Date;

public class ManageEmployeeView extends View {

    private Parent view;

    private Button load, save, saveandclose, plus = new Button("+ New account"), minus = new Button("-"), fromExisting = new Button("+ Add existing account");
    private ImageView profile;
    private VBox profileContainer, data;
    private TextField namet, surnamet, emailt, phonet, salaryt;
    private DatePicker birthdayt;
    private VBox accountField;

    public ManageEmployeeView() {
        view = createView();
    }

    public VBox createView() {
        HBox.setMargin(plus, new Insets(0, 10, 0, 0));
        HBox.setMargin(minus, new Insets(0, 0, 0, 10));

        profileContainer = new VBox();
        profileContainer.setSpacing(10);
        load = new Button("Load photo");
        load.getStyleClass().add("button");
        profile = new ImageView();
        profile.setPreserveRatio(true);
        profile.setFitWidth(400);
        profileContainer.getChildren().addAll(profile, load);
        profileContainer.setAlignment(Pos.CENTER);

        data = new VBox();
        data.setAlignment(Pos.CENTER);
        namet = new TextField();
        surnamet = new TextField();
        emailt = new TextField();
        phonet = new TextField();
        salaryt = new TextField();
        accountField = createField("Account", null);
        data.getChildren().addAll(createField("First name", namet), createField("Last name", surnamet), createField("Email", emailt), createField("Phone", phonet),createField("Salary", salaryt), createField("Birthday", null),accountField);
        data.setPadding(new Insets(20,90,20,10));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(profileContainer, data);

        save = new Button("Save");
        save.getStyleClass().add("button");
        saveandclose = new Button("Save and close");
        saveandclose.getStyleClass().add("button");
        plus.getStyleClass().add("button");
        minus.getStyleClass().add("button");
        HBox saveBar = new HBox();
        saveBar.getChildren().addAll(save, saveandclose);
        saveBar.setSpacing(10);
        saveBar.setAlignment(Pos.BOTTOM_RIGHT);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.getChildren().addAll(hBox,saveBar);
        vBox.setPadding(new Insets(10,20,10,20));
        vBox.getStyleClass().add("container");
        return vBox;
    }

    public VBox createField(String label, TextField tfield) {
        Text text = new Text(label);
        text.getStyleClass().add("textlabel");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        if (label.equals("Birthday")) {
            birthdayt = new DatePicker();
            birthdayt.setValue(LocalDate.now());
            vBox.getChildren().addAll(text, birthdayt);
            return vBox;
        }
        if (label.equals("Account")) {
            HBox hBox = new HBox();
            hBox.getChildren().addAll(plus, fromExisting);
            vBox.getChildren().addAll(text, hBox);
            return vBox;
        }
        tfield.getStyleClass().add("textfield");
        vBox.getChildren().addAll(text, tfield);
        return vBox;
    }

    public void createAccountField(Account acc) {
        Text text = new Text("Account");
        text.getStyleClass().add("textlabel");
        HBox hBox = new HBox();
        if (acc == null) {
            hBox.getChildren().addAll(plus, fromExisting);
            accountField.getChildren().clear();
            accountField.getChildren().addAll(text, hBox);
        } else {
            Text connected = new Text("Connected with the account: " + acc.getUsername());
            connected.getStyleClass().add("connected");
            hBox.getChildren().addAll(connected, minus);
            hBox.setAlignment(Pos.CENTER);
            accountField.getChildren().clear();
            accountField.getChildren().addAll(text, hBox);
        }
    }

    public void setAccountField(VBox vBox) {
        this.accountField = vBox;
    }

    public Button getLoad() {
        return load;
    }

    public ImageView getProfile() {
        return profile;
    }

    public VBox getProfileContainer() {
        return profileContainer;
    }

    public VBox getData() { return data; }

    public TextField getName() {
        return namet;
    }

    public TextField getSurname() {
        return surnamet;
    }

    public TextField getEmail() {
        return emailt;
    }

    public TextField getPhone() {
        return phonet;
    }

    public TextField getSalary() {
        return salaryt;
    }

    public DatePicker getBirthday() {
        return birthdayt;
    }

    public Button getSave() {
        return save;
    }

    public Button getSaveandclose() { return saveandclose; }

    public Button getPlus() {
        return plus;
    }

    public Button getMinus() {
        return minus;
    }

    public Button getFromExisting() {
        return fromExisting;
    }

    public Parent getView() {
        return view;
    }

    public VBox getAccountField() {
        return accountField;
    }
}
