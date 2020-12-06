package model;
// Completed (still needs Javadocs however)

public class InHouse extends Part {
    private String machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, String machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}
