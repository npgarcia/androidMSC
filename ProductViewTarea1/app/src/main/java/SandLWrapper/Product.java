package SandLWrapper;

import nadia.com.productviewtarea1.R;

/**
 * Created by ngarcia on 2/19/15.
 */
public class Product {
    public int productImage;
    public String description;
    public float rate;

    private String standard_shipping = "Shipping and Delivery\n"+"This item ships via FedEx Ground and arrives within 5-7 business days " +
            "of order receipt.\n\n" +
            "We want you to be thrilled with your purchase. If you're not completely satisfied, we’ll gladly " +
            "offer you an exchange or refund of the merchandise price within 30 days of receipt of product.\n\n" +
            "If you have questions about shipping, delivery or returns, please call our Customer Care team at " +
            "866.597.2742 or email us at customercare@serenaandlily.com.";

    public Product(String name) {
        String products[] = ProductWrapper.getProducts();
        if (name.equals(products[0])) {
            productImage = R.drawable.fouta_hero_496_cat;
            description = "From beach to bath to bed: our fabulous Fouta keeps changing its stripes. " +
                    "Here, wide rugby meets skinny pinstripe for a playful contrast on soft, light percale. " +
                    "The striped design has been woven (not just printed) for beautiful effect. A good dose " +
                    "of white gives this a gorgeous, sea-washed look.\n"+
                    "Specs and Care: \n"+
                    "\t00% yarn-dyed cotton percale.\n" +
                    "\t300-thread-count.\n" +
                    "\tMachine wash.\n" +
                    "\tMade in Portugal.\n" +
                    "\tInserts sold separately.\n" +
                    "Duvet Cover\n" +
                    "\tKnife edge and button closure.\n" +
                    "\tInsert ties.\n" +
                    "\tTwin: 68\" x 86\".\n" +
                    "\tFull/Queen: 92\" x 88\".\n" +
                    "\tKing/Cal King: 108\" x 92\".\n" +
                    "Shams\n" +
                    "\tKnife edge and envelope closure.\n" +
                    "\tStandard: 21\" x 27\".\n" +
                    "\tKing: 21\" x 38\".\n" +
                    "\tEuro: 26\"SQ.\n"+
                    standard_shipping;
            rate = 4.2f;
        }else if (name.equals(products[1])) {
            productImage = R.drawable.fouta_hero_427_main_cat;
            description = "From beach to bath to bed: our fabulous Fouta keeps changing its stripes. " +
                    "Here, wide rugby meets skinny pinstripe for a playful contrast on soft, light percale. " +
                    "The striped design has been woven (not just printed) for beautiful effect. A good dose " +
                    "of white gives this a gorgeous, sea-washed look.\n"+
                    "Specs and Care: \n"+
                    "\t00% yarn-dyed cotton percale.\n" +
                    "\t300-thread-count.\n" +
                    "\tMachine wash.\n" +
                    "\tMade in Portugal.\n" +
                    "\tInserts sold separately.\n" +
                    "Duvet Cover\n" +
                    "\tKnife edge and button closure.\n" +
                    "\tInsert ties.\n" +
                    "\tTwin: 68\" x 86\".\n" +
                    "\tFull/Queen: 92\" x 88\".\n" +
                    "\tKing/Cal King: 108\" x 92\".\n" +
                    "Shams\n" +
                    "\tKnife edge and envelope closure.\n" +
                    "\tStandard: 21\" x 27\".\n" +
                    "\tKing: 21\" x 38\".\n" +
                    "\tEuro: 26\"SQ.\n"+
                    standard_shipping;
            rate = 3.8f;
        }else if (name.equals(products[2])) {
            productImage = R.drawable.palmettobdrm_mainbed_125_cat;
            description = "A classic from our pattern library, Serena’s version of an Indian damask makes a bedroom " +
                    "instantly bold. We love the way the loose, painterly lines and vibrant colors strike the perfect " +
                    "balance between preppy and bohemian. \n"+
                    "Specs and Care: \n"+
                    "\t100% cotton sateen.\n" +
                    "\t300-thread-count.\n" +
                    "\tMachine wash.\n" +
                    "\tMade in Portugal.\n" +
                    "\tInserts sold separately.\n" +
                    "Duvet Cover\n" +
                    "\tKnife edge and fabric-covered button closure.\n" +
                    "\tInsert ties.\n" +
                    "\tTwin: 68\" x 86\".\n" +
                    "\tFull/Queen: 92\" x 88\".\n" +
                    "\tKing/Cal King: 108\" x 92\".\n" +
                    "Shams\n" +
                    "\tKnife edge and envelope closure.\n" +
                    "\tEuro: 26\"SQ.\n"+
                    standard_shipping;
            rate = 2.9f;
        }

    }
}
