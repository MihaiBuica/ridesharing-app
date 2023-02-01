package domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAccount implements Account, Serializable, Cloneable{
    protected Set<Vehicle> vehicleSet = new HashSet<>();

    private static final long serialVersionUID = -2272551373694344386L;
    private int id;
    private int type;

    public double getBalance() {
        return balance;
    }

    protected double balance;

    public AbstractAccount(int id, double amount) {
        this.id = id;
        this.balance = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void deposit(final double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot deposit a negative amount");
        }
        this.balance += amount;
    }

    @Override
    public int getId() {
        return id;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractAccount other = (AbstractAccount) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
