package domain;
import domain.account.Account;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;
import domain.vehicle.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


public class RideShareApp implements Serializable {
    private static final long serialVersionUID = -4157871135257285214L;
    private final Set<Client> clients = new HashSet<Client>();
//    private final ArrayLis<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    private VehiclesPool vehiclesPool = VehiclesPool.getInstance();

    public Vehicle getMatchingVehicle(VehicleType type, RentalAccount account) {
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
            // TODO: throw custom exception
        }
        return null;
    }

    public static void main(String[] args){

        RideShareApp rideShareApp = new RideShareApp();
        OwnerAccount owner = new OwnerAccount(0, "Jon");
        RentalAccount user = new RentalAccount(1, "Quinoua",100);

        // Owner adauga un vehicul
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        owner.printVehicles();

        OwnerAccount owner2 = new OwnerAccount(2, "Gigiuk");
        Vehicle vehicle3 = new GreenVehicle(2,"BMW", 15);
        owner2.addVehicle(vehicle3);
        owner2.addVehicle(vehicle1);
        owner2.printVehicles();

        rideShareApp.vehiclesPool.printVehiclesPool();

        System.out.println("=================");
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
        System.out.println(rentedVehicle.toString());
        rideShareApp.vehiclesPool.printVehiclesPool();

        System.out.println(owner.getBalance());
        System.out.println(owner2.getBalance());
        System.out.println(user.getBalance());

        System.out.println("=================");
        user.releaseVehicle(rentedVehicle);
        System.out.println("Owner1: " + owner.getBalance());
        System.out.println("Owner2: " + owner2.getBalance());
        System.out.println("User: " + user.getBalance());
        rideShareApp.vehiclesPool.printVehiclesPool();

        System.out.println("=================");
        rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
        Vehicle rentedVehicle2 = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        user.printRentedVehicles();
        rideShareApp.vehiclesPool.printVehiclesPool();
    }
}
