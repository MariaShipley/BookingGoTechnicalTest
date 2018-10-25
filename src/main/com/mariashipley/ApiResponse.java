package main.com.mariashipley;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse
{
    @SerializedName("supplier_id")
    private String supplierId;

    @SerializedName("options")
    public List<RideOption> rideOptions;
}

