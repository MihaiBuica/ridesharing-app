package domain;

public class AccountFactory {
    public static AbstractAccount newAccount(String accountType, int id) {
        if (accountType == null) {
            return null;
        }
        switch (accountType.toUpperCase()) {
            case "RENT":
                return new RentalAccount();
            case "OWNER":
                return new OwnerAccount();
            default:
                return null;
        }
    }
}
