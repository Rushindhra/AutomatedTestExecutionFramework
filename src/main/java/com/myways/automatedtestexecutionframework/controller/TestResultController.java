package com.myways.automatedtestexecutionframework.controller;

import com.myways.automatedtestexecutionframework.entity.TestResult;
import com.myways.automatedtestexecutionframework.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/test-results")
public class TestResultController {

    @Autowired
    private TestResultRepository testResultRepository;

    // POST API to insert test_results
    @PostMapping
    public ResponseEntity<?> saveTestResults(@RequestBody List<TestResult> results) {

        results.forEach(r -> {
            if (r.getExecutedAt() == null) {
                r.setExecutedAt(LocalDateTime.now());
            }
        });

        return ResponseEntity.ok(testResultRepository.saveAll(results));
    }
}
