package domain;

public class AccountFactory {
    public static AbstractAccount newAccount(String accountType, int id) {
        if (accountType == null) {
            return null;
        }
        switch (accountType.toUpperCase()) {
            case "RENT":
                return new RentalAccount(0, 0);
            case "OWNER":
                return new OwnerAccount(0, 0);
            default:
                return null;
        }
    }
}
