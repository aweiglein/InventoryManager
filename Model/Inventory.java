package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates Inventory
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to inventory
     * @param newPart new part to be added to inventory
     */
    public static void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /**
     * Looks up part by part ID
     * @param partID part ID
     * @return part if found by part ID
     */
    public static Part lookupPart(int partID) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartID() == partID) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Looks up part by part name
     * @param partName part name
     * @return part if found by part name
     */
    public static Part lookupPart(String partName) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName() == partName) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Updates selected part
     * @param selectedPart part to update
     */
    public static void updatePart(Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartID() == selectedPart.getPartID()) {
                allParts.set(i, selectedPart);
                break;
            }
        }
    }

    /**
     * Deletes selected part
     * @param selectedPart part to be deleted
     * @return boolean value
     */
    public static boolean deletePart(Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartID() == selectedPart.getPartID()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds product to inventory
     * @param newProduct new product to be added to inventory
     */
    public static void addProduct(Product newProduct) {
        if (newProduct != null) {
            allProducts.add(newProduct);
        }
    }

    /**
     * Looks up product by product ID
     * @param productID product ID
     * @return product if found by product ID
     */
    public static Product lookupProduct(int productID) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProductID() == productID) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Looks up product by product name
     * @param productName product name
     * @return product if found by product name
     */
    public static Product lookupProduct(String productName) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getName() == productName) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Updates product
     * @param selectedProduct product to update
     */
    public static void updateProduct(Product selectedProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == selectedProduct.getProductID()) {
                allProducts.set(i, selectedProduct);
                break;
            }
        }
    }

    /**
     * Deletes selected product
     * @param selectedProduct product to be deleted
     * @return boolean value
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == selectedProduct.getProductID()) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for all parts in inventory
     * @return list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter for all products in inventory
     * @return list of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
