package tests;

import domain.Client;
import domain.Gender;
import domain.RideShareApp;
import domain.account.OwnerAccount;
import domain.vehicle.ClassicVehicle;
import domain.vehicle.GreenVehicle;
import domain.vehicle.Vehicle;
import exceptions.AccountTypeInvalid;
import exceptions.ClientExistsException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEmail {
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
    }

    @Test(expected= ClientExistsException.class)
    public void testEmailSend() throws AccountTypeInvalid, CloneNotSupportedException, ClientExistsException {
        rideShareApp.addClient(client1);
    }

    @Test
    public void testEmailSentNum() throws AccountTypeInvalid, CloneNotSupportedException, ClientExistsException {
        assertEquals(2, rideShareApp.getEmailService().getSentEmails() +
                rideShareApp.getEmailService().getQueueSize());
    }

}
