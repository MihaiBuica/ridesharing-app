package service;

import domain.Client;
import domain.RideShareApp;
import domain.account.Account;
import domain.account.OwnerAccount;
import domain.vehicle.Vehicle;
import exceptions.AccountTypeInvalid;

import java.sql.Timestamp;
import java.util.*;

public class RideShareAppReport {

    private RideShareApp rideShareApp;

    public RideShareAppReport() {
        rideShareApp = new RideShareApp();
    }

    public RideShareAppReport(RideShareApp rideShareApp) {
        this.rideShareApp = rideShareApp;
    }

    public RideShareApp getRideShareApp() {
        return rideShareApp;
    }

    public int getNumberOfClients()
    {
        return rideShareApp.getClients().size();
    }

    public int getNumberOfAccounts()
    {
        int num = 0;
        num += rideShareApp.getClients().stream().filter(client ->
                client.inspectRentalAccount() != null).count();
        num += rideShareApp.getClients().stream().filter(client ->
                client.inspectOwnerAccount() != null).count();
        return num;
    }

    public SortedSet<Client> getClientsSorted() {
        SortedSet clients = new TreeSet(new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                if (client1.getName() == null) {
                    return 1;
                }
                return client1.getName().compareTo(client2.getName());
            }
        });
        rideShareApp.getClients().stream().forEach(client -> clients.add(client));
        return clients;
    }

    public double getTotalSumInOwnerAccounts() {
        Set<Client> clients = rideShareApp.getClients();
        return clients
                .stream()
                .mapToDouble(client -> client.inspectOwnerAccount().getBalance()).sum();
    }

    public double getTotalSumInRentalAccounts() {
        Set<Client> clients = rideShareApp.getClients();
        return clients
                .stream()
                .mapToDouble(client -> client.inspectRentalAccount().getBalance()).sum();
    }

    public double getTotalSumInAllAccounts() {
        return getTotalSumInOwnerAccounts() + getTotalSumInRentalAccounts();
    }

    public int getTotalNumberOfVehicles() {
        Set<Client> clients = rideShareApp.getClients();
        return clients.stream().filter(client -> client.inspectOwnerAccount() != null)
                .mapToInt(client -> client.inspectOwnerAccount().getVehiclesSize()).sum();
    }

    public Set<Vehicle> getAllRentedVehicles(Client client) {
        Set<Vehicle> rentedVehicles = new HashSet<>();
        rideShareApp.getVehiclesPool().getPool().stream().filter(vehicle ->
                vehicle.getVehicleHistory().containsValue(client.inspectRentalAccount()))
                .forEach(vehicle -> rentedVehicles.add(vehicle));
        return rentedVehicles;
    }

    public int getNumberOfAvailableVehicles() {
        return (int) rideShareApp.getVehiclesPool().getPool().stream()
                .filter(vehicle -> vehicle.getAvailability()).count();
    }

    public int getNumberOfRentedVehicles() {
        return (int) rideShareApp.getVehiclesPool().getPool().stream()
                .filter(vehicle -> !vehicle.getAvailability()).count();
    }

}
