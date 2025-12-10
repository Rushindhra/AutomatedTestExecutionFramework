
package com.myways.automatedtestexecutionframework.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleRequest {
    @Setter
    private String suiteName;
    @Setter
    private String triggeredBy;
    @Setter
    private String executionMode; // PARALLEL or SEQUENTIAL

    // accept both "tests" and "testCaseIds" from JSON
    @JsonAlias({"tests", "testCaseIds"})
    private List<Long> testCaseIds = new ArrayList<>();

    public ScheduleRequest() {}

    public void setTestCaseIds(List<Long> testCaseIds) {
        this.testCaseIds = testCaseIds == null ? new ArrayList<>() : testCaseIds;
    }
}
