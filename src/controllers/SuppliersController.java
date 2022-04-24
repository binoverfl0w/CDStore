package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.jshell.execution.Util;
import models.Item;
import models.Stock;
import models.Supplier;
import utility.Logger;
import utility.Utilities;
import views.SuppliersView;

public class SuppliersController implements ControllerInterface<SuppliersView> {

    public SuppliersController(SuppliersView view) {
        setView(view);
    }

    @Override
    public void setView(SuppliersView view) {
        view.getSupplier().setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        view.getItem().setCellValueFactory(new PropertyValueFactory<>("item"));
        view.getTable().setItems(Stock.getItems());

        view.getSupplierList().setItems(Stock.getSuppliers());
        view.getRemoveSupplierList().setItems(Stock.getSuppliers());

        view.getSupplierList().setValue(Database.Suppliers.getObjects().get(0));
        view.getRemoveSupplierList().setValue(Database.Suppliers.getObjects().get(0));

        view.getAddItem().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String itemName = view.getItemName().getText();
                Supplier supplier = view.getSupplierList().getSelectionModel().getSelectedItem();
                if (Utilities.validateItem(itemName, supplier)) {
                    Stock.getItems().add(new Item(itemName, supplier));
                    supplier.getItems().add(itemName);
                    Database.Suppliers.save();
                }
            }
        });

        view.getAddSupplier().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String supplierName = view.getSupplierName().getText();
                if (Utilities.validateSupplier(supplierName)) {
                    Logger.log("Added supplier: " + supplierName + ".");
                    Stock.getSuppliers().add(new Supplier(supplierName));
                    Database.Suppliers.save();
                }
            }
        });

        view.getRemoveSupplier().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Supplier supplier = view.getRemoveSupplierList().getSelectionModel().getSelectedItem();
                if (supplier != null) {
                    Stock.getSuppliers().remove(supplier);
                    Database.Suppliers.save();
                }
            }
        });

        view.getDelete().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Item item = view.getTable().getSelectionModel().getSelectedItem();
                if (item != null) {
                    item.getSupplier().getItems().remove(item.getItem());
                    Stock.getItems().remove(item);
                    Database.Suppliers.save();
                }
            }
        });
    }
}
