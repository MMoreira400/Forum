package com.forun.Forum.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

public class ErrorHandlerGeneric {
    private HttpStatus Status = HttpStatus.BAD_REQUEST;
    private Date DateTimestamp = new Date();
    private URI Uri;
    private HttpMethod Method;
    private String Message;
}
