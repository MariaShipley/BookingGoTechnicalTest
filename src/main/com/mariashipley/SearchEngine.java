package main.com.mariashipley;

public class SearchEngine
{
    private static final String DAVE_TAXI_API = "https://techtest.rideways.com/dave";

    public static String buildQuery(Coordinate pickUpLocation, Coordinate dropOffLocation)
    {
        StringBuilder query = new StringBuilder(DAVE_TAXI_API);
        query.append("?pickup=");
        query.append(pickUpLocation.getLatitude());
        query.append(",");
        query.append(pickUpLocation.getLongitude());
        query.append("&dropoff=");
        query.append(dropOffLocation.getLatitude());
        query.append(",");
        query.append(dropOffLocation.getLongitude());

        return query.toString();
    }
}
