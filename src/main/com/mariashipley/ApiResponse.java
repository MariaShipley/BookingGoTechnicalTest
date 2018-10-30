package main.com.mariashipley;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ApiResponse
{
    @SerializedName("supplier_id")
    String supplierId;

    @SerializedName("options")
    List<RideOption> rideOptions;
}

