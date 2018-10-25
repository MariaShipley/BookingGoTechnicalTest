package main.com.mariashipley;

import static main.com.mariashipley.InputValidationUtils.isInputValid;
import static main.com.mariashipley.InputValidationUtils.printExceptionMessage;

public class Part1A
{
    public static void main(String[] args)
    {
        if (!isInputValid(args))
        {
            printExceptionMessage();
            System.exit(0);
        }

        Coordinate pickUpLocation = new Coordinate(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        Coordinate dropOffLocation = new Coordinate(Double.parseDouble(args[2]), Double.parseDouble(args[3]));

        if (args.length == 5)
        {
            int numPassengers = Integer.parseInt(args[4]);
        }
    }
}
