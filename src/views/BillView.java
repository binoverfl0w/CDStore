package views;

import abstracts.View;
import com.sun.javafx.scene.control.DoubleField;
import com.sun.javafx.scene.control.IntegerField;
import db.Database;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Bill;


public class BillView extends View {

    private Parent view;
    private ScrollPane scrollPane;
    private VBox container;
    private Button createBill, addProduct;

    public BillView() {
        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        view = createView();
    }

    public VBox createView() {
        createBill = new Button("Create bill");
        addProduct = new Button("Add product");
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.getChildren().addAll(addProduct, createBill);
        bottomBar.setSpacing(10);
        Text title = new Text("Bill no. " + Database.Bills.getObjects().size());
        title.getStyleClass().add("h1");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(title, scrollPane, bottomBar);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        vBox.setSpacing(10);
        vBox.getStyleClass().add("container");
        return vBox;
    }

    public VBox createRowsContainer() {
        container = new VBox();
        container.getChildren().add(createRow());
        return container;
    }

    public HBox createRow() {
        HBox hBox = new HBox();
        hBox.getStyleClass().add("rowContainer");
        ComboBox product = new ComboBox();
        product.setPromptText("Choose a product");
        TextField field = new TextField();
        field.getStyleClass().add("textfield");
        field.setPromptText("Quantity");
        Button removeProduct = new Button("-");
        Text cost = new Text("Cost: 0$");
        cost.getStyleClass().add("textlabel");
        hBox.getChildren().addAll(product, field, cost, removeProduct);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public VBox getRowsContainer() {
        return container;
    }

    public Button getAddProduct() {
        return addProduct;
    }

    public Button getCreateBill() {
        return createBill;
    }

    public Parent getView() {
        return view;
    }
}
