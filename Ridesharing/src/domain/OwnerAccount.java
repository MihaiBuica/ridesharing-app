package domain;

public class OwnerAccount extends AbstractAccount{
    public OwnerAccount(int id, double amount) {
        super(id, amount);
    }

    public void addVehicle(VehiclesPool pool, Vehicle veh)
    {
        pool.addVehicle(veh);
        this.vehicleSet.add(veh);
    }

    @Override
    public void changeBalance(double amount) {
        this.balance += amount;
    }
}
