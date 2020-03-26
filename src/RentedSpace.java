import java.time.LocalDate;
import java.util.Objects;

public class RentedSpace extends AbstractSpace implements Space,Cloneable{
    private LocalDate rentEndsDate;

    public RentedSpace(){
        this(Person.NON_NAMED_PERSON,Vehicle.NO_VEHICLE,LocalDate.now(),LocalDate.now().plusDays(1));
    }

    public RentedSpace(Person person, LocalDate sinceDate, LocalDate rentEndsDate){
        this(person,Vehicle.NO_VEHICLE,sinceDate,rentEndsDate);
    }

    public RentedSpace(Person person, Vehicle vehicle, LocalDate sinceDate,LocalDate rentEndsDate) {
        super(person,vehicle,sinceDate);
        if(rentEndsDate.isBefore(sinceDate)) throw new IllegalArgumentException();
        setRentEndsDate(rentEndsDate);
    }

    public LocalDate getRentEndsDate() {
        return rentEndsDate;
    }

    public void setRentEndsDate(LocalDate rentEndsDate) {
        this.rentEndsDate = Objects.requireNonNull(rentEndsDate);
    }

    @Override
    public int hashCode() {
        return 53*super.hashCode();
    }

    @Override
    public String toString() {
        return "Tenant: " + super.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
