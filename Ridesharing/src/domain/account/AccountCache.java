package domain.account;

import exceptions.AccountTypeInvalid;

import java.util.HashMap;
import java.util.Map;

public class AccountCache {
    private static AccountCache INSTANCE;
    private Map<String, AbstractAccount> accountCache;

    private AccountCache() {
        accountCache = new HashMap<>();
        accountCache.put("OWNER", AccountFactory.newAccount("OWNER"));
        accountCache.put("RENT", AccountFactory.newAccount("RENT"));
    }

    public static AccountCache getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new AccountCache();
        }

        return INSTANCE;
    }

    public AbstractAccount getNewAccount(String type) throws AccountTypeInvalid, CloneNotSupportedException {
        AbstractAccount newAccount;

        switch (type.toUpperCase()){
            case "OWNER":
                newAccount = (AbstractAccount) accountCache.get("OWNER").clone();
                break;
            case "RENT":
                newAccount = (AbstractAccount) accountCache.get("RENT").clone();
                break;
            default:
                throw new AccountTypeInvalid("Invalid account type");
        }

        return newAccount;
    }



}
