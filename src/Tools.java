public class Tools {
    public static void wiseAdd(Vehicle car, Vehicle[] cars){
        for(int i = 0; i<cars.length;i++){
            if(cars[i]==null){
                cars[i] = car;
                return;
            }
        }
    }

    public static void wiseAdd(Vehicle[] smallerArray, Vehicle[] bigArray){
        for(int i = 0; i<smallerArray.length;i++){
            wiseAdd(smallerArray[i],bigArray);
        }
    }
}
