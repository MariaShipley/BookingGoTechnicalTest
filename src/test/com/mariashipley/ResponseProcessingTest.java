package test.com.mariashipley;

import main.com.mariashipley.RideOption;

import java.util.List;

public class ResponseProcessingTest
{
    /**
     * Searches all suppliers for car options for the given locations
     * @param supplier supplier of ride
     * @param ridesOptions list of ride options
     * @return list of ride options with the supplier set to the input supplier
     */
    private static List<RideOption> setSupplierOnList(String supplier, List<RideOption> ridesOptions)
    {
        if (ridesOptions == null)
        {
            return null;
        }

        for (RideOption ride : ridesOptions)
        {
            ride.setSupplier(supplier);
        }

        return ridesOptions;
    }
}
