public class Store {

    private static final double radiusOfEarthInMiles = 3958.8;

    private int id;
    private String address;
    private String city;
    private String state;
    private int postCode;
    private double latitude;
    private double longitude;

    private double distanceFromQuery;

    public Store(int id, String address, String city, String state, int postCode, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void computeDistanceFromQuery(int otherLat, int otherLong) {
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

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
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
