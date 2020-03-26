import Exceptions.NoRentedSpaceException;
import LinkedList.LinkedList;
import Utils.Utils;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;

public class RentedSpacesFloor implements Floor,Cloneable{
    private LinkedList<Space> spaces;

    public RentedSpacesFloor(){
        spaces = new LinkedList<Space>();
    }

    public RentedSpacesFloor(Space[] spaces) {
        Objects.requireNonNull(spaces);
        this.spaces = new LinkedList<Space>(spaces);
    }

    @Override
    public boolean add(Space space) {
        Objects.requireNonNull(space);
        spaces.add(space);
        return true;
    }

    @Override
    public boolean add(Space space, int index) {
        Objects.requireNonNull(space);
        spaces.add(space,index);
        return true;
    }

    @Override
    public Space get(int index) {
        return spaces.get(index);
    }

    @Override
    public Space get(String stateNumber) {
        Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber));
        for(int i = 0; i<spaces.getSize();i++){
            if(spaces.get(i).getVehicle().getStateNumber().equals(stateNumber)){
                return spaces.get(i);
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        return spaces.getSize();
    }

    @Override
    public boolean hasSpace(String stateNumber) {
        Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber));
        for(int i = 0; i<spaces.getSize();i++){
            if(!spaces.get(i).isEmpty() && spaces.get(i).getVehicle().getStateNumber().equals(stateNumber)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Space set(Space space, int index) {
        Objects.requireNonNull(space);
        return spaces.set(space,index);
    }

    @Override
    public Space delete(int index) {
        return spaces.delete(index);
    }

    @Override
    public Space delete(String stateNumber) {
        Space forReturn = get(Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber)));
        for(int i = 0; i<spaces.getSize();i++){
            if(spaces.get(i).getVehicle().getStateNumber().equals(stateNumber)){
                spaces.delete(i);
                return forReturn;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean delete(Space space) {
        Objects.requireNonNull(space);
        for(int i = 0; i<spaces.getSize();i++){
            if(spaces.get(i).equals(space)){
                spaces.delete(i);
                return true;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Space[] getSpaces() {//В целом уёбищный метод
        Space[] forReturn = new Space[spaces.getSize()];
        for(int i = 0; i<spaces.getSize();i++){
            forReturn[i] = spaces.get(i);
        }
        return forReturn;
    }

    @Override
    public Vehicle[] getVehicles() {
        Space[] spaces = getSpaces();
        Vehicle[] forReturn = new Vehicle[spaces.length];
        for(int i = 0; i<forReturn.length;i++){
            forReturn[i] = spaces[i].getVehicle();
        }
        return forReturn;
    }

    @Override
    public Space[] getTypesSpaces(VehicleTypes type) {
        Objects.requireNonNull(type);
        LinkedList<Space> bufList = new LinkedList<Space>();
        for(int i = 0;i<spaces.getSize();i++){
            if(spaces.get(i).getVehicle().getType() == type){
                bufList.add(spaces.get(i));
            }
        }
        Space[] forReturn = new Space[bufList.getSize()];
        for(int i = 0; i<forReturn.length;i++){
            forReturn[i] = bufList.get(i);
        }
        return forReturn;
    }

    @Override
    public Space[] getFreeSpaces() {
        LinkedList<Space> bufList = new LinkedList<Space>();
        for(int i = 0;i<spaces.getSize();i++){
            if(spaces.get(i).isEmpty())bufList.add(spaces.get(i));
        }
        Space[] forReturn = new Space[bufList.getSize()];
        for(int i = 0; i<forReturn.length;i++){
            forReturn[i] = bufList.get(i);
        }
        return forReturn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rented spaces: \n");
        for(int i = 0; i<spaces.getSize();i++){
            sb.append(spaces.get(i).toString()).append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RentedSpacesFloor that = (RentedSpacesFloor) obj;
        boolean answer = that.getSize() == this.getSize();
        if(answer){
            for(int i = 0; i<getSize();i++){
                answer &= (that.getSpaces()[i].equals(this.getSpaces()[i]));
            }
        }
        return answer;
    }

    @Override
    public int hashCode() {
        int hash = 53 * getSize();
        for(int i = 0; i<getSize();i++){
            hash*=spaces.get(i).hashCode();
        }
        return hash;
    }

    @Override
    public int indexOf(Space space) {
        Objects.requireNonNull(space);
        return spaces.indexOf(space);
    }

    @Override
    public int countOfPersonsSpaces(Person person) {
        int count = 0;
        for(int i = 0; i<getSize();i++){
            if(spaces.get(i).getPerson().equals(person)) count++;
        }
        return count;
    }

    public Object clone() throws CloneNotSupportedException{
        return new RentedSpacesFloor(getSpaces().clone());
    }

    @Override
    public LocalDate nearestRentEndsDate() throws NoRentedSpaceException{
        checkRentedSpaces();
        LocalDate date = LocalDate.of(5000,0,0);
        for(int i = 0;i<spaces.getSize();i++){
            if(spaces.get(i) instanceof RentedSpace){
                RentedSpace rs = (RentedSpace) spaces.get(i);
                if(rs.getRentEndsDate().isBefore(date) &&
                rs.getRentEndsDate().isAfter(LocalDate.now().minusDays(1))){}
                date = rs.getRentEndsDate();
            }
        }
        return date;
    }

    @Override
    public Space spaceWithNearestRentEndsDate() throws NoRentedSpaceException{
        LocalDate date = nearestRentEndsDate();
        for(int i = 0;i<spaces.getSize();i++){
            if(spaces.get(i) instanceof RentedSpace){
                RentedSpace rs = (RentedSpace) spaces.get(i);
                if(rs.getRentEndsDate().equals(date)) return rs;
            }
        }
        return null;
    }

    private void checkRentedSpaces() throws NoRentedSpaceException{
        int rentedSpaceCount = 0;
        for(int i = 0; i<spaces.getSize();i++){
            if(spaces.get(i) instanceof RentedSpace) rentedSpaceCount++;
        }
        if(rentedSpaceCount==0) throw new NoRentedSpaceException();
    }
}
