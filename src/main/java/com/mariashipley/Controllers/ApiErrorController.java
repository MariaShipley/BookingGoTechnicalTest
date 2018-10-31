package com.mariashipley.Controllers;

import com.mariashipley.Models.ErrorJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class ApiErrorController implements ErrorController
{
    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ErrorJson> error(WebRequest request, HttpServletResponse response)
    {
        return ResponseEntity.status(response.getStatus())
                .body(new ErrorJson(response.getStatus(), getErrorAttributes(request)));
    }

    private Map<String, Object> getErrorAttributes(WebRequest request)
    {
        return errorAttributes.getErrorAttributes(request, false);
    }

    @Override
    public String getErrorPath()
    {
        return PATH;
    }


}
