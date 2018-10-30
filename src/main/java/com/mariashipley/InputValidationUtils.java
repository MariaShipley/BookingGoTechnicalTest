package com.mariashipley;

public class InputValidationUtils
{
    /**
     * Checks if a given string can be converted to a double.
     * @param string The string to be converted.
     * @return true if the string can be converted into a double, otherwise false.
     */
    public static boolean isDouble(String string)
    {
        try
        {
            Double.parseDouble(string);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Checks if a given string can be converted to a positive integer.
     * @param string The string to be converted.
     * @return true if the string can be converted into a positive integer, otherwise false.
     */
    public static boolean isPositiveInteger(String string)
    {
        try
        {
            Integer num = Integer.parseInt(string);
            return num > 0;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Checks if the array of commandline arguments has length 4 or 5, the acceptable number of parameters for this app.
     * @param args The array of commandline arguments
     * @return true if the number of arguments is 4 or 5, otherwise false.
     */
    public static boolean isAcceptableNumberOfArguments(String[] args)
    {
        if (args.length < 4 || args.length > 5)
        {
            return false;
        }

        return true;
    }

    // does this method need unit tests?
    /**
     * Checks if the commandline arguments are valid for this application
     * @param args The array of commandline arguments
     * @return true if the arguments are valid, otherwise false.
     */
    public static boolean isInputValid(String args[])
    {
        if (!isAcceptableNumberOfArguments(args))
        {
            return false;
        }

        if (!(isDouble(args[0]) &&
                isDouble(args[1]) &&
                isDouble(args[2]) &&
                isDouble(args[3])))
        {
            return false;
        }

        if (args.length == 5 && !isPositiveInteger(args[4]))
        {
            return false;
        }

        return true;
    }

    /**
     * Prints a help message to the commandline
     */
    public static void printExceptionMessage()
    {
        System.out.println("The arguments entered were invalid.");
        System.out.println("Provide arguments in the format:");
        System.out.println("{pick-up latitude} {pick-up longitude} {drop-off latitude} {drop-off longitude} {number of passengers}");
        System.out.println("e.g. 4.321 2.468 5.678 1.357 5");
        System.out.println("The final argument, number of passengers, is optional.");
    }
}
