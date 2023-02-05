package domain.account;

import domain.vehicle.Vehicle;
import exceptions.InsufficientFunds;

public class RentalAccount extends AbstractAccount{
    public RentalAccount(String id, String name, double amount) {
        super(id, name, amount);
    }

    public void addRentedVehicle(Vehicle vehicle) {
        vehicle.setAvailability(false, this);
        this.vehicleSet.add(vehicle);
    }

    @Override
    public String toString() {
        return "RentalAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean changeBalance(double amount) {
        if(this.balance < amount)
        {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public void releaseVehicle(Vehicle vehicle){
        vehicle.releaseVehicle(this);
        this.vehicleSet.remove(vehicle);
    }

    public void printRentedVehicles()
    {
        System.out.println("Vehicles rented by " + this.name);
        vehicleSet.stream().forEach(vehicle -> System.out.println(vehicle.toString()));
    }

}
