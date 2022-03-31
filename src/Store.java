import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Store {

    private static final double radiusOfEarthInMiles = 3958.8;

    private int id;
    private String address;
    private String city;
    private String state;
    private String postCode; // post code may contain hyphens
    private double latitude;
    private double longitude;

    private double distanceFromQuery = 0;

    public Store(int id, String address, String city, String state, String postCode, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("Store #%d. %s, %s, %s, %s. - %,.2f miles.", id, address, city, state, postCode, distanceFromQuery);
    }

    public static Store fromTokens(String[] tokens) throws IllegalArgumentException {
        if (tokens.length < 7) throw new IllegalArgumentException("Expecting 7 tokens to create a store object");

        int id = Integer.parseInt(tokens[0]);
        double lat = Double.parseDouble(tokens[5]);
        double longitude = Double.parseDouble(tokens[6]);

        // redact extra quotation marks from address
        return new Store(id, tokens[1].replace("\"", ""), tokens[2], tokens[3], tokens[4], lat, longitude);
    }

    private String idAndDistance() {
        return String.format("#%d = %,.2f", id, distanceFromQuery);
    }

    public static String listIdsWithDistance(List<Store> stores) {
        return stores.stream().map(Store::idAndDistance).collect(Collectors.joining(" | "));
    }

    public static List<Store> sortedByDistanceFromQuery(List<Store> stores) {
        return stores.stream().sorted(Comparator.comparingDouble(Store::getDistanceFromQuery)).collect(Collectors.toList());
    }

    public void computeDistanceFromQuery(double otherLat, double otherLong) {
        double lat1 = Math.toRadians(latitude);
        double lat2 = Math.toRadians(otherLat);

        double long1 = Math.toRadians(longitude);
        double long2 = Math.toRadians(otherLong);

        double a = Math.pow(Math.sin(lat2-lat1)/2, 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(long2-long1)/2, 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1- a));
        distanceFromQuery = radiusOfEarthInMiles * c;
    }

    public double getDistanceFromQuery() {
        return distanceFromQuery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
