package models;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Stock {
    private final static ObservableList<Category> categories = FXCollections.observableList(Database.Categories.getObjects());
    private final static ObservableList<Product> products = FXCollections.observableList(Database.Products.getObjects());
    private final static ObservableList<Supplier> suppliers = FXCollections.observableList(Database.Suppliers.getObjects());
    private final static ObservableList<Bill> bills = FXCollections.observableList(Database.Bills.getObjects());
    private final static ObservableList<Item> items = FXCollections.observableList(new ArrayList<Item>());

    public static ObservableList<Category> getCategories() {
        return categories;
    }

    public static ObservableList<Product> getProducts() {
        return products;
    }

    public static ObservableList<Bill> getBills() { return bills; }

    public static ObservableList<Item> getItems() {
        if (items.size() == 0) {
            for (Supplier supplier : suppliers) {
                for (String item: supplier.getItems()) {
                    items.add(new Item(item, supplier));
                }
            }
        }
        return items;
    }

    public static ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }

    public static Category getCategoryByID(String categoryID) {
        if (categoryID == null) {
            return null;
        }
        for (Category category : categories) {
            if (category.getID().equals(categoryID)) {
                return category;
            }
        }
        return null;
    }

    public static Supplier getSupplierByID(String supplierID) {
        if (supplierID == null) {
            return null;
        }
        for (Supplier supplier : suppliers) {
            if (supplier.getID().equals(supplierID)) {
                return supplier;
            }
        }
        return null;
    }

    public static void replaceProduct(Product oldProduct, Product newProduct) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getID().equals(oldProduct.getID()))
                index = i;
        }
        if (index > -1)
            products.set(index, newProduct);
    }

    public static void createDefaultCategory() {
        Database.Categories.addObject(new Category("Skip", "No category"));
        Database.Categories.save();
    }

    public static void createDefaultSupplier() {
        Database.Suppliers.addObject(new Supplier("Skip", "No supplier"));
        Database.Suppliers.save();
    }
}
