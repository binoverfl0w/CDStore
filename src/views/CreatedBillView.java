package views;

import abstracts.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Product;


public class CreatedBillView extends View {

    private Parent view;
    private VBox container;
    private ScrollPane scrollPane;
    private Text total_cost;
    private Button save;

    public CreatedBillView() {
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
        view = createView();
    }

    public VBox createView() {
        container = new VBox();

        save = new Button("Save");
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.getChildren().addAll(save);

        VBox vBox = new VBox();
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        total_cost = new Text();
        total_cost.getStyleClass().add("textlabel");
        vBox.getChildren().addAll(scrollPane, total_cost, bottomBar);
        vBox.getStyleClass().add("container");
        return vBox;
    }

    public HBox createRow(Product product, int quantity) {
        HBox hBox = new HBox();
        hBox.getStyleClass().add("rowContainer");
        Text name = new Text("Product: " + product.getName());
        name.getStyleClass().add("textlabel");
        Text price = new Text("Price: " + String.valueOf(product.getPrice()));
        price.getStyleClass().add("textlabel");
        Text qt = new Text("Quantity: " + String.valueOf(quantity));
        qt.getStyleClass().add("textlabel");
        Text cost = new Text("Cost: " + String.valueOf(quantity * product.getPrice()) + "$");
        cost.getStyleClass().add("textlabel");
        hBox.getChildren().addAll(name, price, qt, cost);
        hBox.setSpacing(20);
        return hBox;
    }

    public Text getTotalCost() {
        return total_cost;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public VBox getContainer() {
        return container;
    }

    public Button getSave() {
        return save;
    }

    public Parent getView() {
        return view;
    }
}
