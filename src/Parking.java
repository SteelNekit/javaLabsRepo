import Utils.Utils;
import LinkedList.LinkedList;

import java.lang.reflect.Array;
import java.util.*;
/*Такая хуета потому что в утиле есть свой LinkedList
И идея думает что вместо своего списка я хочу утильный
Чтоб она дохуя не думала будет вот так */

public class Parking implements Iterable<Floor>{
    private Floor[] floors;
    private int size;

    public Parking(int size) {
        this.size = size;
        floors = new Floor[size];
    }

    public Parking(Floor[] floors) {
        Objects.requireNonNull(floors);
        this.floors = new Floor[floors.length];
        size = floors.length;
    }

    public boolean add(Floor floor){
        if(checkFreeFloors()){
            lockAdd(Objects.requireNonNull(floor));
            size++;
            return true;
        }else{
            extend();
            return false;
        }
    }

    public boolean add(Floor floor, int index){
        if(floors[index] == null){
            floors[index] = Objects.requireNonNull(floor);
            size++;
            return true;
        }else{
            return false;
        }
    }

    public Floor get(int index){
        return floors[index];
    }

    public Floor set(Floor floor, int index){
        Objects.requireNonNull(floor);
        Floor buffer = floors[index];
        floors[index] = floor;
        if(buffer == null) size++;
        return buffer;
    }

    public Floor delete(int index){
        Floor buf = floors[index];
        floors[index] = null;
        size--;
        return buf;
    }

    public int getSize() {
        return size;
    }

    public Floor[] getFloors() {
        Floor[] buf = new Floor[size];
        trim();
        for(int i = 0; i<size;i++){
            buf[i] = floors[i];
        }
        return buf;
    }

    public List<Floor> getSortedFloors(){
        ArrayList<Floor> floors = new ArrayList<>();
        for(Floor floor:getFloors()){
            floors.add(floor);
        }
        for(int i = 0; i<floors.size();i++){
            for(int j = 0;j<floors.size()-1;i++){
                if(floors.get(j).size()>floors.get(j+1).size()){
                    Floor c = floors.set(j,floors.get(j+1));
                    floors.set(j+1,c);
                }
            }
        }
        return floors;
    }

    public Collection<Vehicle> getVehicles(){
        ArrayList<Vehicle> cars = new ArrayList<>();
        for(Floor floor: floors){
            cars.addAll(floor.getVehicles());
        }
        return cars;
    }

    public Space getSpace(String stateNumber){
        Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber));
        for(Floor floor: floors){
            if(floor.get(stateNumber)!=null){
                return floor.get(stateNumber);
            }
        }
        throw new NoSuchElementException();
    }

    public Space deleteSpace(String stateNumber){
        Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber));
        Space forReturn = getSpace(stateNumber);
        for(int i = 0; i< floors.length; i++){
            floors[i].remove(stateNumber);
        }
        return forReturn;
    }

    public int getCountOfFreeSpaces(){
        int count = 0;
        for(Floor floor: floors){
            count+=floor.getFreeSpaces().size();
        }
        return count;
    }

    public int getCountOfTypedVehicles(VehicleTypes type){
        Objects.requireNonNull(type);
        int count = 0;
        for(Floor floor: floors){
            count+=floor.getTypesSpaces(type).size();
        }
        return count;
    }

    private void extend(){
        Floor[] newFloors = new Floor[floors.length*2];
        for(int i = 0; i< floors.length; i++){
            newFloors[i] = floors[i];
        }
        floors = newFloors;
    }

    private boolean checkFreeFloors(){
        return !(size == floors.length);
    }

    private void lockAdd(Floor floor){
        for(int i = 0; i< floors.length; i++){
            if(floors[i] == null){
                floors[i] = floor;
            }
        }
    }

    private void trim(){
        for(int i = 0; i< floors.length; i++){
            for(int j = 0; j< floors.length-1; j++){
                if(floors[j]==null && floors[j+1]!=null){
                    Floor buf = floors[j];
                    floors[j] = floors[j+1];
                    floors[j+1] = buf;
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Floors(<");
        sb.append(size).append("> total):\n");
        for(int i = 0;i<size;i++){
            if(floors[i]!=null){
                sb.append(floors[i].toString());
            }else{
                sb.append("тута пустой этаж");
            }
        }
        return sb.toString();
    }

    public Set<Floor> getFloorWithPersonsSpace(Person person){
        Objects.requireNonNull(person);
        HashSet<Floor> forReturn = new HashSet<>();
        for(Floor floor: floors){
            if(floor.countOfPersonsSpaces(person)!=0) forReturn.add(floor);
        }
        return forReturn;
    }

    @Override
    public Iterator<Floor> iterator() {
        return new FloorIterator(getFloors());
    }

    private class FloorIterator implements Iterator<Floor>{
        private LinkedList<Floor> floors;

        public FloorIterator(Floor[] floors){
            this.floors = new LinkedList<Floor>(floors);
        }

        @Override
        public boolean hasNext() {
            return floors.getSize()>0;
        }

        @Override
        public Floor next() {
            return floors.get(0);
        }

        @Override
        public void remove() {
            floors.delete(0);
        }
    }
}
