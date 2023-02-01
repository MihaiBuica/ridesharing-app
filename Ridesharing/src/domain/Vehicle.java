package domain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface Vehicle {
    void setAvailability(Boolean availability, Account tempOwner);

    void releaseVehicle(Account tmpOwner);

    double getPrice();

    ArrayList<Account> getVehicleHistory();
}
