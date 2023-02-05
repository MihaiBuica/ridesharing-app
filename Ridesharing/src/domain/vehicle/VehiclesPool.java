package domain.vehicle;

import domain.account.Account;
import domain.account.RentalAccount;
import exceptions.InsufficientFunds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class VehiclesPool {

    private static VehiclesPool INSTANCE;

    Set<Vehicle> pool;

    private VehiclesPool()
    {
        pool = new HashSet<>();
    }

    static public VehiclesPool getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VehiclesPool();
        }

        return INSTANCE;
    }

    public Vehicle[] getVehicles(VehicleType type)
    {
        Vehicle[] chosenVeh = null;
        switch (type)
        {
            case GREEN:
                chosenVeh = pool.stream().filter(vehicle ->
                        (vehicle instanceof GreenVehicle) && ((GreenVehicle)vehicle).isAvailable).toArray(Vehicle[]::new);
                break;
            case CLASSIC:
            default:
                chosenVeh = pool.stream().filter(vehicle ->
                        (vehicle instanceof ClassicVehicle) && ((ClassicVehicle)vehicle).isAvailable).toArray(Vehicle[]::new);
                break;
        }

        if (chosenVeh == null)
        {
            // TODO: throw custom exception for this
            return chosenVeh;
        }

        return chosenVeh;
    }

    public void addVehicle(Vehicle veh) {
        veh.setAvailability(true, null);
        pool.add(veh);
    }

    public void removeVehicle(Vehicle vehicle, Account user)
    {
        if(vehicle.getAvailability() == true && vehicle.getOwner() == user)
        {
            pool.remove(vehicle);
        }
    }

    public void printVehiclesPool()
    {
        System.out.println("Vehicles pool:");
        pool.stream().forEach(vehicle -> System.out.println(vehicle.toString()));

    }

    public ArrayList<Vehicle> getUserRentHistory(Account userAccount)
    {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        if (userAccount instanceof RentalAccount)
        {
            pool.stream()
                    .filter(vehicle -> vehicle.getVehicleHistory().containsValue(userAccount))
                    .forEach(vehicle -> vehicles.add(vehicle));
        }
        return vehicles;
    }

    public int getVehiclesPoolSize()
    {
        return pool.size();
    }

    public void destroy()
    {
        INSTANCE = null;
    }

    public void removeAllVehicles()
    {
        pool.stream().forEach(vehicle -> this.removeVehicle(vehicle, vehicle.getOwner()));
    }

}
