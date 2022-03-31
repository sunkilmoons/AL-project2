public class Query {

    private int storeCount;
    private double latitude;
    private double longitude;

    public Query(int storeCount, double latitude, double longitude) {
        this.storeCount = storeCount;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
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
