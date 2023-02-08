package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import domain.Client;
import domain.Gender;
import domain.RideShareApp;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;
import domain.vehicle.ClassicVehicle;
import domain.vehicle.GreenVehicle;
import domain.vehicle.Vehicle;
import domain.vehicle.VehicleType;
import exceptions.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class TestAccounts {
    RideShareApp rideShareApp;
    Client client1, client2;
    Vehicle vehicle1, vehicle2, vehicle3;

    @Test(expected=AccountNotRegisteredException.class)
    public void testAccountUnregistered() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();
        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");

        owner.addVehicle(new ClassicVehicle(0,"Nissan", 10));
    }


    @Test(expected=NoVehicleFound.class)
    public void testAccountsGeneral() throws NoVehicleFound, InsufficientFunds, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        System.out.println("============ Accounts test =============");
        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",100);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        owner.printVehicles();
        assertEquals(2, rideShareApp.getVehiclesPool().getVehiclesPoolSize());

        OwnerAccount owner2 = new OwnerAccount(UUID.randomUUID().toString(), "Gigiuk");
        rideShareApp.registerAccount(owner2);
        Vehicle vehicle3 = new GreenVehicle(2,"BMW", 15);
        owner2.addVehicle(vehicle3);
        owner2.addVehicle(vehicle1);
        owner2.printVehicles();

        rideShareApp.getVehiclesPool().printVehiclesPool();
        assertEquals(3, rideShareApp.getVehiclesPool().getVehiclesPoolSize());

        System.out.println("=================");
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
        System.out.println(rentedVehicle.toString());
        rideShareApp.getVehiclesPool().printVehiclesPool();
        assertEquals(1, user.getVehiclesSize());

        assertEquals(0, owner.getBalance(), 0);
        assertEquals(15, owner2.getBalance(), 0);
        assertEquals(85, user.getBalance(), 0);
        System.out.println(owner.getBalance());
        System.out.println(owner2.getBalance());
        System.out.println(user.getBalance());

        System.out.println("=================");
        user.releaseVehicle(rentedVehicle);
        assertEquals(0, owner.getBalance(), 0);
        assertEquals(15, owner2.getBalance(), 0);
        assertEquals(85, user.getBalance(), 0);
        assertTrue(vehicle3.getAvailability());
        rideShareApp.getVehiclesPool().printVehiclesPool();

        System.out.println("=================");
        rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
        Vehicle rentedVehicle2 = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        user.printRentedVehicles();
        rideShareApp.getVehiclesPool().printVehiclesPool();
        assertEquals(2, user.getVehiclesSize());
        user.releaseVehicle(rentedVehicle2);
        assertEquals(1, user.getVehiclesSize());

        System.out.println("=================");
        RentalAccount user2 = new RentalAccount(UUID.randomUUID().toString(), "Jujik",20);
        rideShareApp.registerAccount(user2);
        rideShareApp.getMatchingVehicle(VehicleType.GREEN, user2);
    }


}
