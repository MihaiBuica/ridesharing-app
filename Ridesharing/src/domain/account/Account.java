package domain.account;

import domain.vehicle.VehiclesPool;

public interface Account {
    public String getId();
    public boolean changeBalance(double amount);
    public void setName(String name);
    public void setId(String id);
    public void setBalance(double balance);

    public void setVehiclesPool(VehiclesPool vehiclesPool);

}
