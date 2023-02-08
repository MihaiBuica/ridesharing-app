package tests;
import domain.Client;
import domain.Gender;
import domain.RideShareApp;
import domain.account.Account;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;
import domain.vehicle.ClassicVehicle;
import domain.vehicle.GreenVehicle;
import domain.vehicle.Vehicle;
import domain.vehicle.VehicleType;
import exceptions.AccountNotRegisteredException;
import exceptions.AccountTypeInvalid;
import exceptions.ClientExistsException;
import exceptions.NoVehicleFound;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestClient {

    RideShareApp rideShareApp;
    Client client1, client2;
    Vehicle vehicle1, vehicle2, vehicle3;
    @Before
    public void initialize() throws AccountTypeInvalid, CloneNotSupportedException, ClientExistsException {
        rideShareApp = new RideShareApp();

        client1 = new Client("User1", Gender.MALE, "", "Bucharest");
        client1.setBalance(100);
        client2 = new Client("User2", Gender.MALE, "", "Bucharest");

        rideShareApp.addClient(client1);
        rideShareApp.addClient(client2);

        OwnerAccount ownerAccountClient1 = (OwnerAccount) client1.getOwnerAccount();
        OwnerAccount ownerAccountClient2 = (OwnerAccount) client2.getOwnerAccount();

        vehicle1 = new ClassicVehicle(0,"Nissan", 10);
        vehicle2 = new ClassicVehicle(1,"Toyota", 12);
        vehicle3 = new GreenVehicle(2,"BMW", 12);
    }

    @Test
    public void testClientAccountGeneration() throws AccountTypeInvalid, CloneNotSupportedException {
        Account ownerAccount = client1.getOwnerAccount();
        assertTrue(ownerAccount instanceof OwnerAccount);
        assertFalse(ownerAccount instanceof RentalAccount);
        Account rentalAccount = client1.getRentalAccount();
        assertFalse(rentalAccount instanceof OwnerAccount);
        assertTrue(rentalAccount instanceof RentalAccount);
    }

    @Test
    public void testClientOwnerAccounts() throws AccountTypeInvalid, CloneNotSupportedException, AccountNotRegisteredException {

        OwnerAccount ownerAccountClient1 = (OwnerAccount) client1.getOwnerAccount();
        OwnerAccount ownerAccountClient2 = (OwnerAccount) client2.getOwnerAccount();

        ownerAccountClient1.addVehicle(vehicle1);
        ownerAccountClient2.addVehicle(vehicle2);
        ownerAccountClient2.addVehicle(vehicle3);

        rideShareApp.getVehiclesPool().printVehiclesPool();

        assertEquals(3, rideShareApp.getVehiclesPool().getVehiclesPoolSize());

        rideShareApp.getVehiclesPool().removeVehicle(vehicle1, vehicle1.getOwner());
        rideShareApp.getVehiclesPool().removeVehicle(vehicle2, vehicle2.getOwner());
        rideShareApp.getVehiclesPool().removeVehicle(vehicle3, vehicle1.getOwner());
        assertEquals(1, rideShareApp.getVehiclesPool().getVehiclesPoolSize());

        rideShareApp.getVehiclesPool().removeVehicle(vehicle3, vehicle3.getOwner());
        assertEquals(0, rideShareApp.getVehiclesPool().getVehiclesPoolSize());
    }

    @Test
    public void testClientRentalAccounts() throws AccountTypeInvalid, CloneNotSupportedException, NoVehicleFound, AccountNotRegisteredException {

        OwnerAccount ownerAccountClient1 = (OwnerAccount) client1.getOwnerAccount();
        RentalAccount rentAccountClient1 = (RentalAccount) client1.getRentalAccount();
        OwnerAccount ownerAccountClient2 = (OwnerAccount) client2.getOwnerAccount();

        ownerAccountClient1.addVehicle(vehicle1);
        ownerAccountClient2.addVehicle(vehicle2);
        ownerAccountClient2.addVehicle(vehicle3);

        Vehicle rentedVehicle = rideShareApp
                .getMatchingVehicle(VehicleType.GREEN, rentAccountClient1);

        assertEquals(3, rideShareApp.getVehiclesPool().getVehiclesPoolSize());
        assertTrue(rentedVehicle instanceof GreenVehicle);

        assertEquals(1, ownerAccountClient1.getVehiclesSize());
        assertEquals(2, ownerAccountClient2.getVehiclesSize());
        assertEquals(1, rentAccountClient1.getVehiclesSize());
        assertEquals(ownerAccountClient2, rentedVehicle.getOwner());
        assertEquals(rentAccountClient1, rentedVehicle.getTempOwner());

        rideShareApp.getVehiclesPool().removeVehicle(vehicle1, vehicle1.getOwner());
        rideShareApp.getVehiclesPool().removeVehicle(vehicle2, vehicle2.getOwner());
        rideShareApp.getVehiclesPool().removeVehicle(vehicle3, vehicle3.getOwner());
        assertEquals(1, rideShareApp.getVehiclesPool().getVehiclesPoolSize());

        rentAccountClient1.releaseVehicle(rentedVehicle);
        rideShareApp.getVehiclesPool().removeVehicle(vehicle3, vehicle3.getOwner());
        assertEquals(0, rideShareApp.getVehiclesPool().getVehiclesPoolSize());

    }

}
