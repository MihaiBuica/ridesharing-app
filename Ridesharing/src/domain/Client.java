package domain;

import domain.account.Account;
import domain.account.AccountCache;
import domain.account.RentalAccount;
import exceptions.AccountTypeInvalid;

import java.io.Serializable;
import java.util.UUID;

public class Client implements Serializable {

    private String name;
    private Gender gender;
    private String phoneNumber;
    private String city;
    private Document documents;

    private double balance;
    private Account rentalAccount = null;
    private Account ownerAccount = null;

    public Client(String name, Gender gender, String phoneNumber, String city) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }
    public String getName() {
        return name;
    }
    public Account getRentalAccount() throws AccountTypeInvalid, CloneNotSupportedException {
        if (this.rentalAccount == null)
        {
            this.rentalAccount = AccountCache.getInstance().getNewAccount("RENT");
            this.rentalAccount.setId(UUID.randomUUID().toString());
            this.rentalAccount.setName(this.name);
            this.rentalAccount.setBalance(balance);
        }
        return this.rentalAccount;
    }

    public Account getOwnerAccount() throws AccountTypeInvalid, CloneNotSupportedException {
        if (this.ownerAccount == null)
        {
            this.ownerAccount = AccountCache.getInstance().getNewAccount("OWNER");
            this.ownerAccount.setId(UUID.randomUUID().toString());
            this.ownerAccount.setName(this.name);

        }
        return this.ownerAccount;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", documents=" + documents +
                ", balance=" + balance +
                ", rentalAccount=" + rentalAccount +
                ", ownerAccount=" + ownerAccount +
                '}';
    }
}
