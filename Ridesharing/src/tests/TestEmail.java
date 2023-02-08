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

import static org.junit.Assert.assertEquals;

public class TestEmail {

    @Test(expected= ClientExistsException.class)
    public void testEmailSend() throws AccountTypeInvalid, CloneNotSupportedException, ClientExistsException {
        RideShareApp rideShareApp = new RideShareApp();

        Client client1 = new Client("User1", Gender.MALE, "", "Bucharest");
        client1.setBalance(100);
        Client client2 = new Client("User2", Gender.MALE, "", "Bucharest");

        rideShareApp.addClient(client1);
        rideShareApp.addClient(client2);

        rideShareApp.addClient(client1);
    }

    @Test
    public void testEmailSentNum() throws AccountTypeInvalid, ClientExistsException, CloneNotSupportedException {
        RideShareApp rideShareApp = new RideShareApp();

        Client client1 = new Client("User1", Gender.MALE, "", "Bucharest");
        client1.setBalance(100);
        Client client2 = new Client("User2", Gender.MALE, "", "Bucharest");

        rideShareApp.addClient(client1);
        rideShareApp.addClient(client2);

        assertEquals(2, rideShareApp.getEmailService().getSentEmails() +
                rideShareApp.getEmailService().getQueueSize());
    }

}
