package Model;

/**
 * Creates In-House parts
 */
public class InHouse extends Part {

    private int machineID;

    /**
     * Class constructor
     * @param partID part ID
     * @param name part name
     * @param price price of the product
     * @param stock amount of inventory
     * @param min minimum amount
     * @param max maximum amount
     * @param machineID machine ID
     */
    public InHouse(int partID, String name, double price, int stock, int min, int max, int machineID) {
        setPartID(partID);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        this.machineID = machineID;
    }

    /**
     * Getter for machine ID
     * @return machine ID
     */
    public int getMachineID() { return machineID; }

    /**
     * Setter for machine ID
     * @param machineID machine ID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
