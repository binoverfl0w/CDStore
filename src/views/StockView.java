package views;

import abstracts.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Category;
import models.Product;
import models.Supplier;

public class StockView extends View {

    private Parent view;
    private TableView<Product> table;
    private TableColumn name, price, quantity, category, supplier;
    private Button suppliers, addproduct, addcategory, removecategory, edit, delete;
    private TextField addName, addQuantity, addCategoryt, addPrice;
    private ComboBox addCategory, addSupplier, categoryList;

    public StockView() {
        view = createView();
    }

    public VBox createView() {
        table = new TableView<>();
        name = new TableColumn("Name");
        name.setMinWidth(100);
        price = new TableColumn("Price ($)");
        price.setMinWidth(50);
        quantity = new TableColumn("Quantity");
        quantity.setMinWidth(20);
        category = new TableColumn("Category");
        category.setMinWidth(100);
        supplier = new TableColumn("Supplied by");
        supplier.setMinWidth(100);
        table.getColumns().addAll(name, price, quantity, category, supplier);

        HBox addProductHbox = new HBox();
        addproduct = new Button("Add product");
        addName = new TextField();
        addName.getStyleClass().add("textfield");
        addName.setPromptText("Product name");
        addPrice = new TextField();
        addPrice.getStyleClass().add("textfield");
        addPrice.setPromptText("Price");
        addQuantity = new TextField();
        addQuantity.getStyleClass().add("textfield");
        addQuantity.setPromptText("Quantity");
        addCategory = new ComboBox<Category>();
        addSupplier = new ComboBox<Supplier>();
        addProductHbox.getChildren().addAll(addName, addPrice, addQuantity, addCategory, addSupplier, addproduct);
        addProductHbox.setSpacing(10);
        addProductHbox.setAlignment(Pos.CENTER);

        HBox addCategoryHbox = new HBox();
        addcategory = new Button("Add category");
        addCategoryt = new TextField();
        addCategoryt.getStyleClass().add("textfield");
        addCategoryt.setPromptText("New category");
        addCategoryHbox.getChildren().addAll(addCategoryt, addcategory);
        HBox removeCategoryHbox = new HBox();
        categoryList = new ComboBox();
        removecategory = new Button("Remove category");
        removeCategoryHbox.getChildren().addAll(categoryList, removecategory);
        HBox manageCategory = new HBox();
        manageCategory.setAlignment(Pos.CENTER);
        manageCategory.setSpacing(30);
        manageCategory.getChildren().addAll(addCategoryHbox, removeCategoryHbox);
        addCategoryHbox.setSpacing(10);
        removeCategoryHbox.setSpacing(10);

        HBox bottomBar = new HBox();
        suppliers = new Button("Suppliers");
        edit = new Button("Edit");
        delete = new Button("Delete");
        bottomBar.getChildren().addAll(suppliers, edit, delete);
        bottomBar.setSpacing(10);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        VBox vBox = new VBox();
        vBox.getStyleClass().add("container");
        VBox.setVgrow(table, Priority.ALWAYS);
        vBox.setSpacing(12);
        vBox.getChildren().addAll(table, addProductHbox, manageCategory, bottomBar);
        return vBox;
    }

    public TableView<Product> getTable() {
        return table;
    }

    public TableColumn getName() {
        return name;
    }

    public TableColumn getQuantity() {
        return quantity;
    }

    public TableColumn getCategory() {
        return category;
    }

    public TableColumn getSupplier() {
        return supplier;
    }

    public TableColumn getPrice() {
        return price;
    }

    public Button getSuppliers() {
        return suppliers;
    }

    public Button getAddProduct() {
        return addproduct;
    }

    public Button getAddCategory() {
        return addcategory;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getDelete() {
        return delete;
    }

    public TextField getPName() {
        return addName;
    }

    public TextField getPPrice() {
        return addPrice;
    }

    public TextField getPQuantity() {
        return addQuantity;
    }

    public ComboBox<Category> getPCategory() {
        return addCategory;
    }

    public ComboBox<Supplier> getPSupplier() {
        return addSupplier;
    }

    public TextField getNewCategory() {
        return addCategoryt;
    }

    public ComboBox<Category> getToRemoveCategory() {
        return categoryList;
    }

    public Button getRemoveCategory() {
        return removecategory;
    }

    public Parent getView() {
        return view;
    }
}
