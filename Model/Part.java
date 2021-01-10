package Model;

/**
 * Supplied class Part.java
 * @author Alyssa Weiglein
 */
public abstract class Part {
    private int ID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int ID, String name, double price, int stock, int min, int max) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getStock() {
        return stock;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public int getMin() {
        return min;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public int getMax() {
        return max;
    }

}
