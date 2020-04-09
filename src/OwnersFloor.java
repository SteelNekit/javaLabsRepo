import Exceptions.NoRentedSpaceException;
import LinkedList.LinkedList;
import Utils.Utils;

import java.time.LocalDate;
import java.util.*;

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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for(Space space:this){
            if(space.equals((Space)o)) return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        trim();
        for(int i = 0; i<size;i++){
            if(spaces[i].equals((Space)o)){
                spaces[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Space> collection) {
        Iterator it =  collection.iterator();
        /*Кстати пиздатая хурма с этим итератором
        тут не форыч потому что мне охота потестить эту хурму*/
        while(it.hasNext()){
            this.add((Space)it.next());
        }
        return true;//Просто хз когда нужно возвращать ложь, по этому буду честен
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Iterator it =  collection.iterator();
        boolean flag = false;
        while(it.hasNext()){
            flag |= this.remove(it.next());
        }
        return flag;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        Iterator it =  collection.iterator();
        boolean flag = true;
        while(it.hasNext()){
            flag &= this.contains(it.next());
        }
        return flag;
    }

    @Override
    public void clear() {
        spaces = new Space[16];
        size = 0;
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

    public Space remove(int index){
        Space forReturn = spaces[index];
        spaces[index] = null;
        size--;
        return forReturn;
    }

    public int size() {
        return size;
    }

    //todo Спросить что эта хуета должна делать
    @Override
    public <T> T[] toArray(T[] ts) {
        Space[] buf = toArray();
        int startIndex = 0;
        for(int i = 0; i<ts.length;i++){
            if(ts[i]==null){
                startIndex = i;
                break;
            }
        }
        for(int i = 0;i<ts.length;i++){
            ts[i+startIndex] = (T) buf[i];
        }
        return ts;
        //Сделал так чтобы выходные данные можно было получить и из параметров из на выходе метода
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        trim();
        boolean flag = false;
        for(int i = 0; i<size;i++){
            if(!collection.contains(spaces[i])){
                remove(i);
                flag |= true;
            }
        }
        return flag;
    }

    @Override
    public Space[] toArray(){
        Space[] forReturn = new Space[size];
        trim();
        for(int i = 0; i<forReturn.length;i++){
            forReturn[i] = spaces[i];
        }
        return forReturn;
    }

    public Collection<Vehicle> getVehicles(){
        Space[] spaces = toArray();
        ArrayList<Vehicle> forReturn = new ArrayList<>();
        for(Space space: spaces){
            forReturn.add(space.getVehicle());
        }
        return forReturn;
    }

    @Override
    public List<Space> getTypesSpaces(VehicleTypes type) {
        Objects.requireNonNull(type);
        ArrayList<Space> forReturn = new ArrayList<Space>();
        for(Space space:spaces){
            if(space.getVehicle().getType() == type){
                forReturn.add(space);
            }
        }
        return forReturn;
    }

    @Override
    public Deque<Space> getFreeSpaces() {
        java.util.LinkedList<Space> forReturn = new java.util.LinkedList<Space>();
        int i = 0;
        for(Space space: spaces){
            if(space == null || space.isEmpty()) forReturn.add(space);
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
        boolean answer = that.size() == this.size();
        if(answer){
            for(int i = 0; i< size(); i++){
                answer &= (that.toArray()[i].equals(this.toArray()[i]));
            }
        }
        return answer;
    }

    @Override
    public int hashCode() {
        int hash = 71 * size();
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
        for(Space space:this){
            if(space.getPerson().equals(person)) count++;
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
        return spaces.length-size;
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
        for(Space space:this){
            if(space instanceof RentedSpace){
                RentedSpace rs = (RentedSpace) space;
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
        for(Space space:this){
            if(space instanceof RentedSpace){
                RentedSpace rs = (RentedSpace) space;
                if(rs.getRentEndsDate().equals(date)) return rs;
            }
        }
        return null;
    }

    private void checkRentedSpaces() throws NoRentedSpaceException{
        int rentedSpaceCount = 0;
        for(Space space:this){
            if(space instanceof RentedSpace) rentedSpaceCount++;
        }
        if(rentedSpaceCount==0) throw new NoRentedSpaceException();
    }

    @Override
    public int compareTo(Floor floor) {
        return this.size()-floor.size();
    }

    @Override
    public Iterator<Space> iterator() {
        return new SpaceIterator(toArray());
    }

    private class SpaceIterator implements Iterator<Space>{
        private LinkedList<Space> spaces;

        public SpaceIterator(Space[] spaces){
            this.spaces = new LinkedList<Space>(spaces);
        }

        @Override
        public boolean hasNext() {
            return spaces.getSize()>0;
        }

        @Override
        public Space next() throws NoSuchElementException{
            if(spaces.getSize()<=0) throw new NoSuchElementException("No such elements");
            else{
                return spaces.get(0);
            }
        }

        @Override
        public void remove() {
            spaces.delete(0);
        }
    }
}
