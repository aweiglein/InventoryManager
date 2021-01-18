package Model;

/**
 * Supplied class Part
 */
public abstract class Part {
    protected int partID;
    protected String name;
    protected double price = 0.0;
    protected int stock;
    protected int min;
    protected int max;

    /**
     * Getter for part ID
     * @return part ID
     */
    public int getPartID() {
        return partID;
    }

    /**
     * Setter for part ID
     * @param partID part ID
     */
    public void setPartID(int partID) {
        this.partID = partID;
    }

    /**
     * Getter for part name
     * @return part name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for part name
     * @param name part name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for part price
     * @return part price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for part price
     * @param price part price
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
}
