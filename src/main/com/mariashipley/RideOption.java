package main.com.mariashipley;

import com.google.gson.annotations.SerializedName;

public class RideOption implements Comparable<RideOption>
{
    @SerializedName("car_type")
    private String carType;

    @SerializedName("price")
    private int price;

    /**
     * Constructs a RideOption object that comprises of a car type and its price
     * @param carType The type of car
     * @param price The price of the car
     */
    public RideOption(String carType, int price)
    {
        this.carType = carType;
        this.price = price;
    }

    @Override
    public String toString()
    {
        return carType + " - " + price;
    }

    @Override
    public int compareTo(RideOption rideOption)
    {
        return Integer.compare(this.price, rideOption.price);
    }
}
