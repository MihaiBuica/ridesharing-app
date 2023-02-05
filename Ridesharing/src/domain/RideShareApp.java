package domain;
import domain.account.Account;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;
import domain.vehicle.*;
import exceptions.InsufficientFunds;
import exceptions.NoVehicleFound;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


public class RideShareApp implements Serializable {
    private static final long serialVersionUID = -4157871135257285214L;
    private final Set<Client> clients = new HashSet<Client>();

    public VehiclesPool getVehiclesPool() {
        return vehiclesPool;
    }

    //    private final ArrayLis<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    private VehiclesPool vehiclesPool = VehiclesPool.getInstance();

    public Vehicle getMatchingVehicle(VehicleType type, RentalAccount account) throws NoVehicleFound {
        Vehicle[] availableVehicles = vehiclesPool.getVehicles(type);
        try
        {
            Vehicle vehicleFound = Arrays.stream(availableVehicles)
                    .filter(vehicle -> vehicle.getPrice() < account.getBalance())
                    .findFirst().get();
            account.addRentedVehicle(vehicleFound);
            return vehicleFound;
        }
        catch (NoSuchElementException e)
        {
            System.out.println("No vehicle found matching the conditions and expectations");
            throw new NoVehicleFound("No vehicle");
        }
    }

    public void addClient(Client client)
    {
        clients.add(client);
    }

    public static void main(String[] args){

        RideShareApp rideShareApp = new RideShareApp();


    }

    public void destroyVehiclesPool() {
        this.vehiclesPool.destroy();
    }
}
