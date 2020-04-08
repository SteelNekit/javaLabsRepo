import Exceptions.NoRentedSpaceException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

public interface Floor extends Comparable<Floor>,Iterable<Space>, Collection<Space> {
    public boolean add(Space space);
    public boolean add(Space space, int index);
    public Space get(int index);
    public Space get(String stateNumber);
    public boolean hasSpace(String stateNumber);
    public Space set(Space space, int index);
    public int indexOf(Space space);
    public int countOfPersonsSpaces(Person person);
    public Collection<Vehicle> getVehicles();
    public List<Space> getTypesSpaces(VehicleTypes type);
    public Deque<Space> getFreeSpaces();
    public int hashCode();
    public String toString();
    public Object clone() throws CloneNotSupportedException;
    public LocalDate nearestRentEndsDate() throws NoRentedSpaceException;
    public Space spaceWithNearestRentEndsDate() throws NoRentedSpaceException;
}
