package domain.account;

public interface Account {
    public String getId();
    public boolean changeBalance(double amount);
    public void setName(String name);
    public void setId(String id);
    public void setBalance(double balance);

}
