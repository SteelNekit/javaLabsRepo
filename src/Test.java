import LinkedList.LinkedList;
import Utils.Utils;

public class Test {
    public static void lab1tests(){

    }

    public static void TypesTest(){
        Person person1 = new Person("Никита","Чекрыгин");
        Person person2 = new Person("Максим","Картамышев");
        Person person3 = new Person("Владимир","Сальников");

        Vehicle vehicle1 = new Vehicle("1lab45","Nikus","turboXXL",VehicleTypes.CAR);
        Vehicle vehicle2 = new Vehicle("1lab23","Maksimum","jam",VehicleTypes.CROSSOVER);
        Vehicle vehicle3 = new Vehicle("1lab67","Vidos","vim",VehicleTypes.CAR);
    }

    public static void listTest(){
        LinkedList<String> list = new LinkedList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(list.toString());
        list.set("|",1);
        System.out.println(list.getSize());
        System.out.println(list.toString());
    }

    public static void cloneTest() throws CloneNotSupportedException {
        Person person1 = new Person("Никита","Чекрыгин");
        System.out.println(person1.hashCode());
    }

    public static void regexTest(){
        System.out.println(Utils.checkRegNumberFormat("A111AA111"));
    }
}
