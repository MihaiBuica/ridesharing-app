package domain.vehicle;

import domain.account.Account;

import java.util.Map;

public interface Vehicle {
    void setAvailability(Boolean availability, Account tempOwner);

    void releaseVehicle(Account tmpOwner);

    double getPrice();

    Map<String, Account> getVehicleHistory();

    String toString();

    void setOwner(Account owner);

    Account getOwner();

    Account getTempOwner();

    boolean getAvailability();

    public String getName();

    public int getId();
}
