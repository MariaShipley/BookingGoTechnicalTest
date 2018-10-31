package com.mariashipley.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse
{
    @SerializedName("supplier_id")
    private String supplierId;

    @SerializedName("options")
    private List<RideOption> rideOptions;

    public String getSupplierId()
    {
        return this.supplierId;
    }

    public List<RideOption> getRideOptions()
    {
        return this.rideOptions;
    }
}

