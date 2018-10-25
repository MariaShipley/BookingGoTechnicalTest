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
    }
}
