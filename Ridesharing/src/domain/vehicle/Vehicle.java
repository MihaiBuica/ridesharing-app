package domain.vehicle;

import domain.account.Account;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public interface Vehicle {
    void setAvailability(Boolean availability, Account tempOwner);

    void releaseVehicle(Account tmpOwner);

    double getPrice();

    Map<Timestamp, Account> getVehicleHistory();

    String toString();

    void setOwner(Account owner);
}
