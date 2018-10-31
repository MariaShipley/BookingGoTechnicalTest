package com.mariashipley.Models;

import java.util.Map;

public class ErrorJson
{
    public String timestamp;
    public int status;
    public String error;
    public String message;
    public String path;

    public ErrorJson(int status, Map<String, Object> errorInfo)
    {
        this.status = status;
        this.timestamp = errorInfo.get("timestamp").toString();
        this.error = (String) errorInfo.get("error");
        this.message = (String) errorInfo.get("message");
        this.path = (String) errorInfo.get("path");
    }
}
