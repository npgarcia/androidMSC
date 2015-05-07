package nadia.com.user;

import java.util.Date;
import java.util.UUID;

public class User {
    private UUID userId;
    private String userName;
    private String sex;
    private String geoLocation_country;
    private String geoLocation_region;
    private String geoLocation_place;
    private int yearOfBirth;

    public User() {
        yearOfBirth = -1;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSex(boolean sex) {
        if(sex)
            this.sex = "masculino";
        else
            this.sex = "femenino";
    }

    public String getGeoLocation_country() {
        return geoLocation_country;
    }

    public void setGeoLocation_country(String geoLocation_country) {
        this.geoLocation_country = geoLocation_country;
    }

    public String getGeoLocation_region() {
        return geoLocation_region;
    }

    public void setGeoLocation_region(String geoLocation_region) {
        this.geoLocation_region = geoLocation_region;
    }

    public String getGeoLocation_place() {
        return geoLocation_place;
    }

    public void setGeoLocation_place(String geoLocation_place) {
        this.geoLocation_place = geoLocation_place;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public static UUID uuidForDate(Date d) {
        final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;

        long origTime = d.getTime();
        long time = origTime * 10000 + NUM_100NS_INTERVALS_SINCE_UUID_EPOCH;
        long timeLow = time & 0xffffffffL;
        long timeMid = time & 0xffff00000000L;
        long timeHi = time & 0xfff000000000000L;
        long upperLong = (timeLow << 32) | (timeMid >> 16) | (1 << 12)
                | (timeHi >> 48);
        return new UUID(upperLong, 0xC000000000000000L);
    }

    public String toString() {
        String result;
        if (userId == null) {
            userId = UUID.randomUUID();
        }

        boolean isMale;
        if(sex.equals("femenino"))
            isMale = false;
        else
            isMale = true;

        result = userId.toString() + "," + "'" + userName + "'," + isMale + ","
                + "{'country':'" + geoLocation_country + "',"
                + "'region':'" + geoLocation_region + "'," + "'place':'"
                + geoLocation_place + "'}," + yearOfBirth;
        return result;
    }
}