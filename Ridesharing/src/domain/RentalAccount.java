package domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class RentalAccount extends AbstractAccount{
    public RentalAccount(int id, double amount) {
        super(id, amount);
    }

    public Vehicle getVehicle(VehiclesPool pool, String type) {
        Vehicle[] availableVehicles = pool.getVehicles(type);
        try
        {
            Vehicle vehicleFound = Arrays.stream(availableVehicles)
                    .filter(vehicle -> vehicle.getPrice() < this.balance)
                    .findFirst().get();
            vehicleFound.setAvailability(false, this);
            this.vehicleSet.add(vehicleFound);
            return vehicleFound;
        }
        catch (NoSuchElementException e)
        {
            System.out.println("No vehicle found matching the conditions and expectations");
            // TODO: throw custom exception
        }
        return null;
    }

    @Override
    public void changeBalance(double amount) {
        this.balance -= amount;
        // TODO: throw exception
    }

    public void releaseVehicle(Vehicle vehicle) {
        vehicle.releaseVehicle(this);
    }
}
