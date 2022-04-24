package views;

import abstracts.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Item;
import models.Supplier;

public class SuppliersView extends View {

    private Parent view;
    private TableView<Item> table;
    private TableColumn supplier, item;
    private TextField itemName, supplierName;
    private ComboBox supplierList, removeSupplierList;
    private Button addItem, addSupplier, removeSupplier, delete;

    public SuppliersView() {
        view = createView();
    }

    public VBox createView() {
        table = new TableView<Item>();
        supplier = new TableColumn("Supplier");
        supplier.setMinWidth(100);
        item = new TableColumn("Item");
        item.setMinWidth(100);
        table.getColumns().addAll(supplier, item);

        itemName = new TextField();
        itemName.getStyleClass().add("textfield");
        itemName.setPromptText("Item name");
        supplierList = new ComboBox();
        addItem = new Button("Add item");
        HBox item = new HBox();
        item.setSpacing(10);
        item.getChildren().addAll(supplierList, itemName, addItem);

        supplierName = new TextField();
        supplierName.getStyleClass().add("textfield");
        supplierName.setPromptText("Supplier name");
        addSupplier = new Button("Add supplier");
        removeSupplierList = new ComboBox();
        removeSupplier = new Button("Remove supplier");
        HBox supplier = new HBox();
        supplier.setSpacing(10);
        supplier.getChildren().addAll(supplierName, addSupplier, removeSupplierList, removeSupplier);

        delete = new Button("Delete");
        HBox bottomBar = new HBox();
        bottomBar.getChildren().addAll(delete);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getStyleClass().add("container");
        VBox.setVgrow(table, Priority.ALWAYS);
        vBox.getChildren().addAll(table, supplier, item, bottomBar);
        return vBox;
    }

    public TableColumn getSupplier() {
        return supplier;
    }

    public TableColumn getItem() {
        return item;
    }

    public TableView<Item> getTable() {
        return table;
    }

    public TextField getItemName() {
        return itemName;
    }

    public ComboBox<Supplier> getSupplierList() {
        return supplierList;
    }

    public Button getAddItem() {
        return addItem;
    }

    public TextField getSupplierName() {
        return supplierName;
    }

    public Button getAddSupplier() {
        return addSupplier;
    }

    public ComboBox<Supplier> getRemoveSupplierList() {
        return removeSupplierList;
    }

    public Button getRemoveSupplier() {
        return removeSupplier;
    }

    public Button getDelete() {
        return delete;
    }

    public Parent getView() {
        return view;
    }
}
