package SandLWrapper;

/**
 * Created by ngarcia on 2/19/15.
 */
public class ProductWrapper {

    protected static String[] products = {"Fouta Yarn-Dyed Stripe (Green)",
            "Fouta Yarn-Dyed Stripe (Blue)", "Palmetto",
            "Border Frame"};

    protected ProductWrapper() {

    }

    public static String[] getProducts(){
        return products;
    }

    public static String getProductName(int index){
        return products[index-1];
    }
}
