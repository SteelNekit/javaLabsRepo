import java.time.LocalDate;

public class OwnedSpace extends AbstractSpace implements Space,Cloneable {
    public OwnedSpace(){
        super();
    }

    public OwnedSpace(Person person, LocalDate sinceDate){
        super(person,sinceDate);
    }

    public OwnedSpace(Person person, Vehicle vehicle,LocalDate sinceDate){
        super(person,vehicle,sinceDate);
    }

    @Override
    public int hashCode() {
        return 71 * super.hashCode();
    }

    @Override
    public String toString() {
        return "Owner: " + super.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
