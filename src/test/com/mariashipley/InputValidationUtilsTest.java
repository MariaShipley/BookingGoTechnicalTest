package test.com.mariashipley;

import org.junit.jupiter.api.Test;

import static main.com.mariashipley.InputValidationUtils.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputValidationUtilsTest
{
    @Test
    void isPositiveInteger_PositiveInteger_True()
    {
        assertTrue(isPositiveInteger("2"));
    }

    @Test
    void isPositiveInteger_NegativeInteger_False()
    {
        assertFalse(isPositiveInteger("-2"));
    }

    @Test
    void isPositiveInteger_NotInteger_False()
    {
        assertFalse(isPositiveInteger("string"));
    }

    @Test
    void isDouble_Double_True()
    {
        assertTrue(isDouble("1.2"));
    }

    @Test
    void isDouble_NotDouble_False()
    {
        assertFalse(isDouble("string"));
    }

    @Test
    void isAcceptableNumberOfArguments_CorrectNumOfArguments_True()
    {
        String[] fourArgs = new String[4];
        assertTrue(isAcceptableNumberOfArguments(fourArgs));
    }

    @Test
    void isAcceptableNumberOfArguments_TooFewArguments_False()
    {
        String[] threeArgs = new String[3];
        assertFalse(isAcceptableNumberOfArguments(threeArgs));
    }

    @Test
    void isAcceptableNumberOfArguments_TooManyArguments_False()
    {
        String[] sixArgs = new String[6];
        assertFalse(isAcceptableNumberOfArguments(sixArgs));
    }
}
