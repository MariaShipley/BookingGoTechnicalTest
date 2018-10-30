package com.mariashipley;

class Coordinate
{
    private double latitude;
    private double longitude;

    /**
     * Constructs a Coordinate object which has a latitude and a longitude
     * @param latitude latitude of coordinate
     * @param longitude longitude of coordinate
     */
    Coordinate(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the latitude of Coordinate object
     * @return latitude of coordinate
     */
    double getLatitude()
    {
        return latitude;
    }

    /**
     * Gets the longitude of Coordinate object
     * @return longitude of coordinate
     */
    double getLongitude()
    {
        return longitude;
    }
}
