package domain.vehicle;

import domain.account.Account;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractVehicle implements Vehicle{

    protected Account owner;

    protected Account tempOwner;

    protected String name;
    protected Map<String, Account> history = new HashMap<>();
    protected double price;

    protected boolean isAvailable;

    protected int id;

    public AbstractVehicle(int id, String name, double price)
    {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
    @Override
    public Account getOwner() {
        return owner;
    }
    public Map<String, Account> getVehicleHistory() {
        return history;
    }

    public double getPrice() {
        return price;
    }

    public void setAvailability(Boolean availability, Account tempOwner){
        this.isAvailable = availability;
        this.tempOwner = tempOwner;
        if (!availability)
        {
            owner.changeBalance(this.price);
            tempOwner.changeBalance(this.price);
        }
    }

    @Override
    public void releaseVehicle(Account tempOwner) {
        if (this.tempOwner.equals(tempOwner))
        {
            setAvailability(true, null);
            history.put(DateFormat.getDateInstance(DateFormat.FULL).format(new Date()),
                    tempOwner);
        }
    }

    @Override
    public Account getTempOwner() {
        return tempOwner;
    }

    public boolean getAvailability() {
        return isAvailable;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractVehicle other = (AbstractVehicle) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
