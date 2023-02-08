package tests;
import domain.Client;
import domain.Gender;
import domain.RideShareApp;
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
import service.RideShareAppReport;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestReport {
    RideShareApp rideShareApp;
    RideShareAppReport rideShareAppReport;
    Client client1, client2;
    Vehicle vehicle1, vehicle2, vehicle3;
    @Before
    public void initialize() throws AccountTypeInvalid, CloneNotSupportedException, ClientExistsException, AccountNotRegisteredException {
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
        vehicle3 = new GreenVehicle(2,"BMW", 15);

        ownerAccountClient1.addVehicle(vehicle1);
        ownerAccountClient2.addVehicle(vehicle2);
        ownerAccountClient2.addVehicle(vehicle3);

        rideShareAppReport = new RideShareAppReport(rideShareApp);
    }

    @Test
    public void testReportNumOfClients()
    {
        assertEquals(2, rideShareAppReport.getNumberOfClients());
    }

    @Test
    public void testReportNumOfAccounts()
    {
        assertEquals(4, rideShareAppReport.getNumberOfAccounts());
    }

    @Test
    public void testReportClientsSorted()
    {
        SortedSet clientsSorted = new TreeSet(new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                if (client1.getName() == null) {
                    return 1;
                }
                return client1.getName().compareTo(client2.getName());
            }
        });
        clientsSorted.add(client2);
        clientsSorted.add(client1);
        assertEquals(clientsSorted, rideShareAppReport.getClientsSorted());
    }

    @Test
    public void testReportBalanceRental() throws AccountTypeInvalid, CloneNotSupportedException, ClientExistsException {
        Client client3 = new Client("User3", Gender.MALE, "", "Bucharest");
        client3.getOwnerAccount().setBalance(100);
        rideShareApp.addClient(client3);
        assertEquals(100, rideShareAppReport.getTotalSumInRentalAccounts(), 0);
    }

    @Test
    public void testReportBalanceOwners() throws AccountTypeInvalid, CloneNotSupportedException, NoVehicleFound {
        Client client3 = new Client("User3", Gender.MALE, "", "Bucharest");
        client3.setBalance(100);
        RentalAccount rent1 = (RentalAccount) client3.getRentalAccount();
        rideShareApp.getMatchingVehicle(VehicleType.GREEN, rent1);
        assertEquals(15, rideShareAppReport.getTotalSumInOwnerAccounts(), 0);
    }

    @Test
    public void testReportBalanceAll() throws AccountTypeInvalid, CloneNotSupportedException, ClientExistsException {
        Client client3 = new Client("User3", Gender.MALE, "", "Bucharest");
        client3.getOwnerAccount().setBalance(100);
        rideShareApp.addClient(client3);
        assertEquals(200, rideShareAppReport.getTotalSumInAllAccounts(), 0);
    }

    @Test
    public void testReportNumOfVehs() {
        assertEquals(3, rideShareAppReport.getTotalNumberOfVehicles());
    }

    @Test
    public void testReportNumRentedVehs() throws AccountTypeInvalid, CloneNotSupportedException, NoVehicleFound {
        Client client3 = new Client("User3", Gender.MALE, "", "Bucharest");
        client3.setBalance(100);
        RentalAccount rent1 = (RentalAccount) client3.getRentalAccount();
        rideShareApp.getMatchingVehicle(VehicleType.GREEN, rent1);
        assertEquals(1, rideShareAppReport.getNumberOfRentedVehicles());
    }

    @Test
    public void testReportNumAvailVehs() throws AccountTypeInvalid, CloneNotSupportedException, NoVehicleFound {
        Client client3 = new Client("User3", Gender.MALE, "", "Bucharest");
        client3.setBalance(100);
        RentalAccount rent1 = (RentalAccount) client3.getRentalAccount();
        rideShareApp.getMatchingVehicle(VehicleType.GREEN, rent1);
        assertEquals(2, rideShareAppReport.getNumberOfAvailableVehicles());
    }

    @Test
    public void testReportAllRentedVehs() throws AccountTypeInvalid, CloneNotSupportedException, NoVehicleFound {
        Client client3 = new Client("User3", Gender.MALE, "", "Bucharest");
        client3.setBalance(100);
        RentalAccount rent1 = (RentalAccount) client3.getRentalAccount();
        Set<Vehicle> rentedVehicles = new HashSet<>();

        rentedVehicles.add(vehicle1);
        rentedVehicles.add(vehicle2);

        Vehicle v1 = rideShareApp.getMatchingVehicle(VehicleType.GREEN, rent1);
        Vehicle v2 = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, rent1);
        Vehicle v3 = rideShareApp.getMatchingVehicle(VehicleType.CLASSIC, rent1);
        rent1.releaseVehicle(v2);
        rent1.releaseVehicle(v3);
        assertEquals(rentedVehicles, rideShareAppReport.getAllRentedVehicles(client3));
    }
}
