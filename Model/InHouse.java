package Model;

/**
 * Class creates In-House Parts
 */
public class InHouse extends Part {

    private int machineID;

    public InHouse(int ID, String name, double price, int stock, int min, int max, int machineID) {
        super(ID, name, price, stock, min, max);
        this.machineID = machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    public int getMachineID() {
        return machineID;
    }
}
