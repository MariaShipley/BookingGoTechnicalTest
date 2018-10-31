package com.mariashipley.Controllers;

import com.mariashipley.InputValidationUtils;
import com.mariashipley.SearchEngine;
import com.mariashipley.Models.ApiInputException;
import com.mariashipley.Models.Coordinate;
import com.mariashipley.Models.RideOption;

import com.google.gson.Gson;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController
{
    @RequestMapping(value = "/RidewaysTaxiApp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRideOptions(@RequestParam(value="pickup") String pickUp,
                                         @RequestParam(value="dropoff", defaultValue = "") String dropOff,
                                         @RequestParam(value="passengers", defaultValue = "1") String passengers)
    {
        if (pickUp == null || pickUp.isEmpty())
        {
            throw new ApiInputException("Required String parameter 'pickup' is not present");
        }

        if (dropOff == null || dropOff.isEmpty())
        {
            throw new ApiInputException("Required String parameter 'dropoff' is not present");
        }

        String[] splitPickUpLocation = pickUp.split(",");
        String[] splitDropOffLocation = dropOff.split(",");
        if (splitPickUpLocation.length != 2 || splitDropOffLocation.length != 2)
        {
            throw new ApiInputException("Invalid input");
        }

        String[] inputParameters = {splitPickUpLocation[0], splitPickUpLocation[1],
                                    splitDropOffLocation[0], splitDropOffLocation[1],
                                    passengers};

        if (!InputValidationUtils.isInputValid(inputParameters))
        {
            throw new ApiInputException("Invalid input");

        }

        Coordinate pickUpLocation = new Coordinate(Double.parseDouble(splitPickUpLocation[0]),
                                                   Double.parseDouble(splitPickUpLocation[1]));
        Coordinate dropOffLocation = new Coordinate(Double.parseDouble(splitDropOffLocation[0]),
                                                    Double.parseDouble(splitDropOffLocation[1]));
        int numPassengers = Integer.parseInt(passengers);

        List<RideOption> rideOptions = SearchEngine.searchAll(pickUpLocation, dropOffLocation, numPassengers);

        Gson gson = new Gson();
        return new ResponseEntity<String>(gson.toJson(rideOptions), new HttpHeaders(), HttpStatus.OK);
    }
}
