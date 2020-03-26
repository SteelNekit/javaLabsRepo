import java.time.LocalDate;
import java.time.Period;

public interface Space {

    public Person getPerson();
    public void setPerson(Person person);
    public Vehicle getVehicle();
    public void setVehicle(Vehicle vehicle);
    public boolean isEmpty();
    public String toString();
    public boolean equals(Object obj);
    public int hashCode();
    public Object clone() throws CloneNotSupportedException;
    public LocalDate getSinceDate();
    public void setSinceDate(LocalDate date);
    public Period period();
}
