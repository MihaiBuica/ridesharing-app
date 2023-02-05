package domain.vehicle;

public class ClassicVehicle extends AbstractVehicle{

    public ClassicVehicle(int id, String name, double price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "ClassicVehicle{" +
                "owner=" + owner +
                ", tempOwner=" + tempOwner +
                ", name='" + name + '\'' +
                ", history=" + history +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", id=" + id +
                '}';
    }
}
