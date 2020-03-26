package Utils;

import Exceptions.RegistrationNumberFormatException;
import java.util.regex.Pattern;

public class Utils {
    //Ебать, спросить почему эта хуета не фурычит
    //[ABEKMHOPCTYX][0-9]{3}[ABEKMHOPCTYX]{2}[0-9]{2,3}
    public static String checkRegNumberFormat(String regNumber){
        if(!Pattern.matches("[ABEKMHOPCTYX][0-9]{3}[ABEKMHOPCTYX]{2}[0-9]{2,3}", regNumber)) throw new RegistrationNumberFormatException();
        return regNumber;
    }
}
