package domain.vehicle;

public class GreenVehicle extends AbstractVehicle{
    private int autonomy;

    @Override
    public String toString() {
        return "GreenVehicle{" +
                "autonomy=" + autonomy +
                ", owner=" + owner +
                ", tempOwner=" + tempOwner +
                ", name='" + name + '\'' +
                ", history=" + history +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", id=" + id +
                '}';
    }

    public int getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(int autonomy) {
        this.autonomy = autonomy;
    }

    public GreenVehicle(int id, String name, double price) {
        super(id, name, price);
        this.autonomy = -1;
    }

    public GreenVehicle(int id, String name, double price, int autonomy) {
        super(id, name, price);
        this.autonomy = autonomy;
    }
}
