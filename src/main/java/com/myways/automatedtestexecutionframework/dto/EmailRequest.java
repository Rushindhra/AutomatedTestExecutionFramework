package com.myways.automatedtestexecutionframework.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailRequest {

    private String to;            // optional
    private String subject;       // optional
    private String message;       // required
    private String attachmentPath; // optional

    public EmailRequest() {}
}
