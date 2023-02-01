package domain;

import java.util.HashSet;
import java.util.Set;

public class VehiclesPool {
    Set<Vehicle> pool = new HashSet<>();

    Vehicle[] getVehicles(String type)
    {
        Vehicle[] chosenVeh = null;
        switch (type.toUpperCase())
        {
            case "GREEN":
                chosenVeh = pool.stream().filter(vehicle ->
                        (vehicle instanceof GreenVehicle) && ((GreenVehicle)vehicle).isAvailable).toArray(Vehicle[]::new);
                break;
            case "CLASSIC":
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

    void addVehicle(Vehicle veh)
    {
        veh.setAvailability(true, null);
        pool.add(veh);
    }

}
