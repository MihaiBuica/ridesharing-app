package domain.account;

import domain.vehicle.Vehicle;

public class OwnerAccount extends AbstractAccount{
    public OwnerAccount(String id, String name) {
        super(id, name, 0);
    }

    public void addVehicle(Vehicle veh)
    {
        if(veh.getOwner() == null)
        {
            veh.setOwner(this);
            this.vehiclesPool.addVehicle(veh);
            this.vehicleSet.add(veh);
        }
    }

    public void removeVehicle(Vehicle veh)
    {
        if(veh.getOwner() == this)
        {
            vehicleSet.remove(veh);
            vehiclesPool.removeVehicle(veh, this);
        }
    }

    @Override
    public String toString() {
        return "OwnerAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean changeBalance(double amount) {
        this.balance += amount;
        return true;
    }
}
