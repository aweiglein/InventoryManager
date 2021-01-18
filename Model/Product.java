package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates Products
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Class constructor
     * @param productID product ID
     * @param name product name
     * @param price price of the product
     * @param stock amount of inventory
     * @param min minimum amount
     * @param max maximum amount
     */
    public Product(int productID, String name, double price, int stock, int min, int max) {
        setProductID(productID);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    /**
     * Getter for product ID
     * @return product ID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Setter for product ID
     * @param ProductID product ID
     */
    public void setProductID(int ProductID) {
        this.productID = ProductID;
    }

    /**
     * Getter for product name
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for product name
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for product price
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for product price
     * @param price product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for amount in stock
     * @return amount in stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for amount in stock
     * @param stock amount in stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for minimum
     * @return minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for minimum
     * @param min minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for maximum
     * @return maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for maximum
     * @param max maximum
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds associated part
     * @param selectedPart part to be added
     */
    public void addAssociatedPart(Part selectedPart) {
        associatedParts.add(selectedPart);
    }

    /**
     * Deletes associated part
     * @param selectedPart part to be deleted
     * @return boolean value
     */
    public boolean deleteAssociatedPart(Part selectedPart) {
        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getPartID() == selectedPart.getPartID()) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for all associated parts
     * @return list of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
