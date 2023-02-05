package domain.account;

import domain.vehicle.Vehicle;
import domain.vehicle.VehicleType;
import domain.vehicle.VehiclesPool;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class RentalAccount extends AbstractAccount{
    public RentalAccount(int id, String name, double amount) {
        super(id, name, amount);
    }

    public void addRentedVehicle(Vehicle vehicle)
    {
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
    public void changeBalance(double amount) {
        this.balance -= amount;
        // TODO: throw exception
    }

    public void releaseVehicle(Vehicle vehicle) {
        vehicle.releaseVehicle(this);
        this.vehicleSet.remove(vehicle);
    }

    public void printRentedVehicles()
    {
        System.out.println("Vehicles rented by " + this.name);
        vehicleSet.stream().forEach(vehicle -> System.out.println(vehicle.toString()));
    }
}
