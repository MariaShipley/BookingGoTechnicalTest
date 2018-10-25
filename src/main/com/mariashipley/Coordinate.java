package main.com.mariashipley;

public class Coordinate
{
    private double latitude;
    private double longitude;

    /**
     * Constructs a Coordinate object which has a latitude and a longitude
     * @param latitude
     * @param longitude
     */
    public Coordinate(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the latitude of Coordinate object
     * @return latitude of coordinate
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     * Gets the longitude of Coordinate object
     * @return longitude of coordinate
     */
    public double getLongitude()
    {
        return longitude;
    }
}
