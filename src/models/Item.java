package models;

public class Item {

    private String item;
    private Supplier supplier;

    public Item(String item, Supplier supplier) {
        this.item = item;
        this.supplier = supplier;
    }

    public String getItem() {
        return item;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public String getSupplierName() {
        return supplier.getName();
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
