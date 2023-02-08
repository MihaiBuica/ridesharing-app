package tests;
import domain.RideShareApp;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;
import domain.vehicle.ClassicVehicle;
import domain.vehicle.GreenVehicle;
import domain.vehicle.Vehicle;
import domain.vehicle.VehicleType;
import exceptions.AccountNotRegisteredException;
import exceptions.NoVehicleFound;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class TestRent {


    @Test
    public void testRentClassic() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",100);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);

        assertEquals(1, user.getVehiclesSize());
    }

    @Test
    public void testRentGreen() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",100);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new GreenVehicle(1,"Toyota", 12, 1000);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);

        assertEquals(1, user.getVehiclesSize());
    }

    @Test
    public void testRentMultiple() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",100);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new GreenVehicle(1,"Toyota", 12, 1000);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
        Vehicle rentedVehicle2 = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);

        assertEquals(2, user.getVehiclesSize());
    }

    @Test(expected= NoVehicleFound.class)
    public void testRentException() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",100);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);

        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.GREEN, user);
    }

    @Test
    public void testRentRelease() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        user.setBalance(100);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        assertEquals(1, user.getVehiclesSize());

        user.releaseVehicle(rentedVehicle);
        assertEquals(0, user.getVehiclesSize());
    }

    @Test
    public void testRentAvailability() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        owner.addVehicle(vehicle1);
        user.setBalance(100);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        assertTrue(!vehicle1.getAvailability());

        user.releaseVehicle(rentedVehicle);
        assertEquals(0, user.getVehiclesSize());
        assertTrue(vehicle1.getAvailability());
    }

    @Test
    public void testRentOwner() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        owner.addVehicle(vehicle1);
        user.setBalance(100);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);

        assertEquals(owner, rentedVehicle.getOwner());
    }

    @Test
    public void testRentTempOwner() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        owner.addVehicle(vehicle1);
        user.setBalance(100);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);

        assertEquals(user, rentedVehicle.getTempOwner());

    }

    @Test
    public void testRenTempOwner2() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        owner.addVehicle(vehicle1);
        user.setBalance(100);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);

        assertEquals(user, rentedVehicle.getTempOwner());

        user.releaseVehicle(rentedVehicle);

        assertEquals(null, rentedVehicle.getTempOwner());

    }

    @Test
    public void testRentHistory() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        owner.addVehicle(vehicle1);
        user.setBalance(100);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        assertTrue(!vehicle1.getAvailability());

        user.releaseVehicle(rentedVehicle);
        assertEquals(0, user.getVehiclesSize());
        assertTrue(vehicle1.getAvailability());
    }

    @Test
    public void testRentBalance() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
        Vehicle vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        Vehicle vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        owner.addVehicle(vehicle1);
        owner.addVehicle(vehicle2);
        user.setBalance(23);

        Vehicle rentedVehicle = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        Vehicle rentedVehicle2 = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, user);
        assertEquals(2, user.getVehiclesSize());

        user.releaseVehicle(rentedVehicle);
        user.releaseVehicle(rentedVehicle2);
        assertEquals(0, user.getVehiclesSize());

        assertEquals(1, user.getBalance(), 0);
        assertEquals(22, owner.getBalance(), 0);
    }

    @Test(expected=NoVehicleFound.class)
    public void testAccountsExceptionFunds() throws NoVehicleFound, AccountNotRegisteredException {
        RideShareApp rideShareApp = new RideShareApp();

        OwnerAccount owner = new OwnerAccount(UUID.randomUUID().toString(), "Jon");
        RentalAccount user = new RentalAccount(UUID.randomUUID().toString(), "Quinoua",0);
        rideShareApp.registerAccount(owner);
        rideShareApp.registerAccount(user);
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
