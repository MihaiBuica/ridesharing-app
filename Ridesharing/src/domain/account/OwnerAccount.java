package domain.account;

import domain.vehicle.Vehicle;

public class OwnerAccount extends AbstractAccount{
    public OwnerAccount(int id, String name) {
        super(id, name, 0);
    }

    public void addVehicle(Vehicle veh)
    {
        veh.setOwner(this);
        this.vehiclesPool.addVehicle(veh);
        this.vehicleSet.add(veh);
    }

    @Override
    public String toString() {
        return "OwnerAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void changeBalance(double amount) {
        this.balance += amount;
    }
}
