package domain;
import domain.account.Account;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;
import domain.vehicle.*;
import email.Email;
import email.EmailException;
import email.EmailService;
import exceptions.AccountTypeInvalid;
import exceptions.ClientExistsException;
import exceptions.InsufficientFunds;
import exceptions.NoVehicleFound;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;


public class RideShareApp implements Serializable {
    private static final long serialVersionUID = -4157871135257285214L;

    private final Set<Client> clients = new HashSet<Client>();

    private final ArrayList<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    private VehiclesPool vehiclesPool;

    private EmailService emailService;

    private Client system = new Client("System", Gender.MALE, "", "");

    public RideShareApp()
    {
		listeners.add(client -> {
			System.out.println("Client added: " + client.getName());
		});

        listeners.add(client -> {
			System.out.println("Notification email for client " + client.getName() + " to be sent");

			if(emailService != null) {
				try {
                    System.out.println("EmailService detected");
					emailService.sendNotificationEmail(
							new Email()
									.setFrom(system)
									.setTo(system)
									.setCopy(client)
									.setTitle("Client Added Notification")
									.setBody("Client added: " + client.toString())
					);
				} catch (EmailException e) {
					System.err.println(e.getMessage());
				}
			}
		});

        listeners.add(client -> {
			System.out.println("Client " + client.getName() + " added on: " +
                    DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
		});

        emailService = new EmailService();
        vehiclesPool = new VehiclesPool();
    }
    public Set<Client> getClients() {
        return clients;
    }
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

    public EmailService getEmailService() {
        return emailService;
    }

    public void addClient(final Client client) throws ClientExistsException, AccountTypeInvalid, CloneNotSupportedException {
        if (clients.contains(client)) {
            throw new ClientExistsException("Client already exists into the app");
        }

        clients.add(client);
        registerAccount(client.getOwnerAccount());
        registerAccount(client.getRentalAccount());
        notify(client);
    }
    public static void main(String[] args){

        RideShareApp rideShareApp = new RideShareApp();
        // TODO: service - AppReport unde sa afisam detalii despre aplicatie
        // TODO: email pentru cand se face o tranzactie?
    }

    private void notify(Client client) {
        for (ClientRegistrationListener listener: listeners) {
            listener.onClientAdded(client);
        }
    }

    public VehiclesPool getVehiclesPool() {
        return vehiclesPool;
    }

    public void registerAccount(Account account)
    {
        account.setVehiclesPool(this.vehiclesPool);
    }

}
