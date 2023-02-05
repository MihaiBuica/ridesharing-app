package tests;
import static org.junit.Assert.assertEquals;

import domain.RideShareApp;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;
import domain.vehicle.ClassicVehicle;
import domain.vehicle.GreenVehicle;
import domain.vehicle.Vehicle;
import domain.vehicle.VehicleType;
import exceptions.InsufficientFunds;
import exceptions.NoVehicleFound;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.UUID;

public class TestAccounts {
    @Test
    public void testAccountsGeneral() throws NoVehicleFound, InsufficientFunds {
        RideShareApp rideShareApp = new RideShareApp();

        System.out.println("============ Accounts test =============");
        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",100);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        owner.printVehicles();

        OwnerAccount owner2 = new OwnerAccount(UUID.randomUUID().toString(), "Gigiuk");
        Vehicle vehicle3 = new GreenVehicle(2,"BMW", 15);
        owner2.addVehicle(vehicle3);
        owner2.addVehicle(vehicle1);
        owner2.printVehicles();

        rideShareApp.getVehiclesPool().printVehiclesPool();

        System.out.println("=================");
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
        System.out.println(rentedVehicle.toString());
        rideShareApp.getVehiclesPool().printVehiclesPool();

        System.out.println(owner.getBalance());
        System.out.println(owner2.getBalance());
        System.out.println(user.getBalance());

        System.out.println("=================");
        user.releaseVehicle(rentedVehicle);
        System.out.println("Owner1: " + owner.getBalance());
        System.out.println("Owner2: " + owner2.getBalance());
        System.out.println("User: " + user.getBalance());
        rideShareApp.getVehiclesPool().printVehiclesPool();

        System.out.println("=================");
        rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
        Vehicle rentedVehicle2 = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        user.printRentedVehicles();
        rideShareApp.getVehiclesPool().printVehiclesPool();
        user.releaseVehicle(rentedVehicle2);

        System.out.println("=================");
        RentalAccount user2 = new RentalAccount(UUID.randomUUID().toString(), "Jujik",20);
        rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user2);
        System.out.println(rideShareApp.getVehiclesPool().getUserRentHistory(user));
    }

    @Test(expected=NoVehicleFound.class)
    public void testAccountsException() throws NoVehicleFound {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",100);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
    }

    @Test(expected=NoVehicleFound.class)
    public void testAccountsExceptionFunds() throws NoVehicleFound {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        user.setBalance(100);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        assertEquals(1, user.getVehiclesSize());

        user.releaseVehicle(rentedVehicle);
        assertEquals(0, user.getVehiclesSize());

        user.setBalance(0);
        rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
    }

}
