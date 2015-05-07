package nadia.com.user;

/**
 * Created by ngarcia on 5/2/15.
 */
public class DatoResponse {
    String geoplugin_place;
    String geoplugin_countryCode;
    String geoplugin_region;
    String geoplugin_regionAbbreviated;
    String geoplugin_latitude;
    String geoplugin_longitude;
    String geoplugin_distanceMiles;
    String geoplugin_distanceKilometers;

    public DatoResponse() {
        geoplugin_place = "";
        geoplugin_countryCode = "";
        geoplugin_region = "";
        geoplugin_regionAbbreviated = "";
        geoplugin_latitude = "";
        geoplugin_longitude = "";
        geoplugin_distanceMiles = "";
        geoplugin_distanceKilometers = "";
    }

    public String getGeoplugin_place() {
        return geoplugin_place;
    }

    public void setGeoplugin_place(String geoplugin_place) {
        this.geoplugin_place = geoplugin_place;
    }

    public String getGeoplugin_countryCode() {
        return geoplugin_countryCode;
    }

    public void setGeoplugin_countryCode(String geoplugin_countryCode) {
        this.geoplugin_countryCode = geoplugin_countryCode;
    }

    public String getGeoplugin_region() {
        return geoplugin_region;
    }

    public void setGeoplugin_region(String geoplugin_region) {
        this.geoplugin_region = geoplugin_region;
    }

    public String getGeoplugin_regionAbbreviated() {
        return geoplugin_regionAbbreviated;
    }

    public void setGeoplugin_regionAbbreviated(String geoplugin_regionAbbreviated) {
        this.geoplugin_regionAbbreviated = geoplugin_regionAbbreviated;
    }

    public String getGeoplugin_latitude() {
        return geoplugin_latitude;
    }

    public void setGeoplugin_latitude(String geoplugin_latitude) {
        this.geoplugin_latitude = geoplugin_latitude;
    }

    public String getGeoplugin_longitude() {
        return geoplugin_longitude;
    }

    public void setGeoplugin_longitude(String geoplugin_longitude) {
        this.geoplugin_longitude = geoplugin_longitude;
    }

    public String getGeoplugin_distanceMiles() {
        return geoplugin_distanceMiles;
    }

    public void setGeoplugin_distanceMiles(String geoplugin_distanceMiles) {
        this.geoplugin_distanceMiles = geoplugin_distanceMiles;
    }

    public String getGeoplugin_distanceKilometers() {
        return geoplugin_distanceKilometers;
    }

    public void setGeoplugin_distanceKilometers(String geoplugin_distanceKilometers) {
        this.geoplugin_distanceKilometers = geoplugin_distanceKilometers;
    }
}
