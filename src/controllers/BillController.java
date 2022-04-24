package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import managers.GUIManager;
import models.Bill;
import models.Product;
import models.Stock;
import utility.Utilities;
import views.BillView;

public class BillController implements ControllerInterface<BillView> {

    private Bill bill = null;

    public BillController(BillView bv) {
        setView(bv);
    }

    @Override
    public void setView(BillView view) {
        view.getScrollPane().setContent(view.createRowsContainer());
        for (Node node : view.getRowsContainer().getChildren()) {
            handleProductField(node);
        }

        view.getAddProduct().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HBox hBox = view.createRow();
                handleProductField(hBox);
                view.getRowsContainer().getChildren().add(hBox);
            }
        });

        view.getCreateBill().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Node node: view.getRowsContainer().getChildren()) {
                    if (node instanceof HBox) {
                        ComboBox<Product> products = null;
                        TextField quantity = null;
                        for (Node insideNode : ((HBox) node).getChildren()) {
                            if (insideNode instanceof ComboBox) {
                                products = (ComboBox<Product>) insideNode;
                            }
                            if (insideNode instanceof TextField) {
                                quantity = (TextField) insideNode;
                            }
                        }
                        Product product = products.getSelectionModel().getSelectedItem();
                        String quantity_s = quantity.getText();
                        if (Utilities.validateProductToBuy(product, quantity_s)) {
                            int q = Integer.parseInt(quantity_s);
                            product.setQuantity(product.getQuantity() - q);
                            if (bill == null) {
                                bill = new Bill();
                            }
                            bill.getProducts().add(product);
                            bill.getQuantities().add(q);
                            bill.addToTotalCost(q * product.getPrice());
                        }
                    }
                }
                if (bill != null) {
                    GUIManager.displayCreatedBill(bill);
                    bill = null;
                }
            }
        });
    }

    private void handleProductField(Node node) {
        if (node instanceof HBox) {
            ComboBox<Product> products = null;
            TextField quantity = null;
            Button removeProduct = null;
            Text cost = null;
            for (Node insideNode : ((HBox)node).getChildren()) {
                if (insideNode instanceof ComboBox) {
                    products = ((ComboBox<Product>)insideNode);
                }
                if (insideNode instanceof TextField) {
                    quantity = ((TextField)insideNode);
                }
                if (insideNode instanceof Button) {
                    removeProduct = ((Button)insideNode);
                }
                if (insideNode instanceof Text) {
                    cost = ((Text)insideNode);
                }
            }
            if (products != null) {
                products.setItems(Stock.getProducts());
                products.valueProperty().addListener(new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observableValue, Product product, Product t1) {
                        if (t1 != null) {
                            if (t1.getQuantity() == 0) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information");
                                alert.setHeaderText("Check stock");
                                alert.setContentText("This product isn't in stock.\n");
                                alert.showAndWait();
                            }
                        }
                    }
                });
            }
            if (quantity != null) {
                Text finalCost = cost;
                ComboBox<Product> finalProducts = products;
                quantity.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        if (t1.matches("^[0-9]{1,4}$")) {
                            int q = Integer.parseInt(t1);
                            Product product = finalProducts.getSelectionModel().getSelectedItem();
                            if (product != null) {
                                if (finalCost != null) {
                                    finalCost.setText("Cost: " + q * product.getPrice() + "$");
                                }
                            }
                        } else {
                            finalCost.setText("Cost: 0$");
                        }
                    }
                });
            }
            if (removeProduct != null) {
                removeProduct.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        ((VBox)node.getParent()).getChildren().remove(node);
                    }
                });
            }
        }
    }
}
