package nadia.com.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngarcia on 5/2/15.
 */
public class Anhos {
    private static List<String> anhos = null;

    public static List<String> getAnhos(){
        if(anhos == null) {
            anhos = new ArrayList<>();
            for (int i = 1900; i <= 2015; i++) {
                anhos.add(Integer.toString(i));
            }
        }
        return anhos;
    }

}
