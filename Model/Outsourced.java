package Model;

/**
 * Creates Outsourced parts
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Class constructor
     * @param partID part ID
     * @param name part name
     * @param price price of the product
     * @param stock amount of inventory
     * @param min minimum amount
     * @param max maximum amount
     * @param companyName company name
     */
    public Outsourced(int partID, String name, double price, int stock, int min, int max, String companyName) {
        setPartID(partID);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        this.companyName = companyName;
    }

    /**
     * Getter for company name
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for company name
     * @param companyName company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
