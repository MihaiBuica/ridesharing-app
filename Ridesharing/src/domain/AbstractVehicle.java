package domain;

import java.util.ArrayList;

public abstract class AbstractVehicle implements Vehicle{
    Account owner; //TODO: maybe id si sa-l caut in lista de clienti
    Account tempOwner;
    String name;
    ArrayList<Account> history = new ArrayList<>();
    double price;
    boolean isAvailable;

    public ArrayList<Account> getVehicleHistory() {
        return history;
    }

    public double getPrice() {
        return price;
    }

    public void setAvailability(Boolean availability, Account tempOwner)
    {
        this.isAvailable = availability;
        this.tempOwner = tempOwner;
        if (!availability)
        {
            owner.changeBalance(0.9 * this.price);
            tempOwner.changeBalance(this.price);
        }
    }

    @Override
    public void releaseVehicle(Account tempOwner) {
        if (this.tempOwner.equals(tempOwner))
        {
            setAvailability(true, null);
        }
    }
}
