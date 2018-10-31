package com.mariashipley.Models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiInputException extends RuntimeException
{
    public ApiInputException(String message)
    {
        super(message);
    }
}
