package domain.account;

import domain.account.AbstractAccount;
import domain.account.OwnerAccount;
import domain.account.RentalAccount;

public class AccountFactory {
    public static AbstractAccount newAccount(String accountType) {
        if (accountType == null) {
            return null;
        }
        switch (accountType.toUpperCase()) {
            case "RENT":
                return new RentalAccount("", "", 0);
            case "OWNER":
                return new OwnerAccount("", "");
            default:
                return null;
        }
    }
}
