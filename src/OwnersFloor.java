import Exceptions.NoRentedSpaceException;
import Utils.Utils;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OwnersFloor implements Floor,Cloneable{
    private Space[] spaces;
    private int size;

    public OwnersFloor() {
        spaces = new Space[16];
        size = 0;
    }

    public OwnersFloor(int size) {
        spaces = new Space[size];
        size = 0;
    }

    public OwnersFloor(Space[] spaces) {
        Objects.requireNonNull(spaces);
        this.spaces = new Space[spaces.length];
        for(int i = 0; i<this.spaces.length;i++){
            this.spaces[i] = spaces[i];
        }

        size = checkPlacedSpaces(spaces);
    }

    private void extend(){
        Space[] newSpaces = new Space[spaces.length*2];
        for(int i=0;i<spaces.length;i++){
            newSpaces[i] = spaces[i];
        }
        spaces = newSpaces;
    }

    private int checkPlacedSpaces(Space[] spaces){
        int count = 0;
        for(Space space: spaces){
            if(space!=null && !space.isEmpty())count++;
        }
        return count;
    }

    public boolean add(Space space){
        if(!checkFreeSpaces()){
            extend();
            return false;
        }else{
            lockAdd(Objects.requireNonNull(space));
            size++;
            return true;
        }
    }

    public boolean add(Space space, int number){
        if(spaces[number].isEmpty()){
            spaces[number] = Objects.requireNonNull(space);
            size++;
            return true;
        }else{
            return false;
        }
    }

    public Space get(int number){
        return spaces[number];
    }

    public Space get(String stateNumber){
        for(int i = 0; i<spaces.length;i++){
            if(spaces[i].getVehicle().getStateNumber().equals(Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber)))){
                return spaces[i];
            }
        }
        return null;
    }

    public boolean hasSpace(String stateNumber){
        for(int i = 0; i<spaces.length;i++){
            if(!spaces[i].isEmpty() && spaces[i].getVehicle().getStateNumber().equals(Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber)))){
                return true;
            }
        }
        return false;
    }

    public Space set(Space space, int index){
        Space forReturn = spaces[index];
        spaces[index] = Objects.requireNonNull(space);
        if(forReturn==null) size++;
        return forReturn;
    }

    public Space set(Space space, String stateNumber){
        Space forReturn = get(Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber)));
        for(int i = 0; i<spaces.length;i++){
            if(spaces[i].getVehicle().getStateNumber().equals(stateNumber)){
                spaces[i] = Objects.requireNonNull(space);
            }
        }
        if(forReturn == null) size++;
        return forReturn;
    }

    public Space delete(int index){
        Space forReturn = spaces[index];
        spaces[index] = null;
        size--;
        return forReturn;
    }

    public Space delete(String stateNumber){
        Space forReturn = get(Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber)));
        for(int i = 0; i<spaces.length;i++){
            if(spaces[i].getVehicle().getStateNumber().equals(stateNumber)){
                spaces[i] = null;
                return forReturn;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean delete(Space space) {
        Objects.requireNonNull(space);
        for(int i = 0; i<spaces.length;i++){
            if(spaces[i].equals(space)){
                spaces[i] = null;
                size--;
                return true;
            }
        }
        throw new NoSuchElementException();
    }

    public int getSize() {
        return spaces.length;
    }

    public Space[] getSpaces(){
        Space[] forReturn = new Space[size];
        trim();
        for(int i = 0; i<forReturn.length;i++){
            forReturn[i] = spaces[i];
        }
        return forReturn;
    }

    public Vehicle[] getVehicles(){
        Space[] spaces = getSpaces();
        Vehicle[] forReturn = new Vehicle[size];
        for(int i = 0; i<forReturn.length;i++){
            forReturn[i] = spaces[i].getVehicle();
        }
        return forReturn;
    }

    @Override
    public Space[] getTypesSpaces(VehicleTypes type) {
        Objects.requireNonNull(type);
        Space[] forReturn = new Space[getCountOfTypedVehicle(type)];
        int i = 0;
        for(Space space:spaces){
            if(space.getVehicle().getType() == type){
                forReturn[i++] = space;
            }
        }
        return forReturn;
    }

    @Override
    public Space[] getFreeSpaces() {
        Space[] forReturn = new Space[getCountOfFreeSpace()];
        int i = 0;
        for(Space space: spaces){
            if(space == null || space.isEmpty())forReturn[i++] = space;
        }
        return forReturn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rented spaces: \n");
        for(int i = 0; i<size;i++){
            if(spaces[i]!=null) sb.append(spaces[i].toString()).append('\n');
            else{
                sb.append("Тут также путо как в твоей душе");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OwnersFloor that = (OwnersFloor) obj;
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
        int hash = 71 * getSize();
        for(int i = 0; i<size;i++){
            if(spaces[i]!=null)
            hash*=spaces[i].hashCode();
        }
        return hash;
    }

    @Override
    public int indexOf(Space space) {
        Objects.requireNonNull(space);
        for(int i = 0; i<spaces.length;i++){
            if(spaces[i].equals(space))return i;
        }
        return -1;
    }

    @Override
    public int countOfPersonsSpaces(Person person) {
        Objects.requireNonNull(person);
        int count = 0;
        for(int i=0;i<spaces.length;i++){
            if(spaces[i].getPerson().equals(person)) count++;
        }
        return count;
    }

    private int getCountOfTypedVehicle(VehicleTypes type){
        Objects.requireNonNull(type);
        int count = 0;
        for(Space space:spaces){
            if(space!=null && space.getVehicle().getType()==type)count++;
        }
        return count;
    }

    private int getCountOfFreeSpace(){
        int count=0;
        for(Space space:spaces){
            if(space == null || space.isEmpty())count++;
        }
        return count;
    }

    private void lockAdd(Space space){
        for(int i = 0; i<spaces.length;i++){
            if(spaces[i].isEmpty()){
                spaces[i] = space;
                return;
            }
        }
    }

    private boolean checkFreeSpaces(){
        return !(size == spaces.length);
    }

    public void trim(){
        for(int i = 0; i<spaces.length;i++){
            for(int j = 0; j<spaces.length-1;j++){
                if(spaces[j]==null && spaces[j+1]!=null){
                    Space buf  = spaces[j];
                    spaces[j] = spaces[j+1];
                    spaces[j+1] = buf;
                }
            }
        }
    }

    public Object clone() throws CloneNotSupportedException{
        return new OwnersFloor(this.spaces.clone());
    }

    @Override
    public LocalDate nearestRentEndsDate() throws NoRentedSpaceException{
        checkRentedSpaces();
        LocalDate date = LocalDate.of(5000,0,0);
        for(int i = 0;i<spaces.length;i++){
            if(spaces[i] instanceof RentedSpace){
                RentedSpace rs = (RentedSpace) spaces[i];
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
        for(int i = 0;i<spaces.length;i++){
            if(spaces[i] instanceof RentedSpace){
                RentedSpace rs = (RentedSpace) spaces[i];
                if(rs.getRentEndsDate().equals(date)) return rs;
            }
        }
        return null;
    }

    private void checkRentedSpaces() throws NoRentedSpaceException{
        int rentedSpaceCount = 0;
        for(int i = 0; i<spaces.length;i++){
            if(spaces[i] instanceof RentedSpace) rentedSpaceCount++;
        }
        if(rentedSpaceCount==0) throw new NoRentedSpaceException();
    }
}
