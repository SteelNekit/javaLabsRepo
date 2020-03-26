import Exceptions.NoRentedSpaceException;

import java.time.LocalDate;

public interface Floor {
    public boolean add(Space space);
    public boolean add(Space space, int index);
    public Space get(int index);
    public Space get(String stateNumber);
    public int getSize();
    public boolean hasSpace(String stateNumber);
    public Space set(Space space, int index);
    public Space delete(int index);
    public boolean delete(Space space);
    public int indexOf(Space space);
    public int countOfPersonsSpaces(Person person);
    public Space delete(String stateNumber);
    public Space[] getSpaces();
    public Vehicle[] getVehicles();
    public Space[] getTypesSpaces(VehicleTypes type);
    public Space[] getFreeSpaces();
    public int hashCode();
    public String toString();
    public Object clone() throws CloneNotSupportedException;
    public LocalDate nearestRentEndsDate() throws NoRentedSpaceException;
    public Space spaceWithNearestRentEndsDate() throws NoRentedSpaceException;
}
