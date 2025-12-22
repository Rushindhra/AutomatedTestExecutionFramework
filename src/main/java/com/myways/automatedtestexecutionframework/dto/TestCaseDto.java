package com.myways.automatedtestexecutionframework.dto;

import lombok.Data;

@Data
public class TestCaseDto {
    public String testName;
    public String testType; // API or WEB
    public String framework;
    public String endpoint;
    public String method;
    public String description;
}
