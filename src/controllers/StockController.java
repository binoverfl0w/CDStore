package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import jdk.jshell.execution.Util;
import managers.GUIManager;
import models.*;
import utility.Logger;
import utility.Utilities;
import views.StockView;

public class StockController implements ControllerInterface<StockView> {


    Product selectedProduct = null;

    public StockController(StockView view) {
        setView(view);
    }

    @Override
    public void setView(StockView view) {
        view.getName().setCellValueFactory(new PropertyValueFactory<>("name"));
        view.getQuantity().setCellValueFactory(new PropertyValueFactory<>("quantity"));
        view.getPrice().setCellValueFactory(new PropertyValueFactory<>("price"));
        view.getCategory().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> cellDataFeatures) {
                Product product = cellDataFeatures.getValue();
                Category category = Stock.getCategoryByID(product.getCategoryID());
                if (category != null)
                    return new SimpleStringProperty(category.getName());
                return new SimpleStringProperty("No category");
            }
        });
        view.getSupplier().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> cellDataFeatures) {
                Product product = cellDataFeatures.getValue();
                Supplier supplier = Stock.getSupplierByID(product.getSupplierID());
                if (supplier != null)
                    return new SimpleStringProperty(supplier.getName());
                return new SimpleStringProperty("No suppliers");
            }
        });
        view.getTable().setItems(Stock.getProducts());

        view.getPCategory().setValue(Database.Categories.getObjects().get(0));
        view.getToRemoveCategory().setValue(Database.Categories.getObjects().get(0));
        view.getPSupplier().setValue(Database.Suppliers.getObjects().get(0));

        view.getPCategory().setItems(Stock.getCategories());
        view.getToRemoveCategory().setItems(Stock.getCategories());
        view.getPSupplier().setItems(Stock.getSuppliers());

        view.getAddCategory().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = view.getNewCategory().getText();
                if (Utilities.validateCategory(name)) {
                    Logger.log("Added category: " + name + ".");
                    Category newCategory = new Category(name);
                    Stock.getCategories().add(newCategory);
                    Database.Categories.save();
                }
            }
        });

        view.getRemoveCategory().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Category category = view.getToRemoveCategory().getSelectionModel().getSelectedItem();
                if (category != null && !category.getID().equals("Skip")) {
                    Stock.getCategories().remove(category);
                    Logger.log("Removed category: " + category.getName() + ".");
                    for (int i = 0; i < Stock.getProducts().size(); i++) {
                        if (Stock.getProducts().get(i).getCategoryID().equals(category.getID())) {
                            Stock.getProducts().get(i).setCategoryID(null);
                        }
                    }
                    Database.Products.save();
                    Database.Categories.save();
                    view.getTable().refresh();
                }
            }
        });

        view.getAddProduct().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = view.getPName().getText();
                String price_s = view.getPPrice().getText();
                String quantity_s = view.getPQuantity().getText();
                Category category = view.getPCategory().getSelectionModel().getSelectedItem();
                Supplier supplier = view.getPSupplier().getSelectionModel().getSelectedItem();
                if (Utilities.validateProduct(name, quantity_s, price_s)) {
                    int quantity = Integer.parseInt(quantity_s);
                    double price = Double.parseDouble(price_s);
                    Product product = new Product(name, quantity, price);
                    if (category != null)
                        product.setCategoryID(category.getID());
                    if (supplier != null)
                        product.setSupplierID(supplier.getID());
                    if (view.getAddProduct().getText().equals("Save") && selectedProduct != null) {
                        Stock.replaceProduct(selectedProduct, product);
                        Database.Products.save();
                        view.getAddProduct().setText("Add product");
                        return;
                    }
                    Logger.log("Added product: " + name + ".");
                    Stock.getProducts().add(product);
                    Database.Products.save();
                }
            }
        });

        view.getEdit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedProduct = view.getTable().getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    view.getPName().setText(selectedProduct.getName());
                    view.getPQuantity().setText(String.valueOf(selectedProduct.getQuantity()));
                    view.getPPrice().setText(String.valueOf(selectedProduct.getPrice()));
                    Category category = Stock.getCategoryByID(selectedProduct.getCategoryID());
                    if (category != null)
                        view.getPCategory().setValue(category);
                    else {
                        view.getPCategory().setValue(Database.Categories.getObjects().get(0));
                    }
                    Supplier supplier = Stock.getSupplierByID(selectedProduct.getSupplierID());
                    if (supplier != null)
                        view.getPSupplier().setValue(supplier);
                    else
                        view.getPSupplier().setValue(Database.Suppliers.getObjects().get(0));
                    view.getAddProduct().setText("Save");
                }
            }
        });

        view.getDelete().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = view.getTable().getSelectionModel().getSelectedItem();
                if (product != null) {
                    Logger.log("Removed product: " + product.getName() + ".");
                    Stock.getProducts().remove(product);
                    Database.Products.save();
                }
            }
        });

        view.getSuppliers().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GUIManager.displaySuppliersView();
            }
        });
    }
}
