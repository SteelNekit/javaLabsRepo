import Exceptions.NoRentedSpaceException;
import LinkedList.LinkedList;
import Utils.Utils;
import java.time.LocalDate;
import java.util.*;


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
    public int size() {
        return spaces.getSize();
    }

    @Override
    public boolean hasSpace(String stateNumber) {
        Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber));
        for(Space space:this){
            if(!space.isEmpty() && space.getVehicle().getStateNumber().equals(stateNumber)){
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

    public Space remove(int index) {
        return spaces.delete(index);
    }

    @Override
    public boolean remove(Object o) {
        if(this.contains(o)){
            spaces.delete((Space)o);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        return spaces.indexOf((Space)o)>-1;
    }

    @Override
    public void clear() {
        spaces = new LinkedList<Space>();
    }

    @Override
    public boolean isEmpty() {
        return spaces.getSize()<=0;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        Space[] buf = new Space[size()+ts.length];
        for(int i = 0;i<size();i++){
            buf[i] = spaces.get(i);
        }
        for(int i = 0;i<ts.length;i++){
            buf[size()+i] = (Space)ts[i];
        }
        return (T[]) buf;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Space space:this){
            if(!collection.contains(space)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Space> collection) {
        for(Space space:collection){
            this.add(space);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = false;
        for(Space space:this){
            if(collection.contains(space)){
                remove(space);
                flag=true;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean flag = false;
        for(int i = 0; i<size();i++){
            if(!collection.contains(spaces.get(i))){
                remove(i);
                flag = true;
            }
        }
        return flag;
    }

    public Space remove(String stateNumber) {
        Space forReturn = get(Objects.requireNonNull(Utils.checkRegNumberFormat(stateNumber)));
        for(int i = 0; i<spaces.getSize();i++){
            if(spaces.get(i).getVehicle().getStateNumber().equals(stateNumber)){
                spaces.delete(i);
                return forReturn;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean remove(Space space) {
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
    public Space[] toArray() {//В целом уёбищный метод
        Space[] forReturn = new Space[spaces.getSize()];
        for(int i = 0; i<spaces.getSize();i++){
            forReturn[i] = spaces.get(i);
        }
        return forReturn;
    }

    @Override
    public ArrayList<Vehicle> getVehicles() {
        Space[] spaces = toArray();
        ArrayList<Vehicle> forReturn = new ArrayList<>();
        for(Space space: spaces){
            forReturn.add(space.getVehicle());
        }
        return forReturn;
    }

    @Override
    public ArrayList<Space> getTypesSpaces(VehicleTypes type) {
        Objects.requireNonNull(type);
        ArrayList<Space> forReturn = new ArrayList<>();
        for(Space space:this){
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
        for(Space space: this){
            if(space == null || space.isEmpty()) forReturn.add(space);
        }
        return forReturn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rented spaces: \n");
        for(Space space:this){
            sb.append(space.toString()).append('\n');
        }
        return sb.toString();
    }

    //Сейчас смотрю на это пытаясь вспомнить логику, ебать, еслиб я это сам не писал я б не разобрался
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RentedSpacesFloor that = (RentedSpacesFloor) obj;
        boolean answer = that.size() == this.size();
        if(answer){
            for(int i = 0; i<size();i++){
                answer &= (that.toArray()[i].equals(this.toArray()[i]));
            }
        }
        return answer;
    }

    @Override
    public int hashCode() {
        int hash = 53 * size();
        for(Space space:this){
            hash*=space.hashCode();
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
        for(Space space:this){
            if(space.getPerson().equals(person)) count++;
        }
        return count;
    }

    public Object clone() throws CloneNotSupportedException{
        return new RentedSpacesFloor(toArray().clone());
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
    public SpaceIterator iterator() {
        return new SpaceIterator(this.spaces);
    }

    //Такс, тут голове немножко тяжело, уточнить
    private class SpaceIterator implements Iterator<Space>{
        LinkedList<Space> spaces;

        public SpaceIterator(LinkedList<Space> spaces){
            this.spaces = spaces;
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
