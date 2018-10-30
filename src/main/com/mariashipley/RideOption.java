package main.com.mariashipley;

import com.google.gson.annotations.SerializedName;

public class RideOption implements Comparable<RideOption>
{
    @SerializedName("car_type")
    private String carType;

    @SerializedName("price")
    private int price;

    private String supplier;

    /**
     * Constructs a RideOption object that comprises of a car type, its price and a supplier
     * @param carType The type of car
     * @param price The price of the car
     */
    public RideOption(String carType, int price)
    {
        this.carType = carType;
        this.price = price;
    }

    /**
     * Constructs a RideOption object that comprises of a car type, its price and a supplier
     * @param carType The type of car
     * @param price The price of the car
     * @param supplier The name of the car supplier
     */
    public RideOption(String carType, int price, String supplier)
    {
        this.carType = carType;
        this.price = price;
        this.supplier = supplier;
    }

    @Override
    public String toString()
    {
        if (supplier == null || supplier.isEmpty())
        {
            return getBasicInfoString();
        }
        else
        {
            return carType + " - " + supplier + " - " + price;
        }

    }

    @Override
    public int compareTo(RideOption rideOption)
    {
        return Integer.compare(this.price, rideOption.price);
    }

    public String getCarType()
    {
        return this.carType;
    }

    public int getPrice()
    {
        return this.price;
    }

    public String getSupplier()
    {
        return this.supplier;
    }

    public void setSupplier(String supplier)
    {
        this.supplier = supplier;
    }

    public String getBasicInfoString()
    {
        return carType + " - " + price;
    }
}
