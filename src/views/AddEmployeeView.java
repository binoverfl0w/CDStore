package views;

import abstracts.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AddEmployeeView extends View {

    private Parent view;

    private Button load;
    private ImageView profile;
    private VBox profileContainer;
    private TextField namet, surnamet, emailt, phonet, salaryt, birthdayt;

    public AddEmployeeView() {
        view = createView();
    }

    public HBox createView() {
        profileContainer = new VBox();
        load = new Button("Load");
        profile = new ImageView();
        profile.setPreserveRatio(true);
        profile.setFitWidth(400);
        profileContainer.setMaxWidth(500);
        VBox.setVgrow(profileContainer, Priority.ALWAYS);
        profileContainer.getChildren().addAll(load,profile);
        profileContainer.setAlignment(Pos.CENTER);
        Label name = new Label("First name");
        namet = new TextField();
        Label surname = new Label("Last name");
        surnamet = new TextField();
        Label email = new Label("Email");
        emailt = new TextField();
        Label phone = new Label("Phone");
        phonet = new TextField();
        Label salary = new Label("Salary");
        salaryt = new TextField();
        Label birthday = new Label("Birthday");
        birthdayt = new TextField();
        VBox data = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(profileContainer);
        return hBox;
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

    public TextField getBirthday() {
        return birthdayt;
    }

    public Parent getView() {
        return view;
    }
}
