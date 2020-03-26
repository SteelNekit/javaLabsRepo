import Utils.Utils;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Parking {
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
        trim();
        Floor[] buf = new Floor[size];
        for(int i = 0; i<size;i++){
            buf[i] = floors[i];
        }
        return buf;
    }

    public Floor[] getSortedFloors(){
        Floor[] buf = getFloors();
        for(int i = 0; i<buf.length;i++){
            for(int j = 0; j<buf.length-1;j++){
                if(buf[j].getSize()>buf[j+1].getSize()){
                    Floor anotherBuf = buf[j];
                    buf[j] = buf[j+1];
                    buf[j+1] = anotherBuf;
                }
            }
        }
        return buf;
    }

    public Vehicle[] getVehicles(){
        int vehiclesCount = 0;
        for(Floor floor: floors){
            vehiclesCount+= floor.getVehicles().length;
        }
        Vehicle[] forReturn = new Vehicle[vehiclesCount];
        for(Floor floor: floors){
            Tools.wiseAdd(floor.getVehicles(),forReturn);
        }
        return forReturn;
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
            floors[i].delete(stateNumber);
        }
        return forReturn;
    }

    public int getCountOfFreeSpaces(){
        int count = 0;
        for(Floor floor: floors){
            count+=floor.getFreeSpaces().length;
        }
        return count;
    }

    public int getCountOfTypedVehicles(VehicleTypes type){
        Objects.requireNonNull(type);
        int count = 0;
        for(Floor floor: floors){
            count+=floor.getTypesSpaces(type).length;
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

    public Floor[] getFloorWithPersonsSpace(Person person){
        Objects.requireNonNull(person);
        ArrayList<Floor> bufList = new ArrayList<>();
        Floor[] bufArray = getFloors();
        for(int i = 0; i<bufArray.length;i++){
            if(bufArray[i].countOfPersonsSpaces(person)!=0) bufList.add(bufArray[i]);
        }
        if(bufList.size()==0) return null;
        else{
            Floor[] forReturn = new Floor[bufList.size()];
            for(int i = 0; i<forReturn.length;i++){
                forReturn[i] = bufList.get(i);
            }
            return forReturn;
        }
    }
}